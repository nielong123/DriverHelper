package com.driverhelper.ui.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.driverhelper.R;
import com.driverhelper.other.SerialPortActivity;
import com.driverhelper.other.handle.ObdHandle;
import com.driverhelper.utils.ByteUtil;
import com.jaydenxiao.common.commonutils.ToastUitl;


public class TestActivity extends SerialPortActivity {

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

    @Override
    protected void onIcReaderDataReceived(final byte[] buffer, final int size) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ByteUtil.printHexString("读卡器接收到数据", buffer);
                ToastUitl.show(ByteUtil.getString(buffer), Toast.LENGTH_SHORT);
            }
        });
    }

    @Override
    protected void onOBDDataReceived(final byte[] buffer, final int size) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                byte[] data = new byte[size];
                System.arraycopy(buffer, 0, data, 0, size);
                ByteUtil.printHexString("obd接收到数据", data);
                ObdHandle.handle(data);
            }
        });
    }
}
