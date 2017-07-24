package com.driverhelper.helper;


import android.util.Log;
import android.widget.Toast;

import com.driverhelper.app.MyApplication;
import com.driverhelper.beans.MessageBean;
import com.driverhelper.config.Config;
import com.driverhelper.config.ConstantInfo;
import com.driverhelper.config.TcpBody;
import com.driverhelper.other.encrypt.Encrypt;
import com.driverhelper.utils.ByteUtil;
import com.jaydenxiao.common.baserx.RxBus;
import com.jaydenxiao.common.commonutils.TimeUtil;
import com.jaydenxiao.common.commonutils.ToastUitl;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.driverhelper.config.ConstantInfo.strTerminalSerial;
import static com.driverhelper.config.ConstantInfo.terminalNum;
import static com.driverhelper.config.ConstantInfo.vehicleColor;
import static com.driverhelper.config.ConstantInfo.vehicleNum;
import static com.driverhelper.config.TcpBody.MessageID.clientCommonResponse;
import static com.driverhelper.config.TcpBody.MessageID.findLocatInfoRequest;
import static com.driverhelper.config.TcpBody.MessageID.locationInfoUpdata;
import static com.driverhelper.config.TcpBody.MessageID.register;
import static com.driverhelper.config.TcpBody.MessageID.updataCoachLogin;
import static com.driverhelper.config.TcpBody.VERSION_CODE;
import static com.driverhelper.utils.ByteUtil.int2Bytes;

/**
 * Created by Administrator on 2017/6/7.
 */

public class BodyHelper {

    /***
     * 创建消息头的属性
     *
     * @param isDivision
     *            是否分包
     * @param encryption
     *            加密方式 RSA:1 无:0
     * @param length
     *            长度
     * @return
     */
    static private byte[] makeHeadAttribute(boolean isDivision, int encryption,
                                            int length) {
        String attribute = "00";
        if (isDivision) {
            attribute = attribute + "1";
        } else {
            attribute = attribute + "0";
        }
        switch (encryption) {
            case 0:
                attribute = attribute + "000";
                break;
            case 1:
                attribute = attribute + "001";
                break;
            default:
                attribute = attribute + "000";
        }
        String strLength = Integer.toBinaryString(length);
        attribute = attribute + ByteUtil.autoAddZeroByLength(strLength, 10);
        byte[] data = ByteUtil.hexString2BCD(ByteUtil.autoAddZeroByLength(
                Integer.toHexString(Integer.parseInt(attribute, 2)), 4));

        return data;
    }

    /***
     * 构造数据包的头部
     *
     * @param id
     *            消息id
     *            设备号/电话号码 11位，不足16位的左边补0
     * @return
     */
    static private byte[] makeHead(byte[] id,
                                   boolean isDivision, int encryption, int length) {
        byte[] data;
        data = ByteUtil.add(TcpBody.HEAD, VERSION_CODE);
        data = ByteUtil.add(data, id);
        data = ByteUtil.add(data,
                makeHeadAttribute(isDivision, encryption, length));
        data = ByteUtil.add(data, makePhone2BCD(ConstantInfo.terminalPhoneNumber));
        data = ByteUtil.add(data, getWaterCode());
        data = ByteUtil.add(data, getReserve());
        if (isDivision) { // 如果有分包就加这一项
            data = ByteUtil.add(data, getPackageItem(isDivision));
        }
        System.out.println("hand = ");
        ByteUtil.printHexString(data);
        return data;
    }

    /****
     * 把电话号码变成 BCD码
     *
     * @param phoneNum
     * @return
     */
    private static byte[] makePhone2BCD(String phoneNum) {
        int maxLength = 16;
        return ByteUtil.hexString2BCD(ByteUtil.autoAddZeroByLength(phoneNum,
                maxLength));
    }

    /***
     * 获取流水号
     *
     * @return
     */
    public static byte[] getWaterCode() {
        return ByteUtil.hexString2BCD(WaterCodeHelper.getWaterCode());
    }

    /******
     * 获取透传消息流水号
     * @return
     */
    public static byte[] getExCode() {
        return ByteUtil.hexString2BCD(ExCodeHelper.getExCode());
    }

    /***
     * 预留
     *
     * @return
     */
    public static byte[] getReserve() {
        return new byte[]{(byte) 0x00};
    }

    /****
     * 是否有分包项，和前面的“是否分包”用相同的参数
     *
     * @param isPackage
     * @return
     */
    public static byte[] getPackageItem(boolean isPackage) {
        if (!isPackage) {
            return new byte[0];
        } else {
            return new byte[]{(byte) 0x00, (byte) 0x01};
        }
    }

