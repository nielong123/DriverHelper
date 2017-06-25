package com.driverhelper.helper;

import com.driverhelper.config.Config;
import com.jaydenxiao.common.commonutils.PreferenceUtils;

/**
 * Created by Administrator on 2017/6/2.
 */

/****
 * 写到文件里的配置的帮助类
 */
public class WriteSettingHelper {

    public static boolean isFirstOpen() {
        return PreferenceUtils.getInstance().getSettingBool(Config.WriteSetting.ISFIRST, true);
    }

    public static void setFirstOpen(boolean isFirst) {
        PreferenceUtils.getInstance().setSettingBoolean(Config.WriteSetting.ISFIRST, isFirst);
    }

    public static String getTCP_IP() {
        return PreferenceUtils.getInstance().getSettingStr(Config.WriteSetting.TCP_IP, null);
    }

    public static void setTCP_IP(String ip) {
        PreferenceUtils.getInstance().setSettingString(Config.WriteSetting.TCP_IP, ip);
    }

    public static String getTCP_PORT() {
        return PreferenceUtils.getInstance().getSettingStr(Config.WriteSetting.TCP_PORT, null);
    }

    public static void setTCP_PORT(String ip) {
        PreferenceUtils.getInstance().setSettingString(Config.WriteSetting.TCP_PORT, ip);
    }

    public static String getHEART_SPACE() {
        return PreferenceUtils.getInstance().getSettingStr(Config.WriteSetting.HEART_SPACE, null);
    }

    public static void setHEART_SPACE(String heartSpace) {
        PreferenceUtils.getInstance().setSettingString(Config.WriteSetting.HEART_SPACE, heartSpace);
    }

    public static String getHTTP_URL() {
        return PreferenceUtils.getInstance().getSettingStr(Config.WriteSetting.HTTP_URL, null);
    }

    public static void setHTTP_URL(String httpUrl) {
        PreferenceUtils.getInstance().setSettingString(Config.WriteSetting.HTTP_URL, httpUrl);
    }

    public static String getVEHICLE_NUMBER() {
        return PreferenceUtils.getInstance().getSettingStr(Config.WriteSetting.VEHICLE_NUMBER, null);
    }

    public static void setVEHICLE_NUMBER(String vehicleNumber) {
        PreferenceUtils.getInstance().setSettingString(Config.WriteSetting.VEHICLE_NUMBER, vehicleNumber);
    }

    public static String getDEVICE_CODE() {
        return PreferenceUtils.getInstance().getSettingStr(Config.WriteSetting.DEVICE_CODE, null);
    }

    public static void setDEVICE_CODE(String device_code) {
        PreferenceUtils.getInstance().setSettingString(Config.WriteSetting.DEVICE_CODE, device_code);
    }

    public static String getVEHICLE_COLOR() {
        return PreferenceUtils.getInstance().getSettingStr(Config.WriteSetting.VEHICLE_COLOR, "");
    }

    public static void setVEHICLE_COLOR(String vehicle_color) {
        PreferenceUtils.getInstance().setSettingString(Config.WriteSetting.VEHICLE_COLOR, vehicle_color);
    }

    public static String getTCP_TIMEOUT() {
        return PreferenceUtils.getInstance().getSettingStr(Config.WriteSetting.TIME_OUT, "");
    }

    public static void setTCP_TIMEOUT(String timeOut) {
        PreferenceUtils.getInstance().setSettingString(Config.WriteSetting.TIME_OUT, timeOut);
    }

    public static String getWATER_CODE() {
        return PreferenceUtils.getInstance().getSettingStr(Config.WriteSetting.WATER_CODE, "0000");
    }

    public static void setWATER_CODE(String waterCode) {
        PreferenceUtils.getInstance().setSettingString(Config.WriteSetting.WATER_CODE, waterCode);
    }
}
