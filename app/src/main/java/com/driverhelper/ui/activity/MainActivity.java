package com.driverhelper.ui.activity;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.driverhelper.R;
import com.driverhelper.app.MyApplication;
import com.driverhelper.beans.MSG;
import com.driverhelper.beans.QRbean;
import com.driverhelper.beans.db.StudyInfo;
import com.driverhelper.config.Config;
import com.driverhelper.config.ConstantInfo;
import com.driverhelper.helper.DbHelper;
import com.driverhelper.helper.HandMsgHelper;
import com.driverhelper.helper.IdHelper;
import com.driverhelper.other.timeTask.PhotoTimerTask;
import com.driverhelper.other.timeTask.StudyInfoTimeTask;
import com.driverhelper.other.tcp.TcpHelper;
import com.driverhelper.helper.WriteSettingHelper;
import com.driverhelper.other.Action;
import com.driverhelper.other.SerialPortActivity;
import com.driverhelper.other.handle.ObdHandle;
import com.driverhelper.utils.ByteUtil;
import com.driverhelper.widget.LiveSurfaceView;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.jaydenxiao.common.baserx.RxBus;
import com.jaydenxiao.common.commonutils.TimeUtil;
import com.jaydenxiao.common.commonutils.ToastUitl;
import com.jaydenxiao.common.commonutils.VersionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.OnClick;
import rx.functions.Action1;

import static android.view.KeyEvent.KEYCODE_BACK;
import static com.driverhelper.config.Config.Config_RxBus.RX_TTS_SPEAK;
import static com.driverhelper.config.Config.TextInfoType.ChangeGPSINFO;
import static com.driverhelper.config.Config.TextInfoType.UPDATATIME;
import static com.driverhelper.config.Config.ip;
import static com.driverhelper.config.Config.isStudentLoginOK;
import static com.driverhelper.config.Config.port;
import static com.driverhelper.config.ConstantInfo.embargoStr;
import static com.driverhelper.config.ConstantInfo.isEmbargo;
import static com.driverhelper.config.ConstantInfo.photoTImerDelay;
import static com.driverhelper.config.ConstantInfo.qRbean;
import static com.driverhelper.config.ConstantInfo.studyInfoTimerDelay;
import static com.jaydenxiao.common.commonutils.TimeUtil.dateFormatYMDHMS_;

