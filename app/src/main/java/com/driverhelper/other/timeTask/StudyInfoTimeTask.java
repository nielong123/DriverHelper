package com.driverhelper.other.timeTask;

import android.text.TextUtils;

import com.driverhelper.config.ConstantInfo;
import com.driverhelper.helper.IdHelper;
import com.driverhelper.other.tcp.netty.TcpHelper;
import com.jaydenxiao.common.commonutils.TimeUtil;

import java.util.TimerTask;

import static com.driverhelper.config.ConstantInfo.StudentInfo.id;
import static com.driverhelper.config.ConstantInfo.coachId;
import static com.jaydenxiao.common.commonutils.TimeUtil.dateFormatYMDHMS;

/**
 * Created by Administrator on 2017/9/2.
 */

public class StudyInfoTimeTask extends TimerTask {
    @Override
    public void run() {

        String str = TimeUtil.formatData(dateFormatYMDHMS, TimeUtil.getTime() / 1000).replaceAll(":", "");
        String str66666 = str.substring(str.length() - 6, str.length());
        int studyCode = IdHelper.getStudyCode();
        byte updataType = (byte) 0x01;
        int vehiclSspeed;
        if (TextUtils.isEmpty(ConstantInfo.obdInfo.getSpeed())) {
            vehiclSspeed = 0;
        } else {
            vehiclSspeed = Integer.valueOf(ConstantInfo.obdInfo.getSpeed());
        }
        int distance;
        if (TextUtils.isEmpty(ConstantInfo.obdInfo.getMileage())) {
            distance = 0;
        } else {
            distance = Integer.valueOf(ConstantInfo.obdInfo.getMileage());
        }
        int lon = (int) ConstantInfo.gpsModel.lon;
        int lat = (int) ConstantInfo.gpsModel.lat;
        int speedGPS = (int) ConstantInfo.gpsModel.speedGPS;
        int direction = (int) ConstantInfo.gpsModel.direction;
        long timeGPS = ConstantInfo.gpsModel.timeGPS / 1000;
        long timeSYS = TimeUtil.getTime() / 1000;
        byte recordType = (byte) 0x00;

        TcpHelper.getInstance().sendStudyInfo(str66666, studyCode, updataType, id, coachId,
                vehiclSspeed, distance, lon, lat, speedGPS,
                direction, timeSYS, timeGPS, recordType);
    }
}
