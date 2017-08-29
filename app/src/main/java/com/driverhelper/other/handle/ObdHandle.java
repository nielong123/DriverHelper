package com.driverhelper.other.handle;

import com.driverhelper.utils.ByteUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/7/28.
 */

public class ObdHandle {

    public static HashMap handle(byte[] data) {

        List<byte[]> dataList = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            if (data[i] == (byte) 0x2e) {
                if (i == 9) break;
                if (i >= data.length - 3)           //当0x2e在数据的最后三位时抛弃
                    continue;
                if (data[i + 2] + 1 >= data.length - i - 2)
                    continue;
                byte[] data0 = new byte[data[i + 2] + 4];
                data0[0] = data[i]; // 0x2e
                data0[1] = data[i + 1]; // 种类
                int length = data[i + 2]; // 数据长度
                if ((data.length - i - 2) <= length)
                    continue;
//                System.out.println("data.length = " + data.length);
//                System.out.println("i = " + i);
//                System.out.println("data[i+2] = " + data[i + 2]);
//                System.out.println("[data.length - i] = " + (data.length - i));
                ByteUtil.printHexString(data, "OBD data = ");
                data0[2] = (byte) length;
                System.arraycopy(data, i + 3, data0, 3, data[i + 2] + 1); // 数据，效验
                byte[] checkData = new byte[data0.length - 2];
                System.arraycopy(data0, 1, checkData, 0, checkData.length);
                if (ByteUtil.checkSum(checkData, data0[data0.length - 1])) { // 检查数据sum的结果
                    dataList.add(data0);
                }
            }
        }
        HashMap<String, String> map = new HashMap();
        for (byte[] dataOne : dataList) {
            switch (dataOne[1]) {
                case (byte) 0x02:            //转速
                    long speed = ByteUtil.byte2int(dataOne[3]) + ByteUtil.byte2int(dataOne[4]) * 256;
                    map.put("speed", speed + "");
                    break;
                case (byte) 0x03:            //车速
                    long carSpeed1 = ByteUtil.byte2int(dataOne[3]) + ByteUtil.byte2int(dataOne[4]) * 256;
                    map.put("car speed", carSpeed1 + "");
                    break;
                case (byte) 0x06:            //本次行驶里程
                    long mileage = ByteUtil.byte2int(dataOne[3]) + ByteUtil.byte2int(dataOne[4]) * 256 + ByteUtil.byte2int(dataOne[5]) * 65536;
                    map.put("mileage", mileage + "");
                    break;
                case (byte) 0x7d:
                    if (dataOne[3] == 0) {
//                        Log.e("", "ACC关");
                    } else {
//                        Log.e("", "ACC开");
                    }
                    break;
            }
        }
        return map;
    }
}
