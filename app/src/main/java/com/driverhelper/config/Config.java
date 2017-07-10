package com.driverhelper.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/5/31.
 */

public class Config {

    public static class Config_RxBus {
        public final static String RX_COACH_SIGN = "RX_COACH_SIGN";
        public final static String RX_COACH_SIGN_OK = "RX_COACH_SIGN_OK";
        public final static String RX_SHOW_COACH_INFO = "RX_SHOW_COACH_INFO";
        public final static String RX_STUDENT_SIGN = "RX_STUDENT_SIGN";
        public final static String RX_STUDENT_SIGN_OK = "RX_STUDENT_SIGN_OK";
        public final static String RX_REBACK_MARKACTIVITY = "RX_RETURN_MARKACTIVITY";
        public final static String RX_COACH_SIGN_OUT = "RX_COACH_SIGN_OUT";
        public final static String RX_COACH_SIGN_OUT_OK = "RX_COACH_SIGN_OUT_OK";
        public final static String RX_STUDENT_SIGN_OUT = "RX_STUDENT_SIGN_OUT";    //签退
        public final static String RX_PHOTOGRAPH = "RX_PHOTOGRAPH";         //照相
    }

    public static class RxBusIndex {
        public final static int SHOW_MARKACTIVITY = 0;
        public final static int COACH_SIGN = 1;
        public final static int COACH_OK = 2;
        public final static int SHOW_COACH_INFO = 3;
        public final static int STUDENT_SIGN = 4;
        public final static int STUDENT_SIGN_OK = 5;
        public final static int COACH_SIGN_OUT = 6;
        public final static int STUDENT_SIGN_OUT_OK = 7;
        public final static int PHOTOGRAPH = 999;
    }

    public static class WriteSetting {
        public final static String ISFIRST = "ISFIRST";         //第一次登陆
        public final static String TCP_IP = "tcp_ip";           // tcp  ip
        public final static String TCP_PORT = "tcp_port";           //tcp port
        public final static String TIME_OUT = "time_out";                   //TCP超时时间
        public final static String HEART_SPACE = "HEART_SPACE";         //心跳包间隔
        public final static String HTTP_URL = "HTTP_URL";               //http 地址
        public final static String VEHICLE_NUMBER = "VEHICLE_NUMBER";       //车牌号
        public final static String DEVICE_CODE = "DEVICE_CODE";         //硬件设备号
        public final static String VEHICLE_COLOR = "VEHICLE_COLOR";         //车辆颜色
        public final static String WATER_CODE = "WATER_CODE";       //流水号
        public final static String EXAM_TYPE = "EXAM_TYPE";       //考试类型

    }

    public static List<String> colors = new ArrayList<String>(Arrays.asList("蓝色", "黄色", "黑色", "白色", "绿色"));
}
