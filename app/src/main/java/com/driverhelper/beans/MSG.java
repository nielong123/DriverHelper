package com.driverhelper.beans;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.driverhelper.app.MyApplication;
import com.driverhelper.config.Config;
import com.driverhelper.config.ConstantInfo;
import com.driverhelper.ui.activity.MainActivity;
import com.driverhelper.ui.activity.SettingsActivity;
import com.driverhelper.utils.ByteUtil;

import static com.driverhelper.config.Config.WriteSetting.VEHICLE_COLOR;

/**
 * Created by Administrator on 2017/7/11.
 */

public final class MSG {

    private SharedPreferences sharePreferences;
    private static MSG msg;
    private int noSendCount_GPS;
    private int noSendCount_EDU;
    private int noSendCount_PIC;


    public MSG() {
        this.sharePreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getInstance());
    }

    public static MSG getInstance() {

        if (msg == null) {
            msg = new MSG();
        }
        return msg;
    }

    public int getNoSendCount_GPS() {
        return noSendCount_GPS;
    }

    public void setNoSendCount_GPS(int noSendCount_GPS) {
        this.noSendCount_GPS = noSendCount_GPS;
    }

    public int getNoSendCount_EDU() {
        return noSendCount_EDU;
    }

    public void setNoSendCount_EDU(int noSendCount_EDU) {
        this.noSendCount_EDU = noSendCount_EDU;
    }

    public int getNoSendCount_PIC() {
        return noSendCount_PIC;
    }

    public void setNoSendCount_PIC(int noSendCount_PIC) {
        this.noSendCount_PIC = noSendCount_PIC;
    }

    public void loadProvince() {
        ConstantInfo.province[1] = ByteUtil.hexString2BCD(sharePreferences.getString(Config.WriteSetting.PROVINCE, "00"))[0];
    }

    public void loadCity() {
        ConstantInfo.city[1] = ByteUtil.hexString2BCD(sharePreferences.getString(Config.WriteSetting.CITY, "00"))[0];
    }

    public void loadMODEL() {
        ConstantInfo.MODEL = sharePreferences.getString(Config.WriteSetting.MODEL, "YX/4G 528G");
    }

    public void loadVehicle_number() {
        ConstantInfo.vehicleNum = sharePreferences.getString(Config.WriteSetting.VEHICLE_NUMBER, "00");
    }

    public void loadTerminalPhoneNumber() {
        ConstantInfo.terminalPhoneNumber = sharePreferences.getString(Config.WriteSetting.TERMINALPHONENUMBER, "13469986047");
    }

    public void loadSN() {
        ConstantInfo.SN = sharePreferences.getString(Config.WriteSetting.SN, "");
    }

    public void loadVehicleColor() {
        ConstantInfo.vehicleColor = sharePreferences.getString(VEHICLE_COLOR, "2");
    }

    /***
     *   IMEI
     * @return
     */
    public void loadIMEI() {
        ConstantInfo.IMEI = sharePreferences.getString(Config.WriteSetting.IMEI, "");
    }

