package com.driverhelper.helper;

import com.driverhelper.config.Config;
import com.driverhelper.config.ConstantInfo;
import com.jaydenxiao.common.commonutils.PreferenceUtils;

import static com.driverhelper.config.ConstantInfo.institutionNumber;
import static com.driverhelper.config.ConstantInfo.platformNum;

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

    /*****
     * 教练员编号
     * @return
     */
    public static String getCOACHNUM() {
        return PreferenceUtils.getInstance().getSettingStr(Config.WriteSetting.COACHNUM, null);
    }

    public static void setCOACHNUM(String coachnum) {
        PreferenceUtils.getInstance().setSettingString(Config.WriteSetting.COACHNUM, coachnum);
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
     * 获取透传消息编号
     * @return
     */
    public static String getEX_CODE() {
        return PreferenceUtils.getInstance().getSettingStr(Config.WriteSetting.EX_CODE, "0000");
    }

    public static void setEX_CODE(String waterCode) {
        PreferenceUtils.getInstance().setSettingString(Config.WriteSetting.EX_CODE, waterCode);
    }

    /***
     * 获取学时id
     * @return
     */
    public static String getSTUDY_ID() {
        return PreferenceUtils.getInstance().getSettingStr(Config.WriteSetting.STUDY_CODE, "0000");
    }

    public static void setSTUDY_ID(String studyId) {
        PreferenceUtils.getInstance().setSettingString(Config.WriteSetting.STUDY_CODE, studyId);
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

    /***
     * 获取平台编号
     * @return
     */
    public static byte[] getPLATFORMNUM() {
        byte[] data = PreferenceUtils.getInstance().getSettingBytes(Config.WriteSetting.PLATFORMNUM);
        if (data == null) {
            return new byte[platformNum.length];
        }
        return data;
    }

    public static void setPLATFORMNUM(byte[] examSubjects) {
        PreferenceUtils.getInstance().setSettingBytes(Config.WriteSetting.PLATFORMNUM, examSubjects);
    }

    /***
     * 获取培训机构编号
     * @return
     */
    public static byte[] getINSTITUTIONNUMBER() {
        byte[] data = PreferenceUtils.getInstance().getSettingBytes(Config.WriteSetting.INSTITUTIONNUMBER);
        if (data == null) {
            return new byte[ConstantInfo.institutionNumber.length];
        }
        return data;
    }

    public static void setINSTITUTIONNUMBER(byte[] institutionnumber) {
        PreferenceUtils.getInstance().setSettingBytes(Config.WriteSetting.INSTITUTIONNUMBER, institutionnumber);
    }

    /***
     * 获取终端机构编号
     * @return
     */
    public static byte[] getTERMINALNUM() {
        byte[] data = PreferenceUtils.getInstance().getSettingBytes(Config.WriteSetting.TERMINALNUM);
        if (data == null) {
            return new byte[ConstantInfo.terminalNum.length];
        }
        return data;
    }

    public static void setTERMINALNUM(byte[] terminalnum) {
        PreferenceUtils.getInstance().setSettingBytes(Config.WriteSetting.TERMINALNUM, terminalnum);
    }

    /***
     * 获取证书口令
     * @return
     */
    public static byte[] getCERTIFICATEPASSWORD() {
        byte[] data = PreferenceUtils.getInstance().getSettingBytes(Config.WriteSetting.CERTIFICATEPASSWORD);
        if (data == null) {
            return new byte[ConstantInfo.certificatePassword.length];
        }
        return data;
    }

    public static void setCERTIFICATEPASSWORD(byte[] certificatePassword) {
        PreferenceUtils.getInstance().setSettingBytes(Config.WriteSetting.CERTIFICATEPASSWORD, certificatePassword);
    }

    /***
     * 获取证书口令
     * @return
     */
    public static String getTERMINALCERTIFICATE() {
        return PreferenceUtils.getInstance().getSettingStr(Config.WriteSetting.TERMINALCERTIFICATE, "");
    }

    public static void setTERMINALCERTIFICATE(String terminalCertificate) {
        PreferenceUtils.getInstance().setSettingString(Config.WriteSetting.TERMINALCERTIFICATE, terminalCertificate);
    }


    /***
     * 保存终端注册后返回的信息
     */
    public static void saveRegistInfo() {
        WriteSettingHelper.setINSTITUTIONNUMBER(institutionNumber);
        WriteSettingHelper.setPLATFORMNUM(ConstantInfo.platformNum);
        WriteSettingHelper.setTERMINALNUM(ConstantInfo.terminalNum);
        WriteSettingHelper.setCERTIFICATEPASSWORD(ConstantInfo.certificatePassword);
        WriteSettingHelper.setTERMINALCERTIFICATE(ConstantInfo.terminalCertificate);
    }

    public static void loadRegistInfo() {
        ConstantInfo.institutionNumber = WriteSettingHelper.getINSTITUTIONNUMBER();
        ConstantInfo.platformNum = WriteSettingHelper.getPLATFORMNUM();
        ConstantInfo.terminalNum = WriteSettingHelper.getTERMINALNUM();
        ConstantInfo.certificatePassword = WriteSettingHelper.getCERTIFICATEPASSWORD();
        ConstantInfo.terminalCertificate = WriteSettingHelper.getTERMINALCERTIFICATE();
        ConstantInfo.coachNum = WriteSettingHelper.getCOACHNUM();
    }


}
