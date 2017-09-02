package com.driverhelper.other;

import com.driverhelper.app.MyApplication;
import com.driverhelper.config.ConstantInfo;
import com.driverhelper.helper.DbHelper;
import com.driverhelper.other.tcp.TcpHelper;
import com.jaydenxiao.common.commonutils.TimeUtil;

import java.util.TimerTask;

import static com.jaydenxiao.common.commonutils.TimeUtil.dateFormatYMDHMS_;

/**
 * Created by Administrator on 2017/9/1.
 * 位置信息
 */

public class LocationInfoTimeTask extends TimerTask {
    @Override
    public void run() {

        int lon = (int) (MyApplication.getInstance().lon * Math.pow(10, 6));
        int lat = (int) (MyApplication.getInstance().lat * Math.pow(10, 6));
        int speedVehicle = ConstantInfo.ObdInfo.vehiclSspeed;
        int speedGPS = (int) MyApplication.getInstance().speedGPS;
        int direction = (int) MyApplication.getInstance().direction;
        long timeGPS = MyApplication.getInstance().timeGPS / 1000;                 //秒级
        String timeGPS_ = TimeUtil.formatData(dateFormatYMDHMS_, timeGPS);
        long timeSYS = TimeUtil.getTime()/1000;



        TcpHelper.getInstance().sendMakeLocationInfo(lon, lat, speedVehicle, speedGPS, direction, timeGPS_);

        DbHelper.getInstance().addLocationInfo(
                speedVehicle,
                ConstantInfo.ObdInfo.distance,
                ConstantInfo.ObdInfo.speed,
                timeSYS,
                false,
                speedGPS,
                direction,
                lat,
                lon,
                timeGPS);
    }
}
