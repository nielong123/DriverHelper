package com.driverhelper.helper;

/**
 * Created by Administrator on 2017/7/24.
 */

public final class IdHelper {


    /****
     * 透传消息流水号
     * @return
     */
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

    /****
     * 学时记录里的流水号
     * @return
     */
    public static String getStudyCode() {
        String strId = WriteSettingHelper.getSTUDY_ID();
        if (Integer.valueOf(strId) > 0xffff) {
            strId = "0001";
        } else {
            strId = Integer.valueOf(strId) + 1 + "";
        }
        WriteSettingHelper.setSTUDY_ID(strId);
        int index = strId.length();
        if (strId.length() < 4) {
            for (int i = 0; i < 4 - index; i++) {
                strId = "0" + strId;
            }
        }
        return strId;
    }
}
