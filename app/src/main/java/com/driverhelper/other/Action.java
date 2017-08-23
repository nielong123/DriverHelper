package com.driverhelper.other;

import android.content.Context;
import android.util.Log;

import com.driverhelper.beans.db.StudyInfo;
import com.driverhelper.config.ConstantInfo;
import com.driverhelper.helper.DbHelper;
import com.driverhelper.helper.HandMsgHelper;
import com.driverhelper.helper.TcpHelper;
import com.driverhelper.utils.ByteUtil;
import com.jaydenxiao.common.commonutils.TimeUtil;

import java.util.List;
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


    public void action_8205(HandMsgHelper.Class8205 class8205) {
        switch (class8205.findType) {
            case 1:     //时间
                List<StudyInfo> list = DbHelper.getInstance().queryStudyInfoByTime(ByteUtil.byte2int(class8205.startTime),
                        ByteUtil.byte2int(class8205.endTime));
                if (list.size() == 0) {
                    TcpHelper.getInstance().send0205((byte) 0x03);
                } else {
                    TcpHelper.getInstance().send0205((byte) 0x01);
                    TcpHelper.getInstance().send0205((byte) 0x04);
                    for (StudyInfo info : list) {
                        if (list.indexOf(info) == 0) {
                            TcpHelper.getInstance().send0203(info);
                        }
                    }
                }
                break;
            case 2:     //条数
                List<StudyInfo> list1 = DbHelper.getInstance().queryStudyInfoByNum(class8205.findNum);
                if (list1.size() == 0) {
                    TcpHelper.getInstance().send0205((byte) 0x03);
                } else {
                    TcpHelper.getInstance().send0205((byte) 0x01);
                    TcpHelper.getInstance().send0205((byte) 0x04);
                    for (StudyInfo info : list1) {
                        TcpHelper.getInstance().send0203(info);
                    }
                }
                break;
        }
    }
}
