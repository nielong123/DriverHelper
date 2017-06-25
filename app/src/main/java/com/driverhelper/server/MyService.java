package com.driverhelper.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.driverhelper.helper.TcpHelper;
import com.driverhelper.helper.WriteSettingHelper;
import com.orhanobut.logger.Logger;

public class MyService extends Service {

    TcpHelper tcpHelper;

    String ip, port, timeOut;
    String deviceNum;

    public MyService() {
    }

    protected TcpHelper getTcpHelper() {
        if (tcpHelper == null) {
            tcpHelper = new TcpHelper();
        }
        return tcpHelper;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Logger.d("onBind");
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.d("onCreate");
        initData();
        getTcpHelper().connect(ip, port, 10 * 1000);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.d("onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    void initData() {
        ip = WriteSettingHelper.getTCP_IP();
        port = WriteSettingHelper.getTCP_PORT();
        timeOut = WriteSettingHelper.getTCP_TIMEOUT();
        timeOut = "10*1000";
        deviceNum = WriteSettingHelper.getDEVICE_CODE();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.d("onDestroy");
        getTcpHelper().disConnect();
    }
}
