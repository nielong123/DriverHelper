package com.driverhelper.utils;

import android.content.Context;
import android.hardware.Camera;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/7/19.
 */

public class CameraUtil {

    /******
     * 獲取攝像頭id的方法，某些机器上不适用
     */
    static public int getDefaultCameraId(Context context) {
        int defaultId = -1;

        // Find the total number of cameras available
        int mNumberOfCameras = Camera.getNumberOfCameras();

        // Find the ID of the default camera
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int i = 0; i < mNumberOfCameras; i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                defaultId = i;
            }
        }
        if (-1 == defaultId) {
            if (mNumberOfCameras > 0) {
                // 如果没有后向摄像头
                defaultId = 0;
            } else {
                // 没有摄像头
                Toast.makeText(context, "没有摄像头", Toast.LENGTH_LONG).show();
            }
        }
        return defaultId;
    }
}
