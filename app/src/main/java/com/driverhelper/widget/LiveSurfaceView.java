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

import com.driverhelper.R;
import com.driverhelper.app.MyApplication;
import com.driverhelper.config.ConstantInfo;
import com.driverhelper.other.tcp.netty.TcpHelper;
import com.driverhelper.utils.ByteUtil;
import com.driverhelper.utils.FileUtils;
import com.jaydenxiao.common.commonutils.BitmapUtils;
import com.jaydenxiao.common.commonutils.ImageUtil;
import com.jaydenxiao.common.commonutils.TimeUtil;
import com.jaydenxiao.common.compressorutils.FileUtil;

import java.io.File;
import java.io.IOException;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by Administrator on 2017/8/18.
 */

public class LiveSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Camera.ErrorCallback, Camera.PreviewCallback, Camera.PictureCallback, Camera.ShutterCallback {

    private String TAG = this.toString();
    Camera camera;
    SurfaceHolder holder;
    //    private final static int width = 640;
//    private final static int height = 480;
    private final static int width = 400;
    private final static int height = 320;
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
                if (null == camera) camera = Camera.open(ConstantInfo.camera_ID);
                Camera.Parameters parameters = camera.getParameters();
                parameters.setPictureFormat(PixelFormat.JPEG);
                parameters.set("jpeg-quality", 90);
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
        if (null != bytes) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);//data是字节数据，将其解析成位图
            final Bitmap bitmapWithMark = addWaterMark(bitmap);
            bitmap.recycle();
            Log.e("wechat", "压缩前图片的大小" + bitmapWithMark.getByteCount()
                    + "M宽度为" + bitmapWithMark.getWidth() + "高度为" + bitmapWithMark.getHeight() + "bytes.length = " + bytes.length);
//            byte[] data = BitmapUtils.CompressGetBytes(bitmapWithMark, 100);
            final byte[] data = ByteUtil.bitmap2Bytes(bitmapWithMark);
            Log.e("wechat", "压缩后 = " + data.length);
            if (isSend) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        TcpHelper.getInstance().send0305(fileName.replace(".png", ""), ConstantInfo.coachId, (byte) 0x01, (byte) 0x01, (byte) 0x01, (byte) 0x01, 1, data.length);
                        TcpHelper.getInstance().send0306(fileName.replace(".png", ""), data);
                        isSend = false;
                    }
                }).start();
            }
            if (null != bitmapWithMark) {
//                if (!TextUtils.isEmpty(fileName)) {
                FileUtils.saveBitmap(getContext().getFilesDir().getPath() + "/" + fileName, bitmapWithMark);
//                } else {
//                    FileUtils.saveBitmap(getContext(), bitmapWithMark);
//                }
            }
            camera.stopPreview();
            isPreview = false;
            //保存图片到sdcard
        }
        //再次进入预览
        camera.startPreview();
        isPreview = true;
    }

    @Override
    public void onShutter() {

    }

    private Bitmap addWaterMark(Bitmap bitmap) {
        bitmap = ImageUtil.drawTextToLeftTop(
                getContext(),
                bitmap,
                "驾校编号:" + ByteUtil.getString(ConstantInfo.institutionNumber),
                ConstantInfo.WaterMark.textSize,
                ConstantInfo.WaterMark.textColor,
                10, 10);
        bitmap = ImageUtil.drawTextToLeftTop(
                getContext(),
                bitmap,
                "教练员:" + ConstantInfo.coachId + " " + ConstantInfo.coachId,
                ConstantInfo.WaterMark.textSize,
                ConstantInfo.WaterMark.textColor,
                10, 40);
        bitmap = ImageUtil.drawTextToLeftTop(
                getContext(),
                bitmap,
                "学员:" + ConstantInfo.StudentInfo.name + " " + ConstantInfo.StudentInfo.id,
                ConstantInfo.WaterMark.textSize,
                ConstantInfo.WaterMark.textColor,
                10, 70);

        bitmap = ImageUtil.drawTextToLeftBottom(
                getContext(),
                bitmap,
                ConstantInfo.vehicleNum,
                ConstantInfo.WaterMark.textSize,
                ConstantInfo.WaterMark.textColor,
                10, 10);
        bitmap = ImageUtil.drawTextToLeftBottom(
                getContext(),
                bitmap,
                "车速:" + ConstantInfo.gpsModel.getSpeedGPS() + "km/h",
                ConstantInfo.WaterMark.textSize,
                ConstantInfo.WaterMark.textColor,
                10, 40);

        bitmap = ImageUtil.drawTextToRightBottom(
                getContext(),
                bitmap,
                ConstantInfo.gpsModel.lat + "  " + ConstantInfo.gpsModel.lon,
                ConstantInfo.WaterMark.textSize,
                ConstantInfo.WaterMark.textColor,
                10, 10);

        bitmap = ImageUtil.drawTextToRightBottom(
                getContext(),
                bitmap,
                TimeUtil.getCurrentDay(),
                ConstantInfo.WaterMark.textSize,
                ConstantInfo.WaterMark.textColor,
                10, 40);

        return bitmap;
    }
}
