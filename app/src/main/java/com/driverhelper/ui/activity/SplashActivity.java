package com.driverhelper.ui.activity;

import android.os.Handler;
import android.widget.TextView;

import com.driverhelper.R;
import com.driverhelper.config.Config;
import com.google.zxing.integration.android.IntentIntegrator;
import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.commonutils.VersionUtil;

import butterknife.Bind;

import static com.driverhelper.config.ConstantInfo.reloadInfo;

/**
 * Created by Administrator on 2017/5/31.
 */

public final class SplashActivity extends BaseActivity {

    long delayTime = 1 * 1000;


    @Bind(R.id.version_code)
    TextView versionCode;
    @Bind(R.id.serial_code)
    TextView serialCode;

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
//        versionCode.setText(VersionUtil.getVersion(this));
//        serialCode.setText(VersionUtil.getSerialCode());
    }

    @Override
    public void initData() {
        reloadInfo();
    }


    @Override
    public void initEvent() {
        handler.postDelayed(runnable, delayTime);
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
//            startActivity(Main2Activity.class);
            startActivity(MainActivity.class);
//            startActivity(TestActivity.class);
            finish();
        }
    };

//    void test() {
//        Logger.d(Arrays.toString(ByteUtil.hexString2BCD("1234567890abcdef")));
//        Logger.d(ByteUtil.str2HexStr("1234567890abcdef"));
//    }
}
