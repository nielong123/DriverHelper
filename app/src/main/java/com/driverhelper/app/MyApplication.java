package com.driverhelper.app;

import android.content.Context;

import com.jaydenxiao.common.baseapp.BaseApplication;
import com.jaydenxiao.common.commonutils.PreferenceUtils;
import com.orhanobut.logger.Logger;

/**
 * Created by Administrator on 2017/5/31.
 */

public class MyApplication extends BaseApplication {

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
    }

    void initServer() {

    }

    void initLog() {
        Logger.init("Server");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
