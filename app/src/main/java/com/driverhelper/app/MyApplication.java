package com.driverhelper.app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.driverhelper.beans.MSG;
import com.driverhelper.beans.gen.DaoMaster;
import com.driverhelper.beans.gen.DaoSession;
import com.driverhelper.config.Config;
import com.driverhelper.config.ConstantInfo;
import com.driverhelper.helper.WriteSettingHelper;
import com.jaydenxiao.common.baseapp.BaseApplication;
import com.jaydenxiao.common.baserx.RxBus;
import com.jaydenxiao.common.commonutils.PreferenceUtils;
import com.jaydenxiao.common.commonutils.ToastUitl;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.text.DecimalFormat;

import qingwei.kong.serialportlibrary.SerialPort;

/**
 * Created by Administrator on 2017/5/31.
 */

public class MyApplication extends BaseApplication implements AMapLocationListener {

    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    private SerialPort obdSerialPort = null;
    private SerialPort icReaderSerialPort = null;


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
        myApp = this;
        mApplicationContext = getApplicationContext();
        PreferenceUtils.init(this);
        initData();
        setDatabase();
        initLog();
        initLocation();
    }

    void initData() {
        getIsFirst();
        WriteSettingHelper.loadRegistInfo();
        MSG.getInstance().loadSettings();
    }

    /**
     * 设置greenDao
     */
    private void setDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new DaoMaster.DevOpenHelper(this, "notes-db", null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }


    //初始化定位
    private void initLocation() {
        AMapLocationClient mLocationClient = new AMapLocationClient(this);
        mLocationClient.setLocationListener(this);
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        option.setInterval(40000);
        option.setNeedAddress(false);
        option.setSensorEnable(true);
        mLocationClient.setLocationOption(option);
        mLocationClient.startLocation();
    }

    void initLog() {
        Logger.init("Server");
    }

    void getIsFirst() {
        if (WriteSettingHelper.getISFIRST()) {
            MSG.getInstance().initSettings();
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }


    public SerialPort getObdSerialPort() throws SecurityException, IOException, InvalidParameterException {
        if (obdSerialPort == null) {
            obdSerialPort = new SerialPort(new File("/dev/ttyS3"), 38400, 0);
        }
        return obdSerialPort;
    }

    public SerialPort getIcReaderSerialPort() throws SecurityException, IOException, InvalidParameterException {
        if (icReaderSerialPort == null) {
            icReaderSerialPort = new SerialPort(new File("/dev/ttyS5"), 9600, 0);
        }
        return icReaderSerialPort;
    }

    public void closeObdSerialPort() {
        if (obdSerialPort != null) {
            obdSerialPort.close();
            obdSerialPort = null;
        }
    }

    public void closeIcReaderSerialPort() {
        if (obdSerialPort != null) {
            obdSerialPort.close();
            obdSerialPort = null;
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation.getErrorCode() == 0) {
            DecimalFormat df = new DecimalFormat("######0.00");
            ConstantInfo.gpsModel.lat = Double.valueOf(df.format(aMapLocation.getLatitude()));
            ConstantInfo.gpsModel.lon = Double.valueOf(df.format(aMapLocation.getLongitude()));
            ConstantInfo.gpsModel.speedGPS = aMapLocation.getSpeed();
            ConstantInfo.gpsModel.direction = aMapLocation.getBearing();
            ConstantInfo.gpsModel.timeGPS = aMapLocation.getTime();
            ConstantInfo.gpsModel.isLocation = true;
            RxBus.getInstance().post(Config.Config_RxBus.RX_LOCATION_OK, "");
        } else {
            ConstantInfo.gpsModel.isLocation = false;
            RxBus.getInstance().post(Config.Config_RxBus.RX_LOCATION_FALINE, "");
            Logger.e("AmapError", "location Error, ErrCode:" + aMapLocation.getErrorCode() + ", errInfo:" + aMapLocation.getErrorInfo());
        }
    }
}
