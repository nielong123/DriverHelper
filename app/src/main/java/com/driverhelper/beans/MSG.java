package com.driverhelper.beans;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.driverhelper.app.MyApplication;
import com.driverhelper.config.Config;
import com.driverhelper.config.ConstantInfo;
import com.driverhelper.helper.HandMsgHelper;
import com.driverhelper.ui.activity.MainActivity;
import com.driverhelper.ui.activity.SettingsActivity;
import com.driverhelper.utils.ByteUtil;
import com.jaydenxiao.common.commonutils.PreferenceUtils;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;
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
//        this.sharePreferences = MyApplication.getInstance().getSharedPreferences("com.driverhelper1_preferences", MODE_PRIVATE);
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

    /*************************************************第三个页面的内容 *****************/
    public void getPARAM0001() {
        ConstantInfo.param0001 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.PARAM0001, "15"));
    }

    public void setPARAM0001(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.PARAM0001, str).apply();
        getPARAM0001();
        Log.e("getPARAM0001", "getPARAM0001 = " + ConstantInfo.param0001);
    }

    public void getPARAM0002() {
        ConstantInfo.param0002 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.PARAM0002, "15"));
    }

    public void setPARAM0002(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.PARAM0002, str).apply();
    }

    public void getPARAM0003() {
        ConstantInfo.param0003 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.PARAM0003, "15"));
    }

    public void setPARAM0003(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.PARAM0003, str).apply();
    }

    public void getPARAM0004() {
        ConstantInfo.param0004 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.PARAM0004, "15"));
    }

    public void setPARAM0004(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.PARAM0004, str).apply();
    }

    public void getPARAM0005() {
        ConstantInfo.param0005 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.PARAM0005, "15"));
    }

    public void setPARAM0005(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.PARAM0005, str).apply();
    }

    public void getPARAM0006() {
        ConstantInfo.param0006 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.PARAM0006, "15"));
    }

    public void setPARAM0006(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.PARAM0006, str).apply();
    }

    public void getPARAM0007() {
        ConstantInfo.param0007 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.PARAM0007, "15"));
    }

    public void setPARAM0007(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.PARAM0007, str).apply();
    }

    public void getPARAM0010() {
        ConstantInfo.param0010 = sharePreferences.getString(Config.WriteSetting.param0010, "hello");
    }

    public void setPARAM0010(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0010, str).apply();
    }

    public void getPARAM0011() {
        ConstantInfo.param0011 = sharePreferences.getString(Config.WriteSetting.param0011, "hello");
    }

    public void setPARAM0011(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0011, str).apply();
    }

    public void getPARAM0012() {
        ConstantInfo.param0012 = sharePreferences.getString(Config.WriteSetting.param0012, "hello");
    }

    public void setPARAM0012(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0012, str).apply();
    }

    public void getPARAM0013() {
        ConstantInfo.param0013 = sharePreferences.getString(Config.WriteSetting.param0013, "hello");
    }

    public void setPARAM0013(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0013, str).apply();
    }

    public void getPARAM0014() {
        ConstantInfo.param0014 = sharePreferences.getString(Config.WriteSetting.param0014, "hello");
    }

    public void setPARAM0014(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0014, str).apply();
    }

    public void getPARAM0015() {
        ConstantInfo.param0015 = sharePreferences.getString(Config.WriteSetting.param0015, "hello");
    }

    public void setPARAM0015(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0015, str).apply();
    }

    public void getPARAM0016() {
        ConstantInfo.param0016 = sharePreferences.getString(Config.WriteSetting.param0016, "hello");
    }

    public void setPARAM0016(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0016, str).apply();
    }

    public void getPARAM0017() {
        ConstantInfo.param0017 = sharePreferences.getString(Config.WriteSetting.param0017, "hello");
    }

    public void setPARAM0017(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0017, str).apply();
    }

    public void getPARAM0018() {
        ConstantInfo.param0018 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0018, "15"));
    }

    public void setPARAM0018(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0018, str).apply();
    }

    public void getPARAM0019() {
        ConstantInfo.param0019 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0019, "15"));
    }

    public void setPARAM0019(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0019, str).apply();
    }

    public void getPARAM0020() {
        ConstantInfo.param0020 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0020, "25"));
    }

    public void setPARAM0020(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0020, str).apply();
    }

    public void getPARAM0021() {
        ConstantInfo.param0021 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0021, "25"));
    }

    public void setPARAM0021(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0021, str).apply();
    }

    public void getPARAM0022() {
        ConstantInfo.param0022 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0022, "25"));
    }

    public void setPARAM0022(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0022, str).apply();
    }

