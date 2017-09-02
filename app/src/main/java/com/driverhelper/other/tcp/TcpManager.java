package com.driverhelper.other.tcp;

import android.util.Log;

import com.driverhelper.helper.DbHelper;
import com.jaydenxiao.common.baserx.RxBus;

import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;

import static com.driverhelper.config.Config.Config_RxBus.RX_TTS_SPEAK;

/**
 * Created by Administrator on 2017/9/1.
 * tcp管理器，负责对应发送和接收的数据
 */

public class TcpManager {

    final String TAG = "TcpManager";

    private static volatile TcpManager tcpManager;

    static ConcurrentHashMap<Integer, String> container = new ConcurrentHashMap<>();

    public static TcpManager getInstance() {
        if (tcpManager == null) {
            synchronized (TcpManager.class) {
                if (tcpManager == null) {
                    tcpManager = new TcpManager();
                }
            }
        }
        return tcpManager;
    }


    public void put(int waterId, String features) {
        container.put(waterId, features);
        Log.e(TAG, "container 添加一个内容，container大小为" + container.size() + "   waterId = " + waterId + "features = " + features);
    }

    public void remove(int waterId, int res8001) {
        String features = container.get(waterId);
        DbHelper.getInstance().setUpState(waterId);
        container.remove(waterId);
        if (features != null) {             //有些包不用加入队列
            action(features, res8001);
        }
    }

    private void action(String features, int res8001) {
        Log.e(TAG, "container 移除一个内容，container大小为" + container.size());
        switch (features) {
            case "0003":
                if (res8001 == 0) {
                    RxBus.getInstance().post(RX_TTS_SPEAK, "终端注销成功");
                } else {
                    RxBus.getInstance().post(RX_TTS_SPEAK, "终端注销失败");
                }
                break;
            case "0102":
                if (res8001 == 0) {
                    RxBus.getInstance().post(RX_TTS_SPEAK, "终端鉴权成功");
                } else {
                    RxBus.getInstance().post(RX_TTS_SPEAK, "终端鉴权失败");
                }
                break;
            case "0200":
                break;
            case "0203":
                if (res8001 == 0) {
                    RxBus.getInstance().post(RX_TTS_SPEAK, "学时记录上传成功");
                } else {
                    RxBus.getInstance().post(RX_TTS_SPEAK, "学时记录上传失败");
                }
                break;
            default:
                break;
        }
    }


}
