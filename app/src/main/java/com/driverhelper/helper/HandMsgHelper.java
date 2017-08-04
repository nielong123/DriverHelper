package com.driverhelper.helper;

import com.driverhelper.utils.ByteUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/25.
 */

public final class HandMsgHelper {

    public static class Class8101 {
        byte[] result = new byte[1];
        byte[] coachNum = new byte[16];
        byte[] isReadAdditional = new byte[1];
        byte[] additionalLenght = new byte[1];
        String additionalInfo;
    }

    public static Class8101 getClass8101(byte[] data) {
        Class8101 class8101 = new Class8101();
        int index = 0;
        System.arraycopy(data, index, class8101.result, 0, class8101.result.length);
        index = index + class8101.result.length;
        System.arraycopy(data, index, class8101.coachNum, 0, class8101.coachNum.length);
        index = index + class8101.coachNum.length;
        System.arraycopy(data, index, class8101.isReadAdditional, 0, class8101.isReadAdditional.length);
        index = index + class8101.isReadAdditional.length;
        System.arraycopy(data, index, class8101.additionalLenght, 0, class8101.additionalLenght.length);
        if (class8101.additionalLenght[0] != 0) {
            index = index + class8101.additionalLenght.length;
            byte[] data0 = new byte[class8101.additionalLenght.length];
            System.arraycopy(data, index, data0, 0, class8101.additionalLenght.length);
            class8101.additionalInfo = ByteUtil.getString(data0);
        }
        return class8101;
    }

    static class Class8103 {

        static class Setting {
            int id;
            byte parameterLength;
            String parameter;
        }

        int totleNumber;
        int partNumber;
        List<Setting> settingList = new ArrayList<>();
    }

    public static Class8103 getClass8103(byte[] data) {

        Class8103 class8103 = new Class8103();
        class8103.totleNumber = data[0];
        class8103.partNumber = data[1];
        byte[] data0 = new byte[data.length - 2];
        System.arraycopy(data, 2, data0, 0, data0.length);
        for (int i = 0; i < class8103.totleNumber; i++) {
            Class8103.Setting setting = new Class8103.Setting();
            byte[] data1 = new byte[2];
            System.arraycopy(data0, i, data1, 0, data1.length);
            setting.id = ByteUtil.byte2int(data1);
            setting.parameterLength = data0[i + 1];
            int length = ByteUtil.byte2int(setting.parameterLength);
            byte[] data2 = new byte[length];
            System.arraycopy(data0,i+3,data2,0,length);
            setting.parameter = ByteUtil.getString(data2);
            class8103.settingList.add(setting);
        }
        return class8103;
    }

    static class Class8102 {
        byte[] result = new byte[1];
        byte[] coachNum = new byte[16];
    }

    static Class8102 getClass8102(byte[] data) {
        Class8102 Class8102 = new Class8102();
        int index = 0;
        System.arraycopy(data, index, Class8102.result, 0, Class8102.result.length);
        index = index + Class8102.result.length;
        System.arraycopy(data, index, Class8102.coachNum, 0, Class8102.coachNum.length);
        return Class8102;
    }


    public static class Class8201 {
        public byte result;
        public byte[] studentNum = new byte[16];
        public int totleStudyTime;         //总学时
        public int finishedStudyTime;      //已完成学时
        public int totleMileage;           //培训总里程
        public int finishedMileage;            //已完成里程
        public boolean isUpdataOtherInfo;      //是否报读附加消息
        public int otherInfoLength;            //附加消息长度
        public String otherInfo;               //附加消息
    }

