package com.driverhelper.helper;

import com.driverhelper.utils.ByteUtil;

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

    public static class Class8102 {
        byte[] result = new byte[1];
        byte[] coachNum = new byte[16];
    }

    public static Class8102 getClass8102(byte[] data) {
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

    public static Class8201 getClass8201(byte[] data) {
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

    public static class Class8202 {
        byte result;
        byte[] studentNum = new byte[16];
    }


    public static Class8202 getClass8202(byte[] data) {

        Class8202 class8202 = new Class8202();
        class8202.result = data[0];
        System.arraycopy(data, 1, class8202.studentNum, 0, class8202.studentNum.length);
        return class8202;
    }

    public static class Class8205 {
        byte findType;
        byte[] startTime = new byte[6];
        byte[] endTime = new byte[6];
        byte findNum;
    }

    public static Class8205 getClass8205(byte[] data) {

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
}
