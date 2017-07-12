package com.driverhelper.beans;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.driverhelper.ui.activity.MainActivity;
import com.orhanobut.logger.Logger;

/**
 * Created by Administrator on 2017/7/11.
 */

public final class MainActivity_MSG {

    private SharedPreferences sharePreferences;
    private static MainActivity_MSG msg;
    private MainActivity activity;
    private int noSendCount_GPS, noSendCount_EDU, noSendCount_PIC;

    public MainActivity_MSG() {
    }

    public MainActivity_MSG(MainActivity activity) {
        this.activity = activity;
        this.sharePreferences = PreferenceManager.getDefaultSharedPreferences(activity);
    }

    public static MainActivity_MSG getInstance(@NonNull MainActivity activity) {
        try {
            if (activity == null) {
                Exception exception = new Exception("activity不能为null");
                throw exception;
            }else{
                if (msg == null) {
                    msg = new MainActivity_MSG(activity);
                }
                return msg;
            }
        } catch (Exception exception) {
            Logger.e(exception.getMessage());
            return null;
        }
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
}
