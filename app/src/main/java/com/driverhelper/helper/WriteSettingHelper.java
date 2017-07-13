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

    /***
     * 获取TCP的ip
     * @return
     */
    public static String getTCP_IP() {
        return PreferenceUtils.getInstance().getSettingStr(Config.WriteSetting.TCP_IP, null);
    }

    public static void setTCP_IP(String ip) {
        PreferenceUtils.getInstance().setSettingString(Config.WriteSetting.TCP_IP, ip);
    }

    /***
     * 获取TCP的端口信息
     * @return
     */
    public static String getTCP_PORT() {
        return PreferenceUtils.getInstance().getSettingStr(Config.WriteSetting.TCP_PORT, null);
    }

    public static void setTCP_PORT(String ip) {
        PreferenceUtils.getInstance().setSettingString(Config.WriteSetting.TCP_PORT, ip);
    }

    /***
     * 获取心跳包的间隔
     * @return
     */
    public static String getHEART_SPACE() {
        return PreferenceUtils.getInstance().getSettingStr(Config.WriteSetting.HEART_SPACE, null);
    }

    public static void setHEART_SPACE(String heartSpace) {
        PreferenceUtils.getInstance().setSettingString(Config.WriteSetting.HEART_SPACE, heartSpace);
    }

    /*****
     * 获取服务的URL
     * @return
     */
    public static String getHTTP_URL() {
        return PreferenceUtils.getInstance().getSettingStr(Config.WriteSetting.HTTP_URL, null);
    }

    public static void setHTTP_URL(String httpUrl) {
        PreferenceUtils.getInstance().setSettingString(Config.WriteSetting.HTTP_URL, httpUrl);
    }

    /***
     * 获取车牌号
     * @return
     */
    public static String getVEHICLE_NUMBER() {
        return PreferenceUtils.getInstance().getSettingStr(Config.WriteSetting.VEHICLE_NUMBER, null);
    }

    public static void setVEHICLE_NUMBER(String vehicleNumber) {
        PreferenceUtils.getInstance().setSettingString(Config.WriteSetting.VEHICLE_NUMBER, vehicleNumber);
    }

    /***
     * 获取设备编号
     * @return
     */
    public static String getDEVICE_CODE() {
        return PreferenceUtils.getInstance().getSettingStr(Config.WriteSetting.DEVICE_CODE, null);
    }

    public static void setDEVICE_CODE(String device_code) {
        PreferenceUtils.getInstance().setSettingString(Config.WriteSetting.DEVICE_CODE, device_code);
    }

    /***
     * 获取车辆颜色
     * @return
     */
    public static String getVEHICLE_COLOR() {
        return PreferenceUtils.getInstance().getSettingStr(Config.WriteSetting.VEHICLE_COLOR, "");
    }

    public static void setVEHICLE_COLOR(String vehicle_color) {
        PreferenceUtils.getInstance().setSettingString(Config.WriteSetting.VEHICLE_COLOR, vehicle_color);
    }

    /***
     * 获取链接的超时时间
     * @return
     */
    public static String getTCP_TIMEOUT() {
        return PreferenceUtils.getInstance().getSettingStr(Config.WriteSetting.TIME_OUT, "");
    }

    public static void setTCP_TIMEOUT(String timeOut) {
        PreferenceUtils.getInstance().setSettingString(Config.WriteSetting.TIME_OUT, timeOut);
    }

    /***
     * 获取消息流水号
     * @return
     */
    public static String getWATER_CODE() {
        return PreferenceUtils.getInstance().getSettingStr(Config.WriteSetting.WATER_CODE, "0000");
    }

    public static void setWATER_CODE(String waterCode) {
        PreferenceUtils.getInstance().setSettingString(Config.WriteSetting.WATER_CODE, waterCode);
    }

    /***
     * 获取考试种类  C1  C2  C3
     * @return
     */
    public static String getEXAM_TYPE() {
        return PreferenceUtils.getInstance().getSettingStr(Config.WriteSetting.EXAM_TYPE, "C1");
    }

    public static void setEXAM_TYPE(String examType) {
        PreferenceUtils.getInstance().setSettingString(Config.WriteSetting.EXAM_TYPE, examType);
    }

    /***
     * 获取考试科目 科目2，科目3
     * @return
     */
    public static String getEXAM_SUBJECTS() {
        return PreferenceUtils.getInstance().getSettingStr(Config.WriteSetting.EXAM_TYPE, "科目2");
    }

    public static void setEXAM_SUBJECTS(String examSubjects) {
        PreferenceUtils.getInstance().setSettingString(Config.WriteSetting.EXAM_TYPE, examSubjects);
    }
}