/**********************************************************************************/

    /***
     * 定时拍照时间间隔
     * @return
     */
    public void loadPIC_INTV_min() {
        ConstantInfo.PIC_INTV_min = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.PIC_INTV_min, "15"));
    }

    public void setPIC_INTV_min(int PIC_INTV_min) {
        sharePreferences.edit().putString(Config.WriteSetting.PIC_INTV_min, PIC_INTV_min + "").apply();
    }

    /***
     * 照片上传设置
     * @return
     */
    public void loadUPLOAD_GBN() {
        if (sharePreferences.getBoolean(Config.WriteSetting.UPLOAD_GBN, false)) {
            ConstantInfo.UPLOAD_GBN = 1;
        } else {
            ConstantInfo.UPLOAD_GBN = 0;
        }
    }

    public void setUPLOAD_GBN(int UPLOAD_GBN) {
        if (UPLOAD_GBN == 0) {
            sharePreferences.edit().putBoolean(Config.WriteSetting.UPLOAD_GBN, false).apply();
            return;
        }
        if (UPLOAD_GBN == 1) {
            sharePreferences.edit().putBoolean(Config.WriteSetting.UPLOAD_GBN, true).apply();
            return;
        }
    }

    /***
     * 是否报读
     * @return
     */
    public void loadADDMSG_YN() {
        ConstantInfo.ADDMSG_YN = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.ADDMSG_YN, "1"));
    }

    public void setADDMSG_YN(byte ADDMSG_YN) {
        sharePreferences.edit().putString(Config.WriteSetting.ADDMSG_YN, ADDMSG_YN + "").apply();
    }

    /****
     * 熄火后停止学时计时的延时时间min
     * @return
     */
    public void loadSTOP_DELAY_TIME_min() {
        ConstantInfo.STOP_DELAY_TIME_min = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.STOP_DELAY_TIME_min, "1"));
    }

    public void setSTOP_DELAY_TIME_min(int STOP_DELAY_TIME_min) {
        sharePreferences.edit().putString(Config.WriteSetting.STOP_DELAY_TIME_min, STOP_DELAY_TIME_min + "").apply();
    }

    /****
     * 熄火后GNSS停止学时计时的延时时间s
     * @return
     */
    public void getSTOP_GNSS_UPLOAD_INTV_sec() {
        ConstantInfo.STOP_GNSS_UPLOAD_INTV_sec = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.STOP_GNSS_UPLOAD_INTV_sec, "3600"));
    }

    public void setSTOP_GNSS_UPLOAD_INTV_sec(int STOP_GNSS_UPLOAD_INTV_sec) {
        sharePreferences.edit().putString(Config.WriteSetting.STOP_GNSS_UPLOAD_INTV_sec, STOP_GNSS_UPLOAD_INTV_sec + "").apply();
    }

    /****
     * 熄火后教练自动登出的延时时间
     * @return
     */
    public void getSTOP_COACH_DELAY_TIME_min() {
        ConstantInfo.STOP_COACH_DELAY_TIME_min = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.STOP_COACH_DELAY_TIME_min, "0"));
    }

    public void setSTOP_COACH_DELAY_TIME_min(int STOP_COACH_DELAY_TIME_min) {
        sharePreferences.edit().putString(Config.WriteSetting.STOP_COACH_DELAY_TIME_min, STOP_COACH_DELAY_TIME_min + "").apply();
    }

    /****
     * 重新验证身份时间
     * @return
     */
    public void getUSER_CHK_TIME_min() {
        ConstantInfo.USER_CHK_TIME_min = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.USER_CHK_TIME_min, "0"));
    }

    public void setUSER_CHK_TIME_min(int USER_CHK_TIME_min) {
        sharePreferences.edit().putString(Config.WriteSetting.USER_CHK_TIME_min, USER_CHK_TIME_min + "").apply();
    }

    /***
     * 教练跨校教学
     * @return
     */
    public void getCOACH_TRANS_YN() {
        if (sharePreferences.getBoolean(Config.WriteSetting.COACH_TRANS_YN, false)) {
            ConstantInfo.COACH_TRANS_YN = 1;
        } else {
            ConstantInfo.COACH_TRANS_YN = 2;
        }
    }

    public void setCOACH_TRANS_YN(int COACH_TRANS_YN) {
        if (COACH_TRANS_YN == 1) {
            sharePreferences.edit().putBoolean(Config.WriteSetting.COACH_TRANS_YN, true);
            return;
        }
        if (COACH_TRANS_YN == 2) {
            sharePreferences.edit().putBoolean(Config.WriteSetting.COACH_TRANS_YN, true);
            return;
        }
    }

    /***
     * 学员跨校学习
     * @return
     */
    public void getSTU_TRANS_YN() {
        if (sharePreferences.getBoolean(Config.WriteSetting.STU_TRANS_YN, true)) {
            ConstantInfo.STU_TRANS_YN = 1;
        } else {
            ConstantInfo.STU_TRANS_YN = 2;
        }
    }

    public void setSTU_TRANS_YN(int STU_TRANS_YN) {
        if (STU_TRANS_YN == 1) {
            sharePreferences.edit().putBoolean(Config.WriteSetting.STU_TRANS_YN, true);
            return;
        }
        if (STU_TRANS_YN == 2) {
            sharePreferences.edit().putBoolean(Config.WriteSetting.STU_TRANS_YN, true);
            return;
        }
    }

    /****
     * 响应平台同类消息时间间隔
     * @return
     */
    public void getDUP_MSG_REJECT_INTV_sec() {
        ConstantInfo.DUP_MSG_REJECT_INTV_sec = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.DUP_MSG_REJECT_INTV_sec, "0"));
    }

    public void setDUP_MSG_REJECT_INTV_sec(int DUP_MSG_REJECT_INTV_sec) {
        sharePreferences.edit().putString(Config.WriteSetting.DUP_MSG_REJECT_INTV_sec, DUP_MSG_REJECT_INTV_sec + "").apply();
    }


    public void loadSetting() {
        loadProvince();
        loadCity();
        loadMODEL();
        loadVehicle_number();
        loadTerminalPhoneNumber();
        loadSN();
        loadVehicleColor();
        loadIMEI();
        loadTcpSetting();
    }

    public void loadSetting1(){
        loadPIC_INTV_min();
        loadUPLOAD_GBN();
        loadADDMSG_YN();
        loadSTOP_DELAY_TIME_min();
        getSTOP_GNSS_UPLOAD_INTV_sec();   //熄火后停止学时计时的延时时间, 单位: s
        getSTOP_COACH_DELAY_TIME_min();    //熄火后教练自动登出的延时时间, 单位: min
        getUSER_CHK_TIME_min();            //重新验证身份时间, 单位: min
        getCOACH_TRANS_YN();         //教练跨校教学
        getSTU_TRANS_YN();           //学员跨校学习
        getDUP_MSG_REJECT_INTV_sec();      //响应平台同类消息时间间隔
    }


    public void loadTcpSetting() {
        Config.ip = sharePreferences.getString(Config.WriteSetting.TCP_IP, "221.235.53.37");
        Config.port = sharePreferences.getString(Config.WriteSetting.TCP_PORT, "2346");
        Config.timeOut = sharePreferences.getString(Config.WriteSetting.TIME_OUT, "10*1000");
        Config.timeOut = "10*1000";
    }

}
