package com.driverhelper.ui.activity;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.driverhelper.R;
import com.driverhelper.helper.WriteSettingHelper;
import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.commonutils.VersionUtil;

import butterknife.Bind;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener,
        TextToSpeech.OnInitListener,
        Toolbar.OnMenuItemClickListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.switch1)
    Switch switch1;
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
    @Bind(R.id.textView1)
    TextView textView1;
    @Bind(R.id.textView3)
    TextView textView3;
    @Bind(R.id.textView2)
    TextView textView2;
    @Bind(R.id.textView4)
    TextView textView4;
    @Bind(R.id.textViewStat)
    TextView textViewStat;
    @Bind(R.id.textViewThisTime)
    TextView textViewThisTime;
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
    }

    @Override
    public void initEvent() {
//        mRxManager.on(Config.Config_RxBus.RX_COACH_SIGN, new Action1<String>() {
//
//            @Override
//            public void call(String id) {
//                switchFragment(COACH_SIGN);
//                ToastUitl.show("教练员签到", Toast.LENGTH_SHORT);
//            }
//        });
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
                Intent localIntent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivityForResult(localIntent, 1);
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
}
