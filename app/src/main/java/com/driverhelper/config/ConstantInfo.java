package com.driverhelper.config;

import com.driverhelper.beans.QRbean;
import com.driverhelper.helper.WriteSettingHelper;

/**
 * Created by Administrator on 2017/6/6.
 *
 * 放的都是变量
 */

public class ConstantInfo {

    public static boolean isDebug = true;

    /***
     * 省代码
     */
    public static byte[] province = {(byte) 0x00, (byte) 0x2a};   //test

    public static byte[] city = {(byte) 0x00, (byte) 0x6f};       //test

    /***
     * 制造商id
     */
    public static byte[] makerID = {(byte) 0x48, (byte) 0x5a, (byte) 0x4C, (byte) 0x59, (byte) 0x54};

    public static String SN;

    public static String IMEI = "100221235053037";
    public static String terminalPhoneNumber = "15070000001";    //终端手机号
    public static String vehicleColor;           //车辆颜色
    public static String vehicleNum;            //车牌号
    public static String MODEL ;            //终端型号

    public static byte[] requestWaterCode = new byte[2];      //应答流水号
    public static byte[] result = new byte[1];                  //结果
    public static byte[] platformNum = new byte[5];                        //平台编号
    public static byte[] institutionNumber = new byte[16];        //培训机构编号
    public static byte[] terminalNum = new byte[16];       //计时终端编号
    public static byte[] certificatePassword = new byte[12];       //证书口令;
    public static String terminalCertificate = "";         //终端证书
    public static String coachId;      //教练员编号

    public static long studyTimeThis = 0;             //学习的时间
    public static long studyDistanceThis = 0;             //学习的里程
    public static byte[] classId;             //课堂id
    public static String classType = "1211110000";          //培训课程

    public static byte[] photoData;                           //照片的byte数据
    public static int photoDataSize;                           //照片的byte数据大小
    public static String photoId;                         //上传的照片编号
    public static int totlePhotoNum;                        //上传照片包的总数
    public static int photoIndex;                           //当前包的计数
    public static boolean isEmbargo;                     //禁运状态          true
    public static String embargoStr;            //      禁运提示消息内容


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

}
