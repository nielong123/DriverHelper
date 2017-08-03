package com.driverhelper.config;

import com.driverhelper.beans.QRbean;
import com.driverhelper.helper.WriteSettingHelper;

/**
 * Created by Administrator on 2017/6/6.
 */

public class ConstantInfo {

    public static boolean isDebug = true;

    /***
     * 省代码
     */
    public static byte[] province = {(byte) 0x00, (byte) 0x2a};   //test

    public static byte[] city = {(byte) 0x00, (byte) 0x6f};       //test

    /***
     * 制造商id
     */
    public static byte[] makerID = {(byte) 0x48, (byte) 0x5a, (byte) 0x4C, (byte) 0x59, (byte) 0x54};

    /***
     * 终端型号
     */
    public static String strTerminalTYPE = "43373033444D";

    public static String strTerminalSerial = "1001001";

    public static String strIMEI = "100221235053037";
    public static String terminalPhoneNumber = "15070000001";    //终端手机号
    public static String vehicleColor;           //车辆颜色
    public static String vehicleNum;            //车牌号

    public static byte[] requestWaterCode = new byte[2];      //应答流水号
    public static byte[] result = new byte[1];                  //结果
    public static byte[] platformNum = new byte[5];                        //平台编号
    public static byte[] institutionNumber = new byte[16];        //培训机构编号
    public static byte[] terminalNum = new byte[16];       //计时终端编号
    public static byte[] certificatePassword = new byte[12];       //证书口令;
    public static String terminalCertificate = "";         //终端证书
    public static String coachNum;      //教练员编号

    public static long studyTimeThis = 0;             //学习的时间
    public static long studyDistanceThis = 0;             //学习的时间
    public static byte[] classId;             //课堂id


    public static class StudentInfo{
        public static String studentNum;        //学员编号
        public static int totleTime;            //需要完成的总学时
        public static int finishedTime;               //已完成的学时
        public static int totleMileage;             //需要完成的总里程
        public static int finishedMileage;            //已完成的里程
    }



    public static QRbean qRbean;

    /***
     * 重新读取设置数据
     */
    public static void reloadInfo() {
        switch (WriteSettingHelper.getVEHICLE_COLOR()) {
            case "蓝色":
                vehicleColor = "01";
                break;
            case "黄色":
                vehicleColor = "02";
                break;
            case "黑色":
                vehicleColor = "03";
                break;
            case "白色":
                vehicleColor = "04";
                break;
            default:
                vehicleColor = "09";
                break;
        }

        vehicleNum = WriteSettingHelper.getVEHICLE_NUMBER();
    }
}
