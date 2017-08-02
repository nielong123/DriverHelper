package com.driverhelper.ui.activity;

import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

import com.driverhelper.R;
import com.driverhelper.other.SerialPortActivity;
import com.driverhelper.other.handle.ObdHandle;
import com.driverhelper.utils.ByteUtil;
import com.jaydenxiao.common.commonutils.ToastUitl;

import java.util.HashMap;

import butterknife.Bind;


public class TestActivity extends SerialPortActivity {

    @Bind(R.id.tv1)
    TextView tv1;
    @Bind(R.id.tv2)
    TextView tv2;

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
                tv1.setText(size + "km/h");
                byte[] data = new byte[size];
                System.arraycopy(buffer, 0, data, 0, size);
                ByteUtil.printHexString("obd接收到数据", data);
                final HashMap<String, String> map = ObdHandle.handle(data);
                String str = map.get("speed");
                tv2.setText(str);
            }
        });


//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                byte[] data = new byte[size];
//                System.arraycopy(buffer, 0, data, 0, size);
//                ByteUtil.printHexString("obd接收到数据", data);
//                final HashMap<String, String> map = ObdHandle.handle(data);
//                tv1.setText("km/h");
////
//
//            }
//        }).start();
    }
}
