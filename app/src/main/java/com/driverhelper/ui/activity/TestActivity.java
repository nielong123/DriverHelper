package com.driverhelper.ui.activity;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.driverhelper.R;
import com.driverhelper.beans.db.StudyInfo;
import com.driverhelper.config.Config;
import com.driverhelper.helper.DbHelper;
import com.driverhelper.other.SerialPortActivity;
import com.driverhelper.other.handle.ObdHandle;
import com.driverhelper.other.tcp.netty.TcpHelper;
import com.driverhelper.utils.ByteUtil;
import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.commonutils.ToastUitl;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;


public class TestActivity extends BaseActivity {

    @Bind(R.id.tv1)
    TextView tv1;
    @Bind(R.id.tv2)
    TextView tv2;
    @Bind(R.id.load)
    Button load;
    @Bind(R.id.load1)
    Button load1;
    @Bind(R.id.imageView1)
    ImageView imageView1;

    String path;

    @Override
    public int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    @OnClick({R.id.load, R.id.load1})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.load:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        TcpHelper.getInstance().connect(new InetSocketAddress("192.168.1.110", 4321));
                    }
                }).start();
                break;
            case R.id.load1:
                TcpHelper.getInstance().sendTest();
                break;
        }
    }
}
