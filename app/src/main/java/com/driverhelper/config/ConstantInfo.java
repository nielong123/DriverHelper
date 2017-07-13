package com.driverhelper.config;

import com.driverhelper.helper.WriteSettingHelper;

/**
 * Created by Administrator on 2017/6/6.
 */

public class ConstantInfo {

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
//    public static byte[] terminalTYPE = {(byte) 0x43, (byte) 0x37, (byte) 0x30, (byte) 0x33, (byte) 0x44, (byte) 0x4D, (byte) 0x00, (byte) 0x00, (byte) 0x00,
//            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};

    public static String strTerminalSerial = "1001001";
//    public static byte[] terminalSerial = {(byte) 0x31, (byte) 0x30, (byte) 0x30, (byte) 0x31, (byte) 0x30, (byte) 0x30, (byte) 0x31};

    public static String strIMEI = "100221235053037";
//    public static byte[] IMEI = {(byte) 0x31, (byte) 0x30, (byte) 0x30, (byte) 0x32, (byte) 0x32, (byte) 0x31, (byte) 0x32, (byte) 0x33, (byte) 0x35, (byte) 0x30, (byte) 0x35, (byte) 0x33, (byte) 0x30, (byte) 0x33, (byte) 0x37};
    public static String deviceNum = "15070000001";    //设备号
    public static String vehicleColor;           //车辆颜色
    public static String vehicleNum;            //车牌号


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