    /*************************************************************************************/
    /***
     * 各功能的组包,终端注册
     */
    public static byte[] makeRegist() {
        byte[] resultBody = ConstantInfo.province;
        resultBody = ByteUtil.add(resultBody, ConstantInfo.city); // 城市
        resultBody = ByteUtil.add(resultBody, ConstantInfo.makerID); // 制造商id
        resultBody = ByteUtil.add(resultBody, ByteUtil.str2Bcd(ByteUtil
                .autoAddZeroByLengthOnRight(ConstantInfo.strTerminalTYPE, 40))); // 终端型号
        resultBody = ByteUtil.add(resultBody, ByteUtil.str2Word(strTerminalSerial)); // 终端编号
        resultBody = ByteUtil.add(resultBody, ByteUtil.str2Word(ConstantInfo.strIMEI)); // IMEI
        resultBody = ByteUtil.add(resultBody, ByteUtil.str2Bcd(vehicleColor)); // 车身颜色
        resultBody = ByteUtil.add(resultBody, ByteUtil.str2Word(vehicleNum)); // 车牌号
        int bodyLength = resultBody.length;
        System.out.println("bodyLength = " + bodyLength);
        byte[] resultHead = makeHead(register, false, 0, bodyLength); // 包头固定

        byte[] result = ByteUtil.addXor(ByteUtil.add(resultHead, resultBody));
        result = ByteUtil.addEND(result); // 添加尾部
        result = ByteUtil.checkMark(result);

        ByteUtil.printHexString(result);
        return result;
    }

    /***
     * 终端注销
     *
     * @return
     */
    public static byte[] makeUnRegist() {
        int bodyLength = 0;
        byte[] result = makeHead(TcpBody.MessageID.unRegister, false, 0, bodyLength);
        result = ByteUtil.addXor(result);
        result = ByteUtil.addEND(result);
        result = ByteUtil.checkMark(result);
        ByteUtil.printHexString(result);
        return result;
    }

    /***
     * 终端鉴权
     */
    public static byte[] makeAuthentication() {

        long time = System.currentTimeMillis() / 1000;
        byte[] resultBody = ByteUtil.str2Bcd(ByteUtil
                .decString2hexString(time)); // 时间戳
        try {
            resultBody = ByteUtil.add(resultBody,
                    Encrypt.SHA256(terminalNum,
                            ConstantInfo.terminalCertificate,
                            ByteUtil.getString(ConstantInfo.certificatePassword),
                            time)); // 校验码
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Logger.e(e.getMessage());
        }
        int bodyLength = resultBody.length;
        byte[] resultHead = makeHead(TcpBody.MessageID.authentication, false, 0, bodyLength);
        byte[] result = ByteUtil.addXor(ByteUtil.add(resultHead, resultBody));
        result = ByteUtil.addEND(result);
        result = ByteUtil.checkMark(result);
        ByteUtil.printHexString(result);

        return result;
    }


    /**
     * B.4.2.1.1　上报教练员登录
     *
     * @param IdCard   教练员身份证号 BYTE[18]	ASCII码，不足18位前补0x00
     * @param coachnum 教练员编号 BYTE[16]	统一编号
     * @param carType  准教车型   BYTE[2]	A1\A2\A3\B1\B2\C1\C2\C3\C4\D\E\F
     * @return
     */
    public static byte[] makeCoachLogin(String IdCard, String coachnum, String carType) {
        byte[] resultBody = ByteUtil.str2Word(ByteUtil.autoAddZeroByLength(IdCard,
                18));
        resultBody = ByteUtil.add(resultBody, ByteUtil.str2Word(coachnum));
        resultBody = ByteUtil.add(resultBody, ByteUtil.str2Word(carType));
        resultBody = ByteUtil.add(resultBody, BodyHelper.makeLocationInfo("00000000",
                "40080000",
                (int) (MyApplication.getInstance().lon * Math.pow(10, 6)),
                (int) (MyApplication.getInstance().lat * Math.pow(10, 6)),
                10,
                (int) MyApplication.getInstance().speedGPS,
                (int) MyApplication.getInstance().direction,
                TimeUtil.formatData(TimeUtil.dateFormatYMDHMS_, MyApplication.getInstance().timeGPS / 1000),
                -2000, -2000, -2000, -2000));
        return buildExMsg(updataCoachLogin, 0, 1, 2, resultBody);
    }


    public static byte[] makeHeart() {
        int bodylength = 0;
        byte[] result = makeHead(TcpBody.MessageID.heart, false, 0, bodylength);
        result = ByteUtil.addXor(result);
        result = ByteUtil.addEND(result);
        result = ByteUtil.checkMark(result);
        ByteUtil.printHexString(result);

        return result;
    }

