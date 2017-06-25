package com.jaydenxiao.common.commonutils;

import android.content.Context;
import android.telephony.TelephonyManager;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * Created by Administrator on 2017/6/16.
 */

public class TelephoneUtils {

    public static String getIMEI(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }
}
