package com.driverhelper.helper;

/**
 * Created by Administrator on 2017/7/24.
 */

public final class ExCodeHelper {

    public static String getExCode() {
        String strExCode = WriteSettingHelper.getEX_CODE();
        if (Integer.valueOf(strExCode) > 0xffff) {
            strExCode = "0000";
        } else {
            strExCode = Integer.valueOf(strExCode) + 1 + "";
        }
        WriteSettingHelper.setEX_CODE(strExCode);
        int index = strExCode.length();
        if (strExCode.length() < 4) {
            for (int i = 0; i < 4 - index; i++) {
                strExCode = "0" + strExCode;
            }
        }
        return strExCode;
    }
}