    /****
     * 客户端通用应答
     * @param para1
     * @param para2
     * @param para3
     * @return
     */
    public static byte[] makeClientCommonResponse(int para1, int para2, int para3) {
        byte[] resultBody = int2Bytes(para1, 2);
        resultBody = ByteUtil.add(resultBody, int2Bytes(para2, 2));
        resultBody = ByteUtil.add(resultBody, int2Bytes(para3, 1));

        byte[] resultHead = makeHead(clientCommonResponse, false, 0, resultBody.length); // 包头固定

        byte[] result = ByteUtil.addXor(ByteUtil.add(resultHead, resultBody));
        result = ByteUtil.addEND(result); // 添加尾部
        result = ByteUtil.checkMark(result);

        ByteUtil.printHexString(result);
        return result;
    }

    /**
     * B.3.2.3.16　位置信息汇报
     *
     * @param para1  报警标识	DWORD	报警标识位定义见表B.22
     * @param para2  状态	DWORD	状态位定义见表B.23
     * @param para3  纬度	DWORD	以度为单位的纬度值乘以10的6次方，精确到百万分之一度
     * @param para4  经度	DWORD	以度为单位的纬度值乘以10的6次方，精确到百万分之一度
     * @param para5  行驶记录速度	WORD	行驶记录功能获取的速度，1/10km/h
     * @param para6  卫星定位速度	WORD	1/10km/h
     * @param para7  方向	WORD	0-359，正北为0，顺时针
     * @param para8  时间	BCD[6]	YYMMDDhhmmss(GMT+8时间，本规范之后涉及的时间均采用此时区)
     * @param para9  里程，DWORD，1/10km，对应车上里程表读书  附加信息 >=0 ,负数就不添加
     * @param para10 油量，WORD，1/10L，对应车上油量表读书   附加信息 >=0 ,负数就不添加
     * @param para11 海拔高度，单位为m  附加信息 >=-1000 ,小于-1000不添加
     * @param para12 发动机转速，WORD  附加信息 >=0 ,负数就不添加
     * @return
     */
    public static byte[] makeLocationInfoBody(String para1, String para2, int para3, int para4, int para5, int para6, int para7, String para8, int para9, int para10, int para11, int para12) {
        byte[] resultBody = ByteUtil.hexString2BCD(para1);
        resultBody = ByteUtil.add(resultBody, ByteUtil.hexString2BCD(para2));
        resultBody = ByteUtil.add(resultBody, int2Bytes(para3, 4));
        resultBody = ByteUtil.add(resultBody, int2Bytes(para4, 4));
        resultBody = ByteUtil.add(resultBody, int2Bytes(para5, 2));
        resultBody = ByteUtil.add(resultBody, int2Bytes(para6, 2));
        resultBody = ByteUtil.add(resultBody, int2Bytes(para7, 2));
        resultBody = ByteUtil.add(resultBody, ByteUtil.str2Bcd(para8));
        if (para9 >= 0) {
            resultBody = ByteUtil.add(resultBody, int2Bytes(0x01, 1));
            resultBody = ByteUtil.add(resultBody, int2Bytes(4, 1));
            resultBody = ByteUtil.add(resultBody, int2Bytes(para9, 4));
        }
        if (para10 >= 0) {
            resultBody = ByteUtil.add(resultBody, int2Bytes(0x02, 1));
            resultBody = ByteUtil.add(resultBody, int2Bytes(2, 1));
            resultBody = ByteUtil.add(resultBody, int2Bytes(para10, 2));
        }
        if (para11 >= -1000) {
            resultBody = ByteUtil.add(resultBody, int2Bytes(0x03, 1));
            resultBody = ByteUtil.add(resultBody, int2Bytes(2, 1));
            resultBody = ByteUtil.add(resultBody, int2Bytes(para11, 2));
        }
        if (para12 >= 0) {
            resultBody = ByteUtil.add(resultBody, int2Bytes(0x04, 1));
            resultBody = ByteUtil.add(resultBody, int2Bytes(2, 1));
            resultBody = ByteUtil.add(resultBody, int2Bytes(para12, 2));
        }
//        byte[] resultHead = makeHead(locationInfoUpdata, false, 0, resultBody.length); // 包头固定

//        return sticky(resultHead, resultBody);
        return resultBody;
    }


