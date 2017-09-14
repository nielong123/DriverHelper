package com.driverhelper.other.timeTask;

import com.driverhelper.app.MyApplication;
import com.driverhelper.config.ConstantInfo;
import com.driverhelper.helper.IdHelper;
import com.driverhelper.other.tcp.netty.TcpHelper;
import com.jaydenxiao.common.commonutils.TimeUtil;

import java.util.TimerTask;

import static com.driverhelper.config.ConstantInfo.StudentInfo.studentId;
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
        int vehiclSspeed = ConstantInfo.ObdInfo.vehiclSspeed;
        int distance = ConstantInfo.ObdInfo.distance;
        int lon = (int) ConstantInfo.gpsModel.lon;
        int lat = (int) ConstantInfo.gpsModel.lat;
        int speedGPS = (int) ConstantInfo.gpsModel.speedGPS;
        int direction = (int) ConstantInfo.gpsModel.direction;
        long timeGPS = ConstantInfo.gpsModel.timeGPS / 1000;
        long timeSYS = TimeUtil.getTime() / 1000;
        byte recordType = (byte) 0x00;

        TcpHelper.getInstance().sendStudyInfo(str66666, studyCode, updataType, studentId, coachId,
                vehiclSspeed, distance, lon, lat, speedGPS,
                direction, timeSYS, timeGPS, recordType);
    }
}
