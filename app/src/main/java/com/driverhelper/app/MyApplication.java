package com.driverhelper.app;

import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;

import com.orhanobut.logger.Logger;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.driverhelper.config.Config;
import com.driverhelper.helper.TcpHelper;
import com.driverhelper.helper.WriteSettingHelper;
import com.jaydenxiao.common.baseapp.BaseApplication;
import com.jaydenxiao.common.baserx.RxBus;
import com.jaydenxiao.common.commonutils.PreferenceUtils;
import com.orhanobut.logger.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.driverhelper.config.Config.TextInfoType.ChangeGPSINFO;
import static com.driverhelper.config.Config.TextInfoType.ClearGPSINFO;

/**
 * Created by Administrator on 2017/5/31.
 */

public class MyApplication extends BaseApplication {

    //    private AMapLocation aMapLocation = null;
    private AMapLocationListener locListener = new MyLocationListener();
    private OnLocationReceiveListener mOnLocationReceiveListener;

    TcpHelper tcpHelper;

    String ip, port, timeOut;
    String deviceNum;
    public float speedGPS, direction;
    public double lat, lon;
    public long timeGPS, timeSYS;

    public static Context mApplicationContext;
    public static MyApplication myApp;
    private LocationListener locLnr;
    private LocationManager locMgr;

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
        initLocation();


        ip = WriteSettingHelper.getTCP_IP();
        port = WriteSettingHelper.getTCP_PORT();
        timeOut = WriteSettingHelper.getTCP_TIMEOUT();
        timeOut = "10*1000";
        deviceNum = WriteSettingHelper.getDEVICE_CODE();
    }

    //初始化定位
    private void initLocation() {
        AMapLocationClient mLocationClient = new AMapLocationClient(this);
        mLocationClient.setLocationListener(locListener);
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        option.setInterval(40000l);
        option.setNeedAddress(false);
        mLocationClient.setLocationOption(option);
        mLocationClient.startLocation();
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

    public interface OnLocationReceiveListener {
        void onLbsReceive(AMapLocation location);
    }

    public class MyLocationListener implements AMapLocationListener {

        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation.getErrorCode() == 0) {
                MyApplication.getInstance().lat = amapLocation.getLatitude();
                MyApplication.getInstance().lon = amapLocation.getLongitude();
                MyApplication.getInstance().speedGPS = 36.0F * amapLocation.getSpeed();
                MyApplication.getInstance().direction = amapLocation.getBearing();
                MyApplication.getInstance().timeGPS = amapLocation.getTime();
                RxBus.getInstance().post(Config.Config_RxBus.RX_CHANGE_TEXTINFO, ChangeGPSINFO);
            } else {
                Logger.e("AmapError", "location Error, ErrCode:" + amapLocation.getErrorCode() + ", errInfo:" + amapLocation.getErrorInfo());
                RxBus.getInstance().post(Config.Config_RxBus.RX_CHANGE_TEXTINFO, ClearGPSINFO);
            }
        }
    }
}
