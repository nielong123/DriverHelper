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

        TcpHelper.getInstance().sendMakeLocationInfo(
                (int) (MyApplication.getInstance().lon * Math.pow(10, 6)),
                (int) (MyApplication.getInstance().lat * Math.pow(10, 6)),
                10,         //车辆obd算出的速度
                (int) MyApplication.getInstance().speedGPS,
                (int) MyApplication.getInstance().direction,
                TimeUtil.formatData(dateFormatYMDHMS_, MyApplication.getInstance().timeGPS / 1000)
        );
        DbHelper.getInstance().addLocationInfo(
                ConstantInfo.ObdInfo.vehiclSspeed,
                ConstantInfo.ObdInfo.distance,
                ConstantInfo.ObdInfo.speed,
                TimeUtil.getTime(),
                false,
                MyApplication.getInstance().speedGPS,
                MyApplication.getInstance().direction,
                MyApplication.getInstance().lat,
                MyApplication.getInstance().lon,
                MyApplication.getInstance().timeGPS);
    }
}
