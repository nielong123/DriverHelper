package com.driverhelper.other;

import android.content.Context;
import android.util.Log;

import com.driverhelper.config.ConstantInfo;
import com.driverhelper.helper.HandMsgHelper;
import com.driverhelper.helper.TcpHelper;

import java.util.Timer;
import java.util.TimerTask;

import static com.driverhelper.config.Config.TextInfoType.UPDATATIME;

/**
 * Created by Administrator on 2017/8/21.
 */

public class Action {

    static Action action;

    public static Action getInstance() {
        if (action == null) {
            action = new Action();
        }
        return action;
    }

    /************* 8202_ ******/
    private int index8202_ = 0;

    /***
     * 位置信息查询
     * @param class8202_
     */
    public void action_8202_(final HandMsgHelper.Class8202_ class8202_) {
        if (class8202_.term == 0) return;
        final Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                TcpHelper.getInstance().sendLocationInfo();
                Log.e("", "发送一次   " + index8202_);
                index8202_++;
                if (index8202_ * class8202_.term >= class8202_.timeInterval) {
                    index8202_ = 0;
                    timer.cancel();
                    Log.e("", "  停止  ");
                }
            }
        };
        timer.schedule(task, 0, class8202_.term);
    }

}
