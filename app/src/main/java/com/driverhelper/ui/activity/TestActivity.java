package com.driverhelper.ui.activity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.driverhelper.R;
import com.driverhelper.config.ConstantInfo;
import com.driverhelper.other.tcp.netty.TcpHelper;
import com.jaydenxiao.common.base.BaseActivity;

import java.net.InetSocketAddress;

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
//                        TcpHelper.getInstance().connect(new InetSocketAddress("192.168.1.110", 4321));
                        TcpHelper.getInstance().connect(new InetSocketAddress(ConstantInfo.ip, ConstantInfo.port));
                    }
                }).start();
                break;
            case R.id.load1:
                TcpHelper.getInstance().sendTest();
                break;
        }
    }
}
