package com.driverhelper.helper;

/**
 * Created by Administrator on 2017/6/10.
 */

public final class WaterCodeHelper {

    /***
     * 返回十进制的流水号，如果流水号大于0xffff，则流水号为0
     * @return
     */
    public static String getWaterCode() {
        String strWaterCode = WriteSettingHelper.getWATER_CODE();
        if (Integer.valueOf(strWaterCode) > 0xffff) {
            strWaterCode = "0000";
        } else {
            strWaterCode = Integer.valueOf(strWaterCode) + 1 + "";
        }
        WriteSettingHelper.setWATER_CODE(strWaterCode);
        int index = strWaterCode.length();
        if (strWaterCode.length() < 4) {
            for (int i = 0; i < 4 - index; i++) {
                strWaterCode = "0" + strWaterCode;
            }
        }
        return strWaterCode;
    }
}
