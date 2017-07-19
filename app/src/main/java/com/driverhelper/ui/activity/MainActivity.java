package com.driverhelper.ui.activity;

import com.driverhelper.other.zxing.activity.CaptureActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextClock;
import android.widget.TextView;

import com.driverhelper.R;
import com.driverhelper.app.MyApplication;
import com.driverhelper.beans.MSG;
import com.driverhelper.config.Config;
import com.driverhelper.helper.TcpHelper;
import com.driverhelper.helper.WriteSettingHelper;
import com.driverhelper.other.Preview;
import com.driverhelper.other.ReceiverOBDII;
import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.baserx.RxBus;
import com.jaydenxiao.common.commonutils.VersionUtil;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.Date;
import java.util.Timer;

import butterknife.Bind;
import butterknife.OnClick;
import rx.functions.Action1;

import static com.driverhelper.config.Config.carmerId_HANGJING;
import static com.driverhelper.config.Config.ip;
import static com.driverhelper.config.Config.port;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener,
        TextToSpeech.OnInitListener,
        Toolbar.OnMenuItemClickListener,
        SurfaceHolder.Callback, Camera.ErrorCallback, Camera.PreviewCallback {

    final String TAG = getClass().getName().toUpperCase();

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.networksw)
    Switch networksw;
    @Bind(R.id.surfaceView)
    SurfaceView surfaceView;
    @Bind(R.id.layout)
    FrameLayout layout;
    @Bind(R.id.LLcamera)
    LinearLayout LLcamera;
    @Bind(R.id.JiaoLianButton)
    ImageButton JiaoLianButton;
    @Bind(R.id.JiaoLianTEXT)
    TextView JiaoLianTEXT;
    @Bind(R.id.COACHNUMtext)
    EditText COACHNUMtext;
    @Bind(R.id.IDCARDtext)
    EditText IDCARDtext;
    @Bind(R.id.XueYuanButton)
    ImageButton XueYuanButton;
    @Bind(R.id.XueYuanTEXT)
    TextView XueYuanTEXT;
    @Bind(R.id.STUNUMtext)
    EditText STUNUMtext;
    @Bind(R.id.NoSendText)
    TextView NoSendText;
    @Bind(R.id.LLmingdan)
    LinearLayout LLmingdan;
    @Bind(R.id.lat)
    TextView lat;
    @Bind(R.id.direction)
    TextView direction;
    @Bind(R.id.lon)
    TextView lon;
    @Bind(R.id.speed)
    TextView speed;
    @Bind(R.id.textViewStat)
    TextView textViewStat;
    @Bind(R.id.textViewThisTime)
    TextClock textViewThisTime;
    @Bind(R.id.textViewStartTime)
    TextView textViewStartTime;
    @Bind(R.id.LLgps)
    LinearLayout LLgps;
    @Bind(R.id.textViewSpeed)
    TextView textViewSpeed;
    @Bind(R.id.textViewRPM)
    TextView textViewRPM;
    @Bind(R.id.textViewDistance)
    TextView textViewDistance;
    @Bind(R.id.textViewTime)
    TextView textViewTime;
    @Bind(R.id.LLGroup1)
    LinearLayout LLGroup1;
    @Bind(R.id.nav_view)
    NavigationView navView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private TextToSpeech ttsClient;
    public ReceiverOBDII OBDReceiver = null;
    private Camera camera;
    private SurfaceHolder holder;

    static Timer AliveTm;
    static Timer CHKTm;
    static Timer ChkTHREAD;
    static Timer GPSCHKTm;
    static Timer GPSSendTime;
    static Timer NOSENDtask;
    static Timer StartTm;

    static int OpenCamNum;
    static int StartChkcount;
    public int MSGdataPOS;
    private final static int width = 320;
    private final static int height = 240;
    byte[] mPreBuffer = null;
    int OSver = 4;
    int OSverMid = 0;
    int OSverSub = 0;

    long NetChkTime;
    long NotSpeek;

    String CamPic;
    String SpdStr;
    String GPSStr;

    boolean CamStatOK = false;
    boolean NoSIMcard = false;
    boolean NoSendReding = false;
    boolean QRmode = false;
    boolean SendNewMSGChk = false;
    boolean gpsState;
    boolean isPreview;

    Date CurrDate = new Date();

    private static final int REQUEST_PERM = 1000;
    private static final int REQUEST_QR = 49374;
    private static final int REQUEST_SETTING = 1;
    private static final int RESULT_FAIL = 1;
    private static final int RESULT_OK = 0;
    private static final String TAGcam = "CameraRtn";
    private static final String TAGdev = "DeviceCHK";


    Preview preview;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        initToolBar();
        initCamera();
//        byte[] test = new byte[]{(byte) 0x32, (byte) 0x12, (byte) 0x0a, (byte) 0x0b, (byte) 0xb0, (byte) 0xa0};
        networksw.setOnCheckedChangeListener(onCheckedChangeListener);