    static Class8201 getClass8201(byte[] data) {
        Class8201 class8201 = new Class8201();
        ByteUtil.printHexString(data);
        class8201.result = data[0];
        int index = 1;
        System.arraycopy(data, index, class8201.studentNum, 0, class8201.studentNum.length);
        index += class8201.studentNum.length;
        byte[] data0 = new byte[2];
        System.arraycopy(data, index, data0, 0, data0.length);
        index += data0.length;
        class8201.totleStudyTime = ByteUtil.byte2int(data0);
        byte[] data1 = new byte[2];
        System.arraycopy(data, index, data1, 0, data1.length);
        index += data1.length;
        class8201.finishedStudyTime = ByteUtil.byte2int(data1);

        byte[] data2 = new byte[2];
        System.arraycopy(data, index, data2, 0, data2.length);
        index += data2.length;
        class8201.totleMileage = ByteUtil.byte2int(data2);

        byte[] data3 = new byte[2];
        System.arraycopy(data, index, data3, 0, data3.length);
        index += data3.length;
        class8201.finishedMileage = ByteUtil.byte2int(data3);

        byte[] data4 = new byte[1];
        System.arraycopy(data, index, data4, 0, data4.length);
        index += data4.length;
        if (data4[0] == 0) {
            class8201.isUpdataOtherInfo = false;
        }
        if (data4[0] == 1) {
            class8201.isUpdataOtherInfo = true;
        }
        if (class8201.otherInfoLength != 0) {
            byte[] data5 = new byte[class8201.otherInfoLength];
            System.arraycopy(data, index, data5, 0, data5.length);
            class8201.otherInfo = ByteUtil.getString(data5);
        }

        return class8201;
    }

    static class Class8202 {
        byte result;
        byte[] studentNum = new byte[16];
    }


    static Class8202 getClass8202(byte[] data) {

        Class8202 class8202 = new Class8202();
        class8202.result = data[0];
        System.arraycopy(data, 1, class8202.studentNum, 0, class8202.studentNum.length);
        return class8202;
    }

    static class Class8205 {
        byte findType;
        byte[] startTime = new byte[6];
        byte[] endTime = new byte[6];
        byte findNum;
    }

    static Class8205 getClass8205(byte[] data) {

        int index;
        Class8205 class8205 = new Class8205();
        class8205.findType = data[0];
        index = 1;
        System.arraycopy(data, index, class8205.startTime, 0, class8205.startTime.length);
        index += class8205.startTime.length;
        System.arraycopy(data, index, class8205.endTime, 0, class8205.endTime.length);
        class8205.findNum = data[data.length - 1];
        return class8205;
    }

    static class Class8301 {
        byte updataType;
        byte cameraNum;
        byte size;
    }


    static Class8301 getClass8301(byte[] data) {
        Class8301 class8301 = new Class8301();
        class8301.updataType = data[0];
        class8301.cameraNum = data[1];
        class8301.size = data[2];
        return class8301;
    }

    static class Class8302 {
        public byte type;
        byte[] startTime = new byte[6];
        byte[] endTime = new byte[6];
    }

    public static Class8302 getClass8302(byte[] data) {
        Class8302 class8302 = new Class8302();
        class8302.type = data[0];
        int index = 1;
        System.arraycopy(data, index, class8302.startTime, 0, class8302.startTime.length);
        index += class8302.endTime.length;
        System.arraycopy(data, index, class8302.endTime, 0, class8302.endTime.length);
        return class8302;
    }


    public static class Class8303 {
        byte code;
    }

    public static Class8303 getClass8303(byte[] data) {
        Class8303 class8303 = new Class8303();
        class8303.code = data[0];
        return class8303;
    }

    public static class Class8304 {
        byte[] photoId;
    }

    public static Class8304 getClass8304(byte[] data) {
        Class8304 class8304 = new Class8304();
        System.arraycopy(data, 0, class8304.photoId, 0, class8304.photoId.length);
        return class8304;
    }

    public static class Class8305 {
        byte code;
    }

    public static Class8305 getClass8305(byte[] data) {
        Class8305 class8305 = new Class8305();
        class8305.code = data[0];
        return class8305;
    }

