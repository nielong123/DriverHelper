package com.driverhelper.other;

import com.driverhelper.app.MyApplication;
import com.driverhelper.config.ConstantInfo;
import com.driverhelper.helper.DbHelper;
import com.driverhelper.other.tcp.TcpHelper;
import com.jaydenxiao.common.commonutils.TimeUtil;

import java.util.TimerTask;

/**
 * Created by Administrator on 2017/9/1.
 * 位置信息
 */

public class LocationInfoTimeTask extends TimerTask {
    @Override
    public void run() {

        TcpHelper.getInstance().sendMakeLocationInfo();
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