//        PreferenceUtils.getInstance().setSettingBytes("test", test);
//        ByteUtil.printHexString(PreferenceUtils.getInstance().getSettingBytes("test"));
    }


    void initToolBar() {
        StringBuilder localStringBuilder1 = new StringBuilder().append("EXSUNTerminal 终端系统 ").append(VersionUtil.getVersion(this)).append("  ( 科目");
        StringBuilder localStringBuilder2 = localStringBuilder1.append(WriteSettingHelper.getEXAM_SUBJECTS()).append("  ");
        StringBuilder localStringBuilder3 = localStringBuilder2.append(WriteSettingHelper.getEXAM_TYPE()).append("  ");
        StringBuilder localStringBuilder4 = localStringBuilder3.append(WriteSettingHelper.getVEHICLE_NUMBER()).append("  ");
//        StringBuilder localStringBuilder5 = localStringBuilder4.append(MSG.ClientInfo.param0083).append(" ) : ");
        toolbar.setTitle(new String(localStringBuilder4));
        setSupportActionBar(this.toolbar);

        DrawerLayout localDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle localActionBarDrawerToggle = new ActionBarDrawerToggle(this,
                localDrawerLayout,
                this.toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        localDrawerLayout.setDrawerListener(localActionBarDrawerToggle);
        localActionBarDrawerToggle.syncState();
        ((NavigationView) findViewById(R.id.nav_view)).setNavigationItemSelectedListener(this);
    }


    public boolean onCreateOptionsMenu(Menu paramMenu) {
        getMenuInflater().inflate(R.menu.main, paramMenu);
        return true;
    }

    @Override
    public void initData() {

        this.ttsClient = new TextToSpeech(getApplicationContext(), this);
        MSG.getInstance(this).loadSetting();
        WriteSettingHelper.loadRegistInfo();
    }

    @Override
    public void initEvent() {
        mRxManager.on(Config.Config_RxBus.RX_CHANGE_TEXTINFO, new Action1<Config.TextInfoType>() {
            @Override
            public void call(Config.TextInfoType textInfoType) {
                setTextInfo(textInfoType);
            }
        });
        mRxManager.on(Config.Config_RxBus.RX_TTS_SPEAK, new Action1<String>() {
            @Override
            public void call(String str) {
                ttsClient.speak(str, 1, null);
            }
        });
        mRxManager.on(Config.Config_RxBus.RX_NET_DISCONNECT, new Action1<String>() {
            @Override
            public void call(String str) {
                ttsClient.speak(str, 1, null);
                networksw.setChecked(false);
            }
        });
    }


    private void setTextInfo(Config.TextInfoType type) {
        switch (type) {
            case ChangeGPSINFO:
                if (!gpsState) {
                    ttsClient.speak("gps定位成功", 1, null);
                    gpsState = true;
                }
                direction.setText("方向:" + MyApplication.getInstance().direction + "度");
                speed.setText("速度:" + MyApplication.getInstance().speedGPS + "km/h");
                lat.setText("纬度:" + MyApplication.getInstance().lat);
                lon.setText("经度:" + MyApplication.getInstance().lon);
                break;
            case ClearGPSINFO:
                gpsState = false;
                ttsClient.speak("gps定位失败", 1, null);
                direction.setText(" ");
                speed.setText("");
                lat.setText("");
                lon.setText("");
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopPreview();
    }

    public void onInit(int paramInt) {
        this.ttsClient.speak("欢迎使用EXSUNTerminal计时终端", 1, null);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int i = item.getItemId();
        switch (i) {
            case R.id.nav_def_setting:
                startActivityForResult(SettingsActivity.class, REQUEST_SETTING);
                break;
            case R.id.nav_ZhuCe:
                TcpHelper.getInstance().sendRegistInfo();
                break;
            case R.id.nav_ZhuXiao:
                TcpHelper.getInstance().sendCancellation();
                break;
            case R.id.nav_DEVlogin:
                TcpHelper.getInstance().sendAuthentication();
                break;
        }
        return false;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {

        }
        return false;
    }

    @OnClick({R.id.JiaoLianButton, R.id.XueYuanButton, R.id.textViewThisTime,
            R.id.surfaceView})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.JiaoLianButton:
                startActivity(CaptureActivity.class);
                break;
            case R.id.XueYuanButton:
                break;
            case R.id.textViewThisTime:
                break;
            case R.id.surfaceView:
                if (!CamStatOK)
//                    resetCam();
                    break;
        }
    }

    CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (!TcpHelper.getInstance().isConnected && b) {
                TcpHelper.getInstance().connect(ip, port, 10 * 1000);
                return;
            }
            if (TcpHelper.getInstance().isConnected && !b) {
                RxBus.getInstance().post(Config.Config_RxBus.RX_TTS_SPEAK, "是否断开连接");
                new AlertDialog.Builder(MainActivity.this, R.style.custom_dialog).setTitle("服务器断开提示").setIcon(R.drawable.main_img06).setMessage("是否服务器断开").setCancelable(false).setPositiveButton("断开", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int) {
                        TcpHelper.getInstance().disConnect();
                    }
                }).setNeutralButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int) {
                        MainActivity.this.networksw.setChecked(true);
                        paramAnonymous2DialogInterface.cancel();
                    }
                }).show();
                return;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_SETTING:
                MSG.getInstance(this).loadSetting();
                break;
        }
    }

    private void initCamera() {
        holder = surfaceView.getHolder();
        initPreviewListener(this);
    }

    private void initPreviewListener(SurfaceHolder.Callback callback) {
        holder.addCallback(callback);
    }

    private void startCamera() {
//        LsAdas.open();
    }

    private void resetCam() {
        startCamera();
    }

    @Override
    public void onError(int i, Camera camera) {

    }

    @Override
    public void onPreviewFrame(byte[] bytes, Camera camera) {
        if (bytes == null) {
            Logger.d("################ bytes == null ");
            return;
        } else if (bytes.length != width * height * 3 / 2) {
            Logger.d("################ bytes.length != width * height * 3 / 2 ");
            return;
        } else {
//            Log.d(TAG, "################ onPreviewFrame success  bytes.length = " + bytes.length);
//            Log.d(TAG, " " + bytes[0] + " " + bytes[1] + " " + bytes[2] + " " + bytes[3] + " " + bytes[4] + " " + bytes[5] + " " + bytes[6] + " " + bytes[7] + " " + bytes[8] + " " + bytes[9]);
//            Log.d(TAG, " " + bytes[1000] + " " + bytes[1001] + " " + bytes[1002] + " " + bytes[1003] + " " + bytes[1004] + " " + bytes[1005] + " " + bytes[1006] + " " + bytes[1007] + " " + bytes[1008] + " " + bytes[1009]);
//            Log.d(TAG, " " + bytes[10000] + " " + bytes[10001] + " " + bytes[10002] + " " + bytes[10003] + " " + bytes[10004] + " " + bytes[10005] + " " + bytes[10006] + " " + bytes[10007] + " " + bytes[10008] + " " + bytes[10009]);
        }
        if (mPreBuffer == null) {
            int size = width * height * 3 / 2;
            mPreBuffer = new byte[size];
        }
        camera.addCallbackBuffer(mPreBuffer);
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
            Log.d(TAG, "startPreview      &&&&&&      start      isPreview :  " + isPreview + "      camera : " + camera);
            if (!isPreview) {
                Log.d("", Camera.getNumberOfCameras() + "");
                if (null == camera) camera = Camera.open(carmerId_HANGJING);
                Camera.Parameters parameters = camera.getParameters();
                parameters.setPictureFormat(PixelFormat.JPEG);
                parameters.set("jpeg-quality", 85);
                Log.d(TAG, "width, height ==   " + width + " , " + height);
                parameters.setPreviewSize(width, height);
                parameters.setPictureSize(width, height);
                parameters.setExposureCompensation(0);
                int size = width * height * 3 / 2;
                if (mPreBuffer == null) {
                    mPreBuffer = new byte[size];
                }
                camera.addCallbackBuffer(mPreBuffer);
                camera.setPreviewCallbackWithBuffer(this);
                camera.setParameters(parameters);
                camera.setPreviewDisplay(holder);
                camera.startPreview();
                isPreview = true;
                camera.setErrorCallback(this);
            }
            Log.d(TAG, "startPreview      &&&&&&      end      isPreview :  " + isPreview + "      camera : " + camera);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopPreview() {
        Logger.d("stopPreview      &&&&&&      start      isPreview :  " + isPreview + "      camera : " + camera);
        if (null != camera) {
            if (isPreview) {
                camera.stopPreview();
                camera.setErrorCallback(null);
                isPreview = false;
            }
            camera.release();
            camera = null;
            Logger.d("stopPreview      &&&&&&      camera :  " + camera);
        }
        Logger.d("stopPreview      &&&&&&      end      isPreview :  " + isPreview);
    }

/******
 * 獲取攝像頭id的方法，某些机器上不适用
 */
    //    private int getDefaultCameraId() {
//        int defaultId = -1;
//
//        // Find the total number of cameras available
//        int mNumberOfCameras = Camera.getNumberOfCameras();
//
//        // Find the ID of the default camera
//        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
//        for (int i = 0; i < mNumberOfCameras; i++) {
//            Camera.getCameraInfo(i, cameraInfo);
//            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
//                defaultId = i;
//            }
//        }
//        if (-1 == defaultId) {
//            if (mNumberOfCameras > 0) {
//                // 如果没有后向摄像头
//                defaultId = 0;
//            } else {
//                // 没有摄像头
//                Toast.makeText(getApplicationContext(), "没有摄像头",
//                        Toast.LENGTH_LONG).show();
//            }
//        }
//        return defaultId;
//    }
}


