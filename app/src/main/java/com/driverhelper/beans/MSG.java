package com.driverhelper.beans;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.driverhelper.R;
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
import static com.driverhelper.config.ConstantInfo.IMEI;
import static com.driverhelper.helper.WriteSettingHelper.setVEHICLE_NUMBER;

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
            synchronized (MSG.class) {
                if (msg == null) {
                    msg = new MSG();
                }
            }
        }
        return msg;
    }

    public void loadTcpIp() {
        ConstantInfo.ip = sharePreferences.getString(Config.WriteSetting.TCP_IP, "221.235.53.37");
    }

    public void setTcpIp(String tcpIp) {
        sharePreferences.edit().putString(Config.WriteSetting.TCP_IP, tcpIp).apply();
    }

    public void loadTcpPort() {
        ConstantInfo.port = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.TCP_PORT, "2346"));
    }

    public void setTcpPort(String tcpIp) {
        sharePreferences.edit().putString(Config.WriteSetting.TCP_IP, tcpIp).apply();
    }

    public void loadTcpTimeOut() {
        ConstantInfo.timeOut = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.TIME_OUT, "1000"));
    }

    public void setTcpTimeOut(String timeOut) {
        sharePreferences.edit().putString(Config.WriteSetting.TIME_OUT, timeOut).apply();
    }


    public void loadHttpUrl() {
        ConstantInfo.httpURL = sharePreferences.getString(Config.WriteSetting.HTTP_URL, "http://221.235.53.37/");
    }

    public void setHttpUrl(String tcpIp) {
        sharePreferences.edit().putString(Config.WriteSetting.HTTP_URL, tcpIp).apply();
    }

    public void loadProvince() {
        ConstantInfo.province[1] = ByteUtil.hexString2BCD(sharePreferences.getString(Config.WriteSetting.PROVINCE, "00"))[0];
    }

    public void setProvince(String province) {
        sharePreferences.edit().putString(Config.WriteSetting.PROVINCE, province).apply();
    }

    public void loadCity() {
        ConstantInfo.city[1] = ByteUtil.hexString2BCD(sharePreferences.getString(Config.WriteSetting.CITY, "00"))[0];
    }

    public void setCity(String city) {
        sharePreferences.edit().putString(Config.WriteSetting.CITY, city).apply();
    }

    public void loadMODEL() {
        ConstantInfo.MODEL = sharePreferences.getString(Config.WriteSetting.MODEL, "YX/4G 528G");
    }

    public void setMODEL(String model) {
        sharePreferences.edit().putString(Config.WriteSetting.MODEL, model).apply();
    }

    public void loadVehicle_number() {
        ConstantInfo.vehicleNum = sharePreferences.getString(Config.WriteSetting.VEHICLE_NUMBER, "00");
    }

    public void setVehicle_number(String vehicle_number) {
        sharePreferences.edit().putString(Config.WriteSetting.VEHICLE_NUMBER, vehicle_number).apply();
    }

    public void loadTerminalPhoneNumber() {
        ConstantInfo.terminalPhoneNumber = sharePreferences.getString(Config.WriteSetting.TERMINALPHONENUMBER, "13469986047");
    }

    public void setTerminalPhoneNumber(String phoneNumber) {
        sharePreferences.edit().putString(Config.WriteSetting.TERMINALPHONENUMBER, phoneNumber).apply();
    }

    public void loadSN() {
        ConstantInfo.SN = sharePreferences.getString(Config.WriteSetting.SN, "");
    }

    public void setSN(String sn) {
        sharePreferences.edit().putString(Config.WriteSetting.SN, sn).apply();
    }

    public void loadVehicleColor() {
        ConstantInfo.vehicleColor = sharePreferences.getString(VEHICLE_COLOR, "2");
    }

    public void setVehicleColor(String color) {
        sharePreferences.edit().putString(VEHICLE_COLOR, color).apply();
    }

    /***
     *   IMEI
     * @return
     */
    public void loadIMEI() {
        IMEI = sharePreferences.getString(Config.WriteSetting.IMEI, "");
    }

    public void setIMEI(String imei) {
        sharePreferences.edit().putString(Config.WriteSetting.IMEI, imei).apply();
    }

    public void loadMakerID() {
        ConstantInfo.makerID = ByteUtil.str2Bytes(sharePreferences.getString(Config.WriteSetting.MAKER_ID, ""));
    }

    public void setMakerID(String makerID) {
        sharePreferences.edit().putString(Config.WriteSetting.MAKER_ID, makerID).apply();
    }

    public void loadTrainType() {
        ConstantInfo.trainType = sharePreferences.getString(Config.WriteSetting.TRAIN_TYPE, "2");
    }

    public void setTrainType(String trainType) {
        sharePreferences.edit().putString(Config.WriteSetting.TRAIN_TYPE, trainType).apply();
    }

    public void loadPerdriType() {
        ConstantInfo.perdriType = sharePreferences.getString(Config.WriteSetting.PERDRITYPE, "");
    }

    public void setPerdriType(String perdriType) {
        sharePreferences.edit().putString(Config.WriteSetting.PERDRITYPE, perdriType).apply();
    }

    public void loadCameraID() {
        ConstantInfo.camera_ID = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.CameraID, ""));
    }

    public void setCameraID(int cameraID) {
        sharePreferences.edit().putString(Config.WriteSetting.CameraID, cameraID + "").apply();
    }

    public void loadCar_ID() {
        ConstantInfo.car_Id = sharePreferences.getString(Config.WriteSetting.Car_ID, "");
    }

    public void setCar_ID(String car_id) {
        sharePreferences.edit().putString(Config.WriteSetting.Car_ID, car_id).apply();
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
        if (sharePreferences.getBoolean(Config.WriteSetting.ADDMSG_YN, false)) {
            ConstantInfo.ADDMSG_YN = 1;
        } else {
            ConstantInfo.ADDMSG_YN = 0;
        }
    }

    public void setADDMSG_YN(int ADDMSG_YN) {
        if (ADDMSG_YN == 0) {
            sharePreferences.edit().putBoolean(Config.WriteSetting.ADDMSG_YN, false).apply();
            return;
        }
        if (ADDMSG_YN == 1) {
            sharePreferences.edit().putBoolean(Config.WriteSetting.ADDMSG_YN, true).apply();
            return;
        }
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
            sharePreferences.edit().putBoolean(Config.WriteSetting.COACH_TRANS_YN, true).apply();
            return;
        }
        if (COACH_TRANS_YN == 2) {
            sharePreferences.edit().putBoolean(Config.WriteSetting.COACH_TRANS_YN, false).apply();
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
            sharePreferences.edit().putBoolean(Config.WriteSetting.STU_TRANS_YN, true).apply();
            return;
        }
        if (STU_TRANS_YN == 2) {
            sharePreferences.edit().putBoolean(Config.WriteSetting.STU_TRANS_YN, false).apply();
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
    public void getHEARTDELAY() {
        ConstantInfo.heartdelay = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.HEARTDELAY, "15"));
    }

    public void setHEARTDELAY(int str) {
        sharePreferences.edit().putString(Config.WriteSetting.HEARTDELAY, str + "").apply();
    }

    public void getPARAM0002() {
        ConstantInfo.param0002 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.PARAM0002, "15"));
    }

    public void setPARAM0002(int str) {
        sharePreferences.edit().putString(Config.WriteSetting.PARAM0002, str + "").apply();
    }

    public void getPARAM0003() {
        ConstantInfo.param0003 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.PARAM0003, "15"));
    }

    public void setPARAM0003(int str) {
        sharePreferences.edit().putString(Config.WriteSetting.PARAM0003, str + "").apply();
    }

    public void getPARAM0004() {
        ConstantInfo.param0004 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.PARAM0004, "15"));
    }

    public void setPARAM0004(int str) {
        sharePreferences.edit().putString(Config.WriteSetting.PARAM0004, str + "").apply();
    }

    public void getPARAM0005() {
        ConstantInfo.param0005 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.PARAM0005, "15"));
    }

    public void setPARAM0005(int str) {
        sharePreferences.edit().putString(Config.WriteSetting.PARAM0005, str + "").apply();
    }

    public void getPARAM0006() {
        ConstantInfo.param0006 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.PARAM0006, "15"));
    }

    public void setPARAM0006(int str) {
        sharePreferences.edit().putString(Config.WriteSetting.PARAM0006, str + "").apply();
    }

    public void getPARAM0007() {
        ConstantInfo.param0007 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.PARAM0007, "15"));
    }

    public void setPARAM0007(int str) {
        sharePreferences.edit().putString(Config.WriteSetting.PARAM0007, str + "").apply();
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

    public void setPARAM0018(int str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0018, str + "").apply();
    }

    public void getPARAM0019() {
        ConstantInfo.param0019 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0019, "15"));
    }

    public void setPARAM0019(int str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0019, str + "").apply();
    }

    public void getPARAM0020() {
        ConstantInfo.param0020 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0020, "25"));
    }

    public void setPARAM0020(int str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0020, str + "").apply();
    }

    public void getPARAM0021() {
        ConstantInfo.param0021 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0021, "25"));
    }

    public void setPARAM0021(int str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0021, str + "").apply();
    }

    public void getPARAM0022() {
        ConstantInfo.param0022 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0022, "25"));
    }

    public void setPARAM0022(int str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0022, str + "").apply();
    }

    public void getPARAM0027() {
        ConstantInfo.param0027 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0027, "25"));
    }

    public void setPARAM0027(int str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0027, str + "").apply();
    }

    public void getPARAM0028() {
        ConstantInfo.param0028 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0028, "25"));
    }

    public void setPARAM0028(int str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0028, str + "").apply();
    }

    public void getPARAM0029() {
        ConstantInfo.param0029 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0029, "25"));
    }

    public void setPARAM0029(int str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0029, str + "").apply();
    }

    public void getPARAM002C() {
        ConstantInfo.param002C = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param002C, "25"));
    }

    public void setPARAM002C(int str) {
        sharePreferences.edit().putString(Config.WriteSetting.param002C, str + "").apply();
    }

    public void getPARAM002D() {
        ConstantInfo.param002D = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param002D, "25"));
    }

    public void setPARAM002D(int str) {
        sharePreferences.edit().putString(Config.WriteSetting.param002D, str + "").apply();
    }

    public void getPARAM002E() {
        ConstantInfo.param002E = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param002E, "25"));
    }

    public void setPARAM002E(int str) {
        sharePreferences.edit().putString(Config.WriteSetting.param002E, str + "").apply();
    }

    public void getPARAM002F() {
        ConstantInfo.param002F = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param002F, "25"));
    }

    public void setPARAM002F(int str) {
        sharePreferences.edit().putString(Config.WriteSetting.param002F, str + "").apply();
    }


    public void getPARAM0030() {
        ConstantInfo.param0030 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0030, "25"));
    }

    public void setPARAM0030(int str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0030, str + "").apply();
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

    public void setPARAM0045(int str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0045, str + "").apply();
    }

    public void getPARAM0046() {
        ConstantInfo.param0046 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0046, "45"));
    }

    public void setPARAM0046(int str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0046, str + "").apply();
    }

    public void getPARAM0047() {
        ConstantInfo.param0047 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0047, "45"));
    }

    public void setPARAM0047(int str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0047, str + "").apply();
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
        boolean res = sharePreferences.getBoolean(Config.WriteSetting.param0050, false);
        if (res) {
            ConstantInfo.param0050 = 1;
        } else {
            ConstantInfo.param0050 = 0;
        }
    }

    public void setPARAM0050(int str) {
        switch (str) {
            case 0:
                sharePreferences.edit().putBoolean(Config.WriteSetting.param0050, false).apply();
                break;
            case 1:
                sharePreferences.edit().putBoolean(Config.WriteSetting.param0050, true).apply();
                break;
        }
    }

    public void getPARAM0051() {
        boolean res = sharePreferences.getBoolean(Config.WriteSetting.param0051, false);
        if (res) {
            ConstantInfo.param0051 = 1;
        } else {
            ConstantInfo.param0051 = 0;
        }
    }

    public void setPARAM0051(int PARAM0051) {
        if (PARAM0051 == 1) {
            sharePreferences.edit().putBoolean(Config.WriteSetting.param0051, true).apply();
            return;
        }
        if (PARAM0051 == 0) {
            sharePreferences.edit().putBoolean(Config.WriteSetting.param0051, false).apply();
        }
    }

    public void getPARAM0052() {
        boolean res = sharePreferences.getBoolean(Config.WriteSetting.param0052, false);
        if (res) {
            ConstantInfo.param0052 = 1;
        } else {
            ConstantInfo.param0052 = 0;
        }
    }

    public void setPARAM0052(int PARAM0052) {
        if (PARAM0052 == 1) {
            sharePreferences.edit().putBoolean(Config.WriteSetting.param0052, true).apply();
            return;
        }
        if (PARAM0052 == 0) {
            sharePreferences.edit().putBoolean(Config.WriteSetting.param0052, false).apply();
        }
    }

    public void getPARAM0053() {
        boolean res = sharePreferences.getBoolean(Config.WriteSetting.param0053, false);
        if (res) {
            ConstantInfo.param0053 = 1;
        } else {
            ConstantInfo.param0053 = 0;
        }
    }

    public void setPARAM0053(int PARAM0053) {
        if (PARAM0053 == 1) {
            sharePreferences.edit().putBoolean(Config.WriteSetting.param0053, true).apply();
            return;
        }
        if (PARAM0053 == 0) {
            sharePreferences.edit().putBoolean(Config.WriteSetting.param0053, false).apply();
        }
    }

    public void getPARAM0054() {
        boolean res = sharePreferences.getBoolean(Config.WriteSetting.param0054, false);
        if (res) {
            ConstantInfo.param0054 = 1;
        } else {
            ConstantInfo.param0054 = 0;
        }
    }

    public void setPARAM0054(int PARAM0054) {
        if (PARAM0054 == 1) {
            sharePreferences.edit().putBoolean(Config.WriteSetting.param0054, true).apply();
            return;
        }
        if (PARAM0054 == 0) {
            sharePreferences.edit().putBoolean(Config.WriteSetting.param0054, false).apply();
        }
    }

    public void getPARAM0055() {
        ConstantInfo.param0055 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0055, "55"));
    }

    public void setPARAM0055(int str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0055, str + "").apply();
    }

    public void getPARAM0056() {
        ConstantInfo.param0056 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0056, "55"));
    }

    public void setPARAM0056(int str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0056, str + "").apply();
    }

    public void getPARAM0057() {
        ConstantInfo.param0057 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0057, "55"));
    }

    public void setPARAM0057(int str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0057, str + "").apply();
    }

    public void getPARAM0058() {
        ConstantInfo.param0058 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0058, "55"));
    }

    public void setPARAM0058(int str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0058, str + "").apply();
    }

    public void getPARAM0059() {
        ConstantInfo.param0059 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0059, "55"));
    }

    public void setPARAM0059(int str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0059, str + "").apply();
    }

    public void getPARAM005A() {
        ConstantInfo.param005A = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param005A, "55"));
    }

    public void setPARAM005A(int str) {
        sharePreferences.edit().putString(Config.WriteSetting.param005A, str + "").apply();
    }

    public void getPARAM0070() {
        ConstantInfo.param0070 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0070, "77"));
    }

    public void setPARAM0070(int str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0070, "" + str).apply();
    }

    public void getPARAM0071() {
        ConstantInfo.param0071 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0071, "77"));
    }

    public void setPARAM0071(int str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0071, "" + str).apply();
    }

    public void getPARAM0072() {
        ConstantInfo.param0072 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0072, "77"));
    }

    public void setPARAM0072(int str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0072, "" + str).apply();
    }

    public void getPARAM0073() {
        ConstantInfo.param0073 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0073, "77"));
    }

    public void setPARAM0073(int str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0073, "" + str).apply();
    }

    public void getPARAM0074() {
        ConstantInfo.param0074 = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.param0074, "77"));
    }

    public void setPARAM0074(int str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0074, "" + str).apply();
    }

    public void getDISTANCE() {
        ConstantInfo.distance = Integer.valueOf(sharePreferences.getString(Config.WriteSetting.DISTANCE, "88"));
    }

    public void setDISTANCE(int str) {
        sharePreferences.edit().putString(Config.WriteSetting.DISTANCE, "" + str).apply();
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

    public void setPARAM0085(int str) {
        sharePreferences.edit().putString(Config.WriteSetting.param0085, "" + str).apply();
    }

    public void setSetting0() {
        setProvince(MyApplication.getInstance().getString(R.string.pref_default_param0081_d));
        setCity(MyApplication.getInstance().getString(R.string.pref_default_param0082_d));
        setMODEL(MyApplication.getInstance().getString(R.string.pref_default_MODEL_d));
        setVehicle_number(MyApplication.getInstance().getString(R.string.pref_default_param0083_d));
        setTerminalPhoneNumber(MyApplication.getInstance().getString(R.string.pref_default_DIAL_NO_d));
        setSN(MyApplication.getInstance().getString(R.string.pref_default_SN_d));
        setVehicleColor(MyApplication.getInstance().getString(R.string.pref_default_vehicle_color));
        setIMEI(MyApplication.getInstance().getString(R.string.pref_default_IMEI_d));
        setMakerID(MyApplication.getInstance().getString(R.string.pref_default_VENDER_ID_d));
        setTrainType(MyApplication.getInstance().getString(R.string.pref_default_TRAIN_TYPE_d));
        setPerdriType(MyApplication.getInstance().getString(R.string.pref_default_PERDRITYPE_d));
        setCameraID(ConstantInfo.camera_ID);
        setCar_ID("12345");
    }

    public void loadSetting0() {
        loadProvince();
        loadCity();
        loadMODEL();
        loadVehicle_number();
        loadTerminalPhoneNumber();
        loadSN();
        loadVehicleColor();
        loadIMEI();
        loadMakerID();
        loadTcpSetting();
        loadTrainType();
        loadPerdriType();
        loadCameraID();
        loadCar_ID();


        Log.e("123", "/*******************************************/");
        ByteUtil.printHexString("ConstantInfo.province", ConstantInfo.province);
        ByteUtil.printHexString("ConstantInfo.city", ConstantInfo.city);
        Log.e("ConstantInfo.MODEL", ConstantInfo.MODEL);
        Log.e("ConstantInfo.vehicleNum", ConstantInfo.vehicleNum);
        Log.e("terminalPhoneNumber", ConstantInfo.terminalPhoneNumber);
        Log.e("ConstantInfo.SN", ConstantInfo.SN);
        Log.e("vehicleColor", ConstantInfo.vehicleColor);
        Log.e("IMEI", ConstantInfo.IMEI);
        ByteUtil.printHexString("MakerID", ConstantInfo.makerID);
        Log.e("ip", ConstantInfo.ip);
        Log.e("port", ConstantInfo.port + "");
        Log.e("timeOut", ConstantInfo.timeOut + "");
        Log.e("123", "/*******************************************/");
    }

    public void setSetting1() {
        setPIC_INTV_min(ConstantInfo.PIC_INTV_min);
        setUPLOAD_GBN(ConstantInfo.UPLOAD_GBN);
        setADDMSG_YN(ConstantInfo.ADDMSG_YN);
        setSTOP_DELAY_TIME_min(ConstantInfo.STOP_DELAY_TIME_min);
        setSTOP_GNSS_UPLOAD_INTV_sec(ConstantInfo.STOP_GNSS_UPLOAD_INTV_sec);
        setSTOP_COACH_DELAY_TIME_min(ConstantInfo.STOP_COACH_DELAY_TIME_min);
        setUSER_CHK_TIME_min(ConstantInfo.USER_CHK_TIME_min);
        setCOACH_TRANS_YN(ConstantInfo.COACH_TRANS_YN);
        setSTU_TRANS_YN(ConstantInfo.STU_TRANS_YN);
        setDUP_MSG_REJECT_INTV_sec(ConstantInfo.DUP_MSG_REJECT_INTV_sec);
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
    }

    public void loadSetting2() {
        getHEARTDELAY();
        getPARAM0002();
        getPARAM0003();
        getPARAM0004();
        getPARAM0005();
        getPARAM0006();
        getPARAM0007();
    }

    public void setSetting2() {
        setHEARTDELAY(ConstantInfo.heartdelay);
        setPARAM0002(ConstantInfo.param0002);
        setPARAM0003(ConstantInfo.param0003);
        setPARAM0004(ConstantInfo.param0004);
        setPARAM0005(ConstantInfo.param0005);
        setPARAM0006(ConstantInfo.param0006);
        setPARAM0007(ConstantInfo.param0007);
    }

    public void loadSetting3() {
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
    }

    public void setSetting3() {
        setPARAM0010(ConstantInfo.param0010);
        setPARAM0011(ConstantInfo.param0011);
        setPARAM0012(ConstantInfo.param0012);
        setPARAM0013(ConstantInfo.param0013);
        setPARAM0014(ConstantInfo.param0014);
        setPARAM0015(ConstantInfo.param0015);
        setPARAM0016(ConstantInfo.param0016);
        setPARAM0017(ConstantInfo.param0017);
        setPARAM0018(ConstantInfo.param0018);
        setPARAM0019(ConstantInfo.param0019);
    }

    public void loadSetting4() {
        getPARAM0020();
        getPARAM0021();
        getPARAM0022();
        getPARAM0027();
        getPARAM0028();
        getPARAM0029();
        getPARAM002C();
        getPARAM002D();
        getPARAM002E();
        getPARAM002F();
    }

    public void setSetting4() {
        setPARAM0020(ConstantInfo.param0020);
        setPARAM0021(ConstantInfo.param0021);
        setPARAM0022(ConstantInfo.param0022);
        setPARAM0027(ConstantInfo.param0027);
        setPARAM0028(ConstantInfo.param0028);
        setPARAM0029(ConstantInfo.param0029);
        setPARAM002C(ConstantInfo.param002C);
        setPARAM002D(ConstantInfo.param002D);
        setPARAM002E(ConstantInfo.param002E);
        setPARAM002F(ConstantInfo.param002F);
    }

    public void loadSetting5() {
        getPARAM0030();
    }

    public void setSetting5() {
        setPARAM0030(ConstantInfo.param0030);
    }

    public void loadSetting6() {
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
    }

    public void setSetting6() {
        setPARAM0040(ConstantInfo.param0040);
        setPARAM0041(ConstantInfo.param0041);
        setPARAM0042(ConstantInfo.param0042);
        setPARAM0043(ConstantInfo.param0043);
        setPARAM0044(ConstantInfo.param0044);
        setPARAM0045(ConstantInfo.param0045);
        setPARAM0046(ConstantInfo.param0046);
        setPARAM0047(ConstantInfo.param0047);
        setPARAM0048(ConstantInfo.param0048);
        setPARAM0049(ConstantInfo.param0049);
    }

    public void loadSetting7() {
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
    }

    public void setSetting7() {
        setPARAM0050(ConstantInfo.param0050);
        setPARAM0051(ConstantInfo.param0051);
        setPARAM0052(ConstantInfo.param0052);
        setPARAM0053(ConstantInfo.param0053);
        setPARAM0054(ConstantInfo.param0054);
        setPARAM0055(ConstantInfo.param0055);
        setPARAM0056(ConstantInfo.param0056);
        setPARAM0057(ConstantInfo.param0057);
        setPARAM0058(ConstantInfo.param0058);
        setPARAM0059(ConstantInfo.param0059);
        setPARAM005A(ConstantInfo.param005A);
    }

    public void loadSetting8() {
        getPARAM0070();
        getPARAM0071();
        getPARAM0072();
        getPARAM0073();
        getPARAM0074();
    }

    public void setSetting8() {
        setPARAM0070(ConstantInfo.param0070);
        setPARAM0071(ConstantInfo.param0071);
        setPARAM0072(ConstantInfo.param0072);
        setPARAM0073(ConstantInfo.param0073);
        setPARAM0074(ConstantInfo.param0074);
    }

    public void loadSetting9() {
        getDISTANCE();
        getPARAM0085();
    }

    public void setSetting9() {
        setDISTANCE(ConstantInfo.distance);
        setPARAM0085(ConstantInfo.param0085);
    }

    /*****
     * 第一次登陆时初始化设置
     */
    public void initSettings() {
        setSetting0();
        setSetting1();
        setSetting2();
        setSetting3();
        setSetting4();
        setSetting5();
        setSetting6();
        setSetting7();
        setSetting8();
        setSetting9();
    }


    public void loadSettings() {
        loadSetting0();
        loadSetting1();
        loadSetting2();
        loadSetting3();
        loadSetting4();
        loadSetting5();
        loadSetting6();
        loadSetting7();
        loadSetting8();
        loadSetting9();
    }

    public void setSettings(List<HandMsgHelper.Class8103.Setting> settings) {
        for (HandMsgHelper.Class8103.Setting setting : settings) {
            switch (setting.getId()) {
                case (byte) 0x01:
                    setHEARTDELAY(ByteUtil.byte2int(setting.getByteParameter()));
                    break;
                case (byte) 0x02:
                    setPARAM0002(ByteUtil.byte2int(setting.getByteParameter()));
                    break;
                case (byte) 0x03:
                    setPARAM0003(ByteUtil.byte2int(setting.getByteParameter()));
                    break;
                case (byte) 0x04:
                    setPARAM0004(ByteUtil.byte2int(setting.getByteParameter()));
                    break;
                case (byte) 0x05:
                    setPARAM0005(ByteUtil.byte2int(setting.getByteParameter()));
                    break;
                case (byte) 0x06:
                    setPARAM0006(ByteUtil.byte2int(setting.getByteParameter()));
                    break;
                case (byte) 0x07:
                    setPARAM0007(ByteUtil.byte2int(setting.getByteParameter()));
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
                    setPARAM0021(ByteUtil.byte2int(setting.getByteParameter()));
                    break;
                case (byte) 0x22:
                    setPARAM0022(ByteUtil.byte2int(setting.getByteParameter()));
                    break;
                case (byte) 0x27:
                    setPARAM0027(ByteUtil.byte2int(setting.getByteParameter()));
                    break;
                case (byte) 0x28:
                    setPARAM0028(ByteUtil.byte2int(setting.getByteParameter()));
                    break;
                case (byte) 0x29:
                    setPARAM0028(ByteUtil.byte2int(setting.getByteParameter()));
                    break;
                case (byte) 0x2c:
                    setPARAM0028(ByteUtil.byte2int(setting.getByteParameter()));
                    break;
                case (byte) 0x2d:
                    setPARAM0028(ByteUtil.byte2int(setting.getByteParameter()));
                    break;
                case (byte) 0x2e:
                    setPARAM0028(ByteUtil.byte2int(setting.getByteParameter()));
                    break;
                case (byte) 0x2f:
                    setPARAM0028(ByteUtil.byte2int(setting.getByteParameter()));
                    break;


                case (byte) 0x30:
                    setPARAM0030(ByteUtil.byte2int(setting.getByteParameter()));
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
                    setPARAM0045(ByteUtil.byte2int(setting.getByteParameter()));
                    break;
                case (byte) 0x46:
                    setPARAM0046(ByteUtil.byte2int(setting.getByteParameter()));
                    break;
                case (byte) 0x47:
                    setPARAM0047(ByteUtil.byte2int(setting.getByteParameter()));
                    break;
                case (byte) 0x48:
                    setPARAM0048(setting.getStrParameter());
                    break;
                case (byte) 0x49:
                    setPARAM0049(setting.getStrParameter());
                    break;
                case (byte) 0x51:
                    setPARAM0051(ByteUtil.byte2int(setting.getByteParameter()));
                    break;
                case (byte) 0x52:
                    setPARAM0055(ByteUtil.byte2int(setting.getByteParameter()));
                    break;
                case (byte) 0x53:
                    setPARAM0053(ByteUtil.byte2int(setting.getByteParameter()));
                    break;
                case (byte) 0x54:
                    setPARAM0054(ByteUtil.byte2int(setting.getByteParameter()));
                    break;
                case (byte) 0x55:
                    setPARAM0055(ByteUtil.byte2int(setting.getByteParameter()));
                    break;
                case (byte) 0x56:
                    setPARAM0056(ByteUtil.byte2int(setting.getByteParameter()));
                    break;
                case (byte) 0x57:
                    setPARAM0057(ByteUtil.byte2int(setting.getByteParameter()));
                    break;
                case (byte) 0x58:
                    setPARAM0058(ByteUtil.byte2int(setting.getByteParameter()));
                    break;
                case (byte) 0x59:
                    setPARAM0059(ByteUtil.byte2int(setting.getByteParameter()));
                    break;
                case (byte) 0x5A:
                    setPARAM005A(ByteUtil.byte2int(setting.getByteParameter()));
                    break;
                case (byte) 0x71:
                    setPARAM0071(ByteUtil.byte2int(setting.getByteParameter()));
                    break;
                case (byte) 0x72:
                    setPARAM0072(ByteUtil.byte2int(setting.getByteParameter()));
                    break;
                case (byte) 0x73:
                    setPARAM0073(ByteUtil.byte2int(setting.getByteParameter()));
                    break;
                case (byte) 0x74:
                    setPARAM0074(ByteUtil.byte2int(setting.getByteParameter()));
                    break;
                case (byte) 0x80:
                    setDISTANCE(ByteUtil.byte2int(setting.getByteParameter()));
                    break;
                case (byte) 0x81:
                    setProvince(ByteUtil.byte2int(setting.getByteParameter()) + "");
                    break;
                case (byte) 0x82:
                    setCity(ByteUtil.byte2int(setting.getByteParameter()) + "");
                    break;
                case (byte) 0x83:
                    setVEHICLE_NUMBER(ByteUtil.byte2int(setting.getByteParameter()) + "");
                    break;
                case (byte) 0x84:
                    setVehicleColor(ByteUtil.byte2int(setting.getByteParameter()) + "");
                    break;
                case (byte) 0x85:
//                    setVehicleColor(ByteUtil.byte2int(setting.()) + "");
                    break;
            }
        }
    }


    public void loadTcpSetting() {
        loadTcpIp();
        loadTcpPort();
        loadTcpTimeOut();
        loadHttpUrl();
    }


}
