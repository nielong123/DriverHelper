package com.driverhelper.other;

import android.text.TextUtils;

import com.driverhelper.config.Config;
import com.driverhelper.config.ConstantInfo;
import com.driverhelper.other.tcp.netty.TcpHelper;
import com.driverhelper.other.timeTask.LocationInfoTimeTask;
import com.driverhelper.other.timeTask.PhotoTimerRunnable;
import com.driverhelper.other.timeTask.StudyInfoTimeTask;
import com.driverhelper.ui.activity.MainActivity;
import com.driverhelper.utils.ByteUtil;
import com.driverhelper.widget.LiveSurfaceView;
import com.jaydenxiao.common.baserx.RxBus;

import java.util.Timer;

import static com.driverhelper.config.ConstantInfo.UPLOAD_GBN;
import static com.driverhelper.config.ConstantInfo.locationTimer;
import static com.driverhelper.config.ConstantInfo.locationTimerDelay;
import static com.driverhelper.config.ConstantInfo.studyInfoTimerDelay;

/**
 * Created by Administrator on 2017/9/13.
 */

public final class Business {

    /****
     * 是否鉴权成功
     * @return
     */
    static public boolean is0102_OK() {
        return !(TextUtils.isEmpty(ByteUtil.getString(ConstantInfo.institutionNumber)) ||
                TextUtils.isEmpty(ByteUtil.getString(ConstantInfo.platformNum)) ||
                TextUtils.isEmpty(ByteUtil.getString(ConstantInfo.terminalNum)) ||
                TextUtils.isEmpty(ByteUtil.getString(ConstantInfo.certificatePassword)) ||
                TextUtils.isEmpty(ConstantInfo.terminalCertificate));
    }

    /*****
     * 是改变的设置生效
     */
    static public void reActivaSettings(LiveSurfaceView surfaceView) {
        TcpHelper.getInstance().setHeartDelay(ConstantInfo.heartdelay);     //心跳包间隔

        if (ConstantInfo.photoThread != null && UPLOAD_GBN == 1) {              //拍照间隔
            ConstantInfo.photoThread.interrupt();
            ConstantInfo.photoThread = null;
        }
        startPhotoTimer(surfaceView);
    }


    public static void startPhotoTimer(LiveSurfaceView surfaceView) {
        switch (UPLOAD_GBN) {           //是否自动上传照片
            case 1:
                if (ConstantInfo.photoThread != null) {
                    ConstantInfo.photoThread.interrupt();
                    ConstantInfo.photoThread = null;
                }
                if (surfaceView != null) {
                    ConstantInfo.photoThread = new Thread(new PhotoTimerRunnable(surfaceView));
                    ConstantInfo.photoThread.start();
                } else {
                    ConstantInfo.photoThread = new Thread(new PhotoTimerRunnable());
                    ConstantInfo.photoThread.start();
                }
                break;
            case 0:
                if (ConstantInfo.photoThread != null) {
                    ConstantInfo.photoThread.interrupt();
                    ConstantInfo.photoThread = null;
                }
                break;
        }
    }

    public static void stopPhotoTimer() {
        if (ConstantInfo.photoThread != null) {
            ConstantInfo.photoThread.interrupt();
            ConstantInfo.photoThread = null;
        }
    }

    public static void startStudyInfoTimer() {
        if (ConstantInfo.studyInfoTimer != null) {
            ConstantInfo.studyInfoTimer.cancel();
            ConstantInfo.studyInfoTimer = null;
        }
        ConstantInfo.studyInfoTimer = new Timer(true);
        ConstantInfo.studyInfoTimer.schedule(new StudyInfoTimeTask(), 10, studyInfoTimerDelay);
    }

    public static void stopStudyInfoTimer() {
        if (ConstantInfo.studyInfoTimer != null) {
            ConstantInfo.studyInfoTimer.cancel();
            ConstantInfo.studyInfoTimer = null;
        }
    }

    public static void startUpDataLocationInfo() {
        RxBus.getInstance().post(Config.Config_RxBus.RX_TTS_SPEAK, "开始上传位置信息");
        locationTimer = new Timer(true);
        locationTimer.schedule(new LocationInfoTimeTask(), 1000, locationTimerDelay);          //暂定十秒一次,要改成可以设置的
    }

    public static void stopUpDataLocationInfo() {
        if (locationTimer != null) {
            locationTimer.cancel();
            locationTimer = null;
        }
    }
}
