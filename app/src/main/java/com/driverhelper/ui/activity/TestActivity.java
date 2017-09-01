package com.driverhelper.ui.activity;

import android.content.SharedPreferences;
import android.util.Log;
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
import com.driverhelper.utils.ByteUtil;
import com.jaydenxiao.common.commonutils.ToastUitl;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;


public class TestActivity extends SerialPortActivity {

    @Bind(R.id.tv1)
    TextView tv1;
    @Bind(R.id.tv2)
    TextView tv2;
    @Bind(R.id.load)
    Button load;
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
        List<StudyInfo> list = DbHelper.getInstance().queryStudyInfoAll();
        for (StudyInfo data : list) {
            Log.d("studyinfo", data.toString());
        }

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
    }

    @OnClick(R.id.load)
    public void onClick() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.driverhelper1_preferences", MODE_PRIVATE);
        Log.e("123", sharedPreferences.getString(Config.WriteSetting.PROVINCE, ""));
//        MSG.getInstance().setPARAM0001("1111");
//        MSG.getInstance().getPARAM0001();
//        Log.d("123",ConstantInfo.param0001 + "");
//        Bitmap bitmap = AssetsHelper.getImageFromAssetsFile(this, "123456.jpg");
//        byte[] data = ByteUtil.bitmap2Bytes(bitmap);
////        bitmap.recycle();
//        String path = PhotoHelper.saveBitmap(this, data);
//        Bitmap bt = PhotoHelper.loadBitmap(path);
//        imageView1.setImageBitmap(bt);
    }
}
