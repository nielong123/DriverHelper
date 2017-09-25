package com.driverhelper.other.timeTask;

import com.driverhelper.app.MyApplication;
import com.driverhelper.config.ConstantInfo;
import com.driverhelper.helper.DbHelper;
import com.driverhelper.other.tcp.netty.TcpHelper;
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

        int lon = (int) (ConstantInfo.gpsModel.lon * Math.pow(10, 6));
        int lat = (int) (ConstantInfo.gpsModel.lat * Math.pow(10, 6));
        int speedVehicle = Integer.valueOf(ConstantInfo.obdInfo.getSpeed());
        int speedGPS = (int) ConstantInfo.gpsModel.speedGPS;
        int direction = (int) ConstantInfo.gpsModel.direction;
        long timeGPS = ConstantInfo.gpsModel.timeGPS / 1000;                 //秒级
        String timeGPS_ = TimeUtil.formatData(dateFormatYMDHMS_, timeGPS);
        long timeSYS = TimeUtil.getTime() / 1000;

        TcpHelper.getInstance().sendMakeLocationInfo(lon, lat, speedVehicle, speedGPS, direction, timeGPS_);

        DbHelper.getInstance().addLocationInfo(
                speedVehicle,
                Integer.valueOf(ConstantInfo.obdInfo.getMileage()),
                Integer.valueOf(ConstantInfo.obdInfo.getSpeed()),
                timeSYS,
                false,
                speedGPS,
                direction,
                lat,
                lon,
                timeGPS
        );
    }
}