    /**
     * B.3.2.3.16　位置信息汇报
     *
     * @param warningSignHex 报警标识	DWORD	报警标识位定义见表B.22
     * @param stateHex       状态	DWORD	状态位定义见表B.23
     * @param para3          纬度	DWORD	以度为单位的纬度值乘以10的6次方，精确到百万分之一度
     * @param para4          经度	DWORD	以度为单位的纬度值乘以10的6次方，精确到百万分之一度
     * @param para5          行驶记录速度	WORD	行驶记录功能获取的速度，1/10km/h
     * @param para6          卫星定位速度	WORD	1/10km/h
     * @param para7          方向	WORD	0-359，正北为0，顺时针
     * @param para8          时间	BCD[6]	YYMMDDhhmmss(GMT+8时间，本规范之后涉及的时间均采用此时区)
     * @param para9          里程，DWORD，1/10km，对应车上里程表读书  附加信息 >=0 ,负数就不添加
     * @param para10         油量，WORD，1/10L，对应车上油量表读书   附加信息 >=0 ,负数就不添加
     * @param para11         海拔高度，单位为m  附加信息 >=-1000 ,小于-1000不添加
     * @param para12         发动机转速，WORD  附加信息 >=0 ,负数就不添加
     * @return
     */
    public static byte[] makeLocationInfo(String warningSignHex, String stateHex, int para3, int para4, int para5, int para6, int para7, String para8, int para9, int para10, int para11, int para12) {

        byte[] resultBody = makeLocationInfoBody(warningSignHex,
                stateHex,
                para3,
                para4,
                para5,
                para6,
                para7,
                para8,
                para9,
                para10,
                para11,
                para12);
        byte[] resultHead = makeHead(locationInfoUpdata, false, 0, resultBody.length); // 包头固定
        return sticky(resultHead, resultBody);
    }


    /**
     * B.3.2.3.19　位置信息查询应答
     *
     * @param para1 报警标识   DWORD	报警标识位定义见表B.22
     * @param para2 状态     DWORD	状态位定义见表B.23
     * @param para3 纬度     DWORD	以度为单位的纬度值乘以10的6次方，精确到百万分之一度
     * @param para4 经度     DWORD	以度为单位的纬度值乘以10的6次方，精确到百万分之一度
     * @param para5 行驶记录速度 WORD	行驶记录功能获取的速度，1/10km/h
     * @param para6 卫星定位速度 WORD	1/10km/h
     * @param para7 方向     WORD	0-359，正北为0，顺时针
     * @param para8 时间     BCD[6]	YYMMDDhhmmss(GMT+8时间，本规范之后涉及的时间均采用此时区)
     * @return
     */
    public static byte[] makeFindLocatInfoRequest(String para1, String para2, int para3, int para4, int para5, int para6, int para7, String para8) {

        byte[] resultBody = makeLocationInfoBody(para1, para2, para3, para4, para5, para6, para7, para8, -2000, -2000, -2000, -2000);
        byte[] resultHead = makeHead(findLocatInfoRequest, false, 0, resultBody.length); // 包头固定

        byte[] result = sticky(resultHead, resultBody);
        ByteUtil.printHexString(result);
        return result;
    }

    /***
     *          黏
     * @param head
     * @param body
     * @return
     */
    private static byte[] sticky(byte[] head, byte[] body) {
        byte[] result = ByteUtil.addXor(ByteUtil.add(head, body));
        result = ByteUtil.addEND(result); // 添加尾部
        result = ByteUtil.checkMark(result);

        ByteUtil.printHexString(result);
        return result;
    }


    /**
     * B.4.1.2　扩展计时培训消息内容
     *
     * @param exMsgId 透传消息ID
     * @param para2   消息时效类型 ，0：实时消息，1：补传消息；
     * @param para3   应答属性，0：不需要应答，1：需要应答；
     * @param para4   表示加密算法，0：未加密，1：SHA1，2：SHA256；其他保留
     * @param para6   数据内容
     * @return
     */
    public static byte[] buildExMsg(byte[] exMsgId, int para2, int para3, int para4, byte[] para6) {
        int attrr = para2 + para3 * 2 + para4 * 16;
        byte[] resultBody = ByteUtil.add(exMsgId, int2Bytes(attrr, 2));
        resultBody = ByteUtil.add(resultBody, getExCode());
        resultBody = ByteUtil.add(resultBody, ByteUtil.str2Word(strTerminalSerial));
        resultBody = ByteUtil.add(resultBody, int2Bytes(para6.length, 4));
        resultBody = ByteUtil.add(resultBody, para6);
        ///加密
        if (para4 == 2) {

        }

        return resultBody;
    }


    static String TAG = "ReceiveInfo";

    static List<byte[]> midDatas;
    static int index, totle;

