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
    public static String httpURL;
    public static byte[] province = new byte[2];   //test
    public static byte[] city = new byte[2];       //test
    public static byte[] makerID;
    public static String SN;
    public static String IMEI;
    public static String terminalPhoneNumber;    //终端手机号
    public static String vehicleColor;           //车辆颜色
    public static String vehicleNum;            //车牌号
    public static String MODEL;            //终端型号
    public static String trainType;     //培训种类
    public static String perdriType;        //准教车型
    public static String camera_ID;      //摄像头编号
    public static String car_Id;        //教练车id

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
    public static int PIC_INTV_min = 15;          //定时拍照间隔
    public static int UPLOAD_GBN = 1;       //照片上传设置
    public static int ADDMSG_YN = 0;        //是否报读附加消息
    public static int STOP_DELAY_TIME_min = 5;      //熄火后停止学时计时的延时时间, 单: min
    public static int STOP_GNSS_UPLOAD_INTV_sec = 3600;   //熄火后停止学时计时的延时时间, 单位: s
    public static int STOP_COACH_DELAY_TIME_min = 150;    //熄火后教练自动登出的延时时间, 单位: min
    public static int USER_CHK_TIME_min = 30;            //重新验证身份时间, 单位: min
    public static int COACH_TRANS_YN = 1;         //教练跨校教学
    public static int STU_TRANS_YN = 2;           //学员跨校学习
    public static int DUP_MSG_REJECT_INTV_sec = 30;      //响应平台同类消息时间间隔

    public static int param0001 = 120;
    public static int param0002 = 10;
    public static int param0003 = 1;
    public static int param0004 = 10;
    public static int param0005 = 1;
    public static int param0006 = 100;
    public static int param0007 = 1;

    public static String param0010 = "apn";
    public static String param0011 = "userName";
    public static String param0012 = "password";
    public static String param0013 = "127.0.0.1";
    public static String param0014 = "apn";
    public static String param0015 = "wirelessUserName";
    public static String param0016 = "wirelessPassWord";
    public static String param0017 = "backupsAddress";
    public static int param0018 = 9000;
    public static int param0019 = 9001;

    public static int param0020 = 0;
    public static int param0021 = 1;
    public static int param0022 = 3600;
    public static int param0027 = 3600;
    public static int param0028 = 600;
    public static int param0029 = 60;
    public static int param002C = 500;
    public static int param002D = 1000;
    public static int param002E = 1000;
    public static int param002F = 500;

    public static int param0030 = 999;            //不知道这什么用

    public static String param0040 = "8005008888";
    public static String param0041 = "8005008889";
    public static String param0042 = "8005008890";
    public static String param0043 = "8005008891";
    public static String param0044 = "8005008892";
    public static int param0045 = 0;
    public static int param0046 = 0;
    public static int param0047 = 0;
    public static String param0048 = "110";
    public static String param0049;

    public static int param0050 = 1;
    public static int param0051 = 0;
    public static int param0052 = 0;
    public static int param0053 = 0;
    public static int param0054 = 0;
    public static int param0055 = 60;
    public static int param0056 = 20;
    public static int param0057 = 3600;
    public static int param0058 = 3600;
    public static int param0059 = 300;
    public static int param005A = 600;

    public static int param0070 = 3;
    public static int param0071 = 128;
    public static int param0072 = 128;
    public static int param0073 = 64;
    public static int param0074 = 128;

    public static int distance = 3;
    public static int param0081;
    public static int param0082;
    public static String param0083;
    public static int param0084;
    public static int param0085 = 1000;


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
