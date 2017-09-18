package com.driverhelper.helper;

import android.util.Log;

import com.driverhelper.utils.ByteUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/24.
 */

public final class IdHelper {

    /***
     * 返回十进制的流水号，如果流水号大于0xffff，则流水号为0
     * @return
     */
    public static synchronized int getWaterCode() {
        int waterCode = WriteSettingHelper.getWATER_CODE();
        if (waterCode > 0xffff) {
            waterCode = 0;
        } else {
            waterCode += 1;
        }
        WriteSettingHelper.setWATER_CODE(waterCode);
        return waterCode;
    }

    /***
     * 锁定一部分的流水号
     * @param number
     * @return
     */
    public static synchronized List<Integer> clockWaterCode(int number) {
        List<Integer> list = new ArrayList<>();
        int waterCode = WriteSettingHelper.getWATER_CODE();
        if (number + waterCode > 0xffff) {          //归零
            WriteSettingHelper.setWATER_CODE(number + waterCode - 0xffff);
            for (int i = 0; i < 0xffff - waterCode; i++) {
                list.add(i);
            }
            for (int i = 0; i < (waterCode + number) - 0xffff; i++) {
                list.add(i);
            }
        } else {
            WriteSettingHelper.setWATER_CODE(number + waterCode);
            for (int i = 0; i < number; i++) {
                list.add(waterCode + i);
            }
        }
        return list;
    }

    /****
     * 透传消息流水号
     * @return
     */
    public synchronized static int getExCode() {
        int exCode = WriteSettingHelper.getEX_CODE();
        if (Integer.valueOf(exCode) > 0xffff) {
            exCode = 0;
        } else {
            exCode += 1;
        }
        WriteSettingHelper.setEX_CODE(exCode);
        return exCode;
    }

    /****
     * 学时记录里的流水号
     * @return
     */
    public synchronized static int getStudyCode() {
        int id = WriteSettingHelper.getSTUDY_ID();
        if (id > 0xffff) {
            id = 1;
        } else {
            id = id + 1;
        }
        WriteSettingHelper.setSTUDY_ID(id);
        return id;
    }
}
