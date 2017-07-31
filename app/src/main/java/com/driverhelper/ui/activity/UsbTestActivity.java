package com.driverhelper.ui.activity;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;


import com.driverhelper.R;

import java.util.HashMap;
import java.util.Iterator;

public class UsbTestActivity extends AppCompatActivity {

    //设备列表
    private HashMap<String, UsbDevice> deviceList;
    //从设备读数据
    private Button read_btn;
    //给设备写数据（发指令）
    private Button write_btn;

    private TextView tvInfo;
    //USB管理器:负责管理USB设备的类
    private UsbManager usbManager;
    //找到的USB设备
    private UsbDevice mUsbDevice;
    //代表USB设备的一个接口
    private UsbInterface mInterface;
    private UsbDeviceConnection mDeviceConnection;
    //代表一个接口的某个节点的类:写数据节点
    private UsbEndpoint usbEpOut;
    //代表一个接口的某个节点的类:读数据节点
    private UsbEndpoint usbEpIn;
    //要发送信息字节
    private byte[] sendbytes;
    //接收到的信息字节
    private byte[] receiveytes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usb_test);
        tvInfo = (TextView) findViewById(R.id.tv);
        initUsbData();
    }

    private void initUsbData() {
        usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        HashMap<String, UsbDevice> deviceHashMap = usbManager.getDeviceList();
        Iterator<UsbDevice> iterator = deviceHashMap.values().iterator();
        while (iterator.hasNext()) {
            UsbDevice device = iterator.next();
            tvInfo.append("device name: " + device.getDeviceName() + "\n" + "device product name:"
                    + device.getProductName() + "vendor id:" + device.getVendorId() +
                    "device serial: " + device.getSerialNumber());
        }
        // }
    }
}
