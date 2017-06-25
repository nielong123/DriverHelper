package com.driverhelper.ui.activity;

import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.driverhelper.R;
import com.driverhelper.config.Config;
import com.driverhelper.helper.WriteSettingHelper;
import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.commonutils.ToastUitl;

import butterknife.Bind;
import butterknife.OnClick;

import static com.driverhelper.config.ConstantInfo.reloadInfo;

public class SettingActivity extends BaseActivity {

    @Bind(R.id.tcp_ip)
    EditText tcpIp;
    @Bind(R.id.tcp_port)
    EditText tcpPort;
    @Bind(R.id.heart_second)
    EditText heartSecond;
    @Bind(R.id.http_url)
    EditText httpUrl;
    @Bind(R.id.submit)
    Button submit;
    @Bind(R.id.abandon)
    Button abandon;
    @Bind(R.id.vehicle_number)
    EditText vehicleNumber;
    @Bind(R.id.device_code)
    EditText deviceCode;
    @Bind(R.id.vehicle_color)
    TextView vehicleColor;

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        tcpIp.setText(WriteSettingHelper.getTCP_IP());
        tcpPort.setText(WriteSettingHelper.getTCP_PORT());
        heartSecond.setText(WriteSettingHelper.getHEART_SPACE());
        httpUrl.setText(WriteSettingHelper.getHTTP_URL());
        vehicleNumber.setText(WriteSettingHelper.getVEHICLE_NUMBER());
        deviceCode.setText(WriteSettingHelper.getDEVICE_CODE());
        vehicleColor.setText(WriteSettingHelper.getVEHICLE_COLOR());
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    @OnClick({R.id.submit, R.id.abandon, R.id.vehicle_color})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit:
                if (!submit()) {
                    break;
                }
                saveSetting();
                reloadInfo();
                ToastUitl.show("设置保存成功", Toast.LENGTH_SHORT);
                finish();
                break;
            case R.id.abandon:
                finish();
                break;
            case R.id.vehicle_color:
                OptionsPickerView optionsPickerView = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        vehicleColor.setText(Config.colors.get(options1));
                    }
                })
                        .isDialog(true)
                        .setCyclic(false, false, false)
                        .setTitleText("车牌颜色")
                        .build();
                optionsPickerView.setPicker(Config.colors);
                optionsPickerView.show();
                break;
        }
    }

    boolean submit() {
        if (TextUtils.isEmpty(tcpIp.getText().toString().trim())) {
            ToastUitl.show("请输入TCP的目标IP", Toast.LENGTH_SHORT);
            return false;
        }
        if (TextUtils.isEmpty(tcpPort.getText().toString().trim())) {
            ToastUitl.show("请输入TCP的目标端口", Toast.LENGTH_SHORT);
            return false;
        }
        if (TextUtils.isEmpty(heartSecond.getText().toString().trim())) {
            ToastUitl.show("请输入TCP的心跳包间隔", Toast.LENGTH_SHORT);
            return false;
        }
        if (TextUtils.isEmpty(httpUrl.getText().toString().trim())) {
            ToastUitl.show("请输入HTTP的URL", Toast.LENGTH_SHORT);
            return false;
        }
        if (TextUtils.isEmpty(vehicleNumber.getText().toString().trim())) {
            ToastUitl.show("请输入车牌号", Toast.LENGTH_SHORT);
            return false;
        }
        if (TextUtils.isEmpty(deviceCode.getText().toString().trim())) {
            ToastUitl.show("请输入设备号", Toast.LENGTH_SHORT);
            return false;
        }
        if (TextUtils.isEmpty(vehicleColor.getText().toString().trim())) {
            ToastUitl.show("请输入HTTP的URL", Toast.LENGTH_SHORT);
            return false;
        }
        return true;
    }

    void saveSetting() {
        WriteSettingHelper.setTCP_IP(tcpIp.getText().toString().trim());
        WriteSettingHelper.setTCP_PORT(tcpPort.getText().toString().trim());
        WriteSettingHelper.setHEART_SPACE(heartSecond.getText().toString().trim());
        WriteSettingHelper.setHTTP_URL(httpUrl.getText().toString().trim());
        WriteSettingHelper.setVEHICLE_NUMBER(vehicleNumber.getText().toString().trim());
        WriteSettingHelper.setDEVICE_CODE(deviceCode.getText().toString().trim());
        WriteSettingHelper.setVEHICLE_COLOR(vehicleColor.getText().toString().trim());
    }
}
