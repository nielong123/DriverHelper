package com.jaydenxiao.common.commonutils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.jaydenxiao.common.R;

import static android.os.Build.SERIAL;

/**
 * Created by nl on 2017/5/22.
 */

public class VersionUtil {

    /***
     * 获取程序版本号
     * @param context
     * @return
     */
    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return context.getString(R.string.version_name) + version;
        } catch (Exception e) {
            e.printStackTrace();
            return context.getString(R.string.can_not_find_version_name);
        }
    }


    public static String getSerialCode() {
        return "硬件码:" + android.os.Build.SERIAL;
    }
}
