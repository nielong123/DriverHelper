package com.driverhelper.other.timeTask;

import com.driverhelper.config.ConstantInfo;
import com.driverhelper.helper.DbHelper;
import com.driverhelper.widget.LiveSurfaceView;
import com.jaydenxiao.common.commonutils.TimeUtil;

import java.util.TimerTask;

import static com.driverhelper.config.ConstantInfo.PIC_INTV_min;
import static com.jaydenxiao.common.commonutils.TimeUtil.dateFormatYMDHMS_;

/**
 * Created by Administrator on 2017/9/3.
 */

public class PhotoTimerRunnable implements Runnable {

    public static boolean stoped = false;
    static LiveSurfaceView surfaceView;

    public PhotoTimerRunnable() {
        super();
    }

    public PhotoTimerRunnable(LiveSurfaceView mSurfaceView) {
        super();
        surfaceView = mSurfaceView;
    }

    @Override
    public void run() {
        while (!stoped) {
            long time = TimeUtil.getTime() / 1000;
            String str = TimeUtil.formatData(dateFormatYMDHMS_, TimeUtil.getTime());
            String sms = str.substring(str.length() - 6, str.length());
            String photoPath = time + ".png";
            surfaceView.doTakePictureAndSend(photoPath, ConstantInfo.StudentInfo.id, LiveSurfaceView.UpType.autoPhoto);
            DbHelper.getInstance().addphotoInfo(0, ConstantInfo.StudentInfo.id, ConstantInfo.coachId, (int) ConstantInfo.classId + "",
                    photoPath, sms, time, false);
            try {
                Thread.sleep(PIC_INTV_min * 1000 * 60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ConstantInfo.photoThreadNum++;
        }

    }
}