//    public void getPARAM0023() {
//        ConstantInfo.param0023 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0023, "25"));
//    }
//
//    public void setPARAM0023(String str) {
//        sharePreferences.edit().putString(Config.WriteSetting.param0023, str).apply();
//    }
//
//    public void getPARAM0024() {
//        ConstantInfo.param0024 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0024, "25"));
//    }
//
//    public void setPARAM0024(String str) {
//        sharePreferences.edit().putString(Config.WriteSetting.param0024, str).apply();
//    }
//
//    public void getPARAM0025() {
//        ConstantInfo.param0025 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0025, "25"));
//    }
//
//    public void setPARAM0025(String str) {
//        sharePreferences.edit().putString(Config.WriteSetting.param0025, str).apply();
//    }
//
//    public void getPARAM0026() {
//        ConstantInfo.param0026 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0026, "25"));
//    }
//
//    public void setPARAM0026(String str) {
//        sharePreferences.edit().putString(Config.WriteSetting.param0026, str).apply();
//    }

    public void getPARAM0027() {
        ConstantInfo.param0027 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0027, "25"));
    }

    public void setPARAM0027(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0027, str).apply();
    }

    public void getPARAM0028() {
        ConstantInfo.param0028 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0028, "25"));
    }

    public void setPARAM0028(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0028, str).apply();
    }

    public void getPARAM0029() {
        ConstantInfo.param0029 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0029, "25"));
    }

    public void setPARAM0029(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0029, str).apply();
    }

    public void getPARAM002C() {
        ConstantInfo.param002C = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param002C, "25"));
    }

    public void setPARAM002C(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.param002C, str).apply();
    }

    public void getPARAM002D() {
        ConstantInfo.param002D = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param002D, "25"));
    }

    public void setPARAM002D(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.param002D, str).apply();
    }

    public void getPARAM002E() {
        ConstantInfo.param002E = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param002E, "25"));
    }

    public void setPARAM002E(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.param002E, str).apply();
    }

    public void getPARAM002F() {
        ConstantInfo.param002F = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param002F, "25"));
    }

    public void setPARAM002F(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.param002F, str).apply();
    }


    public void getPARAM0030() {
        ConstantInfo.param0030 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0030, "25"));
    }

    public void setPARAM0030(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0030, str).apply();
    }


    public void getPARAM0040() {
        ConstantInfo.param0040 = sharePreferences.getString(Config.WriteSetting.param0040, "hello");
    }

    public void setPARAM0040(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0040, str).apply();
    }

    public void getPARAM0041() {
        ConstantInfo.param0041 = sharePreferences.getString(Config.WriteSetting.param0041, "hello");
    }

    public void setPARAM0041(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0041, str).apply();
    }

    public void getPARAM0042() {
        ConstantInfo.param0042 = sharePreferences.getString(Config.WriteSetting.param0042, "hello");
    }

    public void setPARAM0042(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0042, str).apply();
    }

    public void getPARAM0043() {
        ConstantInfo.param0043 = sharePreferences.getString(Config.WriteSetting.param0043, "hello");
    }

    public void setPARAM0043(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0043, str).apply();
    }

    public void getPARAM0044() {
        ConstantInfo.param0044 = sharePreferences.getString(Config.WriteSetting.param0044, "hello");
    }

    public void setPARAM0044(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0044, str).apply();
    }

    public void getPARAM0045() {
        ConstantInfo.param0045 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0045, "45"));
    }

    public void setPARAM0045(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0045, str).apply();
    }

    public void getPARAM0046() {
        ConstantInfo.param0046 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0046, "45"));
    }

    public void setPARAM0046(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0046, str).apply();
    }

    public void getPARAM0047() {
        ConstantInfo.param0047 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0047, "45"));
    }

    public void setPARAM0047(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0047, str).apply();
    }

    public void getPARAM0048() {
        ConstantInfo.param0048 = sharePreferences.getString(Config.WriteSetting.param0048, "hello");
    }

    public void setPARAM0048(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0048, str).apply();
    }

    public void getPARAM0049() {
        ConstantInfo.param0049 = sharePreferences.getString(Config.WriteSetting.param0049, "hello");
    }

    public void setPARAM0049(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0049, str).apply();
    }


    public void getPARAM0050() {
        ConstantInfo.param0050 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0050, "55"));
    }

    public void setPARAM0050(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0050, str).apply();
    }

    public void getPARAM0051() {
        ConstantInfo.param0051 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0051, "55"));
    }

    public void setPARAM0051(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0051, str).apply();
    }

    public void getPARAM0052() {
        ConstantInfo.param0052 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0052, "55"));
    }

    public void setPARAM0052(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0052, str).apply();
    }

    public void getPARAM0053() {
        ConstantInfo.param0053 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0053, "55"));
    }

    public void setPARAM0053(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0053, str).apply();
    }

    public void getPARAM0054() {
        ConstantInfo.param0054 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0054, "55"));
    }

    public void setPARAM0054(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0054, str).apply();
    }

    public void getPARAM0055() {
        ConstantInfo.param0055 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0055, "55"));
    }

    public void setPARAM0055(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0055, str).apply();
    }

    public void getPARAM0056() {
        ConstantInfo.param0056 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0056, "55"));
    }

    public void setPARAM0056(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0056, str).apply();
    }

    public void getPARAM0057() {
        ConstantInfo.param0057 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0057, "55"));
    }

    public void setPARAM0057(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0057, str).apply();
    }

    public void getPARAM0058() {
        ConstantInfo.param0058 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0058, "55"));
    }

    public void setPARAM0058(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0058, str).apply();
    }

    public void getPARAM0059() {
        ConstantInfo.param0059 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0059, "55"));
    }

    public void setPARAM0059(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0059, str).apply();
    }

    public void getPARAM005A() {
        ConstantInfo.param005A = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param005A, "55"));
    }

    public void setPARAM005A(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.param005A, str).apply();
    }

    public void getPARAM0070() {
        ConstantInfo.param0070 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0070, "77"));
    }

    public void setPARAM0070(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0070, str).apply();
    }

    public void getPARAM0071() {
        ConstantInfo.param0071 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0071, "77"));
    }

    public void setPARAM0071(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0071, str).apply();
    }

    public void getPARAM0072() {
        ConstantInfo.param0072 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0072, "77"));
    }

    public void setPARAM0072(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0072, str).apply();
    }

    public void getPARAM0073() {
        ConstantInfo.param0073 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0073, "77"));
    }

    public void setPARAM0073(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0073, str).apply();
    }

    public void getPARAM0074() {
        ConstantInfo.param0074 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0074, "77"));
    }

    public void setPARAM0074(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0074, str).apply();
    }

    public void getPARAM0080() {
        ConstantInfo.param0080 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0080, "88"));
    }

    public void setPARAM0080(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0080, str).apply();
    }

    public void getPARAM0081() {
        ConstantInfo.param0081 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0081, "88"));
    }

    public void setPARAM0081(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0081, str).apply();
    }

    public void getPARAM0082() {
        ConstantInfo.param0082 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0082, "88"));
    }

    public void setPARAM0082(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0082, str).apply();
    }

    public void getPARAM0083() {
        ConstantInfo.param0083 = sharePreferences.getString(Config.WriteSetting.param0083, "hello");
    }

    public void setPARAM0083(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0083, str).apply();
    }

    public void getPARAM0084() {
        ConstantInfo.param0084 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0084, "88"));
    }

    public void setPARAM0084(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0084, str).apply();
    }

    public void getPARAM0085() {
        ConstantInfo.param0085 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0085, "88"));
    }

    public void setPARAM0085(String str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0085, str).apply();
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

    public void loadSetting1() {
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
        getPARAM0001();
        getPARAM0002();
        getPARAM0003();
        getPARAM0004();
        getPARAM0005();
        getPARAM0006();
        getPARAM0007();

        getPARAM0010();
        getPARAM0011();
        getPARAM0012();
        getPARAM0013();
        getPARAM0014();
        getPARAM0015();
        getPARAM0016();
        getPARAM0017();
        getPARAM0018();
        getPARAM0019();

        getPARAM0020();
        getPARAM0021();
        getPARAM0022();
//        getPARAM0023();
//        getPARAM0024();
//        getPARAM0025();
//        getPARAM0026();
        getPARAM0027();
        getPARAM0028();
        getPARAM0029();
        getPARAM002C();
        getPARAM002D();
        getPARAM002E();
        getPARAM002F();

        getPARAM0030();

        getPARAM0040();
        getPARAM0041();
        getPARAM0042();
        getPARAM0043();
        getPARAM0044();
        getPARAM0045();
        getPARAM0046();
        getPARAM0047();
        getPARAM0048();
        getPARAM0049();

        getPARAM0050();
        getPARAM0051();
        getPARAM0052();
        getPARAM0053();
        getPARAM0054();
        getPARAM0055();
        getPARAM0056();
        getPARAM0057();
        getPARAM0058();
        getPARAM0059();
        getPARAM005A();

        getPARAM0070();
        getPARAM0071();
        getPARAM0072();
        getPARAM0073();
        getPARAM0074();

        getPARAM0080();
        getPARAM0081();
        getPARAM0082();
        getPARAM0083();
        getPARAM0084();
        getPARAM0085();
    }

    public void initSetting() {
        setPARAM0001("1");
        setPARAM0002("1");
        setPARAM0003("1");
        setPARAM0004("1");
        setPARAM0005("1");
        setPARAM0006("1");
        setPARAM0007("1");

        setPARAM0010("1");
        setPARAM0011("1");
        setPARAM0012("1");
        setPARAM0013("1");
        setPARAM0014("1");
        setPARAM0015("1");
        setPARAM0016("1");
        setPARAM0017("1");
        setPARAM0018("1");
        setPARAM0019("1");

        setPARAM0020("1");
        setPARAM0021("1");
        setPARAM0022("1");
//        setPARAM0023("1");
//        setPARAM0024("1");
//        setPARAM0025("1");
//        setPARAM0026("1");
        setPARAM0027("1");
        setPARAM0028("1");
        setPARAM0029("1");
        setPARAM002C("1");
        setPARAM002D("1");
        setPARAM002E("1");
        setPARAM002F("1");

        setPARAM0030("1");

        setPARAM0040("1");
        setPARAM0041("1");
        setPARAM0042("1");
        setPARAM0043("1");
        setPARAM0044("1");
        setPARAM0045("1");
        setPARAM0046("1");
        setPARAM0047("1");
        setPARAM0048("1");
        setPARAM0049("1");

        setPARAM0050("1");
        setPARAM0051("1");
        setPARAM0052("1");
        setPARAM0053("1");
        setPARAM0054("1");
        setPARAM0055("1");
        setPARAM0056("1");
        setPARAM0057("1");
        setPARAM0058("1");
        setPARAM0059("1");
        setPARAM005A("1");

        setPARAM0070("1");
        setPARAM0071("1");
        setPARAM0072("1");
        setPARAM0073("1");
        setPARAM0074("1");

        setPARAM0080("1");
        setPARAM0081("1");
        setPARAM0082("1");
        setPARAM0083("1");
        setPARAM0084("1");
        setPARAM0085("1");
    }

    public void setSettings(List<HandMsgHelper.Class8103.Setting> settings) {
        for (HandMsgHelper.Class8103.Setting setting : settings) {
            switch (setting.getId()) {
                case (byte) 0x01:
                    setPARAM0001(ByteUtil.byte2int(setting.getByteParameter()) + "");
                    break;
                case (byte) 0x02:
                    setPARAM0002(ByteUtil.byte2int(setting.getByteParameter()) + "");
                    break;
                case (byte) 0x03:
                    setPARAM0003(ByteUtil.byte2int(setting.getByteParameter()) + "");
                    break;
                case (byte) 0x04:
                    setPARAM0004(ByteUtil.byte2int(setting.getByteParameter()) + "");
                    break;
                case (byte) 0x05:
                    setPARAM0005(ByteUtil.byte2int(setting.getByteParameter()) + "");
                    break;
                case (byte) 0x06:
                    setPARAM0006(ByteUtil.byte2int(setting.getByteParameter()) + "");
                    break;
                case (byte) 0x07:
                    setPARAM0007(ByteUtil.byte2int(setting.getByteParameter()) + "");
                    break;

                case (byte) 0x10:
                    setPARAM0010(setting.getStrParameter());
                    break;
                case (byte) 0x11:
                    setPARAM0011(setting.getStrParameter());
                    break;
                case (byte) 0x12:
                    setPARAM0012(setting.getStrParameter());
                    break;
                case (byte) 0x13:
                    setPARAM0013(setting.getStrParameter());
                    break;
                case (byte) 0x14:
                    setPARAM0014(setting.getStrParameter());
                    break;
                case (byte) 0x15:
                    setPARAM0015(setting.getStrParameter());
                    break;
                case (byte) 0x16:
                    setPARAM0016(setting.getStrParameter());
                    break;
                case (byte) 0x17:
                    setPARAM0017(setting.getStrParameter());
                    break;
                case (byte) 0x21:
                    setPARAM0021(ByteUtil.byte2int(setting.getByteParameter()) + "");
                    break;
                case (byte) 0x22:
                    setPARAM0022(ByteUtil.byte2int(setting.getByteParameter()) + "");
                    break;
//                case (byte) 0x23:
//                    setPARAM0023(ByteUtil.byte2int(setting.getByteParameter()) + "");
//                    break;
//                case (byte) 0x24:
//                    setPARAM0024(ByteUtil.byte2int(setting.getByteParameter()) + "");
//                    break;
//                case (byte) 0x25:
//                    setPARAM0025(ByteUtil.byte2int(setting.getByteParameter()) + "");
//                    break;
//                case (byte) 0x26:
//                    setPARAM0026(ByteUtil.byte2int(setting.getByteParameter()) + "");
//                    break;
                case (byte) 0x27:
                    setPARAM0027(ByteUtil.byte2int(setting.getByteParameter()) + "");
                    break;

                case (byte) 0x30:
                    setPARAM0030(setting.getStrParameter());
                    break;
                case (byte) 0x41:
                    setPARAM0041(setting.getStrParameter());
                    break;
                case (byte) 0x42:
                    setPARAM0042(setting.getStrParameter());
                    break;
                case (byte) 0x43:
                    setPARAM0043(setting.getStrParameter());
                    break;
                case (byte) 0x44:
                    setPARAM0044(setting.getStrParameter());
                    break;
                case (byte) 0x45:
                    setPARAM0045(ByteUtil.byte2int(setting.getByteParameter()) + "");
                    break;
                case (byte) 0x46:
                    setPARAM0046(ByteUtil.byte2int(setting.getByteParameter()) + "");
                    break;
                case (byte) 0x47:
                    setPARAM0047(ByteUtil.byte2int(setting.getByteParameter()) + "");
                    break;
                case (byte) 0x48:
                    setPARAM0048(setting.getStrParameter());
                    break;
                case (byte) 0x49:
                    setPARAM0049(setting.getStrParameter());
                    break;
                case (byte) 0x51:
                    setPARAM0051(ByteUtil.byte2int(setting.getByteParameter()) + "");
                    break;
                case (byte) 0x52:
                    setPARAM0055(ByteUtil.byte2int(setting.getByteParameter()) + "");
                    break;
                case (byte) 0x53:
                    setPARAM0053(ByteUtil.byte2int(setting.getByteParameter()) + "");
                    break;
                case (byte) 0x54:
                    setPARAM0054(ByteUtil.byte2int(setting.getByteParameter()) + "");
                    break;
                case (byte) 0x55:
                    setPARAM0055(ByteUtil.byte2int(setting.getByteParameter()) + "");
                    break;
                case (byte) 0x56:
                    setPARAM0056(ByteUtil.byte2int(setting.getByteParameter()) + "");
                    break;
                case (byte) 0x57:
                    setPARAM0057(ByteUtil.byte2int(setting.getByteParameter()) + "");
                    break;
                case (byte) 0x58:
                    setPARAM0058(ByteUtil.byte2int(setting.getByteParameter()) + "");
                    break;
                case (byte) 0x59:
                    setPARAM0059(ByteUtil.byte2int(setting.getByteParameter()) + "");
                    break;
                case (byte) 0x5A:
                    setPARAM005A(ByteUtil.byte2int(setting.getByteParameter()) + "");
                    break;
                case (byte) 0x71:
                    setPARAM0071(ByteUtil.byte2int(setting.getByteParameter()) + "");
                    break;
                case (byte) 0x72:
                    setPARAM0072(ByteUtil.byte2int(setting.getByteParameter()) + "");
                    break;
                case (byte) 0x73:
                    setPARAM0073(ByteUtil.byte2int(setting.getByteParameter()) + "");
                    break;
                case (byte) 0x74:
                    setPARAM0074(ByteUtil.byte2int(setting.getByteParameter()) + "");
                    break;
                case (byte) 0x81:
                    setPARAM0081(ByteUtil.byte2int(setting.getByteParameter()) + "");
                    break;
                case (byte) 0x82:
                    setPARAM0082(ByteUtil.byte2int(setting.getByteParameter()) + "");
                    break;
                case (byte) 0x83:
                    setPARAM0083(ByteUtil.byte2int(setting.getByteParameter()) + "");
                    break;
                case (byte) 0x84:
                    setPARAM0084(ByteUtil.byte2int(setting.getByteParameter()) + "");
                    break;
                case (byte) 0x85:
                    setPARAM0085(ByteUtil.byte2int(setting.getByteParameter()) + "");
                    break;
            }
        }
    }


    public void loadTcpSetting() {
        Config.ip = sharePreferences.getString(Config.WriteSetting.TCP_IP, "221.235.53.37");
        Config.port = sharePreferences.getString(Config.WriteSetting.TCP_PORT, "2346");
        Config.timeOut = sharePreferences.getString(Config.WriteSetting.TIME_OUT, "10*1000");
        Config.timeOut = "10*1000";
    }


}
