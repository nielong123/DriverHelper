package com.jaydenxiao.common.commonutils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

/****
 * Nl 2016-8-17
 */
public class PreferenceUtils {

    public static final String USERINFO = "userinfo";
    public static final String REMEMBER = "REMEMBER";
    public static final String AUTOLOGIN = "autoLogin";

    private static final String SettingFileName = "base_configPost";
    private SharedPreferences settings;
    private static PreferenceUtils mInstance;

    public PreferenceUtils(Context context) {
        settings = context.getSharedPreferences(SettingFileName, 0);
    }

    public SharedPreferences getPreferences() {
        return settings;
    }

    public static final PreferenceUtils getInstance() {
        if (null == mInstance) {
            throw new IllegalArgumentException(
                    "You must call init() method before call getInstance()");
        }
        return mInstance;
    }

    public static final void init(final Context context) {
        if (null == mInstance) {
            mInstance = new PreferenceUtils(context);
        }
    }

    public String getSettingStr(String key, String defaultValue) {
        return settings.getString(key, defaultValue);
    }

    public String getSettingXmlStr(String key, String defaultValue) {
        return settings.getString(key, defaultValue).replace("&amp;", "&");
    }

    public int getSettingInt(String key, int defaultValue) {
        return settings.getInt(key, defaultValue);
    }

    public Boolean getSettingBool(String key, boolean defaultValue) {
        return settings.getBoolean(key, defaultValue);
    }

    public void setSettingString(String key, String value) {
        settings.edit().putString(key, value).commit();
    }

    public void setSettingInt(String key, int value) {
        settings.edit().putInt(key, value).commit();
    }

    public void setSettingBoolean(String key, boolean value) {
        settings.edit().putBoolean(key, value).commit();
    }

    public void setSettingLong(String key, long value) {
        settings.edit().putLong(key, value).commit();
    }

    public long getSettingLong(String key, long value) {

        return settings.getLong(key, value);
    }

    public void removeKey(String key) {
        if (settings.contains(key)) {
            settings.edit().remove(key).commit();
        }
    }


    public void setSettingObject(String key, Object object) {
        Gson gson = new Gson();
        setSettingString(key, gson.toJson(object));
    }


    public <T> T getSettingObject(String key, Class<T> type) {
        String str = getSettingStr(key, "");
        if (!"".equals(str)) {
            Gson gson = new Gson();
            return gson.fromJson(str, type);
        }
        return null;
    }

    public byte[] getSettingBytes(String key) {
        String str = settings.getString(key, "");
        return ByteUtils.hexString2BCD(str);
    }

    public void setSettingBytes(String key, byte[] data) {
        setSettingString(key, ByteUtils.bcdByte2bcdString(data));
    }
}
