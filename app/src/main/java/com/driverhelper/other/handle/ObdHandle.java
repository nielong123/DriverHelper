package com.driverhelper.other.handle;

import android.util.Log;

import com.driverhelper.utils.ByteUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/28.
 */

public class ObdHandle {

    public static void handle(byte[] data) {
        List<byte[]> dataList = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            if (data[i] == (byte) 0x2e) {
                if (i + 2 >= data.length) break;
                byte[] data0 = new byte[data[i + 2] + 4];
                data0[0] = data[i];             //0x2e
                data0[1] = data[i + 1];         //种类
                int length = data[i + 2];           //数据长度
                data0[2] = (byte) length;
                if ((data.length - i - 1) <= length)
                    break;
//                Log.d("", "data.length - i  -1 = " + (data.length - i - 1) + "length = " + length);
//                Log.d("", "data.length - i = " + (data.length - i));
//                Log.d("", "length = " + length);
//                ByteUtil.printHexString("data0 1111= ", data0);
                System.arraycopy(data, i + 3, data0, 3, data[i + 2] + 1);       //数据，效验
//                ByteUtil.printHexString("data0 2222= ", data0);
                byte[] checkData = new byte[data0.length - 2];
                System.arraycopy(data0, 1, checkData, 0, checkData.length);
                if (ByteUtil.checkSum(checkData, data0[data0.length - 1])) {          //检查数据sum的结果
                    dataList.add(data0);
                }
            }
        }
        for (byte[] data1 : dataList) {
//            ByteUtil.printHexString(data1);
//            ByteUtil.printHex("data1[1] = ", data1[1]);
            switch (data1[1]) {
                case (byte) 0x02:            //转速
                    long speed = data[3] + data[4] * 256;
//                    Log.e("", "转速 = " + speed + "rpm");
                    break;
                case (byte) 0x03:            //车速
                    long speed1 = data[3] + data[4] * 256;
//                    Log.e("", "车速 = " + speed1 + "km/h");
                    break;
                case (byte) 0x06:            //本次行驶里程
                    long mileage = data[3] + data[4] * 256 + data[5] * 65536;
//                    Log.e("", "里程 = " + mileage + "km");
                    break;
                case (byte) 0x7d:
                    if (data[3] == 0) {
//                        Log.e("", "ACC关");
                    } else {
//                        Log.e("", "ACC开");
                    }
                    break;
            }
        }



    }
}
