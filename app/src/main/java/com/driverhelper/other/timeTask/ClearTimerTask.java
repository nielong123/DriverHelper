package com.driverhelper.other.timeTask;

import android.util.Log;

import com.driverhelper.other.tcp.TcpManager;

import java.util.TimerTask;

/**
 * Created by Administrator on 2017/9/3.
 */

public final class ClearTimerTask extends TimerTask {

    public String TAG = "ClearTimerTask";
    public static int maxIndex = 2;

    @Override
    public void run() {
        TcpManager.getInstance().clearContainer();
        Log.e(TAG, "自动清除 Container大小为    " + TcpManager.getInstance().getNum());
    }
}
