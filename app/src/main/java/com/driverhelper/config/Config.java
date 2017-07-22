package com.driverhelper.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/5/31.
 */

public class Config {

    public static String ip, port, timeOut;

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

        public final static String RX_CHANGE_TEXTINFO = "RX_CHANGE_TEXTINFO";       //刷新界面
        public final static String RX_TTS_SPEAK = "RX_TTS_SPEAK";           //語音
        public final static String RX_NET_DISCONNECT = "RX_NET_DISCONNECT";           //网络连接中断
    }

    public static class WriteSetting {
        public final static String ISFIRST = "ISFIRST";         //第一次登陆
        public final static String TCP_IP = "tcp_ip";           // tcp  ip
        public final static String TCP_PORT = "tcp_port";           //tcp port
        public final static String TIME_OUT = "time_out";                   //TCP超时时间
        public final static String HEART_SPACE = "HEART_SPACE";         //心跳包间隔
        public final static String HTTP_URL = "HTTP_URL";               //http 地址
        public final static String VEHICLE_NUMBER = "VEHICLE_NUMBER";       //车牌号
        public final static String TERMINALPHONENUMBER = "TERMINALPHONENUMBER";         //终端手机号
        public final static String IMEI = "IMEI";
        public final static String VEHICLE_COLOR = "VEHICLE_COLOR";         //车辆颜色
        public final static String WATER_CODE = "WATER_CODE";       //流水号
        public final static String EXAM_TYPE = "EXAM_TYPE";       //考试类型
        public final static String PROVINCE = "PROVINCE";       //省
        public final static String CITY = "CITY";       //省
        public final static String PLATFORMNUM = "platformNum";     //平台编号
        public final static String INSTITUTIONNUMBER = "institutionNumber";     //培训机构编号
        public final static String TERMINALNUM = "terminalNum";     //终端编号
        public final static String CERTIFICATEPASSWORD = "certificatePassword";     //证书口令
        public final static String TERMINALCERTIFICATE = "terminalCertificate";     //终端证书

    }


    public enum TextInfoType {
        ChangeGPSINFO,
        ClearGPSINFO,
        SETJIAOLIAN,
        SETXUEYUAN;
    }

    public static List<String> colors = new ArrayList<String>(Arrays.asList("蓝色", "黄色", "黑色", "白色", "绿色"));

    public static final int carmerId_HANGJING = 4;  //4
}