public class MainActivity extends SerialPortActivity implements NavigationView.OnNavigationItemSelectedListener,
        TextToSpeech.OnInitListener {

    final String TAG = getClass().getName().toUpperCase();

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.networksw)
    Switch networksw;
    @Bind(R.id.surfaceView)
    LiveSurfaceView surfaceView;
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
    Context context;

    private static final int REQUEST_SETTING = 1;

    Timer studyTimer;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void sendMessage(int what) {
        Message message = new Message();
        message.what = what;
        handler.sendMessage(message);
    }

    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Config.TextInfoType.UPDATATIME:
                    if (textViewTime != null) {
                        textViewTime.setText(TimeUtil.getFriendlyDuration2(ConstantInfo.studyTimeThis));
                    }
                    break;
                case Config.TextInfoType.ChangeGPSINFO:
                    direction.setText("方向:" + MyApplication.getInstance().direction + "度");
                    speed.setText("速度:" + MyApplication.getInstance().speedGPS + "km/h");
                    lat.setText("纬度:" + MyApplication.getInstance().lat);
                    lon.setText("经度:" + MyApplication.getInstance().lon);
                    break;
                case Config.TextInfoType.ClearGPSINFO:
                    direction.setText(" ");
                    speed.setText("");
                    lat.setText("");
                    lon.setText("");
                    break;
                case Config.TextInfoType.SETJIAOLIAN:
                    COACHNUMtext.setText(qRbean.getName());
                    IDCARDtext.setText(qRbean.getNumber());
                    break;
                case Config.TextInfoType.SETXUEYUAN:
                    XueYuanTEXT.setText(qRbean.getName());
                    STUNUMtext.setText(qRbean.getNumber());
                    textViewStat.setText("培训学时:" + ConstantInfo.StudentInfo.totleTime / 10 + " / " + ConstantInfo.StudentInfo.finishedTime / 10 + "分\n"
                            + "培训里程:" + ConstantInfo.StudentInfo.totleMileage + "km / " + ConstantInfo.StudentInfo.finishedMileage + "km");
                    break;
                case Config.TextInfoType.CLEARJIAOLIAN:
                    COACHNUMtext.setText("");
                    IDCARDtext.setText("");
                    break;
                case Config.TextInfoType.CLEARXUEYUAN:

                    XueYuanTEXT.setText("");
                    STUNUMtext.setText("");
                    textViewTime.setText("00:00:00");
                    textViewStat.setText("培训学时:" + 0 + " / " + 0 + "分\n"
                            + "培训里程:" + 0 + "km / " + 0 + "km");
                    break;
            }
            super.handleMessage(msg);
        }
    };


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


    Toolbar.OnMenuItemClickListener onMenuItemClickListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {

            switch (menuItem.getItemId()) {
                case R.id.NoSendConti:
                    test();
                    break;
                case R.id.action_0402_1:
                    TcpHelper.getInstance().send0402((byte) 0x01, "");
                    break;
                case R.id.action_0402_5:
                    TcpHelper.getInstance().send0402((byte) 0x05, "");
                    break;
                case R.id.action_0402_7:
                    TcpHelper.getInstance().send0402((byte) 0x07, "");
                    break;
                case R.id.deteleStudyInfo:
                    DbHelper.getInstance().deteleStudyInfoByTime(TimeUtil.getFirstTimeOfDay());
                    break;
                case R.id.deteleStudyInfo_Uped:
                    DbHelper.getInstance().deteleStudyInfoUped();
                    break;
                case R.id.upNoUpInfo:
                    List<StudyInfo> list = DbHelper.getInstance().queryStudyInfoByUp();
                    TcpHelper.getInstance().reSendRStudyInfo(list);
                    break;
            }
            return false;
        }
    };


    void initToolBar() {
        StringBuilder localStringBuilder1 = new StringBuilder().append("EXSUNTerminal 终端系统 ").append(VersionUtil.getVersion(this)).append("  ( 科目");
        StringBuilder localStringBuilder2 = localStringBuilder1.append(WriteSettingHelper.getEXAM_SUBJECTS()).append("  ");
        StringBuilder localStringBuilder3 = localStringBuilder2.append(WriteSettingHelper.getEXAM_TYPE()).append("  ");
        StringBuilder localStringBuilder4 = localStringBuilder3.append(WriteSettingHelper.getVEHICLE_NUMBER()).append("  ");
//        StringBuilder localStringBuilder5 = localStringBuilder4.append(MSG.ClientInfo.param0083).append(" ) : ");
        toolbar.setTitle(new String(localStringBuilder4));
        setSupportActionBar(this.toolbar);
        toolbar.setOnMenuItemClickListener(onMenuItemClickListener);

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
        context = this;
        this.ttsClient = new TextToSpeech(getApplicationContext(), this);
        if (MyApplication.getInstance().isFirst)
            this.ttsClient.speak("首次登陆，请设置终端参数", 1, null);
        WriteSettingHelper.loadRegistInfo();
        MSG.getInstance().loadSetting1();
        MSG.getInstance().loadSetting();
        TcpHelper.getInstance().connect(ip, port, 10 * 1000);
    }

    @Override
    public void initEvent() {
        mRxManager.on(Config.Config_RxBus.RX_NET_CONNECTED, new Action1<Object>() {
            @Override
            public void call(Object obj) {
                networksw.setChecked(true);
            }
        });
        mRxManager.on(Config.Config_RxBus.RX_LOCATION_OK, new Action1<Object>() {
            @Override
            public void call(Object object) {
                ttsClient.speak("gps定位成功", 1, null);
                sendMessage(ChangeGPSINFO);
            }
        });
        mRxManager.on(Config.Config_RxBus.RX_LOCATION_FALINE, new Action1<Object>() {
            @Override
            public void call(Object object) {
                ttsClient.speak("gps定位失败", 1, null);
                sendMessage(Config.TextInfoType.ClearGPSINFO);
            }
        });
        mRxManager.on(RX_TTS_SPEAK, new Action1<String>() {
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
        mRxManager.on(Config.Config_RxBus.RX_COACH_LOGINOK, new Action1<String>() {
            @Override
            public void call(String str) {
                ttsClient.speak(str, 1, null);
                sendMessage(Config.TextInfoType.SETJIAOLIAN);
                Config.isCoachLoginOK = true;
                ConstantInfo.coachId = qRbean.getNumber();
                WriteSettingHelper.setCOACHNUM(ConstantInfo.coachId);
            }
        });
        mRxManager.on(Config.Config_RxBus.RX_COACH_LOGOUTOK, new Action1<String>() {
            @Override
            public void call(String str) {
                ttsClient.speak(str, 1, null);
                sendMessage(Config.TextInfoType.CLEARJIAOLIAN);
                Config.isCoachLoginOK = false;
                WriteSettingHelper.setCOACHNUM("");
            }
        });
        mRxManager.on(Config.Config_RxBus.RX_STUDENT_LOGINOK, new Action1<HandMsgHelper.Class8201>() {
            @Override
            public void call(HandMsgHelper.Class8201 class8201) {
                ttsClient.speak("学员登录成功", 1, null);
                sendMessage(Config.TextInfoType.SETXUEYUAN);
                Config.isStudentLoginOK = true;
                ConstantInfo.StudentInfo.studentId = ByteUtil.getString(class8201.studentNum);
                ConstantInfo.StudentInfo.totleMileage = class8201.totleMileage;
                ConstantInfo.StudentInfo.finishedMileage = class8201.finishedMileage;
                ConstantInfo.StudentInfo.totleTime = class8201.totleStudyTime;
                ConstantInfo.StudentInfo.finishedTime = class8201.finishedStudyTime;
                Log.e("studentId", "studentId = " + ConstantInfo.StudentInfo.studentId);
                Log.e("totleMileage", "totleMileage = " + ConstantInfo.StudentInfo.totleMileage);
                Log.e("finishedMileage", "finishedMileage = " + ConstantInfo.StudentInfo.finishedMileage);
                Log.e("totleTime", "totleTime = " + ConstantInfo.StudentInfo.totleTime);
                Log.e("finishedTime", "finishedTime = " + ConstantInfo.StudentInfo.finishedTime);
                startStudy();
            }
        });
        mRxManager.on(Config.Config_RxBus.RX_STUDENT_LOGOUTOK, new Action1<String>() {
            @Override
            public void call(String str) {
                stopStudy();
                ttsClient.speak(str, 1, null);
                sendMessage(Config.TextInfoType.CLEARXUEYUAN);
            }
        });
        mRxManager.on(Config.Config_RxBus.RX_SETTING_8103, new Action1<HandMsgHelper.Class8103>() {
            @Override
            public void call(HandMsgHelper.Class8103 class8103) {
                ttsClient.speak("收到设置终端参数请求", 1, null);
                if (class8103.getSettingList() != null) {
                    MSG.getInstance().setSettings(class8103.getSettingList());
                }
            }
        });
        mRxManager.on(Config.Config_RxBus.RX_SETTING_EMBARGOSTATE, new Action1<HandMsgHelper.Class8502>() {
            @Override
            public void call(HandMsgHelper.Class8502 class8502) {
                if (!TextUtils.isEmpty(class8502.data)) {
                    ttsClient.speak(class8502.data, 1, null);
                }
                switch (class8502.state) {
                    case 1:
                        isEmbargo = false;
                        TcpHelper.getInstance().send0502((byte) 0x01, (byte) 0x01, class8502.dataLength, class8502.data);
                        break;
                    case 2:
                        isEmbargo = true;
                        TcpHelper.getInstance().send0502((byte) 0x01, (byte) 0x02, class8502.dataLength, class8502.data);
                        break;
                }
                embargoStr = class8502.data;
                WriteSettingHelper.setEMBARGO(isEmbargo);
                WriteSettingHelper.setEMBARGOSTR(embargoStr);
            }
        });
        mRxManager.on(Config.Config_RxBus.RX_SETTING_8106, new Action1<HandMsgHelper.Class8106>() {
            @Override
            public void call(HandMsgHelper.Class8106 class8106) {
                ttsClient.speak("收到终端查询请求", 1, null);
                TcpHelper.getInstance().send0104(class8106.waterCode, class8106.idList);            //8106和8104都是返回0104,区别在于8106返回所有，8104返回查询指定的
            }
        });
        mRxManager.on(Config.Config_RxBus.RX_SETTING_8104, new Action1<Integer>() {
            @Override
            public void call(Integer waterId) {
                ttsClient.speak("收到查询终端所有参数请求", 1, null);
                TcpHelper.getInstance().sendAll0104(waterId);            //8106和8104都是返回0104,区别在于8106返回所有，8104返回查询指定的
            }
        });
        mRxManager.on(Config.Config_RxBus.RX_SETTING_0501, new Action1<HandMsgHelper.Class8501>() {
            @Override
            public void call(HandMsgHelper.Class8501 class8501) {
                ttsClient.speak("收到设置请求", 1, null);
                WriteSettingHelper.set0501(class8501);
            }
        });
        mRxManager.on(Config.Config_RxBus.RX_SETTING_8301, new Action1<HandMsgHelper.Class8301>() {
            @Override
            public void call(HandMsgHelper.Class8301 class8301) {
                String photoId = TimeUtil.getTime() / 1000 + "";
                TcpHelper.getInstance().send0301(class8301.updataType);
                surfaceView.doTakePictureAndSend(photoId);
            }
        });
        mRxManager.on(Config.Config_RxBus.RX_SETTING_8302, new Action1<HandMsgHelper.Class8302>() {
            @Override
            public void call(HandMsgHelper.Class8302 class8302) {
                Action.getInstance().action_0303(class8302);
            }
        });
        mRxManager.on(Config.Config_RxBus.RX_SETTING_8202_, new Action1<HandMsgHelper.Class8202_>() {

            @Override
            public void call(HandMsgHelper.Class8202_ class8202_) {
                Action.getInstance().action_8202_(class8202_);
                ttsClient.speak("临时位置追踪", 1, null);
            }
        });
        mRxManager.on(Config.Config_RxBus.RX_SETTING_8205, new Action1<HandMsgHelper.Class8205>() {

            @Override
            public void call(HandMsgHelper.Class8205 class8205) {
                ttsClient.speak("命令上报学时记录" + class8205.findType, 1, null);
                Action.getInstance().action_8205(class8205);
            }
        });
        mRxManager.on(Config.Config_RxBus.RX_SETTING_8304, new Action1<HandMsgHelper.Class8304>() {

            @Override
            public void call(HandMsgHelper.Class8304 class8304) {
                ttsClient.speak("上传指定照片" + ByteUtil.getString(class8304.photoId), 1, null);
                Action.getInstance().action_8304(MainActivity.this, class8304);
            }
        });
    }


    @Override
    protected void onOBDDataReceived(final byte[] buffer, final int length) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                byte[] data = new byte[length];
                System.arraycopy(buffer, 0, data, 0, length);
//                ByteUtil.printHexString("obd接收到数据", data);
                HashMap<String, String> map = ObdHandle.handle(data);
                String carSpeed = map.get("car speed");
                if (!TextUtils.isEmpty(carSpeed)) {
                    textViewSpeed.setText(carSpeed + "km/h");
                }
                String speed = map.get("speed");
                if (!TextUtils.isEmpty(speed)) {
                    textViewRPM.setText(speed + "RPM");
                }
                String mileage = map.get("mileage");
                if (!TextUtils.isEmpty(mileage)) {
                    textViewDistance.setText(mileage + "km");
                }
            }
        });
    }

    @Override
    protected void onIcReaderDataReceived(final byte[] buffer, final int size) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ByteUtil.printHexString("读卡器接收到数据", buffer);
            }
        });
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


    @OnClick({R.id.JiaoLianButton, R.id.XueYuanButton, R.id.textViewThisTime, R.id.surfaceView})
    public void onClick(View view) {
        if (!ConstantInfo.isEmbargo) {
            switch (view.getId()) {
                case R.id.JiaoLianButton:
                    if (!Config.isCoachLoginOK) {
                        String str = "{\"name\": \"张泽斌\",\"id\": \"130727199604011092\",\"number\": \"3400452633643758\",\"type\": \"C1\"}";
                        qRbean = new Gson().fromJson(str, QRbean.class);
                        coachLogin();
//                        startScanActivity("教练员签到");
                    } else {
                        if (isStudentLoginOK) {
                            ttsClient.speak("学员未登出，请先学员登出", 1, null);
                            return;
                        } else {
                            new AlertDialog.Builder(MainActivity.this, R.style.custom_dialog).setTitle("教练员登出提示").setIcon(R.drawable.main_img06).setMessage("教练员是否登出").setCancelable(false).setPositiveButton("登出", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int) {

                                    coachLogout();
                                }
                            }).setNeutralButton("取消", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int) {
                                    paramAnonymous2DialogInterface.dismiss();
                                }
                            }).show();
                        }
                    }
                    break;
                case R.id.XueYuanButton:
                    if (!Config.isStudentLoginOK) {
                        String str = "{\"name\": \"肖雪\",\"id\": \"420117199305250036\",\"number\": \"0655824366114239\"}";
                        qRbean = new Gson().fromJson(str, QRbean.class);
                        studentLogin(qRbean.getNumber());
//                        startScanActivity("学员签到");
                    } else {
                        new AlertDialog.Builder(MainActivity.this, R.style.custom_dialog).setTitle("学员登出提示").setIcon(R.drawable.main_img06).setMessage("学员是否登出").setCancelable(false).setPositiveButton("登出", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int) {
                                studentLogout();
                            }
                        }).setNeutralButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int) {
                                paramAnonymous2DialogInterface.dismiss();
                            }
                        }).show();
                    }
                    break;
                case R.id.textViewThisTime:
                    break;
                case R.id.surfaceView:
                    surfaceView.doTakePicture();
                    break;
            }
        } else {
            RxBus.getInstance().post(Config.Config_RxBus.RX_TTS_SPEAK, ConstantInfo.embargoStr);
        }
    }


    private void startScanActivity(String title) {
        RxBus.getInstance().post(RX_TTS_SPEAK, title);
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt(title);
        integrator.setCameraId(Config.carmerId_HANGJING);  // Use a specific camera of the device
        integrator.setBeepEnabled(false);
//        integrator.setTimeout(10 * 1000);
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();
    }

    CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (!TcpHelper.getInstance().isConnected && b) {
                TcpHelper.getInstance().connect(ip, port, 10 * 1000);
                return;
            }
            if (TcpHelper.getInstance().isConnected && !b) {
                RxBus.getInstance().post(RX_TTS_SPEAK, "是否断开连接");
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
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();

                /********************************************************************************/
////                ToastUitl.show(result.getContents(), Toast.LENGTH_SHORT);
//                String str = "{\"name\": \"肖雪\",\"id\": \"420117199305250036\",\"number\": \"0655824366114239\"}";//为了调试把逻辑写反了
////                String str = "{\"name\": \"张泽斌\",\"id\": \"130727199604011092\",\"number\": \"3400452633643758\",\"type\": \"C1\"}";
//                qRbean = new Gson().fromJson(str, QRbean.class);
////                ToastUitl.show(result.getContents(), Toast.LENGTH_SHORT);
////                qRbean = new Gson().fromJson(result.getContents(), QRbean.class);
////                    coachLogin();                       //教练登录
//                studentLogin(qRbean.getNumber());                 //学员登录
                /********************************************************************************/


            } else {
                ToastUitl.show(result.getContents(), Toast.LENGTH_SHORT);
//                String str = "{\"name\": \"肖雪\",\"id\": \"420117199305250036\",\"number\": \"0655824366114239\"}";//为了调试把逻辑写反了
//                String str = "{\"name\": \"张泽斌\",\"id\": \"130727199604011092\",\"number\": \"3400452633643758\",\"type\": \"C1\"}";
//                qRbean = new Gson().fromJson(str, QRbean.class);
                ToastUitl.show(result.getContents(), Toast.LENGTH_SHORT);
                qRbean = new Gson().fromJson(result.getContents(), QRbean.class);
                if (qRbean.getType() != null && !Config.isCoachLoginOK) {
                    coachLogin();                       //教练登录
                    return;
                }
                if (qRbean.getType() == null && Config.isCoachLoginOK && !Config.isStudentLoginOK) {
                    studentLogin(qRbean.getNumber());                 //学员登录
                    return;
                }
                if (qRbean.getType() == null && Config.isStudentLoginOK) {
                    RxBus.getInstance().post(RX_TTS_SPEAK, "学员已签到");
                    return;
                }
                if (qRbean.getType() != null && Config.isCoachLoginOK) {
                    RxBus.getInstance().post(RX_TTS_SPEAK, "教练员已签到");
                    return;
                }
                if (qRbean.getType() == null && !Config.isCoachLoginOK) {
                    RxBus.getInstance().post(RX_TTS_SPEAK, "教练员未签到");
                    return;
                }
            }
        }
        switch (requestCode) {
            case REQUEST_SETTING:
                MSG.getInstance().loadSetting();
                MSG.getInstance().loadSetting1();
                WriteSettingHelper.loadRegistInfo();
                break;
        }
    }


    private void coachLogin() {
        RxBus.getInstance().post(RX_TTS_SPEAK, "教练员扫描成功");
        TcpHelper.getInstance().sendCoachLogin(qRbean.getId(), qRbean.getNumber(), qRbean.getType());
    }

    private void coachLogout() {
        TcpHelper.getInstance().sendCoachLogout();
    }

    private void studentLogin(String studentNum) {
        if (TextUtils.isEmpty(ConstantInfo.coachId)) {
            RxBus.getInstance().post(RX_TTS_SPEAK, "教练员未签到");
            return;
        }
        RxBus.getInstance().post(RX_TTS_SPEAK, "学员扫描成功");
        TcpHelper.getInstance().sendStudentLogin(ConstantInfo.coachId, studentNum);
    }

    private void studentLogout() {
        TcpHelper.getInstance().sendStudentLogiout();
        ToastUitl.show("学员登出", Toast.LENGTH_SHORT);
    }

    /***
     * 开始培训
     */
    private void startStudy() {
        ConstantInfo.studyTimeThis = 0;
        studyTimer = new Timer(true);
        studyTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                sendMessage(UPDATATIME);
                ConstantInfo.studyTimeThis++;
            }
        }, 1000, 1000);

        ConstantInfo.studyInfoTimer = new Timer(true);
        ConstantInfo.studyInfoTimer.schedule(new StudyInfoTimeTask(), 10, studyInfoTimerDelay);

        ConstantInfo.photoTimer = new Timer(true);
        ConstantInfo.photoTimer.schedule(new PhotoTimerTask(surfaceView), 10, photoTImerDelay);

    }

    private void stopStudy() {
        if (studyTimer != null) {
            studyTimer.cancel();
            studyTimer = null;
        }
        if (ConstantInfo.studyInfoTimer != null) {
            ConstantInfo.studyInfoTimer.cancel();
            ConstantInfo.studyInfoTimer = null;
        }
        if (ConstantInfo.photoTimer != null) {
            ConstantInfo.photoTimer.cancel();
            ConstantInfo.photoTimer = null;
        }
        Config.isStudentLoginOK = false;
        ConstantInfo.StudentInfo.studentId = null;
        ConstantInfo.studyTimeThis = 0;
        ConstantInfo.studyDistanceThis = 0;
    }


    private void test() {
        Log.e("111", "/**************************************************/");
        for (int i = 0; i < 20; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int waterCode = IdHelper.getWaterCode();
                    Log.w("getWaterCode", waterCode + "");
                    byte[] byte111 = ByteUtil.int2WORD(waterCode);
                    ByteUtil.printHexString("byte", byte111);
                    Log.e("getWaterCode1111", ByteUtil.byte2int(byte111) + "");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KEYCODE_BACK:
                new AlertDialog.Builder(MainActivity.this, R.style.custom_dialog).setTitle("是否退出应用").setIcon(R.drawable.main_img06).setMessage("是否退出应用").setCancelable(false).setPositiveButton("退出", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int) {
                        MainActivity.this.finish();
                    }
                }).setNeutralButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int) {
                        paramAnonymous2DialogInterface.dismiss();
                    }
                }).show();
                break;
        }
        return false;
    }
}


