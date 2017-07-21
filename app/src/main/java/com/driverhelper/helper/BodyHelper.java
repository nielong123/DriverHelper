package com.driverhelper.helper;


import android.util.Log;
import android.widget.Toast;

import com.driverhelper.beans.MessageBean;
import com.driverhelper.config.Config;
import com.driverhelper.config.ConstantInfo;
import com.driverhelper.config.TcpBody;
import com.driverhelper.other.encrypt.Encrypt;
import com.driverhelper.other.jiaminew.TestSignAndVerify;
import com.driverhelper.utils.ByteUtil;
import com.jaydenxiao.common.baserx.RxBus;
import com.jaydenxiao.common.commonutils.ToastUitl;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.driverhelper.config.ConstantInfo.strTerminalSerial;
import static com.driverhelper.config.ConstantInfo.terminalNum;
import static com.driverhelper.config.ConstantInfo.vehicleColor;
import static com.driverhelper.config.ConstantInfo.vehicleNum;
import static com.driverhelper.config.TcpBody.MessageID.register;
import static com.driverhelper.config.TcpBody.VERSION_CODE;

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
     * 终端鉴权 加密数据长度有问题
     */
    public static byte[] makeAuthentication() {

        long time = System.currentTimeMillis() / 1000;
        byte[] timeByte = ByteUtil.str2Bcd(ByteUtil.decString2hexString(time));
        byte[] resultBody = ByteUtil.str2Bcd(ByteUtil
                .decString2hexString(time)); // 时间戳
        try {
//            resultBody = ByteUtil.add(resultBody,
//                    TestSignAndVerify.encryption(ByteUtil.add(terminalNum, timeByte),
//                            ConstantInfo.terminalCertificate,
//                            ByteUtil.getString(ConstantInfo.certificatePassword),
//                            time)); // 校验码
//            resultBody = ByteUtil.add(resultBody,
//                    Encrypt.SHA256(ByteUtil.add(terminalNum, timeByte),
//                            ConstantInfo.terminalCertificate,
//                            ByteUtil.getString(ConstantInfo.certificatePassword),
//                            time)); // 校验码
            resultBody = ByteUtil.add(resultBody,
                    Encrypt.SHA256("6972159358655724".getBytes(),
                            "MIIKLQIBAzCCCfcGCSqGSIb3DQEHAaCCCegEggnkMIIJ4DCCCdwGCSqGSIb3DQEHAaCCCc0EggnJMIIJxTCCBHwGCyqGSIb3DQEMCgEDoIIEDjCCBAoGCiqGSIb3DQEJFgGgggP6BIID9jCCA/IwggLaoAMCAQICBgFX0YCGLDANBgkqhkiG9w0BAQsFADBjMQswCQYDVQQGEwJDTjERMA8GA1UECh4IVv1OpE/hkBoxDDAKBgNVBAsTA1BLSTEzMDEGA1UEAx4qAE8AcABlAHIAYQB0AGkAbwBuACAAQwBBACAAZgBvAHIAIFb9TqRP4ZAaMB4XDTE2MTAxNzA3MTcyM1oXDTI2MTAxNTA3MTcyM1owQTELMAkGA1UEBhMCQ04xFTATBgNVBAoeDIuhZfZ+yHrvi8FOZjEbMBkGA1UEAxMSeHcwMDE3MTIyNTU1NTkyODM0MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAk4tZw2NoQrO3mc4g4396f4Kmol9+vs75STqTmex4bSi9nxNvMhImOxt/Q9hG74eOkMlj0e9ci3H5OV3SwwlMiBORv8HQtViaitkE5EVXwaLK2ftO2tQ5EK3lQU8pptZuVdrLFgTY/GmrVESQO9rZEzJL8QSx5zh9lGoCkKQ5Lvn6T5XxeD4bs9rfuhzHgP0GL1IP8pAp2A2TMaQLIJP7HAfWWCavVE//O0iNb5g+YvtKc2Tqw0unYGfuqRjJGEi3pB2O8751FgwILHwWbSxMiFAYyTscDzp33OTLzLcGjIX5Kxc2QONFo8gqlzw6wEyuGu33uiFkcn2UFkk0A90YZwIDAQABo4HNMIHKMB8GA1UdIwQYMBaAFFyqjorSazJMB8Bjazw0/H5+sq57MB0GA1UdDgQWBBRVtZp4awiO5WK6NHwzhOxWT27wBjBdBgNVHR8EVjBUMFKgUKBOpEwwSjEXMBUGA1UEAx4OAEMAUgBMADEAMAAwADAxDzANBgNVBAseBgBDAFIATDERMA8GA1UECh4IVv1OpE/hkBoxCzAJBgNVBAYTAkNOMAkGA1UdEwQCMAAwCwYDVR0PBAQDAgD4MBEGCWCGSAGG+EIBAQQEAwIAoDANBgkqhkiG9w0BAQsFAAOCAQEARuBa8M/dGec/IysTACdpAbxMhQSnXrX7K3iy/yD9g2p2rgTTq9Y4gRGmCUbe03GF8joQUO6o4dHKoDMoaCXh+63/jCujsnKxPoUliaWNEKfMWeNL+V1CcEbksGQ60GUEluAzOY3AGk9+yXkkP1/jJlgFkYLGuXs0Zm6Rr3d78+CFnx4NmTDLG9678j9QZdjfTmU+nTgwlRyD9qGXsRlpbXIXqqrH3g/18EYdZh5qUnN5+S5tPNH57WeMlHvhb7AY7VY66koXkH0krWDUbIwSLH19ABOE/80rihCX+OxvOZ9Ywnp6OzpH6iyJGqcwlTRxgUlAX6dpx1ynK/bKGEG6kzFbMBkGCSqGSIb3DQEJFDEMHgoAQQBMAEkAQQBTMD4GCSqGSIb3DQEJFTExBC9mNiA0NiA1OCA4ZiBjYSBmMyA0MiBiMyA1NiAwMCBkMSBiYyAyZCBlMyA5NSBkYjCCBUEGCyqGSIb3DQEMCgECoIIE7jCCBOowHAYKKoZIhvcNAQwBBjAOBAi/bKP7ONpSPgICBAAEggTImUXkXeK67Tj4o+Fwp9FO9+hP+3ljyMBCG5qLbtMv759Aj10+cVo0r2tmsy2JiqX7maY04+wJoYjaNbF6WF/sc/NfaJ5zc5/hmR7M29FxuUXm/eNm+ww8TO5mqEcOEayRS2RLgEDQUKKbyTaouB0bfwHj+6M7r7+NgQsQG9wx1AfXMm2dqRrucyWI0eXeoTjerOgq/WEdnvbNnIRrnEG8iHnz05r9abRaeKlzM/wyU19NepjwOcRkEg/fi8y9rHroloC/BbbOc4It0vqbGNrB+0WsrGqrA6P9h7BLaIfHkK6v3493j6ngeU1cNa9sI/vBq+hYp9Zly9wwYoaNKIP+db3njBBHaztSWh/gcoiOTUwaok668QSPVcUM02BLqwLfOqtIXTf1b3EmrGNtuvMja0RaTbjeIiPtaLEU8V8KtbwDSDbWc+HcgVCVyaBbRhWjBL3dqi3GuYSwFyaZwHLZWTSfpyKaoqAsKTou4qx+cKptrbHNS3ne3pAVSx86mPztFATrbg3y19rVzp/2G04cDpMEq5/OSPbFejMb+Zvz3xAb0yUCizk6K+b+GViZgE7eV+YvN9pLCITyqCvj8mJBjb64gT+CNU30InvZSgImWuJaygoFJkKjIErr9n7gXW3NNUHwG9wBdE2Sehvnd6GLoXadTLYqQakJ7htb8VeWnLCoxyfgI/r5g75NwqMXbWtiX6rb2AJKHPToAGFnRIV0F7tP11/yy7Nuuk/q6qrB5onlelfYqDqf2LMtj1OV7YwBJkBD90w1ZpSP1RZYbsq8XIHJJkiUJmKQPze5+B21Q5edD5O52II64042OOnz9/yWCnSVughDnYP/30Aa6ysEm6RWmWQBcNX8yjlVijZaQaTzO0TGbTCOyrl8sUJmInAAqu1j+Nko7SllkjSBpp3LFoAnxUlf6hidUYoqa3H9wxYIBELc7MrP1QKJS6PFwuU8XFRMOJV1VEUJVbTiHx1Ey/jI1RJfgbF1e/JeBQLRujrhwenpKUqvryga6hxDZu4HJig08qS7JTOdwwwwVJsWSC+53Gt7JdgrY41saBSvcedW4GqFJtAn1cSlQfITfo0IV5mz8uwCr7r4HCzArZQ+vKNpuHynGA8towab9uMXTAEnJdywVbd/FYY6b4DQDMWXUrzPzY4XfOq6P/zFMA1+t87yhi5M3pviWss0qWlqVEkNdcRztdkWfKu16ST3bEyOoSvzs0d7FA99U6wsqMazCs4bxl7JB8SLrfD2JM6CZm+hF8PVpAl6vOZnb+7QIYa6brWc8s3DtVCyzGbcdJXuGH9LDle8FiIpx3tDQRE6upyQBMCHQzILhLKW82nhK82Ofi4CYb8G2PN302/mJKgQMwEjsGDbuf+HB56Ii1A766xq7z0mlK8XDwTy2kE1pz0A+MQLyfAw44Vkafn7wKq31bQUNeJ+joxkPrFoK5VZcRyo/ZE47WFdb5qveD6OArgvlzcUpK03sdIojKlzVPOSixjitOtFPOT8xnUWyj1iD/h2FuqGUEtVX8QQQiST4Nly4FnURDriOHCmdALvg6ZAhPDqXtC05GnTE8bmOckxFRlyvF+VdQB7HHb9KzQ4anffP/ZAhB4XptZXYsc442Fki5VAQRprmS8hMUAwPgYJKoZIhvcNAQkVMTEEL2Y2IDQ2IDU4IDhmIGNhIGYzIDQyIGIzIDU2IDAwIGQxIGJjIDJkIGUzIDk1IGRiMC0wITAJBgUrDgMCGgUABBRdlw2Gdlh49Q9HcPUEM7uIRGqYBgQI3i3mMb2wlC8=",
                            "GgSiLEtTNDS2",
                            1500433671)); // 校验码
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

    /****
     * 终端登录
     *
     * @param terminalNumber
     * @param terminalNumberPwd
     * @param insertCode
     * @return
     */
    public static byte[] makeLogin(String terminalNumber,
                                   String terminalNumberPwd, String insertCode) {
        byte[] resultBody = ByteUtil.hexString2BCD(terminalNumber); // 终端编号
        resultBody = ByteUtil.add(resultBody,
                ByteUtil.hexString2BCD(terminalNumberPwd)); // 终端密码
        resultBody = ByteUtil.add(resultBody,
                ByteUtil.hexString2BCD(insertCode)); // 平台接入码

        int bodyLength = resultBody.length;

        byte[] resultHead = makeHead(TcpBody.MessageID.login, false, 0, bodyLength);
        byte[] result = ByteUtil.addXor(ByteUtil.add(resultHead, resultBody));
        result = ByteUtil.addEND(result);
        result = ByteUtil.checkMark(result);
        ByteUtil.printHexString(result);

        return result;
    }

    /****
     * 终端登出
     *
     * @param terminalNumber
     * @param terminalNumberPwd
     * @return
     */
    public static byte[] makeLogout(String terminalNumber,
                                    String terminalNumberPwd) {
        byte[] resultBody = ByteUtil.hexString2BCD(terminalNumber); // 终端编号
        resultBody = ByteUtil.add(resultBody,
                ByteUtil.hexString2BCD(terminalNumberPwd)); // 终端密码

        int bodyLength = resultBody.length;

        byte[] resultHead = makeHead(TcpBody.MessageID.logout, false, 0, bodyLength);
        byte[] result = ByteUtil.addXor(ByteUtil.add(resultHead, resultBody));
        result = ByteUtil.addEND(result);
        result = ByteUtil.checkMark(result);
        ByteUtil.printHexString(result);

        return result;
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

    static String TAG = "ReceiveInfo";

    static List<byte[]> midDatas;
    static int index, totle;

    public static void handleReceiveInfo(byte[] data) {
        if (ByteUtil.checkXOR(data)) {
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
                            midDatas = new ArrayList<byte[]>();
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
                                    ByteUtil.printHexString(midData);
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
                                posIndex = posIndex + ConstantInfo.terminalNum.length;
                                System.arraycopy(dataResult, posIndex, ConstantInfo.certificatePassword, 0, ConstantInfo.certificatePassword.length);      //证书口令
                                posIndex = posIndex + ConstantInfo.certificatePassword.length;
                                int passwordLength = dataResult.length - posIndex;
                                byte[] data0 = new byte[passwordLength];
                                System.arraycopy(dataResult, posIndex, data0, 0, data0.length);
                                ConstantInfo.terminalCertificate = ByteUtil.getString(data0);     //终端证书
//                                ByteUtil.printHexString(dataResult);
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
            }
        }
    }

}
