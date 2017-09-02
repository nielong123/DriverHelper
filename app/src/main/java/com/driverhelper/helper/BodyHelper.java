package com.driverhelper.helper;


import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.driverhelper.app.MyApplication;
import com.driverhelper.beans.MSG;
import com.driverhelper.beans.MessageBean;
import com.driverhelper.beans.db.StudyInfo;
import com.driverhelper.config.Config;
import com.driverhelper.config.ConstantInfo;
import com.driverhelper.other.tcp.TcpBody;
import com.driverhelper.other.encrypt.Encrypt;
import com.driverhelper.other.tcp.TcpHelper;
import com.driverhelper.other.tcp.TcpManager;
import com.driverhelper.utils.ByteUtil;
import com.jaydenxiao.common.baserx.RxBus;
import com.jaydenxiao.common.commonutils.TimeUtil;
import com.jaydenxiao.common.commonutils.ToastUitl;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.driverhelper.config.Config.Config_RxBus.RX_SETTING_8202_;
import static com.driverhelper.config.Config.Config_RxBus.RX_SETTING_8205;
import static com.driverhelper.config.Config.Config_RxBus.RX_SETTING_8301;
import static com.driverhelper.config.Config.Config_RxBus.RX_SETTING_8302;
import static com.driverhelper.config.Config.Config_RxBus.RX_SETTING_8304;
import static com.driverhelper.config.Config.Config_RxBus.RX_TTS_SPEAK;
import static com.driverhelper.config.ConstantInfo.SN;
import static com.driverhelper.config.ConstantInfo.classType;
import static com.driverhelper.config.ConstantInfo.coachId;
import static com.driverhelper.config.ConstantInfo.terminalNum;
import static com.driverhelper.config.ConstantInfo.vehicleColor;
import static com.driverhelper.config.ConstantInfo.vehicleNum;
import static com.driverhelper.other.tcp.TcpBody.MessageID.findLocatInfoRequest;
import static com.driverhelper.other.tcp.TcpBody.MessageID.id0001;
import static com.driverhelper.other.tcp.TcpBody.MessageID.id0104;
import static com.driverhelper.other.tcp.TcpBody.MessageID.id0203;
import static com.driverhelper.other.tcp.TcpBody.MessageID.id0205;
import static com.driverhelper.other.tcp.TcpBody.MessageID.id0301;
import static com.driverhelper.other.tcp.TcpBody.MessageID.id0302;
import static com.driverhelper.other.tcp.TcpBody.MessageID.id0303;
import static com.driverhelper.other.tcp.TcpBody.MessageID.id0304;
import static com.driverhelper.other.tcp.TcpBody.MessageID.id0305;
import static com.driverhelper.other.tcp.TcpBody.MessageID.id0306;
import static com.driverhelper.other.tcp.TcpBody.MessageID.id0401;
import static com.driverhelper.other.tcp.TcpBody.MessageID.id0402;
import static com.driverhelper.other.tcp.TcpBody.MessageID.id0403;
import static com.driverhelper.other.tcp.TcpBody.MessageID.id0501;
import static com.driverhelper.other.tcp.TcpBody.MessageID.id0502;
import static com.driverhelper.other.tcp.TcpBody.MessageID.id0503;
import static com.driverhelper.other.tcp.TcpBody.MessageID.locationInfoUpdata;
import static com.driverhelper.other.tcp.TcpBody.MessageID.register;
import static com.driverhelper.other.tcp.TcpBody.MessageID.transparentInfo;
import static com.driverhelper.other.tcp.TcpBody.MessageID.updataCoachLogin;
import static com.driverhelper.other.tcp.TcpBody.MessageID.updataCoachLogout;
import static com.driverhelper.other.tcp.TcpBody.MessageID.updataStudentLogin;
import static com.driverhelper.other.tcp.TcpBody.MessageID.updataStudentLogiout;
import static com.driverhelper.other.tcp.TcpBody.VERSION_CODE;
import static com.driverhelper.other.tcp.TcpBody.driving;
import static com.driverhelper.utils.ByteUtil.int2Bytes;
import static com.driverhelper.utils.ByteUtil.int2DWORD;
import static com.driverhelper.utils.ByteUtil.int2WORD;

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
                                   boolean isDivision,
                                   int encryption,
                                   int totle,
                                   int index,
                                   int length) {
        byte[] data;
        data = ByteUtil.add(TcpBody.HEAD, VERSION_CODE);
        data = ByteUtil.add(data, id);
        data = ByteUtil.add(data,
                makeHeadAttribute(isDivision, encryption, length));
        data = ByteUtil.add(data, makePhone2BCD(ConstantInfo.terminalPhoneNumber));
        byte[] watercode = getWaterCode();
        data = ByteUtil.add(data, watercode);
        ByteUtil.printHexString("water code ", watercode);
        data = ByteUtil.add(data, getReserve());
        if (isDivision) { // 分包
            data = ByteUtil.add(data, ByteUtil.int2WORD(totle));
            data = ByteUtil.add(data, ByteUtil.int2WORD(index));
        }
        ByteUtil.printHexString("hand = ", data);
        ByteUtil.printHexString("watercode = ", watercode);
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
        return ByteUtil.int2WORD(IdHelper.getWaterCode());
    }

    /******
     * 获取透传消息流水号
     * @return
     */
    public static byte[] getExCode() {
        return ByteUtil.int2WORD(IdHelper.getExCode());
    }

    /***
     * 预留
     *
     * @return
     */
    public static byte[] getReserve() {
        return new byte[]{(byte) 0x00};
    }

    /*************************************************************************************/
    /***
     * 各功能的组包,终端注册
     */
    public static byte[] makeRegist() {
        byte[] resultBody = ConstantInfo.province;
        resultBody = ByteUtil.add(resultBody, ConstantInfo.city); // 城市
        resultBody = ByteUtil.add(resultBody, ConstantInfo.makerID); // 制造商id
        resultBody = ByteUtil.add(resultBody, ByteUtil.autoAddZeroByLengthOnRight(ConstantInfo.MODEL, 20).getBytes()); // 终端型号
        resultBody = ByteUtil.add(resultBody, SN.getBytes()); // 终端编号
        resultBody = ByteUtil.add(resultBody, ByteUtil.str2Bytes(ConstantInfo.IMEI)); // IMEI
        resultBody = ByteUtil.add(resultBody, vehicleColor.getBytes()); // 车身颜色
        resultBody = ByteUtil.add(resultBody, ByteUtil.str2Bytes(vehicleNum)); // 车牌号
        int bodyLength = resultBody.length;
        System.out.println("bodyLength = " + bodyLength);
        byte[] resultHead = makeHead(register, false, 0, 0, 0, bodyLength); // 包头固定

        byte[] result = ByteUtil.addXor(ByteUtil.add(resultHead, resultBody));
        result = ByteUtil.addEND(result); // 添加尾部
        result = ByteUtil.checkMark(result);

//        ByteUtil.printHexString(result);
        return result;
    }

    /***
     * 终端注销
     *
     * @return
     */
    public static byte[] makeUnRegist() {
        int bodyLength = 0;
        byte[] result = makeHead(TcpBody.MessageID.unRegister, false, 0, 0, 0, bodyLength);
        result = ByteUtil.addXor(result);
        result = ByteUtil.addEND(result);
        result = ByteUtil.checkMark(result);
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
        byte[] resultHead = makeHead(TcpBody.MessageID.authentication, false, 0, 0, 0, resultBody.length);
        byte[] result = ByteUtil.addXor(ByteUtil.add(resultHead, resultBody));
        result = ByteUtil.addEND(result);
        result = ByteUtil.checkMark(result);
        return result;
    }


    /**
     * B.4.2.1.1　上报教练员登录
     *
     * @param idCard   教练员身份证号 BYTE[18]	ASCII码，不足18位前补0x00
     * @param coachnum 教练员编号 BYTE[16]	统一编号
     * @param carType  准教车型   BYTE[2]	A1\A2\A3\B1\B2\C1\C2\C3\C4\D\E\F
     * @return
     */
    public static byte[] makeCoachLogin(String idCard, String coachnum, String carType) {

        byte[] resultBody = ByteUtil.str2Bytes(coachnum.toUpperCase());
        resultBody = ByteUtil.add(resultBody, ByteUtil.str2Bytes(idCard.toUpperCase()));
        resultBody = ByteUtil.add(resultBody, ByteUtil.str2Bytes(carType.toUpperCase()));
        resultBody = ByteUtil.add(resultBody, BodyHelper.makeLocationInfoBody("00000000",
                "40080000",
                (int) (MyApplication.getInstance().lon * Math.pow(10, 6)),
                (int) (MyApplication.getInstance().lat * Math.pow(10, 6)),
                10,
                (int) MyApplication.getInstance().speedGPS,
                (int) MyApplication.getInstance().direction,
                TimeUtil.formatData(TimeUtil.dateFormatYMDHMS_, MyApplication.getInstance().timeGPS / 1000),
                -2000, -2000, -2000, -2000)
        );

        resultBody = buildExMsg(updataCoachLogin, 0, 1, 2, resultBody);
        resultBody = ByteUtil.add(driving, resultBody);
        byte[] resultHead = makeHead(transparentInfo, false, 0, 0, 0, resultBody.length);
        return sticky(resultHead, resultBody);
    }

    /****
     * 教练员登出
     * @param coachnum
     * @return
     */
    public static byte[] makeCoachLogout(String coachnum) {
        byte[] resultBody = ByteUtil.str2Bytes(coachnum);
        resultBody = ByteUtil.add(resultBody, BodyHelper.makeLocationInfoBody("00000000",
                "40080000",
                (int) (MyApplication.getInstance().lon * Math.pow(10, 6)),
                (int) (MyApplication.getInstance().lat * Math.pow(10, 6)),
                10,
                (int) MyApplication.getInstance().speedGPS,
                (int) MyApplication.getInstance().direction,
                TimeUtil.formatData(TimeUtil.dateFormatYMDHMS_, MyApplication.getInstance().timeGPS / 1000),
                -2000, -2000, -2000, -2000)
        );

        resultBody = buildExMsg(updataCoachLogout, 0, 1, 2, resultBody);
        resultBody = ByteUtil.add(driving, resultBody);
        byte[] resultHead = makeHead(transparentInfo, false, 0, 0, 0, resultBody.length);
        return sticky(resultHead, resultBody);
    }

    /****
     * 学员登录
     * @param coachNum
     * @param studentNum
     * @return
     */
    public static byte[] makeStudentLogin(String coachNum, String studentNum) {
        byte[] resultBody = ByteUtil.str2Bytes(studentNum);
        ByteUtil.printHexString("ByteUtil.str2Word(studentNum)", ByteUtil.str2Bytes(studentNum));
        resultBody = ByteUtil.add(resultBody, ByteUtil.str2Bytes(coachNum));      //当前教练编号
        Log.e("coachNum", "coachNum = " + coachNum);
        resultBody = ByteUtil.add(resultBody, ByteUtil.str2Bcd("1212110000"));                //培训课程
        long time = TimeUtil.getTime() / 1000;
        ConstantInfo.classId = time;
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD((int) ConstantInfo.classId));//课堂id  时间戳
        resultBody = ByteUtil.add(resultBody, BodyHelper.makeLocationInfoBody("00000000",
                "40080000",
                (int) (MyApplication.getInstance().lon * Math.pow(10, 6)),
                (int) (MyApplication.getInstance().lat * Math.pow(10, 6)),
                10,
                (int) MyApplication.getInstance().speedGPS,
                (int) MyApplication.getInstance().direction,
                TimeUtil.formatData(TimeUtil.dateFormatYMDHMS_, time),
                -2000, -2000, -2000, -2000)
        );

        resultBody = buildExMsg(updataStudentLogin, 0, 1, 2, resultBody);
        resultBody = ByteUtil.add(driving, resultBody);
        byte[] resultHead = makeHead(transparentInfo, false, 0, 0, 0, resultBody.length);
        byte[] result = sticky(resultHead, resultBody);
        ByteUtil.printHexString("  result  ", result);
        return result;
    }


    /****
     * 学员登出
     * @param studentNum
     * @return
     */
    public static byte[] makeStudentLogiout(String studentNum) {
        long time = TimeUtil.getTime() / 1000;
        byte[] resultBody = ByteUtil.str2Bytes(studentNum);              //学员编号
        resultBody = ByteUtil.add(resultBody, ByteUtil.str2Bcd(TimeUtil.formatData(TimeUtil.dateFormatYMDHMS_, time)));     //登出时间
//        ByteUtil.printHexString("登出时间  =  ", ByteUtil.str2Bcd(TimeUtil.formatData(TimeUtil.dateFormatYMDHMS_, time)));
//        resultBody = ByteUtil.add(resultBody, ByteUtil.str2Word(studyTime / 60 + ""));                //学员该次登录总时间   min
        resultBody = ByteUtil.add(resultBody, ByteUtil.str2Bytes("01"));                //学员该次登录总时间   min
//        resultBody = ByteUtil.add(resultBody, ByteUtil.str2Word(studyDistance / 10 + ""));                //学员该次登录总时间   min
        resultBody = ByteUtil.add(resultBody, ByteUtil.str2Bytes("02"));                //学员该次登录总时间   min
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD((int) ConstantInfo.classId));           //课堂id  时间戳
        resultBody = ByteUtil.add(resultBody, BodyHelper.makeLocationInfoBody("00000000",
                "40080000",
                (int) (MyApplication.getInstance().lon * Math.pow(10, 6)),
                (int) (MyApplication.getInstance().lat * Math.pow(10, 6)),
                10,
                (int) MyApplication.getInstance().speedGPS,
                (int) MyApplication.getInstance().direction,
                TimeUtil.formatData(TimeUtil.dateFormatYMDHMS_, time),
                -2000, -2000, -2000, -2000)
        );
        Log.d("", " resultBody.length = " + resultBody.length);
        resultBody = buildExMsg(updataStudentLogiout, 0, 1, 2, resultBody);
        resultBody = ByteUtil.add(driving, resultBody);
        byte[] resultHead = makeHead(transparentInfo, false, 0, 0, 0, resultBody.length);
        return sticky(resultHead, resultBody);
    }

    /****
     * 上传学时信息
     * @param updataType
     * @return
     */
    public static byte[] makeSendStudyInfo(String time666, int studyCode, byte updataType, String studentId, String coachId, int vehiclSspeed, int distance, int lon, int lat, int speedGPS, int direction, long timeSYS,
                                           byte recordType) {
        byte[] resultBody = ByteUtil.add(("0000000000000000" + time666).getBytes(), ByteUtil.int2DWORD(studyCode));           //26        学时记录编号
        resultBody = ByteUtil.add(resultBody, updataType);              //      上报类型
        resultBody = ByteUtil.add(resultBody, studentId.getBytes());       //学员编号
        resultBody = ByteUtil.add(resultBody, coachId.getBytes());             //教练员编号
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD((int) ConstantInfo.classId));//课堂id  时间戳
        resultBody = ByteUtil.add(resultBody, ByteUtil.str2Bcd(time666));               //記錄產生時間
        resultBody = ByteUtil.add(resultBody, ByteUtil.str2Bcd(classType));                //培训课程
        resultBody = ByteUtil.add(resultBody, recordType);
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2WORD(vehiclSspeed));            //最大速度
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2WORD(distance));            //最大里程  10
        resultBody = ByteUtil.add(resultBody, BodyHelper.makeLocationInfoBody("00000000",
                "40080000",
                lon,
                lat,
                10,
                speedGPS,
                direction,
                TimeUtil.formatData(TimeUtil.dateFormatYMDHMS_, timeSYS),
                20, -2000, -2000, 30)
        );

        resultBody = buildExMsg(id0203, 0, 1, 2, resultBody);
        resultBody = ByteUtil.add(driving, resultBody);
        byte[] resultHead = makeHead(transparentInfo, false, 0, 0, 0, resultBody.length);
        byte[] result = sticky(resultHead, resultBody);
        ByteUtil.printHexString(result);
        return result;
    }

    /****
     * 上传学时信息
     * @param updataType
     * @return
     */
    public static byte[] makeSendStudyInfo(byte updataType, String time666, byte recordType) {
        byte[] resultBody = ByteUtil.add(("0000000000000000" + time666).getBytes(), ByteUtil.int2DWORD(IdHelper.getStudyCode()));           //26        学时记录编号
        resultBody = ByteUtil.add(resultBody, updataType);              //      上报类型
        resultBody = ByteUtil.add(resultBody, ConstantInfo.StudentInfo.studentId.getBytes());       //学员编号
        resultBody = ByteUtil.add(resultBody, ConstantInfo.coachId.getBytes());             //教练员编号
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD((int) ConstantInfo.classId));//课堂id  时间戳
        resultBody = ByteUtil.add(resultBody, ByteUtil.str2Bcd(time666));               //記錄產生時間
        resultBody = ByteUtil.add(resultBody, ByteUtil.str2Bcd(classType));                //培训课程
        resultBody = ByteUtil.add(resultBody, recordType);
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2WORD(ConstantInfo.ObdInfo.vehiclSspeed));            //最大速度
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2WORD(ConstantInfo.ObdInfo.distance));            //最大里程  10
        resultBody = ByteUtil.add(resultBody, BodyHelper.makeLocationInfoBody("00000000",
                "40080000",
                (int) (MyApplication.getInstance().lon * Math.pow(10, 6)),
                (int) (MyApplication.getInstance().lat * Math.pow(10, 6)),
                10,
                (int) MyApplication.getInstance().speedGPS,
                (int) MyApplication.getInstance().direction,
                TimeUtil.formatData(TimeUtil.dateFormatYMDHMS_, MyApplication.getInstance().timeGPS / 1000),
                20, -2000, -2000, 30)
        );

        resultBody = buildExMsg(id0203, 0, 1, 2, resultBody);
        resultBody = ByteUtil.add(driving, resultBody);
        byte[] resultHead = makeHead(transparentInfo, false, 0, 0, 0, resultBody.length);
        byte[] result = sticky(resultHead, resultBody);
        ByteUtil.printHexString(result);
        return result;
    }

    /****
     * 重传学时信息
     * @return
     */
    public static byte[] makeReSendStudyInfo(StudyInfo studyInfo) {
        byte[] resultBody = ByteUtil.add(("0000000000000000" + studyInfo.getMakeTime()).getBytes(), ByteUtil.int2DWORD(IdHelper.getStudyCode()));           //26        学时记录编号
        resultBody = ByteUtil.add(resultBody, (byte) 0x01);              //      上报类型
        resultBody = ByteUtil.add(resultBody, ByteUtil.str2Bytes(studyInfo.getStudentId()));       //学员编号
        resultBody = ByteUtil.add(resultBody, ByteUtil.str2Bytes(studyInfo.getCoachId()));             //教练员编号
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(Integer.valueOf(studyInfo.getClassId())));           //课堂id  时间戳
        resultBody = ByteUtil.add(resultBody, ByteUtil.str2Bcd(studyInfo.getMakeTime()));               //記錄產生時間
        resultBody = ByteUtil.add(resultBody, ByteUtil.str2Bcd(studyInfo.getType()));                //培训课程
        resultBody = ByteUtil.add(resultBody, (byte) 0x00);                                      //记录状态
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2WORD(studyInfo.getSpeed()));            //最大速度
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2WORD(studyInfo.getDistance()));            //最大里程  10
        resultBody = ByteUtil.add(resultBody, BodyHelper.makeLocationInfoBody("00000000",
                "40080000",
                (int) (MyApplication.getInstance().lon * Math.pow(10, 6)),
                (int) (MyApplication.getInstance().lat * Math.pow(10, 6)),
                10,
                (int) MyApplication.getInstance().speedGPS,
                (int) MyApplication.getInstance().direction,
                TimeUtil.formatData(TimeUtil.dateFormatYMDHMS_, MyApplication.getInstance().timeGPS / 1000),
                20, -2000, -2000, 30)
        );

        resultBody = buildExMsg(id0203, 0, 1, 2, resultBody);
        resultBody = ByteUtil.add(driving, resultBody);
        byte[] resultHead = makeHead(transparentInfo, false, 0, 0, 0, resultBody.length);
        byte[] result = sticky(resultHead, resultBody);
        ByteUtil.printHexString(result);
        return result;
    }

    public static byte[] makeAll0104(int waterId) {
        byte[] resultBody = ByteUtil.int2WORD(waterId);
        resultBody = ByteUtil.add(resultBody, (byte) 0x3B);          //应答参数个数
        resultBody = ByteUtil.add(resultBody, (byte) 0x3B);          //包参数个数

        MSG.getInstance().getPARAM0001();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(1));
        resultBody = ByteUtil.add(resultBody, (byte) 0x04);
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0001));
        MSG.getInstance().getPARAM0002();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(2));
        resultBody = ByteUtil.add(resultBody, (byte) 0x04);
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0002));
        MSG.getInstance().getPARAM0003();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(3));
        resultBody = ByteUtil.add(resultBody, (byte) 0x04);
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0003));
        MSG.getInstance().getPARAM0004();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(4));
        resultBody = ByteUtil.add(resultBody, (byte) 0x04);
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0004));
        MSG.getInstance().getPARAM0005();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(5));
        resultBody = ByteUtil.add(resultBody, (byte) 0x04);
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0005));
        MSG.getInstance().getPARAM0006();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(6));
        resultBody = ByteUtil.add(resultBody, (byte) 0x04);
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0006));
        MSG.getInstance().getPARAM0007();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(7));
        resultBody = ByteUtil.add(resultBody, (byte) 0x04);
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0007));

        MSG.getInstance().getPARAM0010();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(10));
        resultBody = ByteUtil.add(resultBody, (byte) ConstantInfo.param0010.getBytes().length);
        resultBody = ByteUtil.add(resultBody, ConstantInfo.param0010.getBytes());
        MSG.getInstance().getPARAM0011();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(11));
        resultBody = ByteUtil.add(resultBody, (byte) ConstantInfo.param0011.getBytes().length);
        resultBody = ByteUtil.add(resultBody, ConstantInfo.param0011.getBytes());
        MSG.getInstance().getPARAM0012();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(12));
        resultBody = ByteUtil.add(resultBody, (byte) ConstantInfo.param0012.getBytes().length);
        resultBody = ByteUtil.add(resultBody, ConstantInfo.param0012.getBytes());
        MSG.getInstance().getPARAM0013();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(13));
        resultBody = ByteUtil.add(resultBody, (byte) ConstantInfo.param0013.getBytes().length);
        resultBody = ByteUtil.add(resultBody, ConstantInfo.param0013.getBytes());
        MSG.getInstance().getPARAM0014();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(14));
        resultBody = ByteUtil.add(resultBody, (byte) ConstantInfo.param0014.getBytes().length);
        resultBody = ByteUtil.add(resultBody, ConstantInfo.param0014.getBytes());
        MSG.getInstance().getPARAM0015();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(15));
        resultBody = ByteUtil.add(resultBody, (byte) ConstantInfo.param0015.getBytes().length);
        resultBody = ByteUtil.add(resultBody, ConstantInfo.param0015.getBytes());
        MSG.getInstance().getPARAM0016();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(16));
        resultBody = ByteUtil.add(resultBody, (byte) ConstantInfo.param0016.getBytes().length);
        resultBody = ByteUtil.add(resultBody, ConstantInfo.param0016.getBytes());
        MSG.getInstance().getPARAM0017();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(17));
        resultBody = ByteUtil.add(resultBody, (byte) ConstantInfo.param0017.getBytes().length);
        resultBody = ByteUtil.add(resultBody, ConstantInfo.param0017.getBytes());
        MSG.getInstance().getPARAM0018();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(18));
        resultBody = ByteUtil.add(resultBody, (byte) 0x04);
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0018));
        MSG.getInstance().getPARAM0019();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(9));
        resultBody = ByteUtil.add(resultBody, (byte) 0x04);
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0019));

        MSG.getInstance().getPARAM0020();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(20));
        resultBody = ByteUtil.add(resultBody, (byte) 0x04);
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0020));
        MSG.getInstance().getPARAM0021();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(21));
        resultBody = ByteUtil.add(resultBody, (byte) 0x04);
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0021));
        MSG.getInstance().getPARAM0022();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(22));
        resultBody = ByteUtil.add(resultBody, (byte) 0x04);
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0022));
        MSG.getInstance().getPARAM0027();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(27));
        resultBody = ByteUtil.add(resultBody, (byte) 0x04);
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0027));
        MSG.getInstance().getPARAM0028();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(28));
        resultBody = ByteUtil.add(resultBody, (byte) 0x04);
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0028));
        MSG.getInstance().getPARAM0029();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(29));
        resultBody = ByteUtil.add(resultBody, (byte) 0x04);
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0029));
        MSG.getInstance().getPARAM002C();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(0x2C));
        resultBody = ByteUtil.add(resultBody, (byte) 0x04);
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param002C));
        MSG.getInstance().getPARAM002D();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(0x2D));
        resultBody = ByteUtil.add(resultBody, (byte) 0x04);
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param002D));
        MSG.getInstance().getPARAM002E();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(0x2E));
        resultBody = ByteUtil.add(resultBody, (byte) 0x04);
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param002E));
        MSG.getInstance().getPARAM002F();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(0x2F));
        resultBody = ByteUtil.add(resultBody, (byte) 0x04);
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param002F));

        MSG.getInstance().getPARAM0030();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(0x30));
        resultBody = ByteUtil.add(resultBody, (byte) 0x04);
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0030));

        MSG.getInstance().getPARAM0040();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(0x40));
        resultBody = ByteUtil.add(resultBody, (byte) ConstantInfo.param0040.getBytes().length);
        resultBody = ByteUtil.add(resultBody, ConstantInfo.param0040.getBytes());
        MSG.getInstance().getPARAM0041();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(0x41));
        resultBody = ByteUtil.add(resultBody, (byte) ConstantInfo.param0041.getBytes().length);
        resultBody = ByteUtil.add(resultBody, ConstantInfo.param0041.getBytes());
        MSG.getInstance().getPARAM0042();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(0x42));
        resultBody = ByteUtil.add(resultBody, (byte) ConstantInfo.param0042.getBytes().length);
        resultBody = ByteUtil.add(resultBody, ConstantInfo.param0042.getBytes());
        MSG.getInstance().getPARAM0043();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(0x43));
        resultBody = ByteUtil.add(resultBody, (byte) ConstantInfo.param0043.getBytes().length);
        resultBody = ByteUtil.add(resultBody, ConstantInfo.param0043.getBytes());
        MSG.getInstance().getPARAM0044();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(0x44));
        resultBody = ByteUtil.add(resultBody, (byte) ConstantInfo.param0044.getBytes().length);
        resultBody = ByteUtil.add(resultBody, ConstantInfo.param0044.getBytes());
        MSG.getInstance().getPARAM0045();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(0x45));
        resultBody = ByteUtil.add(resultBody, (byte) 0x04);
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0045));
        MSG.getInstance().getPARAM0046();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(0x46));
        resultBody = ByteUtil.add(resultBody, (byte) 0x04);
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0046));
        MSG.getInstance().getPARAM0047();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(0x47));
        resultBody = ByteUtil.add(resultBody, (byte) 0x04);
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0047));
        MSG.getInstance().getPARAM0048();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(0x48));
        resultBody = ByteUtil.add(resultBody, (byte) ConstantInfo.param0048.getBytes().length);
        resultBody = ByteUtil.add(resultBody, ConstantInfo.param0048.getBytes());
        MSG.getInstance().getPARAM0049();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(0x49));
        resultBody = ByteUtil.add(resultBody, (byte) ConstantInfo.param0049.getBytes().length);
        resultBody = ByteUtil.add(resultBody, ConstantInfo.param0049.getBytes());

        MSG.getInstance().getPARAM0050();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(0x50));
        resultBody = ByteUtil.add(resultBody, (byte) 0x04);
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0050));
        MSG.getInstance().getPARAM0051();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(0x51));
        resultBody = ByteUtil.add(resultBody, (byte) 0x04);
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0051));
        MSG.getInstance().getPARAM0052();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(0x52));
        resultBody = ByteUtil.add(resultBody, (byte) 0x04);
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0052));
        MSG.getInstance().getPARAM0053();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(0x53));
        resultBody = ByteUtil.add(resultBody, (byte) 0x04);
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0053));
        MSG.getInstance().getPARAM0054();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(0x54));
        resultBody = ByteUtil.add(resultBody, (byte) 0x04);
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0054));
        MSG.getInstance().getPARAM0055();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(0x55));
        resultBody = ByteUtil.add(resultBody, (byte) 0x04);
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0055));
        MSG.getInstance().getPARAM0056();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(0x56));
        resultBody = ByteUtil.add(resultBody, (byte) 0x04);
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0056));
        MSG.getInstance().getPARAM0057();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(0x57));
        resultBody = ByteUtil.add(resultBody, (byte) 0x04);
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0057));
        MSG.getInstance().getPARAM0058();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(0x58));
        resultBody = ByteUtil.add(resultBody, (byte) 0x04);
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0058));
        MSG.getInstance().getPARAM0059();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(0x59));
        resultBody = ByteUtil.add(resultBody, (byte) 0x04);
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0059));
        MSG.getInstance().getPARAM005A();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(0x5A));
        resultBody = ByteUtil.add(resultBody, (byte) 0x04);
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param005A));

        MSG.getInstance().getPARAM0070();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(0x70));
        resultBody = ByteUtil.add(resultBody, (byte) 0x04);
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0070));
        MSG.getInstance().getPARAM0071();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(0x71));
        resultBody = ByteUtil.add(resultBody, (byte) 0x04);
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0071));
        MSG.getInstance().getPARAM0072();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(0x72));
        resultBody = ByteUtil.add(resultBody, (byte) 0x04);
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0072));
        MSG.getInstance().getPARAM0073();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(0x73));
        resultBody = ByteUtil.add(resultBody, (byte) 0x04);
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0073));
        MSG.getInstance().getPARAM0074();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(0x74));
        resultBody = ByteUtil.add(resultBody, (byte) 0x04);
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0074));

        MSG.getInstance().getPARAM0080();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(0x80));
        resultBody = ByteUtil.add(resultBody, (byte) 0x04);
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0080));
        MSG.getInstance().getPARAM0081();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(0x81));
        resultBody = ByteUtil.add(resultBody, (byte) 0x02);
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2WORD(ConstantInfo.param0081));
        MSG.getInstance().getPARAM0082();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(0x82));
        resultBody = ByteUtil.add(resultBody, (byte) 0x02);
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2WORD(ConstantInfo.param0082));
        MSG.getInstance().getPARAM0083();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(0x83));
        resultBody = ByteUtil.add(resultBody, (byte) ConstantInfo.param0083.getBytes().length);
        resultBody = ByteUtil.add(resultBody, ConstantInfo.param0083.getBytes());
        MSG.getInstance().getPARAM0084();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(0x84));
        resultBody = ByteUtil.add(resultBody, (byte) 0x01);
        resultBody = ByteUtil.add(resultBody, (byte) ConstantInfo.param0084);
        MSG.getInstance().getPARAM0085();
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(0x85));
        resultBody = ByteUtil.add(resultBody, (byte) 0x04);
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0085));

        byte[] resultHead = makeHead(id0104, false, 0, 0, 0, resultBody.length); // 包头固定

        byte[] result = ByteUtil.addXor(ByteUtil.add(resultHead, resultBody));
        result = ByteUtil.addEND(result); // 添加尾部
        result = ByteUtil.checkMark(result);

        ByteUtil.printHexString(result);
        return result;
    }

    public static byte[] make0104(int waterId, @NonNull List<byte[]> idList) {
        byte[] resultBody = ByteUtil.int2WORD(waterId);
        resultBody = ByteUtil.add(resultBody, (byte) idList.size());          //应答参数个数
        resultBody = ByteUtil.add(resultBody, (byte) idList.size());          //包参数个数
        for (byte[] id : idList) {
            resultBody = ByteUtil.add(resultBody, id);
            switch (ByteUtil.byte2int(id)) {
                case (byte) 0x01:
                    MSG.getInstance().getPARAM0001();
                    resultBody = ByteUtil.add(resultBody, (byte) 0x04);
                    resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0001));
                    break;
                case (byte) 0x02:
                    MSG.getInstance().getPARAM0002();
                    resultBody = ByteUtil.add(resultBody, (byte) 0x04);
                    resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0002));
                    break;
                case (byte) 0x03:
                    MSG.getInstance().getPARAM0003();
                    resultBody = ByteUtil.add(resultBody, (byte) 0x04);
                    resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0003));
                    break;
                case (byte) 0x04:
                    MSG.getInstance().getPARAM0004();
                    resultBody = ByteUtil.add(resultBody, (byte) 0x04);
                    resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0004));
                    break;
                case (byte) 0x05:
                    MSG.getInstance().getPARAM0005();
                    resultBody = ByteUtil.add(resultBody, (byte) 0x04);
                    resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0005));
                    break;
                case (byte) 0x06:
                    MSG.getInstance().getPARAM0006();
                    resultBody = ByteUtil.add(resultBody, (byte) 0x04);
                    resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0006));
                    break;
                case (byte) 0x07:
                    MSG.getInstance().getPARAM0007();
                    resultBody = ByteUtil.add(resultBody, (byte) 0x04);
                    resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0007));
                    break;


                case (byte) 0x10:
                    MSG.getInstance().getPARAM0010();
                    resultBody = ByteUtil.add(resultBody, (byte) ConstantInfo.param0010.getBytes().length);
                    resultBody = ByteUtil.add(resultBody, ConstantInfo.param0010.getBytes());
                    break;
                case (byte) 0x11:
                    MSG.getInstance().getPARAM0011();
                    resultBody = ByteUtil.add(resultBody, (byte) ConstantInfo.param0011.getBytes().length);
                    resultBody = ByteUtil.add(resultBody, ConstantInfo.param0011.getBytes());
                    break;
                case (byte) 0x12:
                    MSG.getInstance().getPARAM0012();
                    resultBody = ByteUtil.add(resultBody, (byte) ConstantInfo.param0012.getBytes().length);
                    resultBody = ByteUtil.add(resultBody, ConstantInfo.param0012.getBytes());
                    break;
                case (byte) 0x13:
                    MSG.getInstance().getPARAM0013();
                    resultBody = ByteUtil.add(resultBody, (byte) ConstantInfo.param0013.getBytes().length);
                    resultBody = ByteUtil.add(resultBody, ConstantInfo.param0013.getBytes());
                    break;
                case (byte) 0x14:
                    MSG.getInstance().getPARAM0014();
                    resultBody = ByteUtil.add(resultBody, (byte) ConstantInfo.param0014.getBytes().length);
                    resultBody = ByteUtil.add(resultBody, ConstantInfo.param0014.getBytes());
                    break;
                case (byte) 0x15:
                    MSG.getInstance().getPARAM0015();
                    resultBody = ByteUtil.add(resultBody, (byte) ConstantInfo.param0015.getBytes().length);
                    resultBody = ByteUtil.add(resultBody, ConstantInfo.param0015.getBytes());
                    break;
                case (byte) 0x16:
                    MSG.getInstance().getPARAM0016();
                    resultBody = ByteUtil.add(resultBody, (byte) ConstantInfo.param0016.getBytes().length);
                    resultBody = ByteUtil.add(resultBody, ConstantInfo.param0016.getBytes());
                    break;
                case (byte) 0x17:
                    MSG.getInstance().getPARAM0017();
                    resultBody = ByteUtil.add(resultBody, (byte) ConstantInfo.param0017.getBytes().length);
                    resultBody = ByteUtil.add(resultBody, ConstantInfo.param0017.getBytes());
                    break;
                case (byte) 0x18:
                    MSG.getInstance().getPARAM0018();
                    resultBody = ByteUtil.add(resultBody, (byte) 0x04);
                    resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0018));
                    break;
                case (byte) 0x19:
                    MSG.getInstance().getPARAM0019();
                    resultBody = ByteUtil.add(resultBody, (byte) 0x04);
                    resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0019));
                    break;

                case (byte) 0x20:
                    MSG.getInstance().getPARAM0020();
                    resultBody = ByteUtil.add(resultBody, (byte) 0x04);
                    resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0020));
                    break;
                case (byte) 0x21:
                    MSG.getInstance().getPARAM0021();
                    resultBody = ByteUtil.add(resultBody, (byte) 0x04);
                    resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0021));
                    break;
                case (byte) 0x22:
                    MSG.getInstance().getPARAM0022();

                    resultBody = ByteUtil.add(resultBody, (byte) 0x04);
                    resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0022));
                    break;
