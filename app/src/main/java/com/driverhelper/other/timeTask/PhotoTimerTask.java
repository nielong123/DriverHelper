package com.driverhelper.other.timeTask;

import com.driverhelper.config.ConstantInfo;
import com.driverhelper.helper.DbHelper;
import com.driverhelper.widget.LiveSurfaceView;
import com.jaydenxiao.common.commonutils.TimeUtil;

import java.util.TimerTask;

import static com.jaydenxiao.common.commonutils.TimeUtil.dateFormatYMDHMS_;

/**
 * Created by Administrator on 2017/9/3.
 */

public class PhotoTimerTask extends TimerTask {


    static LiveSurfaceView surfaceView;

    public PhotoTimerTask() {
        super();
    }

    public PhotoTimerTask(LiveSurfaceView mSurfaceView) {
        super();
        surfaceView = mSurfaceView;
    }

    @Override
    public void run() {
        long time = TimeUtil.getTime() / 1000;
        String str = TimeUtil.formatData(dateFormatYMDHMS_, TimeUtil.getTime());
        String sms = str.substring(str.length() - 6, str.length());
        String photoPath = time + ".png";
        surfaceView.doTakePictureAndSend(photoPath);
        DbHelper.getInstance().addphotoInfo(0, ConstantInfo.StudentInfo.studentId, ConstantInfo.coachId, (int) ConstantInfo.classId + "",
                photoPath, sms, time, false);
    }
}
