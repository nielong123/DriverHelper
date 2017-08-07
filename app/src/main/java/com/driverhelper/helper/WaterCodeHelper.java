package com.driverhelper.helper;

import android.util.Log;

/**
 * Created by Administrator on 2017/6/10.
 */

public final class WaterCodeHelper {
    /***
     * 返回十进制的流水号，如果流水号大于0xffff，则流水号为0
     * @return
     */
    public static int getWaterCode() {
        int waterCode = WriteSettingHelper.getWATER_CODE();
        if (waterCode > 0xffff) {
            waterCode = 0;
        } else {
            waterCode += 1;
        }
        Log.e("water code", "get  strWaterCode = " + waterCode);
        WriteSettingHelper.setWATER_CODE(waterCode);
        return waterCode;
    }
}
