package com.driverhelper.config;

import com.driverhelper.beans.QRbean;
import com.driverhelper.helper.WriteSettingHelper;

import java.util.Timer;

/**
 * Created by Administrator on 2017/6/6.
 * <p>
 * 放的都是变量
 */

public class ConstantInfo {

    public static boolean isDebug = true;

    public static String ip;
    public static int port, timeOut;

    /***
     * 省代码
     */
    public static byte[] province = {(byte) 0x00, (byte) 0x2a};   //test

    public static byte[] city = {(byte) 0x00, (byte) 0x6f};       //test

    /***
     * 制造商id
     */
    public static byte[] makerID;
//    public static byte[] makerID = {(byte) 0x48, (byte) 0x5a, (byte) 0x4C, (byte) 0x59, (byte) 0x54};

    public static String SN;

    public static String IMEI;
    public static String terminalPhoneNumber;    //终端手机号
    public static String vehicleColor;           //车辆颜色
    public static String vehicleNum;            //车牌号
    public static String MODEL;            //终端型号

    public static byte[] requestWaterCode;      //应答流水号
    public static byte[] result;                  //结果
    public static byte[] platformNum;                        //平台编号
    public static byte[] institutionNumber;        //培训机构编号
    public static byte[] terminalNum;       //计时终端编号
    public static byte[] certificatePassword;       //证书口令;
    public static String terminalCertificate = null;         //终端证书
    public static String coachId;      //教练员编号

    public static long studyTimeThis = 0;             //学习的时间
    public static long studyDistanceThis = 0;             //学习的里程
    public static long classId;             //课堂id  学生登录后生产
    public static String classType = "1211110000";          //培训课程

    public static boolean isEmbargo;                     //禁运状态          true
    public static String embargoStr;            //      禁运提示消息内容
    public static boolean is0102_OK;                //是否鉴权成功
    public static boolean isDisConnectByUser;           //用户手动断开连接

    public static int gpsdisConnectIndex = 0;           //gps断开连接的计数
    public static final int gpsDisConnectMex = 5;           //gps断开报警的最大次数


    /***** 0502的设置  start ***************/
    public static int PIC_INTV_min;          //定时拍照间隔
    public static int UPLOAD_GBN;       //照片上传设置
    public static int ADDMSG_YN;        //是否报读附加消息
    public static int STOP_DELAY_TIME_min;      //熄火后停止学时计时的延时时间, 单: min
    public static int STOP_GNSS_UPLOAD_INTV_sec;   //熄火后停止学时计时的延时时间, 单位: s
    public static int STOP_COACH_DELAY_TIME_min;    //熄火后教练自动登出的延时时间, 单位: min
    public static int USER_CHK_TIME_min;            //重新验证身份时间, 单位: min
    public static int COACH_TRANS_YN;         //教练跨校教学
    public static int STU_TRANS_YN;           //学员跨校学习
    public static int DUP_MSG_REJECT_INTV_sec;      //响应平台同类消息时间间隔

    public static int param0001;
    public static int param0002;
    public static int param0003;
    public static int param0004;
    public static int param0005;
    public static int param0006;
    public static int param0007;

    public static String param0010;
    public static String param0011;
    public static String param0012;
    public static String param0013;
    public static String param0014;
    public static String param0015;
    public static String param0016;
    public static String param0017;
    public static int param0018;
    public static int param0019;

    public static int param0020;
    public static int param0021;
    public static int param0022;
    public static int param0023;
    public static int param0024;
    public static int param0025;
    public static int param0026;
    public static int param0027;
    public static int param0028;
    public static int param0029;
    public static int param002C;
    public static int param002D;
    public static int param002E;
    public static int param002F;

    public static int param0030;

    public static String param0040;
    public static String param0041;
    public static String param0042;
    public static String param0043;
    public static String param0044;
    public static int param0045;
    public static int param0046;
    public static int param0047;
    public static String param0048;
    public static String param0049;

    public static int param0050;
    public static int param0051;
    public static int param0052;
    public static int param0053;
    public static int param0054;
    public static int param0055;
    public static int param0056;
    public static int param0057;
    public static int param0058;
    public static int param0059;
    public static int param005A;

    public static int param0070;
    public static int param0071;
    public static int param0072;
    public static int param0073;
    public static int param0074;

    public static int param0080;
    public static int param0081;
    public static int param0082;
    public static String param0083;
    public static int param0084;
    public static int param0085;


    public static class StudentInfo {
        public static String studentId;        //学员编号
        public static int totleTime;            //需要完成的总学时
        public static int finishedTime;               //已完成的学时
        public static int totleMileage;             //需要完成的总里程
        public static int finishedMileage;            //已完成的里程
    }

    public static class ObdInfo {
        public static int vehiclSspeed = 10;     //车速
        public static int speed = 20;        //发动机转速
        public static int distance = 15;         //距离
    }


    public static QRbean qRbean;

    public static Timer locationTimer;
    public static Timer studyInfoTimer;
    public static Timer clearTimer;
    public static Timer photoTimer;

    public static long locationTimerDelay = 10 * 1000;
    public static long studyInfoTimerDelay = 5 * 1000;
    public static long clearTimerDelay = 1 * 60 * 1000;
    public static long photoTImerDelay = 15 * 30 * 1000;

}
