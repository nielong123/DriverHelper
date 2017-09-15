package com.driverhelper.other;

import android.content.Context;

import com.driverhelper.beans.db.StudyInfo;
import com.driverhelper.config.ConstantInfo;
import com.driverhelper.helper.DbHelper;
import com.driverhelper.helper.HandMsgHelper;
import com.driverhelper.other.tcp.netty.TcpHelper;
import com.driverhelper.utils.ByteUtil;
import com.driverhelper.utils.FileUtils;
import com.jaydenxiao.common.compressorutils.FileUtil;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
                index8202_++;
                if (index8202_ * class8202_.term >= class8202_.timeInterval) {
                    index8202_ = 0;
                    timer.cancel();
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


    public void action_0303(HandMsgHelper.Class8302 class8302) {
        List<String> list = DbHelper.getInstance().queryPictureByTime(ByteUtil.byte2int(class8302.startTime),
                ByteUtil.byte2int(class8302.startTime));
        if (list.size() != 0) {
            TcpHelper.getInstance().send0303(list);
        } else {
            TcpHelper.getInstance().send0303(null);
        }
    }

    public void action_8304(Context context, HandMsgHelper.Class8304 class8304) {
        String name = ByteUtil.getString(class8304.photoId);
        if (FileUtil.fileIsExists(context.getFilesDir().getPath() + "/" + name + ".png")) {
            TcpHelper.getInstance().send0304((byte) 0x00);
            byte[] data = FileUtils.loadBitmapFromCache(context, name + ".png");
            TcpHelper.getInstance().send0305(name, ConstantInfo.coachId, (byte) 0x01, (byte) 0x01, (byte) 0x01, (byte) 0x01, 1, data.length);
            TcpHelper.getInstance().send0306(name, data);
        } else {
            TcpHelper.getInstance().send0304((byte) 0x01);
        }
    }
}
