package com.driverhelper.app;

import android.content.Context;

import com.driverhelper.helper.TcpHelper;
import com.driverhelper.helper.WriteSettingHelper;
import com.jaydenxiao.common.baseapp.BaseApplication;
import com.jaydenxiao.common.commonutils.PreferenceUtils;
import com.orhanobut.logger.Logger;

/**
 * Created by Administrator on 2017/5/31.
 */

public class MyApplication extends BaseApplication {

    TcpHelper tcpHelper;

    String ip, port, timeOut;
    String deviceNum;

    public static Context mApplicationContext;
    public static MyApplication myApp;

    public static MyApplication getInstance() {
        return myApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initData();
        initLog();
        initServer();
    }

    void initData() {
        myApp = this;
        mApplicationContext = getApplicationContext();
        PreferenceUtils.init(this);
        ip = WriteSettingHelper.getTCP_IP();
        port = WriteSettingHelper.getTCP_PORT();
        timeOut = WriteSettingHelper.getTCP_TIMEOUT();
        timeOut = "10*1000";
        deviceNum = WriteSettingHelper.getDEVICE_CODE();
    }

    protected TcpHelper getTcpHelper() {
        if (tcpHelper == null) {
            tcpHelper = new TcpHelper();
        }
        return tcpHelper;
    }

    void initServer() {
        getTcpHelper().connect(ip, port, 10 * 1000);
    }

    void initLog() {
        Logger.init("Server");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
