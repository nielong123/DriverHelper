package com.jaydenxiao.common.commonutils;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeUtils {


    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final SimpleDateFormat DEFAULT_DATE_MIN_FORMAT_1 = new SimpleDateFormat("yyyy/MM/dd HH:mm");

    public static final SimpleDateFormat DEFAULT_DATE_MIN_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static final SimpleDateFormat DATE_FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd");

    public static final SimpleDateFormat DATE_FORMAT_TIME = new SimpleDateFormat("HH:mm");

    private TimeUtils() {
        throw new AssertionError();
    }


    /****
     * 获取当前时间
     *
     * @param isMin
     * @return
     */
    public static String getTimeNow(boolean isMin) {
        if (isMin) {
            return DEFAULT_DATE_MIN_FORMAT.format(new Date());
        } else {
            return DEFAULT_DATE_FORMAT.format(new Date());
        }
    }


    public static Date getDateByStr(String strTime) {
        SimpleDateFormat sdf = DEFAULT_DATE_MIN_FORMAT;
        Date date = null;
        try {
            date = sdf.parse(strTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /***
     * 取今天零点时间
     *
     * @param isMin
     * @return
     */
    public static String getTodayZeroTime(boolean isMin) {
        if (isMin) {
            return getTime(System.currentTimeMillis() / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset(), DEFAULT_DATE_MIN_FORMAT);
        } else {
            return getTime(System.currentTimeMillis() / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset(), DEFAULT_DATE_FORMAT);
        }
    }

    /***
     * 取当前时间的前一天时间
     *
     * @return
     */
    public static String getTodayBeforeTime(SimpleDateFormat dateFormat) {
        return getTime((System.currentTimeMillis() - (1000 * 3600 * 24)), dateFormat);
    }


    /**
     * long time to string
     *
     * @param timeInMillis
     * @param dateFormat
     * @return
     */
    public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(timeInMillis));
    }

    /**
     * long time to string, format is {@link #DEFAULT_DATE_FORMAT}
     *
     * @param timeInMillis
     * @return
     */
    public static String getTime(long timeInMillis) {
        return getTime(timeInMillis, DEFAULT_DATE_FORMAT);
    }

    /**
     * get current time in milliseconds
     *
     * @return
     */
    public static long getCurrentTimeInLong() {
        return System.currentTimeMillis();
    }

    /**
     * get current time in milliseconds, format is {@link #DEFAULT_DATE_FORMAT}
     *
     * @return
     */
    public static String getCurrentTimeInString() {
        return getTime(getCurrentTimeInLong());
    }

    /**
     * get current time in milliseconds
     *
     * @return
     */
    public static String getCurrentTimeInString(SimpleDateFormat dateFormat) {
        return getTime(getCurrentTimeInLong(), dateFormat);
    }


    public static long getTimeStampFromString(String timeString) {
        Date dt;
        long timeMillis;
        try {
            dt = DEFAULT_DATE_MIN_FORMAT.parse(timeString);
            timeMillis = dt.getTime();
        } catch (ParseException e) {
            timeMillis = 0;
        }
        return timeMillis;
    }

    public static String timeDifference(long time1, long time2) {
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        long diff;
        String flag;
        if (time1 < time2) {
            diff = time2 - time1;
            flag = "前";
        } else {
            diff = time1 - time2;
            flag = "后";
        }
        day = diff / (24 * 60 * 60 * 1000);
        hour = (diff / (60 * 60 * 1000) - day * 24);
        min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
        sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        if (day != 0) return day + "天" + flag;
        if (hour != 0) return hour + "小时" + flag;
        if (min != 0) return min + "分钟" + flag;
        return "刚刚";
    }


    /***
     * 截取时间字符串的前十位，变成yyyy-YY-DD的格式
     *
     * @param strTime
     * @return
     */
    public static String TimeStr2yyyyYYDD(@NonNull String strTime) {
        if (strTime != null && strTime.length() > 9) {
            return strTime.substring(0, 10);
        }
        return null;
    }

    /***
     * 将当前时间变成今天凌晨零点
     *
     * @param strTime
     * @return
     */
    public static String TimeStr2Zero(@NonNull String strTime) {
        if (strTime != null && strTime.length() > 9) {
            return strTime.substring(0, 10) + " 00:00";
        }
        return null;
    }
}