//                case (byte) 0x23:
//                    MSG.getInstance().getPARAM0023();
//                    
//                    resultBody = ByteUtil.add(resultBody, (byte) 0x04);
//                    resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0023));
//                    break;
//                case (byte) 0x24:
//                    MSG.getInstance().getPARAM0024();
//                    
//                    resultBody = ByteUtil.add(resultBody, (byte) 0x04);
//                    resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0024));
//                    break;
//                case (byte) 0x25:
//                    MSG.getInstance().getPARAM0025();
//                    
//                    resultBody = ByteUtil.add(resultBody, (byte) 0x04);
//                    resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0025));
//                    break;
//                case (byte) 0x26:
//                    MSG.getInstance().getPARAM0026();
//                    
//                    resultBody = ByteUtil.add(resultBody, (byte) 0x04);
//                    resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0026));
//                    break;
                case (byte) 0x27:
                    MSG.getInstance().getPARAM0027();
                    resultBody = ByteUtil.add(resultBody, (byte) 0x04);
                    resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0027));
                    break;
                case (byte) 0x28:
                    MSG.getInstance().getPARAM0028();
                    resultBody = ByteUtil.add(resultBody, (byte) 0x04);
                    resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0028));
                    break;
                case (byte) 0x29:
                    MSG.getInstance().getPARAM0029();
                    resultBody = ByteUtil.add(resultBody, (byte) 0x04);
                    resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0029));
                    break;
                case (byte) 0x2C:
                    MSG.getInstance().getPARAM002C();
                    resultBody = ByteUtil.add(resultBody, (byte) 0x04);
                    resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param002C));
                    break;
                case (byte) 0x2D:
                    MSG.getInstance().getPARAM002D();
                    resultBody = ByteUtil.add(resultBody, (byte) 0x04);
                    resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param002D));
                    break;
                case (byte) 0x2E:
                    MSG.getInstance().getPARAM002E();
                    resultBody = ByteUtil.add(resultBody, (byte) 0x04);
                    resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param002E));
                    break;
                case (byte) 0x2F:
                    MSG.getInstance().getPARAM002F();
                    resultBody = ByteUtil.add(resultBody, (byte) 0x04);
                    resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param002F));
                    break;

                case (byte) 0x30:
                    MSG.getInstance().getPARAM0030();
                    resultBody = ByteUtil.add(resultBody, (byte) 0x04);
                    resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0030));
                    break;

                case (byte) 0x40:
                    MSG.getInstance().getPARAM0040();
                    resultBody = ByteUtil.add(resultBody, (byte) ConstantInfo.param0040.getBytes().length);
                    resultBody = ByteUtil.add(resultBody, ConstantInfo.param0040.getBytes());
                    break;
                case (byte) 0x41:
                    MSG.getInstance().getPARAM0041();
                    resultBody = ByteUtil.add(resultBody, (byte) ConstantInfo.param0041.getBytes().length);
                    resultBody = ByteUtil.add(resultBody, ConstantInfo.param0041.getBytes());
                    break;
                case (byte) 0x42:
                    MSG.getInstance().getPARAM0042();

                    resultBody = ByteUtil.add(resultBody, (byte) ConstantInfo.param0042.getBytes().length);
                    resultBody = ByteUtil.add(resultBody, ConstantInfo.param0042.getBytes());
                    break;
                case (byte) 0x43:
                    MSG.getInstance().getPARAM0043();

                    resultBody = ByteUtil.add(resultBody, (byte) ConstantInfo.param0043.getBytes().length);
                    resultBody = ByteUtil.add(resultBody, ConstantInfo.param0043.getBytes());
                    break;
                case (byte) 0x44:
                    MSG.getInstance().getPARAM0044();
                    resultBody = ByteUtil.add(resultBody, (byte) ConstantInfo.param0040.getBytes().length);
                    resultBody = ByteUtil.add(resultBody, ConstantInfo.param0044.getBytes());
                    break;
                case (byte) 0x45:
                    MSG.getInstance().getPARAM0045();
                    resultBody = ByteUtil.add(resultBody, (byte) 0x04);
                    resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0045));
                    break;
                case (byte) 0x46:
                    MSG.getInstance().getPARAM0046();
                    resultBody = ByteUtil.add(resultBody, (byte) 0x04);
                    resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0046));
                    break;
                case (byte) 0x47:
                    MSG.getInstance().getPARAM0047();
                    resultBody = ByteUtil.add(resultBody, (byte) 0x04);
                    resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0047));
                    break;
                case (byte) 0x48:
                    MSG.getInstance().getPARAM0048();
                    resultBody = ByteUtil.add(resultBody, (byte) ConstantInfo.param0048.getBytes().length);
                    resultBody = ByteUtil.add(resultBody, ConstantInfo.param0048.getBytes());
                    break;
                case (byte) 0x49:
                    MSG.getInstance().getPARAM0049();
                    resultBody = ByteUtil.add(resultBody, (byte) ConstantInfo.param0049.getBytes().length);
                    resultBody = ByteUtil.add(resultBody, ConstantInfo.param0049.getBytes());
                    break;

                case (byte) 0x50:
                    MSG.getInstance().getPARAM0051();
                    resultBody = ByteUtil.add(resultBody, (byte) 0x04);
                    resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0050));
                    break;
                case (byte) 0x51:
                    MSG.getInstance().getPARAM0051();
                    resultBody = ByteUtil.add(resultBody, (byte) 0x04);
                    resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0051));
                    break;
                case (byte) 0x52:
                    MSG.getInstance().getPARAM0052();
                    resultBody = ByteUtil.add(resultBody, (byte) 0x04);
                    resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0052));
                    break;
                case (byte) 0x53:
                    MSG.getInstance().getPARAM0053();
                    resultBody = ByteUtil.add(resultBody, (byte) 0x04);
                    resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0053));
                    break;
                case (byte) 0x54:
                    MSG.getInstance().getPARAM0054();
                    resultBody = ByteUtil.add(resultBody, (byte) 0x04);
                    resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0054));
                    break;
                case (byte) 0x55:
                    MSG.getInstance().getPARAM0055();
                    resultBody = ByteUtil.add(resultBody, (byte) 0x04);
                    resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0055));
                    break;
                case (byte) 0x56:
                    MSG.getInstance().getPARAM0056();
                    resultBody = ByteUtil.add(resultBody, (byte) 0x04);
                    resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0056));
                    break;
                case (byte) 0x57:
                    MSG.getInstance().getPARAM0057();
                    resultBody = ByteUtil.add(resultBody, (byte) 0x04);
                    resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0057));
                    break;
                case (byte) 0x58:
                    MSG.getInstance().getPARAM0058();
                    resultBody = ByteUtil.add(resultBody, (byte) 0x04);
                    resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0058));
                    break;
                case (byte) 0x59:
                    MSG.getInstance().getPARAM0059();
                    resultBody = ByteUtil.add(resultBody, (byte) 0x04);
                    resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0059));
                    break;
                case (byte) 0x5A:
                    MSG.getInstance().getPARAM005A();
                    resultBody = ByteUtil.add(resultBody, (byte) 0x04);
                    resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param005A));
                    break;

                case (byte) 0x70:
                    MSG.getInstance().getPARAM0070();
                    resultBody = ByteUtil.add(resultBody, (byte) 0x04);
                    resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0070));
                    break;
                case (byte) 0x71:
                    MSG.getInstance().getPARAM0071();
                    resultBody = ByteUtil.add(resultBody, (byte) 0x04);
                    resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0071));
                    break;
                case (byte) 0x72:
                    MSG.getInstance().getPARAM0072();

                    resultBody = ByteUtil.add(resultBody, (byte) 0x04);
                    resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0072));
                    break;
                case (byte) 0x73:
                    MSG.getInstance().getPARAM0073();

                    resultBody = ByteUtil.add(resultBody, (byte) 0x04);
                    resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0073));
                    break;
                case (byte) 0x74:
                    MSG.getInstance().getPARAM0074();

                    resultBody = ByteUtil.add(resultBody, (byte) 0x04);
                    resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0074));
                    break;

                case (byte) 0x80:
                    MSG.getInstance().getPARAM0080();
                    resultBody = ByteUtil.add(resultBody, (byte) 0x04);
                    resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0080));
                    break;
                case (byte) 0x81:
                    MSG.getInstance().getPARAM0081();
                    resultBody = ByteUtil.add(resultBody, (byte) 0x02);
                    resultBody = ByteUtil.add(resultBody, ByteUtil.int2WORD(ConstantInfo.param0081));
                    break;
                case (byte) 0x82:
                    MSG.getInstance().getPARAM0082();
                    resultBody = ByteUtil.add(resultBody, (byte) 0x02);
                    resultBody = ByteUtil.add(resultBody, ByteUtil.int2WORD(ConstantInfo.param0082));
                    break;
                case (byte) 0x83:
                    MSG.getInstance().getPARAM0083();
                    resultBody = ByteUtil.add(resultBody, (byte) ConstantInfo.param0083.getBytes().length);
                    resultBody = ByteUtil.add(resultBody, ConstantInfo.param0083.getBytes());
                    break;
                case (byte) 0x84:
                    MSG.getInstance().getPARAM0084();
                    resultBody = ByteUtil.add(resultBody, (byte) 0x01);
                    resultBody = ByteUtil.add(resultBody, (byte) ConstantInfo.param0084);
                    break;
                case (byte) 0x85:
                    MSG.getInstance().getPARAM0085();
                    resultBody = ByteUtil.add(resultBody, (byte) 0x04);
                    resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(ConstantInfo.param0085));
                    break;
            }
        }
        byte[] resultHead = makeHead(id0104, false, 0, 0, 0, resultBody.length); // 包头固定

        byte[] result = ByteUtil.addXor(ByteUtil.add(resultHead, resultBody));
        result = ByteUtil.addEND(result); // 添加尾部
        result = ByteUtil.checkMark(result);

        ByteUtil.printHexString("0104 = ", result);
        return result;
    }


    public static byte[] make0203(byte updataType, byte recordType, StudyInfo info) {

        byte[] resultBody = ByteUtil.add(("0000000000000000" + info.getMakeTime()).getBytes(), ByteUtil.int2DWORD(info.getWaterCode()));           //26        学时记录编号
        resultBody = ByteUtil.add(resultBody, updataType);              //      上报类型
        resultBody = ByteUtil.add(resultBody, info.getStudentId().getBytes());       //学员编号
        resultBody = ByteUtil.add(resultBody, info.getCoachId().getBytes());             //教练员编号
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(info.getWaterCode()));           //课堂id  时间戳
        resultBody = ByteUtil.add(resultBody, ByteUtil.str2Bcd(info.getMakeTime()));               //記錄產生時間
        resultBody = ByteUtil.add(resultBody, ByteUtil.str2Bcd(classType));                //培训课程
        resultBody = ByteUtil.add(resultBody, recordType);
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2WORD(info.getSpeed()));            //最大速度
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2WORD(info.getDistance()));            //最大里程  10
        resultBody = ByteUtil.add(resultBody, BodyHelper.makeLocationInfoBody("00000000",
                "40080000",
                (int) (MyApplication.getInstance().lon * Math.pow(10, 6)),
                (int) (MyApplication.getInstance().lat * Math.pow(10, 6)),
                10,
                (int) MyApplication.getInstance().speedGPS,
                (int) MyApplication.getInstance().direction,
                TimeUtil.formatData(TimeUtil.dateFormatYMDHMS_, MyApplication.getInstance().timeGPS / 1000),
                20, -2000, -2000, 30)
        );

        resultBody = buildExMsg(id0203, 0, 1, 2, resultBody);
        resultBody = ByteUtil.add(driving, resultBody);
        byte[] resultHead = makeHead(transparentInfo, false, 0, 0, 0, resultBody.length);
        byte[] result = sticky(resultHead, resultBody);
        ByteUtil.printHexString(result);
        return result;
    }

    /****
     * 上传学时信息
     * @param type  1：查询的记录正在上传；
    2：SD卡没有找到；
    3：执行成功，但无指定记录；
    4：执行成功，稍候上报查询结果
    9：其他错误

     * @return
     */
    public static byte[] make0205(byte type) {

        byte[] resultBody = new byte[]{type};
        resultBody = buildExMsg(id0205, 0, 1, 2, resultBody);
        resultBody = ByteUtil.add(driving, resultBody);
        byte[] resultHead = makeHead(transparentInfo, false, 0, 0, 0, resultBody.length);
        return sticky(resultHead, resultBody);
    }


    /****
     * 立即拍照应答
     * @param updataType
     * @return
     */
    public static byte[] make0301(byte doResult, byte updataType, byte carmerNum, byte size) {

        byte[] resultBody = new byte[]{doResult, updataType, carmerNum, size};
        resultBody = buildExMsg(id0301, 0, 1, 2, resultBody);
        resultBody = ByteUtil.add(driving, resultBody);
        byte[] resultHead = makeHead(transparentInfo, false, 0, 0, 0, resultBody.length);
        return sticky(resultHead, resultBody);
    }

    /*****
     *
     * @param photoId       照片id
     * @param id                学员或者教练员编号
     * @param updataType            上传模式
     * @param eventType
     * @param totle                         摄像头通道好
     * @param photoSize             照片大小  xxx KB
     * @return
     */
    public static byte[] make0305(String photoId, String id, byte updataType, byte carmerId, byte photoSizeXxX, byte eventType, int totle, int photoSize) {

        byte[] resultBody = photoId.getBytes();            //照片编号
        resultBody = ByteUtil.add(resultBody, id.getBytes());
        resultBody = ByteUtil.add(resultBody, updataType);
        resultBody = ByteUtil.add(resultBody, carmerId);         //摄像头通道好
        resultBody = ByteUtil.add(resultBody, photoSizeXxX);         //照片尺寸
        resultBody = ByteUtil.add(resultBody, eventType);
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2WORD(totle));
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD(photoSize));
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2DWORD((int) new Date().getTime() / 1000));           //课堂id
        resultBody = ByteUtil.add(resultBody, BodyHelper.makeLocationInfoBody("00000000",
                "40080000",
                (int) (MyApplication.getInstance().lon * Math.pow(10, 6)),
                (int) (MyApplication.getInstance().lat * Math.pow(10, 6)),
                10,
                (int) MyApplication.getInstance().speedGPS,
                (int) MyApplication.getInstance().direction,
                TimeUtil.formatData(TimeUtil.dateFormatYMDHMS_, MyApplication.getInstance().timeGPS / 1000),
                20, -2000, -2000, 30)
        );
        resultBody = ByteUtil.add(resultBody, (byte) 0x50);           //人脸识别程度
        resultBody = buildExMsg(id0305, 0, 1, 2, resultBody);
        resultBody = ByteUtil.add(driving, resultBody);
        byte[] resultHead = makeHead(transparentInfo, false, 0, 0, 0, resultBody.length);
        return sticky(resultHead, resultBody);
    }

    public static byte[] make0302(byte doResult) {
        byte[] resultBody = new byte[]{doResult};
        resultBody = buildExMsg(id0302, 0, 1, 2, resultBody);
        resultBody = ByteUtil.add(driving, resultBody);
        byte[] resultHead = makeHead(transparentInfo, false, 0, 0, 0, resultBody.length);
        return sticky(resultHead, resultBody);
    }

    public static byte[] make0303(byte isUpdataEnd, int totle, List<String> list) {

        byte[] resultBody = new byte[]{isUpdataEnd, (byte) totle};
        if (totle != 0) {
            resultBody = ByteUtil.add(resultBody, (byte) (list.size()));
            for (String str : list) {
                resultBody = ByteUtil.add(resultBody, str.replaceAll(".pngpng", "").getBytes());
            }
        }
        resultBody = buildExMsg(id0303, 0, 1, 2, resultBody);
        resultBody = ByteUtil.add(driving, resultBody);
        byte[] resultHead = makeHead(transparentInfo, false, 0, 0, 0, resultBody.length);
        byte[] result = sticky(resultHead, resultBody);
        ByteUtil.printHexString("result 0303 = ", result);
        return result;
    }

    /****
     * B.4.2.3.9　上传指定照片应答
     * @param
     * @return
     */
    public static byte[] make0304(byte eventType) {

        byte[] resultBody = new byte[]{eventType};            //
        resultBody = buildExMsg(id0304, 0, 1, 2, resultBody);
        resultBody = ByteUtil.add(driving, resultBody);
        byte[] resultHead = makeHead(transparentInfo, false, 0, 0, 0, resultBody.length);
        return sticky(resultHead, resultBody);
    }

    /****
     * B.4.2.3.11　上传照片数据包
     * @param
     * @return
     */
    public static byte[] make0306(byte[] data, int totle, int index, boolean isPart) {

        byte[] resultBody = data;
        byte[] resultHead = makeHead(transparentInfo, isPart, 0, totle, index, resultBody.length);
        return sticky(resultHead, resultBody);
    }

    public static List<byte[]> make0306Part(String photoId, byte[] data) {
        List<byte[]> list = new ArrayList<>();
        byte[] resultBody = photoId.getBytes();
        resultBody = ByteUtil.add(resultBody, data);
        resultBody = buildExMsg(id0306, 0, 1, 2, resultBody);
        resultBody = ByteUtil.add(driving, resultBody);
        int number = (resultBody.length / 1000);
        if (number > 0) {
            int index = 0;
            for (int i = 0; i <= number; i++) {
                if (i != number) {
                    byte[] data1 = new byte[1000];
                    System.arraycopy(resultBody, index, data1, 0, data1.length);
                    index += 1000;
                    list.add(data1);
                } else {
                    int length = resultBody.length % 1000;
                    byte[] data2 = new byte[length];
                    System.arraycopy(resultBody, index, data2, 0, data2.length);
                    list.add(data2);
                }
            }
        } else {
            list.add(resultBody);
        }
        return list;
    }

    public static int getMake0306length(String photoId) {
        byte[] resultBody = photoId.getBytes();
        resultBody = buildExMsg(id0306, 0, 1, 2, resultBody);
        resultBody = ByteUtil.add(driving, resultBody);
        return resultBody.length;
    }

    public static byte[] make0403(String vehicleId) {
        vehicleId = "12345679012345678901234567890123456";
        byte[] resultBody = vehicleId.getBytes();
        resultBody = buildExMsg(id0403, 0, 1, 2, resultBody);
        resultBody = ByteUtil.add(driving, resultBody);
        return resultBody;
    }

    public static byte[] makeHeart() {
        byte[] result = makeHead(TcpBody.MessageID.heart, false, 0, 0, 0, 0);
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
     * @return
     */
    public static byte[] makeClientCommonResponse(int para1, int para2) {
        byte[] resultBody = int2WORD(IdHelper.getWaterCode());
        resultBody = ByteUtil.add(resultBody, int2WORD(para1));
        resultBody = ByteUtil.add(resultBody, (byte) para2);

        byte[] resultHead = makeHead(id0001, false, 0, 0, 0, resultBody.length); // 包头固定

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
            resultBody = ByteUtil.add(resultBody, int2Bytes(0x05, 1));
            resultBody = ByteUtil.add(resultBody, int2Bytes(2, 1));
            resultBody = ByteUtil.add(resultBody, int2Bytes(para12, 2));
        }
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
        byte[] resultHead = makeHead(locationInfoUpdata, false, 0, 0, 0, resultBody.length); // 包头固定
        return sticky(resultHead, resultBody);
    }


    /****
     * A.1.1.1.1　设置计时终端应用参数应答
     * @param
     * @return
     */
    public static byte[] make0501(byte type) {

        byte[] resultBody = new byte[]{type};

        resultBody = buildExMsg(id0501, 0, 1, 2, resultBody);
        resultBody = ByteUtil.add(driving, resultBody);
        byte[] resultHead = makeHead(transparentInfo, false, 0, 0, 0, resultBody.length);
        return sticky(resultHead, resultBody);
    }

    public static byte[] make0502(byte result, byte state, byte length, String str) {

        byte[] resultBody = new byte[]{result};
        resultBody = ByteUtil.add(resultBody, state);
        resultBody = ByteUtil.add(resultBody, length);
        if (length != 0) {
            resultBody = ByteUtil.add(resultBody, str.getBytes());
        }
        resultBody = buildExMsg(id0502, 0, 1, 2, resultBody);
        resultBody = ByteUtil.add(driving, resultBody);
        byte[] resultHead = makeHead(transparentInfo, false, 0, 0, 0, resultBody.length);
        return sticky(resultHead, resultBody);
    }

    /****
     *
     * @param parameter1
     * @param parameter2    定时拍照时间间隔
     * @param parameter3    照片上传设置
     * @param parameter4    是否报读附加消息
     * @param parameter5        熄火后停止学时计时的延时时间
     * @param parameter6       熄火后GNSS数据包上传间隔
     * @param parameter7    熄火后教练自动登出的延时时间
     * @param parameter8    重新验证身份时间
     * @param parameter9       教练跨校教学
     * @param parameter10       学员跨校学习
     * @param parameter11 响应平台同类消息时间间隔
     * @return
     */
    public static byte[] make0503(int parameter1,
                                  int parameter2,
                                  int parameter3,
                                  int parameter4,
                                  int parameter5,
                                  int parameter6,
                                  int parameter7,
                                  int parameter8,
                                  int parameter9,
                                  int parameter10,
                                  int parameter11) {

        byte[] resultBody = new byte[]{(byte) parameter1};            //定时拍照时间间隔
        resultBody = ByteUtil.add(resultBody, (byte) parameter2);      //照片上传设置
        resultBody = ByteUtil.add(resultBody, (byte) parameter3);      //是否报读附加消息
        resultBody = ByteUtil.add(resultBody, (byte) parameter4);       //是否报读附加消息
        resultBody = ByteUtil.add(resultBody, (byte) parameter5);           //熄火后停止学时计时的延时时间
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2WORD(parameter6));     //5	熄火后GNSS数据包上传间隔	WORD	单位：s，默认值3600，0表示不上传
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2WORD(parameter7));       //熄火后教练自动登出的延时时间
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2WORD(parameter8));           //重新验证身份时间
        resultBody = ByteUtil.add(resultBody, (byte) parameter9);           //重新验证身份时间
        resultBody = ByteUtil.add(resultBody, (byte) parameter10);           //重新验证身份时间
        resultBody = ByteUtil.add(resultBody, ByteUtil.int2WORD(parameter11));           //重新验证身份时间

        resultBody = buildExMsg(id0503, 0, 1, 2, resultBody);
        resultBody = ByteUtil.add(driving, resultBody);
        byte[] resultHead = makeHead(transparentInfo, false, 0, 0, 0, resultBody.length);
        return sticky(resultHead, resultBody);
    }

    public static byte[] make0401(byte infoType, byte personType) {
        byte[] resultBody = new byte[20];
        resultBody[0] = infoType;
        resultBody[1] = personType;
        resultBody = ByteUtil.add(resultBody, "420106198804290911".getBytes());
        resultBody = buildExMsg(id0401, 0, 1, 2, resultBody);
        resultBody = ByteUtil.add(driving, resultBody);
        byte[] resultHead = makeHead(transparentInfo, false, 0, 0, 0, resultBody.length);
        return sticky(resultHead, resultBody);
    }

    public static byte[] make0402(byte infoType, String id) {
        byte[] resultBody = new byte[20];
        resultBody[0] = infoType;
        if (infoType == 2 || infoType == 3 || infoType == 4 || infoType == 6) {
            resultBody = ByteUtil.add(resultBody, id.getBytes());
        }
        resultBody = buildExMsg(id0402, 0, 1, 2, resultBody);
        resultBody = ByteUtil.add(driving, resultBody);
        byte[] resultHead = makeHead(transparentInfo, false, 0, 0, 0, resultBody.length);
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
        byte[] resultHead = makeHead(findLocatInfoRequest, false, 0, 0, 0, resultBody.length); // 包头固定

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

        byte[] resultBody = ByteUtil.add(exMsgId, exMsgId);
        resultBody = ByteUtil.add(resultBody, getExCode());
        resultBody = ByteUtil.add(resultBody, terminalNum);
        resultBody = ByteUtil.add(resultBody, int2DWORD(para6.length));
        resultBody = ByteUtil.add(resultBody, para6);
        ///加密
        if (para4 == 2) {
            byte[] jiami = Encrypt.SHA256(resultBody,
                    ConstantInfo.terminalCertificate,
                    ByteUtil.getString(ConstantInfo.certificatePassword),
                    0);
            resultBody = ByteUtil.add(resultBody, jiami); // 校验码  时间戳用0
        }
//        ByteUtil.printHexString("加密最后的结果和前面", resultBody);
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
                case "8003":                //补传分包
                    HandMsgHelper.Class8003 class8003 = HandMsgHelper.getClass8003(messageBean.bodyBean);
                    break;
                case "8100":            //终端注册应答
                    if (messageBean.headBean.bodyLength == 3) {
                        switch (messageBean.bodyBean[2]) {
                            case 0:
                                break;
                            case 1:
                                RxBus.getInstance().post(RX_TTS_SPEAK, "车辆已被注册");
                                break;
                            case 2:
                                RxBus.getInstance().post(RX_TTS_SPEAK, "数据库中无该车辆");
                                break;
                            case 3:
                                RxBus.getInstance().post(RX_TTS_SPEAK, "终端已被注册");
                                break;
                            case 4:
                                RxBus.getInstance().post(RX_TTS_SPEAK, "数据库中无该终端");
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
                                }
                                int posIndex = 0;
                                ConstantInfo.requestWaterCode = new byte[2];
                                System.arraycopy(dataResult, posIndex, ConstantInfo.requestWaterCode, 0, ConstantInfo.requestWaterCode.length);       //应答流水号
                                posIndex = ConstantInfo.requestWaterCode.length;
                                ConstantInfo.result = new byte[1];
                                System.arraycopy(dataResult, posIndex, ConstantInfo.result, 0, ConstantInfo.result.length);         //结果
                                posIndex = posIndex + ConstantInfo.result.length;
                                ConstantInfo.platformNum = new byte[5];
                                System.arraycopy(dataResult, posIndex, ConstantInfo.platformNum, 0, ConstantInfo.platformNum.length);        //平台编号
                                posIndex = posIndex + ConstantInfo.platformNum.length;
                                ConstantInfo.institutionNumber = new byte[16];
                                System.arraycopy(dataResult, posIndex, ConstantInfo.institutionNumber, 0, ConstantInfo.institutionNumber.length);      //培训机构编号
                                posIndex = posIndex + ConstantInfo.institutionNumber.length;
                                ConstantInfo.terminalNum = new byte[16];
                                System.arraycopy(dataResult, posIndex, ConstantInfo.terminalNum, 0, ConstantInfo.terminalNum.length);          //终端编号
                                ByteUtil.printHexString("终端编号 : ", ConstantInfo.terminalNum);
                                posIndex = posIndex + ConstantInfo.terminalNum.length;
                                ConstantInfo.certificatePassword = new byte[12];
                                System.arraycopy(dataResult, posIndex, ConstantInfo.certificatePassword, 0, ConstantInfo.certificatePassword.length);      //证书口令
                                posIndex = posIndex + ConstantInfo.certificatePassword.length;
                                int passwordLength = dataResult.length - posIndex;
                                byte[] data0 = new byte[passwordLength];
                                System.arraycopy(dataResult, posIndex, data0, 0, data0.length);
                                ConstantInfo.terminalCertificate = ByteUtil.getString(data0);     //终端证书
                                midDatas = null;
                                RxBus.getInstance().post(RX_TTS_SPEAK, "终端注册成功");
                                WriteSettingHelper.saveRegistInfo();
                            }
                        }
                    }
                    break;
                case "8001":            //服务端通用应答
                    //7E 80 80 01 00 05 00 00 00 00 00 10 00 31 2B 0D 2A 00 36 00 03 01 1D 7E
                    if (messageBean.headBean.bodyLength == 5) {
                        TcpManager.getInstance().remove(messageBean.headBean.waterCode, messageBean.headBean.res8001);
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
                    HandMsgHelper.Class8103 class8103 = HandMsgHelper.getClass8103(messageBean.bodyBean);
                    RxBus.getInstance().post(Config.Config_RxBus.RX_SETTING_8103, class8103);
                    Log.w("class8103", class8103.toString());
                    break;
                case "8104":           //查询所有终端参数
                    RxBus.getInstance().post(Config.Config_RxBus.RX_SETTING_8104, messageBean.headBean.waterCode);
                    break;
                case "8105":
                    HandMsgHelper.Class8105 class8105 = HandMsgHelper.getClass8105(messageBean.bodyBean);
                    switch (class8105.commandId) {
                        case 1:
                            if (class8105.commandInfo != null) {
                                String info = ByteUtil.getString(class8105.commandInfo);
                                String url = info.substring(0, info.indexOf(";", 1));
                                Log.e("download", url);
                            }
                            break;
                        case 2:
                            break;
                        case 3:
                            break;
                        case 4:
                            break;
                        case 5:
                            break;
                        case 6:
                            break;
                        case 7:
                            break;
                    }
                    break;
                case "8106":           //8106和8104都应答0104
                    HandMsgHelper.Class8106 class8106 = HandMsgHelper.getClass8106(messageBean.bodyBean);
                    class8106.waterCode = messageBean.headBean.waterCode;
                    RxBus.getInstance().post(Config.Config_RxBus.RX_SETTING_8106, class8106);
                    break;

                case "8201":            //位置信息查询
                    TcpHelper.getInstance().sendLocationInfo();
                    break;

                case "8202":        //临时位置追踪控制
                    HandMsgHelper.Class8202_ class8202_ = HandMsgHelper.getClass8202_(messageBean.bodyBean);
                    RxBus.getInstance().post(RX_SETTING_8202_, class8202_);

                    break;
                case "8900":            //透传消息应答
                    messageBean.getThroughExpand(messageBean.bodyBean);
                    if (messageBean.throughExpand.type == driving) {        //驾培业务
                        switch (ByteUtil.bcd2Str(messageBean.throughExpand.id)) {
                            case "8101":                        //教练员登录
                                HandMsgHelper.Class8101 class8101 = HandMsgHelper.getClass8101(messageBean.throughExpand.data);
                                switch (Integer.valueOf(ByteUtil.bcdByte2bcdString(class8101.result))) {
                                    case 1:
                                        RxBus.getInstance().post(Config.Config_RxBus.RX_COACH_LOGINOK, "教练员登录成功");
                                        coachId = ByteUtil.getString(class8101.coachNum);
                                        break;
                                    case 2:
                                        RxBus.getInstance().post(RX_TTS_SPEAK, "无效的教练员编号");
                                        Config.isCoachLoginOK = false;
                                        break;
                                    case 3:
                                        RxBus.getInstance().post(RX_TTS_SPEAK, "准教车型不符");
                                        Config.isCoachLoginOK = false;
                                        break;
                                    case 9:
                                        RxBus.getInstance().post(RX_TTS_SPEAK, "其他错误");
                                        Config.isCoachLoginOK = false;
                                        break;
                                }
                                break;
                            case "8102":                //教练员登出
                                HandMsgHelper.Class8102 class8102 = HandMsgHelper.getClass8102(messageBean.throughExpand.data);
                                switch (Integer.valueOf(ByteUtil.bcdByte2bcdString(class8102.result))) {
                                    case 1:
                                        RxBus.getInstance().post(Config.Config_RxBus.RX_COACH_LOGOUTOK, "教练员登出成功");
                                        break;
                                    case 2:
                                        RxBus.getInstance().post(RX_TTS_SPEAK, "教练员登出失败");
                                        Config.isCoachLoginOK = false;
                                        break;
                                    case 9:
                                        RxBus.getInstance().post(RX_TTS_SPEAK, "教练员登出失败，其他错误");
                                        Config.isCoachLoginOK = false;
                                        break;
                                }
                                break;
                            case "8201":                    //学员登录
                                HandMsgHelper.Class8201 class8201 = HandMsgHelper.getClass8201(messageBean.throughExpand.data);
                                Logger.d(class8201.toString());
                                switch (class8201.result) {
                                    case 1:
                                        RxBus.getInstance().post(Config.Config_RxBus.RX_STUDENT_LOGINOK, class8201);
                                        break;
                                    case 2:
                                        RxBus.getInstance().post(RX_TTS_SPEAK, "无效的学员编号");
                                        break;
                                    case 3:
                                        RxBus.getInstance().post(RX_TTS_SPEAK, "禁止登录的学员");
                                        break;
                                    case 4:
                                        RxBus.getInstance().post(RX_TTS_SPEAK, "区域外教学提醒");
                                        break;
                                    case 5:
                                        RxBus.getInstance().post(RX_TTS_SPEAK, "准教车型与培训车型不符");
                                        break;
                                    case 9:
                                        RxBus.getInstance().post(RX_TTS_SPEAK, "其他错误");
                                        break;
                                }
                                break;
                            case "8202":                //学员登出
                                HandMsgHelper.Class8202 class8202 = HandMsgHelper.getClass8202(messageBean.throughExpand.data);
                                switch (class8202.result) {
                                    case 1:
                                        RxBus.getInstance().post(Config.Config_RxBus.RX_STUDENT_LOGOUTOK, "学员登出成功");
                                        break;
                                    case 2:
                                        RxBus.getInstance().post(RX_TTS_SPEAK, "学员登出失败");
                                        Config.isCoachLoginOK = false;
                                        break;
                                    case 9:
                                        RxBus.getInstance().post(RX_TTS_SPEAK, "学员登出失败，其他错误");
                                        Config.isCoachLoginOK = false;
                                        break;
                                }
                                break;
                            case "8205":            //先传0205-01  开始，然后传0205-
                                HandMsgHelper.Class8205 class8205 = HandMsgHelper.getClass8205(messageBean.throughExpand.data);
                                RxBus.getInstance().post(RX_SETTING_8205, class8205);
                                break;
                            case "8301":
                                HandMsgHelper.Class8301 class8301 = HandMsgHelper.getClass8301(messageBean.throughExpand.data);
                                RxBus.getInstance().post(RX_SETTING_8301, class8301);
                                break;
                            case "8302":
                                HandMsgHelper.Class8302 class8302 = HandMsgHelper.getClass8302(messageBean.throughExpand.data);
                                TcpHelper.getInstance().send0302();
                                RxBus.getInstance().post(RX_SETTING_8302, class8302);
                                break;
                            case "8304":        //上传指定照片
                                HandMsgHelper.Class8304 class8304 = HandMsgHelper.getClass8304(messageBean.throughExpand.data);
                                RxBus.getInstance().post(RX_SETTING_8304, class8304);
                                break;
                            case "8305":
                                HandMsgHelper.Class8305 class8305 = HandMsgHelper.getClass8305(messageBean.throughExpand.data);
//                                switch (class8305.code) {
//                                    case (byte) 0x00:
//                                        TcpHelper.getInstance().send0306(ConstantInfo.photoId, photoData);
//                                        break;
//                                    case (byte) 0x01:
//                                        TcpHelper.getInstance().send0306(ConstantInfo.photoId, photoData);
//                                        break;
//                                    case (byte) 0x09:
//                                        break;
//                                    case (byte) 0xff:
//                                        break;
//                                }
                                break;
                            case "8501":           //设置计时终端应用参数应答
                                HandMsgHelper.Class8501 class8501 = HandMsgHelper.getClass8501(messageBean.throughExpand.data);
                                Logger.w(class8501.toString());
                                RxBus.getInstance().post(Config.Config_RxBus.RX_SETTING_0501, class8501);
                                break;
                            case "8502":
                                HandMsgHelper.Class8502 class8502 = HandMsgHelper.getClass8502(messageBean.throughExpand.data);
                                RxBus.getInstance().post(Config.Config_RxBus.RX_SETTING_EMBARGOSTATE, class8502);
                                break;
                            case "8401":            //A.1.1.1.1　请求身份认证信息应答
                                HandMsgHelper.Class8401 class8401 = HandMsgHelper.getClass8401(messageBean.throughExpand.data);
                                TcpHelper.getInstance().send0401();
                                break;
                            case "8402":
                                HandMsgHelper.Class8402 class8402 = HandMsgHelper.getClass8402(messageBean.throughExpand.data);
                                switch (class8402.result) {
                                    case 0:
                                        RxBus.getInstance().post(RX_TTS_SPEAK, "请求编号成功");
                                        break;
                                    default:
                                        RxBus.getInstance().post(RX_TTS_SPEAK, "请求编号失败");
                                        break;
                                }
                                break;
                            case "8403":
                                HandMsgHelper.Class8403 class8403 = HandMsgHelper.getClass8403(messageBean.throughExpand.data);
                                break;
                            case "8503":            //A.1.1.1.1　查询计时终端应用参数
                                MSG.getInstance().loadSetting1();
                                TcpHelper.getInstance().send0503((byte) 0x00,
                                        ConstantInfo.PIC_INTV_min,        //定时拍照时间间隔
                                        ConstantInfo.UPLOAD_GBN,      //照片上传设置
                                        ConstantInfo.ADDMSG_YN,       //是否报读附加消息
                                        ConstantInfo.STOP_DELAY_TIME_min,
                                        ConstantInfo.STOP_GNSS_UPLOAD_INTV_sec,
                                        ConstantInfo.STOP_COACH_DELAY_TIME_min,
                                        ConstantInfo.USER_CHK_TIME_min,
                                        ConstantInfo.COACH_TRANS_YN,
                                        ConstantInfo.STU_TRANS_YN,
                                        ConstantInfo.DUP_MSG_REJECT_INTV_sec);
                                break;
                            default:
                                break;
                        }
                    }


                    break;

                default:
                    break;
            }
        }
    }

}
