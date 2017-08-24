package com.driverhelper.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.driverhelper.config.ConstantInfo;
import com.driverhelper.helper.TcpHelper;
import com.driverhelper.utils.FileUtils;

import java.io.IOException;

/**
 * Created by Administrator on 2017/8/18.
 */

public class LiveSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Camera.ErrorCallback, Camera.PreviewCallback, Camera.PictureCallback, Camera.ShutterCallback {

    private String TAG = this.toString();
    Camera camera;
    SurfaceHolder holder;
    private final static int width = 640;
    private final static int height = 480;
    byte[] preBuffer = null;
    boolean isPreview;
    boolean isSend;

    String fileName;

    public LiveSurfaceView(Context context) {
        super(context);
        initCamera();
    }

    public LiveSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initCamera();
    }

    public LiveSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCamera();
    }

    void initCamera() {
        holder = this.getHolder();
        holder.addCallback(this);
    }

    @Override
    public void onError(int i, Camera camera) {

    }

    @Override
    public void onPreviewFrame(byte[] bytes, Camera camera) {
        if (bytes == null) {
            return;
        } else if (bytes.length != width * height * 3 / 2) {
            return;
        }
        if (preBuffer == null) {
            int size = width * height * 3 / 2;
            preBuffer = new byte[size];
        }
        camera.addCallbackBuffer(preBuffer);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        startPreview();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        stopPreview();
    }

    private void startPreview() {
        try {
            if (!isPreview) {
                Log.d("", Camera.getNumberOfCameras() + "");
                if (null == camera) camera = Camera.open(4);
                Camera.Parameters parameters = camera.getParameters();
                parameters.setPictureFormat(PixelFormat.JPEG);
                parameters.set("jpeg-quality", 85);
                Log.d(TAG, "width, height ==   " + width + " , " + height);
                parameters.setPreviewSize(width, height);
                parameters.setPictureSize(width, height);
                parameters.setExposureCompensation(0);
                int size = width * height * 3 / 2;
                if (preBuffer == null) {
                    preBuffer = new byte[size];
                }
                camera.addCallbackBuffer(preBuffer);
                camera.setPreviewCallbackWithBuffer(this);
                camera.setParameters(parameters);
                camera.setPreviewDisplay(holder);
                camera.startPreview();
                isPreview = true;
                camera.setErrorCallback(this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopPreview() {
        if (null != camera) {
            if (isPreview) {
                camera.stopPreview();
                camera.setErrorCallback(null);
                isPreview = false;
            }
            camera.release();
            camera = null;
        }
    }

    public void doTakePicture() {
        fileName = null;
        if (isPreview && (camera != null)) {
            camera.takePicture(this, null, this);
        }
    }

    public void doTakePicture(String fileName) {
        this.fileName = fileName;
        if (isPreview && (camera != null)) {
            camera.takePicture(this, null, this);
        }
    }

    public void doTakePictureAndSend(String fileName) {
        this.fileName = fileName;
        this.isSend = true;
        if (isPreview && (camera != null)) {
            camera.takePicture(this, null, this);
        }
    }

    /****
     * 照相
     * @param bytes
     * @param camera
     */
    @Override
    public void onPictureTaken(byte[] bytes, Camera camera) {
        Bitmap b = null;
        if (null != bytes) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            b = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);//data是字节数据，将其解析成位图
            if (isSend) {
                TcpHelper.getInstance().send0305(fileName.replace(".png", ""), ConstantInfo.coachId, (byte) 0x01, (byte) 0x01, (byte) 0x01, (byte) 0x01, 1, bytes.length);
                TcpHelper.getInstance().send0306(fileName.replace(".png", ""), bytes);
                isSend = false;
            }
            camera.stopPreview();
            isPreview = false;
        }
        //保存图片到sdcard
        if (null != b) {
            if (!TextUtils.isEmpty(fileName)) {
                FileUtils.saveBitmap(getContext(), b, fileName);
            } else {
                FileUtils.saveBitmap(getContext(), b);
            }
        }
        //再次进入预览
        camera.startPreview();
        isPreview = true;
    }

    @Override
    public void onShutter() {

    }
}
