package com.driverhelper.other.timeTask;

import android.view.SurfaceView;

import com.driverhelper.widget.LiveSurfaceView;
import com.jaydenxiao.common.commonutils.TimeUtil;

import java.util.TimerTask;

import static com.jaydenxiao.common.commonutils.TimeUtil.dateFormatYMDHMS_;

/**
 * Created by Administrator on 2017/9/3.
 */

public class PhotoTimerTask extends TimerTask {


    LiveSurfaceView surfaceView;

    public PhotoTimerTask() {

    }

    public PhotoTimerTask(LiveSurfaceView surfaceView) {
        super();
        this.surfaceView = surfaceView;
    }

    @Override
    public void run() {
        String str = TimeUtil.formatData(dateFormatYMDHMS_, TimeUtil.getTime());
        String sms = str.substring(str.length() - 6, str.length());
        String photoPath = TimeUtil.getTime() / 1000 + ".png";
        surfaceView.doTakePictureAndSend(photoPath);
        //                DbHelper.getInstance().addStudyInfoDao(null, IdHelper.getStudyCode(), ConstantInfo.StudentInfo.studentId, ConstantInfo.coachId, ByteUtil.byte2int(ConstantInfo.classId),
//                        photoPath, sms, ConstantInfo.classType, ConstantInfo.ObdInfo.vehiclSspeed, ConstantInfo.ObdInfo.distance, ConstantInfo.ObdInfo.speed,
//                        TimeUtil.getTime());
    }
}
