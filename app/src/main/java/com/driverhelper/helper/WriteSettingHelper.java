package com.driverhelper.helper;

import com.driverhelper.config.Config;
import com.driverhelper.config.ConstantInfo;
import com.driverhelper.utils.ByteUtil;
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
    public static int getWATER_CODE() {
        return PreferenceUtils.getInstance().getSettingInt(Config.WriteSetting.WATER_CODE, 0);
    }

    public static void setWATER_CODE(int waterCode) {
        PreferenceUtils.getInstance().setSettingInt(Config.WriteSetting.WATER_CODE, waterCode);
    }

    /***
     * 获取透传消息编号
     * @return
     */
    public static int getEX_CODE() {
        return PreferenceUtils.getInstance().getSettingInt(Config.WriteSetting.EX_CODE, 0);
    }

    public static void setEX_CODE(int waterCode) {
        PreferenceUtils.getInstance().setSettingInt(Config.WriteSetting.EX_CODE, waterCode);
    }

    /***
     * 获取学时id
     * @return
     */
    public static int getSTUDY_ID() {
        return PreferenceUtils.getInstance().getSettingInt(Config.WriteSetting.STUDY_CODE, 0);
    }

    public static void setSTUDY_ID(int studyId) {
        PreferenceUtils.getInstance().setSettingInt(Config.WriteSetting.STUDY_CODE, studyId);
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
     *   是否禁训
     * @return
     */
    public static boolean getEMBARGO() {
        return PreferenceUtils.getInstance().getSettingBool(Config.WriteSetting.EMBARGO, false);
    }

    public static void setEMBARGO(boolean isEmbargo) {
        PreferenceUtils.getInstance().setSettingBoolean(Config.WriteSetting.EMBARGO, isEmbargo);
    }

    /***
     * 禁训内容
     * @return
     */
    public static String getEMBARGOSTR() {
        return PreferenceUtils.getInstance().getSettingStr(Config.WriteSetting.EMBARGODSTR, "");
    }

    public static void setEMBARGOSTR(String str) {
        PreferenceUtils.getInstance().setSettingString(Config.WriteSetting.EMBARGODSTR, str);
    }

    /***
     * 定时拍照时间间隔
     * @return
     */
    public static int getPIC_INTV_min() {
        return PreferenceUtils.getInstance().getSettingInt(Config.WriteSetting.PIC_INTV_min, 15);
    }

    public static void setPIC_INTV_min(int PIC_INTV_min) {
        PreferenceUtils.getInstance().setSettingInt(Config.WriteSetting.PIC_INTV_min, PIC_INTV_min);
    }

    /***
     * 照片上传设置
     * @return
     */
    public static int getUPLOAD_GBN() {
        return PreferenceUtils.getInstance().getSettingInt(Config.WriteSetting.UPLOAD_GBN, 15);
    }

    public static void setUPLOAD_GBN(int UPLOAD_GBN) {
        PreferenceUtils.getInstance().setSettingInt(Config.WriteSetting.UPLOAD_GBN, UPLOAD_GBN);
    }

    /***
     * 是否报读
     * @return
     */
    public static int getADDMSG_YN() {
        return PreferenceUtils.getInstance().getSettingInt(Config.WriteSetting.ADDMSG_YN, 1);
    }

    public static void setADDMSG_YN(byte ADDMSG_YN) {
        PreferenceUtils.getInstance().setSettingInt(Config.WriteSetting.ADDMSG_YN, ADDMSG_YN);
    }

    /****
     * 熄火后停止学时计时的延时时间min
     * @return
     */
    public static int getSTOP_DELAY_TIME_min() {
        return PreferenceUtils.getInstance().getSettingInt(Config.WriteSetting.STOP_DELAY_TIME_min, 0);
    }

    public static void setSTOP_DELAY_TIME_min(int STOP_DELAY_TIME_min) {
        PreferenceUtils.getInstance().setSettingInt(Config.WriteSetting.STOP_DELAY_TIME_min, STOP_DELAY_TIME_min);
    }

    /****
     * 熄火后GNSS停止学时计时的延时时间s
     * @return
     */
    public static int getSTOP_GNSS_UPLOAD_INTV_sec() {
        return PreferenceUtils.getInstance().getSettingInt(Config.WriteSetting.STOP_GNSS_UPLOAD_INTV_sec, 3600);
    }

    public static void setSTOP_GNSS_UPLOAD_INTV_sec(int STOP_GNSS_UPLOAD_INTV_sec) {
        PreferenceUtils.getInstance().setSettingInt(Config.WriteSetting.STOP_GNSS_UPLOAD_INTV_sec, STOP_GNSS_UPLOAD_INTV_sec);
    }

    /****
     * 熄火后教练自动登出的延时时间
     * @return
     */
    public static int getSTOP_COACH_DELAY_TIME_min() {
        return PreferenceUtils.getInstance().getSettingInt(Config.WriteSetting.STOP_COACH_DELAY_TIME_min, 0);
    }

    public static void setSTOP_COACH_DELAY_TIME_min(int STOP_COACH_DELAY_TIME_min) {
        PreferenceUtils.getInstance().setSettingInt(Config.WriteSetting.STOP_COACH_DELAY_TIME_min, STOP_COACH_DELAY_TIME_min);
    }

    /****
     * 重新验证身份时间
     * @return
     */
    public static int getUSER_CHK_TIME_min() {
        return PreferenceUtils.getInstance().getSettingInt(Config.WriteSetting.USER_CHK_TIME_min, 0);
    }

    public static void setUSER_CHK_TIME_min(int USER_CHK_TIME_min) {
        PreferenceUtils.getInstance().setSettingInt(Config.WriteSetting.USER_CHK_TIME_min, USER_CHK_TIME_min);
    }

    /***
     * 教练跨校教学
     * @return
     */
    public static int getCOACH_TRANS_YN() {
        return PreferenceUtils.getInstance().getSettingInt(Config.WriteSetting.COACH_TRANS_YN, 2);
    }

    public static void setCOACH_TRANS_YN(int COACH_TRANS_YN) {
        PreferenceUtils.getInstance().setSettingInt(Config.WriteSetting.COACH_TRANS_YN, COACH_TRANS_YN);
    }

    /***
     * 学员跨校学习
     * @return
     */
    public static int getSTU_TRANS_YN() {
        return PreferenceUtils.getInstance().getSettingInt(Config.WriteSetting.STU_TRANS_YN, 1);
    }

    public static void setSTU_TRANS_YN(int STU_TRANS_YN) {
        PreferenceUtils.getInstance().setSettingInt(Config.WriteSetting.STU_TRANS_YN, STU_TRANS_YN);
    }

    /****
     * 响应平台同类消息时间间隔
     * @return
     */
    public static int getDUP_MSG_REJECT_INTV_sec() {
        return PreferenceUtils.getInstance().getSettingInt(Config.WriteSetting.DUP_MSG_REJECT_INTV_sec, 0);
    }

    public static void setDUP_MSG_REJECT_INTV_sec(int DUP_MSG_REJECT_INTV_sec) {
        PreferenceUtils.getInstance().setSettingInt(Config.WriteSetting.DUP_MSG_REJECT_INTV_sec, DUP_MSG_REJECT_INTV_sec);
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
        ConstantInfo.isEmbargo = WriteSettingHelper.getEMBARGO();
        ConstantInfo.embargoStr = WriteSettingHelper.getEMBARGOSTR();

        ConstantInfo.PIC_INTV_min = WriteSettingHelper.getPIC_INTV_min();
        ConstantInfo.UPLOAD_GBN = WriteSettingHelper.getUPLOAD_GBN();
        ConstantInfo.ADDMSG_YN = WriteSettingHelper.getADDMSG_YN();
        ConstantInfo.STOP_DELAY_TIME_min = WriteSettingHelper.getSTOP_DELAY_TIME_min();
        ConstantInfo.STOP_GNSS_UPLOAD_INTV_sec = WriteSettingHelper.getSTOP_GNSS_UPLOAD_INTV_sec();   //熄火后停止学时计时的延时时间, 单位: s
        ConstantInfo.STOP_COACH_DELAY_TIME_min = getSTOP_COACH_DELAY_TIME_min();    //熄火后教练自动登出的延时时间, 单位: min
        ConstantInfo.USER_CHK_TIME_min = getUSER_CHK_TIME_min();            //重新验证身份时间, 单位: min
        ConstantInfo.isCOACH_TRANS_YN = getCOACH_TRANS_YN();         //教练跨校教学
        ConstantInfo.isSTU_TRANS_YN = getSTU_TRANS_YN();           //学员跨校学习
        ConstantInfo.DUP_MSG_REJECT_INTV_sec = getDUP_MSG_REJECT_INTV_sec();      //响应平台同类消息时间间隔
    }

    public static void set0501(HandMsgHelper.Class8501 class8501) {
        WriteSettingHelper.setPIC_INTV_min(class8501.cameraInterval);       //定时拍照时间间隔
        WriteSettingHelper.setUPLOAD_GBN(class8501.photoUpDataSetting);     //照片上传设置
        WriteSettingHelper.setADDMSG_YN(class8501.isReadOther);                  //是否报读附加消息
        WriteSettingHelper.setSTOP_DELAY_TIME_min(class8501.flameoutDelay);     //熄火后停止学时计时的延时时间
        WriteSettingHelper.setSTOP_GNSS_UPLOAD_INTV_sec(ByteUtil.byte2int(class8501.flameoutGNSSDelay));            //熄火后GNSS数据包上传间隔
        WriteSettingHelper.setSTOP_COACH_DELAY_TIME_min(ByteUtil.byte2int(class8501.flameoutCoachLogoutTime));      //熄火后教练自动登出的延时时间
        WriteSettingHelper.setUSER_CHK_TIME_min(ByteUtil.byte2int(class8501.reloadIdentityTime));       //重新验证身份的时间
        WriteSettingHelper.setCOACH_TRANS_YN(class8501.isCoachJumpSchool);          //教练
        WriteSettingHelper.setSTU_TRANS_YN(class8501.isStdentJumpSchool);          //学员
        WriteSettingHelper.setDUP_MSG_REJECT_INTV_sec(ByteUtil.byte2int(class8501.onCallMessageTime));
    }
}
