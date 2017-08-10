package com.driverhelper.beans;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.driverhelper.config.Config;
import com.driverhelper.config.ConstantInfo;
import com.driverhelper.ui.activity.MainActivity;
import com.driverhelper.utils.ByteUtil;

import static com.driverhelper.config.Config.WriteSetting.VEHICLE_COLOR;

/**
 * Created by Administrator on 2017/7/11.
 */

public final class MSG {

    private SharedPreferences sharePreferences;
    private static MSG msg;
    private MainActivity activity;
    private int noSendCount_GPS;
    private int noSendCount_EDU;
    private int noSendCount_PIC;

    public MSG() {
    }

    public MSG(MainActivity activity) {
        this.activity = activity;
        this.sharePreferences = PreferenceManager.getDefaultSharedPreferences(activity);
    }

    public static MSG getInstance(@NonNull MainActivity activity) {

        if (msg == null) {
            msg = new MSG(activity);
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


    public void loadSetting() {
        loadSN();
        loadProvince();
        loadMODEL();
        loadCity();
        loadVehicle_number();
        loadTcpSetting();
        loadIMEI();
        loadVehicleColor();
        loadTerminalPhoneNumber();
    }


    public void loadTcpSetting() {
        Config.ip = sharePreferences.getString(Config.WriteSetting.TCP_IP, "221.235.53.37");
        Config.port = sharePreferences.getString(Config.WriteSetting.TCP_PORT, "2346");
        Config.timeOut = sharePreferences.getString(Config.WriteSetting.TIME_OUT, "10*1000");
        Config.timeOut = "10*1000";
    }

    public static class ClientInfo {
        public static int CameraID = 9;
    }

}