    public static class Class8501 {
        byte parameterId;           //参数id
        byte cameraInterval;        //定时拍照间隔
        byte photoUpDataSetting;        //照片上传设置
        byte isReadOther;         //是否报读附加消息
        byte flameoutDelay;         //4	熄火后停止学时计时的延时时间	BYTE	单位：min
        byte[] flameoutGNSSDelay = new byte[2];     //熄火后GNSS数据包上传间隔
        byte[] flameoutCoachLogoutTime = new byte[2];       //熄火后教练自动登出的延时时间
        byte[] reloadIdentityTime = new byte[2];                    //重新验证身份时间
        byte isCoachJumpSchool;                     //教练跨校教学  1.允许  2.no
        byte isStdentJumpSchool;            //学员跨校学习
        byte[] onCallMessageTime = new byte[2];           //响应平台同类消息时间间隔
    }

    public static Class8501 getClass8501(byte[] data) {
        int index = 0;
        Class8501 class8501 = new Class8501();
        class8501.parameterId = data[index++];
        class8501.cameraInterval = data[index++];
        class8501.photoUpDataSetting = data[index++];
        class8501.isReadOther = data[index++];
        class8501.flameoutDelay = data[index++];
        System.arraycopy(data, index, class8501.flameoutGNSSDelay, 0, class8501.flameoutGNSSDelay.length);
        index += class8501.flameoutGNSSDelay.length;
        System.arraycopy(data, index, class8501.flameoutCoachLogoutTime, 0, class8501.flameoutCoachLogoutTime.length);
        index += class8501.flameoutCoachLogoutTime.length;
        System.arraycopy(data, index, class8501.reloadIdentityTime, 0, class8501.reloadIdentityTime.length);
        index += class8501.reloadIdentityTime.length;
        class8501.isCoachJumpSchool = data[index++];
        class8501.isStdentJumpSchool = data[index++];
        System.arraycopy(data, index, class8501.onCallMessageTime, 0, class8501.onCallMessageTime.length);
        return class8501;
    }


    public static class Class8401 {
        byte[] id = new byte[2];
        byte result;
        byte[] infoLength = new byte[4];
        byte[] info;
    }

    public static Class8401 getClass8401(byte[] data) {
        Class8401 class8401 = new Class8401();
        int index = 0;
        System.arraycopy(data, index, class8401.id, 0, class8401.id.length);
        index += class8401.id.length;
        class8401.result = data[index++];
        System.arraycopy(data, index, class8401.infoLength, 0, class8401.infoLength.length);
        int length = ByteUtil.byte2int(class8401.infoLength);
        if (length != 0) {
            class8401.info = new byte[length];
            System.arraycopy(data, index, class8401.info, 0, class8401.info.length);
        }
        return class8401;
    }

    public static class Class8402 {
        byte[] id = new byte[2];
        byte result;
        byte[] num = new byte[16];
        byte[] vehicleType = new byte[2];
    }

    public static Class8402 getClass8402(byte[] data) {
        Class8402 class8402 = new Class8402();
        int index = 0;
        System.arraycopy(data, index, class8402.id, 0, class8402.id.length);
        index += class8402.id.length;
        class8402.result = data[index++];
        if (class8402.result == 1) return class8402;
        System.arraycopy(data, index, class8402.num, 0, class8402.num.length);
        System.arraycopy(data, index, class8402.vehicleType, 0, class8402.vehicleType.length);
        return class8402;
    }


    public static class Class8403 {
        byte[] id = new byte[2];
        byte result;
        byte vehicleColor;
        String vehicleMark;
    }

    public static Class8403 getClass8403(byte[] data) {
        Class8403 class8403 = new Class8403();
        int index = 0;
        System.arraycopy(data, index, class8403.id, 0, class8403.id.length);
        index += class8403.id.length;
        class8403.result = data[index++];
        class8403.vehicleColor = data[index++];
        byte[] data1 = new byte[data.length - index];
        System.arraycopy(data, index, data1, 0, data1.length);
        class8403.vehicleMark = ByteUtil.getString(data1);
        return class8403;
    }

}
