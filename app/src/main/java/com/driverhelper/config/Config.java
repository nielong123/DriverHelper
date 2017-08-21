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

        public final static String RX_LOCATION_OK = "RX_LOCATION_OK";           //定位成功
        public final static String RX_LOCATION_FALINE = "RX_LOCATION_FAILE";           //定位失败
        public final static String RX_CHANGE_TEXTINFO = "RX_CHANGE_TEXTINFO";       //刷新界面
        public final static String RX_TTS_SPEAK = "RX_TTS_SPEAK";           //語音
        public final static String RX_NET_DISCONNECT = "RX_NET_DISCONNECT";           //网络连接中断
        public final static String RX_COACH_LOGINOK = "RX_COACH_LOGINOK";           //教练员登录
        public final static String RX_COACH_LOGOUTOK = "RX_COACH_LOGOUTOK";           //教练员登出
        public final static String RX_STUDENT_LOGINOK = "RX_STUDENT_LOGINOK";           //学员员登录
        public final static String RX_STUDENT_LOGOUTOK = "RX_STUDENT_LOGOUTOK";           //学员登出
        public final static String RX_SETTING_EMBARGOSTATE = "RX_SETTING_EMBARGOSTATE";           //设置禁运状态
        public final static String RX_SETTING_8103 = "RX_SETTING_8103";
        public final static String RX_SETTING_8104 = "RX_SETTING_8104";
        public final static String RX_SETTING_8106 = "RX_SETTING_8106";
        public final static String RX_SETTING_8202_ = "RX_SETTING_8202_";
        public final static String RX_SETTING_0501 = "RX_SETTING_0501";           //
        public final static String RX_SETTING_8301 = "RX_SETTING_8301";
    }

    public static class WriteSetting {
        public final static String ISFIRST = "ISFIRST";         //第一次登陆
        public final static String TCP_IP = "tcp_ip";           // tcp  ip
        public final static String TCP_PORT = "tcp_port";           //tcp port
        public final static String TIME_OUT = "time_out";                   //TCP超时时间
        public final static String HTTP_URL = "HTTP_URL";               //http 地址
        public final static String VEHICLE_NUMBER = "VEHICLE_NUMBER";       //车牌号
        public final static String TERMINALPHONENUMBER = "TERMINALPHONENUMBER";         //终端手机号
        public final static String SN = "SN";           //终端出厂编号
        public final static String IMEI = "IMEI";
        public final static String MODEL = "MODEL";                 //终端型号
        public final static String VEHICLE_COLOR = "VEHICLE_COLOR";         //车辆颜色
        public final static String WATER_CODE = "WATER_CODE111";       //流水号
        public final static String EX_CODE = "EX_CODE11";       //透传消息流水号
        public final static String STUDY_CODE = "STUDY_CODE11";       //课堂ID
        public final static String EXAM_TYPE = "EXAM_TYPE";       //考试类型
        public final static String PROVINCE = "PROVINCE";       //省
        public final static String DISTANCE = "DISTANCE";       //里程
        public final static String CITY = "CITY";       //省
        public final static String PLATFORMNUM = "platformNum";     //平台编号
        public final static String INSTITUTIONNUMBER = "institutionNumber";     //培训机构编号
        public final static String TERMINALNUM = "terminalNum";     //终端编号
        public final static String CERTIFICATEPASSWORD = "certificatePassword";     //证书口令
        public final static String TERMINALCERTIFICATE = "terminalCertificate";     //终端证书
        public final static String COACHNUM = "COACHNUM";           //          教练员编号
        public final static String EMBARGO = "EMBARGO";             //禁运状态
        public final static String EMBARGODSTR = "EMBARGOSTR";             //禁运状态
        public final static String PIC_INTV_min = "PIC_INTV_min";           //定时拍照时间间隔
        public final static String UPLOAD_GBN = "UPLOAD_GBN";           //照片上传设置
        public final static String ADDMSG_YN = "ADDMSG_YN11";             //是否报读附加消息
        public final static String STOP_DELAY_TIME_min = "STOP_DELAY_TIME_min";  //熄火后停止学时计时的延时时间  min
        public final static String STOP_GNSS_UPLOAD_INTV_sec = "STOP_GNSS_UPLOAD_INTV_sec";     //熄火后停止学时计时的延时时间  s
        public final static String STOP_COACH_DELAY_TIME_min = "STOP_COACH_DELAY_TIME_min";     //熄火后教练自动登出的延时时间
        public final static String USER_CHK_TIME_min = "USER_CHK_TIME_min";  //重新验证身份时间
        public final static String COACH_TRANS_YN = "COACH_TRANS_YN";           //教练跨校教学
        public final static String STU_TRANS_YN = "STU_TRANS_YN";   //学员跨校学习
        public final static String DUP_MSG_REJECT_INTV_sec = "DUP_MSG_REJECT_INTV_sec";     //响应平台同类消息时间间隔

        public final static String PARAM0001 = "param0001";
        public final static String PARAM0002 = "param0002";
        public final static String PARAM0003 = "param0003";
        public final static String PARAM0004 = "param0004";
        public final static String PARAM0005 = "param0005";
        public final static String PARAM0006 = "param0006";
        public final static String PARAM0007 = "param0007";

        public final static String param0010 = "param0010";
        public final static String param0011 = "param0011";
        public static final String param0012 = "param0012";
        public static final String param0013 = "param0013";
        public static final String param0014 = "param0014";
        public static final String param0015 = "param0015";
        public static final String param0016 = "param0016";
        public static final String param0017 = "param0017";
        public static final String param0018 = "param0018";
        public static final String param0019 = "param0019";

        public static final String param0020 = "param0020";
        public static final String param0021 = "param0021";
        public static final String param0022 = "param0022";
        public static final String param0023 = "param0023";
        public static final String param0024 = "param0024";
        public static final String param0025 = "param0025";
        public static final String param0026 = "param0026";
        public static final String param0027 = "param0027";
        public static final String param0028 = "param0028";
        public static final String param0029 = "param0029";
        public static final String param002C = "param002C";
        public static final String param002D = "param002D";
        public static final String param002E = "param002E";
        public static final String param002F = "param002F";

        public static final String param0030 = "param0030";

        public static final String param0040 = "param0040";
        public static final String param0041 = "param0041";
        public static final String param0042 = "param0042";
        public static final String param0043 = "param0043";
        public static final String param0044 = "param0044";
        public static final String param0045 = "param0045";
        public static final String param0046 = "param0046";
        public static final String param0047 = "param0047";
        public static final String param0048 = "param0048";
        public static final String param0049 = "param0049";

        public static final String param0050 = "param0050";
        public static final String param0051 = "param0051";
        public static final String param0052 = "param0052";
        public static final String param0053 = "param0053";
        public static final String param0054 = "param0054";
        public static final String param0055 = "param0055";
        public static final String param0056 = "param0056";
        public static final String param0057 = "param0057";
        public static final String param0058 = "param0058";
        public static final String param0059 = "param0059";
        public static final String param005A = "param005A";

        public static final String param0070 = "param0070";
        public static final String param0071 = "param0071";
        public static final String param0072 = "param0072";
        public static final String param0073 = "param0073";
        public static final String param0074 = "param0074";

        public static final String param0080 = "param0080";
        public static final String param0081 = "param0081";
        public static final String param0082 = "param0082";
        public static final String param0083 = "param0083";
        public static final String param0084 = "param0084";
        public static final String param0085 = "param0085";

    }


    public static class TextInfoType {
        final public static int SETSTUDYINFO = 0;
        final public static int ChangeGPSINFO = 1;
        final public static int ClearGPSINFO = 2;
        final public static int SETJIAOLIAN = 3;
        final public static int CLEARJIAOLIAN = 4;
        final public static int SETXUEYUAN = 5;
        final public static int CLEARXUEYUAN = 6;
        final public static int UPDATATIME = 7;
    }

    public static List<String> colors = new ArrayList<String>(Arrays.asList("蓝色", "黄色", "黑色", "白色", "绿色"));

    public static final int carmerId_HANGJING = 4;  //4

    public static boolean isCoachLoginOK;           //教练员登录成功
    public static boolean isStudentLoginOK;         //学员登录成功

}
