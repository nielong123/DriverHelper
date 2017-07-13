package com.driverhelper.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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
import com.driverhelper.other.ReceiverOBDII;
import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.baserx.RxBus;
import com.jaydenxiao.common.commonutils.VersionUtil;

import java.util.Date;
import java.util.Timer;

import butterknife.Bind;
import butterknife.OnClick;
import rx.functions.Action1;

import static com.driverhelper.config.Config.ip;
import static com.driverhelper.config.Config.port;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener,
        TextToSpeech.OnInitListener,
        Toolbar.OnMenuItemClickListener {

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

    Date CurrDate = new Date();

    private static final int REQUEST_PERM = 1000;
    private static final int REQUEST_QR = 49374;
    private static final int REQUEST_SETTING = 1;
    private static final int RESULT_FAIL = 1;
    private static final int RESULT_OK = 0;
    private static final String TAGcam = "CameraRtn";
    private static final String TAGdev = "DeviceCHK";

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
        networksw.setOnCheckedChangeListener(onCheckedChangeListener);
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
        }
        return false;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {

        }
        return false;
    }

    @OnClick({R.id.JiaoLianButton, R.id.XueYuanButton, R.id.textViewThisTime})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.JiaoLianButton:
                break;
            case R.id.XueYuanButton:
                break;
            case R.id.textViewThisTime:
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

}