    public static void handleReceiveInfo(byte[] data) {
        if (ByteUtil.checkXOR(data)) {
            ByteUtil.printRecvHexString(data);
            MessageBean messageBean = ByteUtil.handlerInfo(data);
            switch (messageBean.headBean.messageId) {
                case "8100":            //终端注册应答
                    if (messageBean.headBean.bodyLength == 3) {
                        switch (messageBean.bodyBean[2]) {
                            case 0:
                                break;
                            case 1:
                                RxBus.getInstance().post(Config.Config_RxBus.RX_TTS_SPEAK, "车辆已被注册");
                                break;
                            case 2:
                                RxBus.getInstance().post(Config.Config_RxBus.RX_TTS_SPEAK, "数据库中无该车辆");
                                break;
                            case 3:
                                RxBus.getInstance().post(Config.Config_RxBus.RX_TTS_SPEAK, "终端已被注册");
                                break;
                            case 4:
                                RxBus.getInstance().post(Config.Config_RxBus.RX_TTS_SPEAK, "数据库中无该终端");
                                break;
                        }
                    } else {
                        if (midDatas == null) {
                            midDatas = new ArrayList<>();
                        }
                        index = messageBean.headBean.encapsulationInfo.index;
                        totle = messageBean.headBean.encapsulationInfo.totle;
                        if (index <= totle) {
                            midDatas.add(messageBean.bodyBean);
                            if (messageBean.headBean.encapsulationInfo.index == messageBean.headBean.encapsulationInfo.totle) {
                                int totleLength = 0;
                                int posLength = 0;
                                for (byte[] midData : midDatas) {
                                    totleLength = totleLength + midData.length;
                                }
                                byte[] dataResult = new byte[totleLength];
                                for (byte[] midData : midDatas) {
                                    System.arraycopy(midData, 0, dataResult, posLength, midData.length);
                                    posLength = posLength + midData.length;
//                                    ByteUtil.printHexString(midData);
                                }
                                int posIndex = 0;
                                System.arraycopy(dataResult, posIndex, ConstantInfo.requestWaterCode, 0, ConstantInfo.requestWaterCode.length);       //应答流水号
                                posIndex = ConstantInfo.requestWaterCode.length;
                                System.arraycopy(dataResult, posIndex, ConstantInfo.result, 0, ConstantInfo.result.length);         //结果
                                posIndex = posIndex + ConstantInfo.result.length;
                                System.arraycopy(dataResult, posIndex, ConstantInfo.platformNum, 0, ConstantInfo.platformNum.length);        //平台编号
                                posIndex = posIndex + ConstantInfo.platformNum.length;
                                System.arraycopy(dataResult, posIndex, ConstantInfo.institutionNumber, 0, ConstantInfo.institutionNumber.length);      //培训机构编号
                                posIndex = posIndex + ConstantInfo.institutionNumber.length;
                                System.arraycopy(dataResult, posIndex, ConstantInfo.terminalNum, 0, ConstantInfo.terminalNum.length);          //终端编号
                                ByteUtil.printHexString("终端编号 : ", ConstantInfo.terminalNum);
                                posIndex = posIndex + ConstantInfo.terminalNum.length;
                                System.arraycopy(dataResult, posIndex, ConstantInfo.certificatePassword, 0, ConstantInfo.certificatePassword.length);      //证书口令
                                posIndex = posIndex + ConstantInfo.certificatePassword.length;
                                int passwordLength = dataResult.length - posIndex;
                                byte[] data0 = new byte[passwordLength];
                                System.arraycopy(dataResult, posIndex, data0, 0, data0.length);
                                ConstantInfo.terminalCertificate = ByteUtil.getString(data0);     //终端证书
                                midDatas = null;
                                RxBus.getInstance().post(Config.Config_RxBus.RX_TTS_SPEAK, "终端注册成功");
                                WriteSettingHelper.saveRegistInfo();
                            }
                        }
                    }
                    break;
                case "8001":            //服务端通用应答
                    if (messageBean.headBean.bodyLength == 5) {
                        switch (messageBean.bodyBean[4]) {
                            case 0:
                                ToastUitl.show("成功/确认", Toast.LENGTH_SHORT);
                                break;
                            case 1:
                                ToastUitl.show("失败", Toast.LENGTH_SHORT);
                                break;
                            case 2:
                                ToastUitl.show("消息有误", Toast.LENGTH_SHORT);
                                break;
                            case 3:
                                ToastUitl.show("不支持", Toast.LENGTH_SHORT);
                                break;
                        }
                    }
                    break;

                case "8103":            //设置终端参数


                    break;

                default:
                    break;
            }
        }
    }

}
