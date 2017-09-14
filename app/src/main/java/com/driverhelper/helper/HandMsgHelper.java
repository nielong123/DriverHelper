package com.driverhelper.helper;

import android.util.Log;

import com.driverhelper.utils.ByteUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/7/25.
 */

public final class HandMsgHelper {

    public static class Class8003 {
        byte[] waterCode = new byte[2];
        byte totle;
        List<byte[]> idList = new ArrayList<>();
    }

    public static Class8003 getClass8003(byte[] data) {
        Class8003 class8003 = new Class8003();
        System.arraycopy(data, 0, class8003.waterCode, 0, class8003.waterCode.length);
        class8003.totle = data[3];
        for (int i = 0; i < class8003.totle; i++) {
            byte[] data1 = new byte[1];
            System.arraycopy(data, 3 + i, data1, 0, 1);
        }
        return class8003;
    }

    public static class Class8101 {
        public byte[] result = new byte[1];
        byte[] coachNum = new byte[16];
        public byte[] isReadAdditional = new byte[1];
        byte[] additionalLenght = new byte[1];
        public String additionalInfo;
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

    static public class Class8103 {

        public List<Setting> getSettingList() {
            return settingList;
        }

        public void setSettingList(List<Setting> settingList) {
            this.settingList = settingList;
        }

        public static class Setting {
            int id;
            byte parameterLength;
            String strParameter;
            byte[] byteParameter;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getStrParameter() {
                return strParameter;
            }

            public void setStrParameter(String strParameter) {
                this.strParameter = strParameter;
            }

            public byte[] getByteParameter() {
                return byteParameter;
            }

            public void setByteParameter(byte[] byteParameter) {
                this.byteParameter = byteParameter;
            }

            @Override
            public String toString() {
                return "Setting{" +
                        "id=" + id +
                        ", parameterLength=" + parameterLength +
                        ", strParameter='" + strParameter + '\'' +
                        ", byteParameter=" + Arrays.toString(byteParameter) +
                        '}';
            }
        }

        int totleNumber;

        @Override
        public String toString() {
            return "Class8103{" +
                    "totleNumber=" + totleNumber +
                    ", partNumber=" + partNumber +
                    ", settingList=" + settingList +
                    '}';
        }

        int partNumber;
        List<Setting> settingList = new ArrayList<>();
    }

    public static Class8103 getClass8103(byte[] data) {

        ByteUtil.printHexString("data = ", data);
        Class8103 class8103 = new Class8103();
        class8103.totleNumber = data[0];
        class8103.partNumber = data[1];
        int index = 2;
        for (int i = 0; i < class8103.totleNumber; i++) {
            Class8103.Setting setting = new Class8103.Setting();
            byte[] data0 = new byte[4];
            System.arraycopy(data, index, data0, 0, data0.length);
            index += data0.length;
            setting.id = ByteUtil.byte2int(data0);
            ByteUtil.printHexString("data0 = ", data0);

            setting.parameterLength = data[index++];
            System.out.println("index1 = " + index);
            System.out.println("setting.parameterLength = "
                    + setting.parameterLength);
            byte[] data1 = new byte[setting.parameterLength];
            System.arraycopy(data, index, data1, 0, data1.length);
            ByteUtil.printHexString("data1 = ", data1);
            index += data1.length;
            if (setting.id == (byte) 0x01 || setting.id == (byte) 0x02
                    || setting.id == (byte) 0x03 || setting.id == (byte) 0x04
                    || setting.id == (byte) 0x05 || setting.id == (byte) 0x06
                    || setting.id == (byte) 0x07 || setting.id == (byte) 0x18
                    || setting.id == (byte) 0x19 || setting.id == (byte) 0x20
                    || setting.id == (byte) 0x21 || setting.id == (byte) 0x22
                    || setting.id == (byte) 0x27 || setting.id == (byte) 0x28
                    || setting.id == (byte) 0x29 || setting.id == (byte) 0x2C
                    || setting.id == (byte) 0x2d || setting.id == (byte) 0x2E
                    || setting.id == (byte) 0x2F || setting.id == (byte) 0x30
                    || setting.id == (byte) 0x45 || setting.id == (byte) 0x46
                    || setting.id == (byte) 0x47 || setting.id == (byte) 0x50
                    || setting.id == (byte) 0x51 || setting.id == (byte) 0x52
                    || setting.id == (byte) 0x53 || setting.id == (byte) 0x54
                    || setting.id == (byte) 0x55 || setting.id == (byte) 0x56
                    || setting.id == (byte) 0x57 || setting.id == (byte) 0x58
                    || setting.id == (byte) 0x59 || setting.id == (byte) 0x5A
                    || setting.id == (byte) 0x70 || setting.id == (byte) 0x71
                    || setting.id == (byte) 0x72 || setting.id == (byte) 0x73
                    || setting.id == (byte) 0x74 || setting.id == (byte) 0x80
                    || setting.id == (byte) 0x81 || setting.id == (byte) 0x82
                    || setting.id == (byte) 0x84) {
                setting.byteParameter = data1;
            }
            if (setting.id == (byte) 0x010 || setting.id == (byte) 0x11
                    || setting.id == (byte) 0x12 || setting.id == (byte) 0x13
                    || setting.id == (byte) 0x14 || setting.id == (byte) 0x15
                    || setting.id == (byte) 0x16 || setting.id == (byte) 0x17
                    || setting.id == (byte) 0x40 || setting.id == (byte) 0x41
                    || setting.id == (byte) 0x42 || setting.id == (byte) 0x43
                    || setting.id == (byte) 0x44 || setting.id == (byte) 0x48
                    || setting.id == (byte) 0x49 || setting.id == (byte) 0x83) {
                setting.strParameter = ByteUtil.getString(data1);
            }

            class8103.settingList.add(setting);
            System.out.println("/*******************************/");
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

    static class Class8105 {
        byte commandId;
        byte[] commandInfo;
    }

    static Class8105 getClass8105(byte[] data) {
        Class8105 class8105 = new Class8105();
        class8105.commandId = data[0];
        class8105.commandInfo = new byte[data.length - 1];
        System.arraycopy(data, 1, class8105.commandInfo, 0, class8105.commandInfo.length);
        return class8105;
    }


    public static class Class8106 {
        public byte totleNum;
        public int waterCode;
        public List<byte[]> idList = new ArrayList<>();
    }


    public static Class8106 getClass8106(byte[] data) {
        Class8106 class8106 = new Class8106();
        class8106.totleNum = data[0];
        for (int i = 0; i < class8106.totleNum; i++) {
            byte[] id = new byte[4];
            System.arraycopy(data, i * 4 + 1, id, 0, id.length);
            class8106.idList.add(id);
        }
        return class8106;
    }


    public static class Class8201 {
        @Override
        public String toString() {
            return "Class8201{" +
                    "result=" + result +
                    ", studentNum=" + Arrays.toString(studentNum) +
                    ", totleStudyTime=" + totleStudyTime +
                    ", finishedStudyTime=" + finishedStudyTime +
                    ", totleMileage=" + totleMileage +
                    ", finishedMileage=" + finishedMileage +
                    ", isUpdataOtherInfo=" + isUpdataOtherInfo +
                    ", otherInfoLength=" + otherInfoLength +
                    ", otherInfo='" + otherInfo + '\'' +
                    '}';
        }

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
        ByteUtil.printHexString("学员登录应答:",data);
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
        byte[] data5 = new byte[1];
        System.arraycopy(data, index, data5, 0, data5.length);
        index += data5.length;
        class8201.otherInfoLength = ByteUtil.byte2int(data5);

        if (class8201.otherInfoLength != 0) {
            byte[] data6 = new byte[class8201.otherInfoLength];
            System.arraycopy(data, index, data6, 0, data6.length);
            class8201.otherInfo = ByteUtil.getString(data6);
        }

        return class8201;
    }

    /***
     * 零食位置跟踪
     */
    public static class Class8202_ {
        public int waterCode;
        public int timeInterval;
        public long term;
    }

    static Class8202_ getClass8202_(byte[] data) {
        Class8202_ class8202_ = new Class8202_();
        byte[] data0 = new byte[2];
        System.arraycopy(data, 0, data0, 0, data0.length);
        class8202_.term = ByteUtil.byte2int(data0);
        byte[] data1 = new byte[4];
        System.arraycopy(data, 2, data1, 0, data1.length);
        class8202_.timeInterval = ByteUtil.byte2int(data1);
        return class8202_;
    }


    /***
     * 透传的8202
     */
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

    public static class Class8205 {
        public byte findType;
        public byte[] startTime = new byte[6];
        public byte[] endTime = new byte[6];
        public byte findNum;
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

    public static class Class8301 {
        public byte updataType;
        public byte cameraNum;
        public byte size;
    }


    static Class8301 getClass8301(byte[] data) {
        Class8301 class8301 = new Class8301();
        class8301.updataType = data[0];
        class8301.cameraNum = data[1];
        class8301.size = data[2];
        return class8301;
    }

    public static class Class8302 {
        public byte type;
        public byte[] startTime = new byte[6];
        public byte[] endTime = new byte[6];
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
        public byte[] photoId = new byte[10];
    }

    public static Class8304 getClass8304(byte[] data) {
        Class8304 class8304 = new Class8304();
        System.arraycopy(data, 0, class8304.photoId, 0, class8304.photoId.length);
        Log.e("", ByteUtil.getString(class8304.photoId));
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
        byte PIC_INTV_min;        //定时拍照间隔
        byte UPLOAD_GBN;        //照片上传设置
        byte ADDMSG_YN;         //是否报读附加消息
        byte STOP_DELAY_TIME_min;         //4	熄火后停止学时计时的延时时间	BYTE	单位：min
        byte[] STOP_GNSS_UPLOAD_INTV_sec = new byte[2];     //熄火后GNSS数据包上传间隔
        byte[] STOP_COACH_DELAY_TIME_min = new byte[2];       //熄火后教练自动登出的延时时间
        byte[] USER_CHK_TIME_min = new byte[2];                    //重新验证身份时间
        byte COACH_TRANS_YN;                     //教练跨校教学  1.允许  2.no
        byte STU_TRANS_YN;            //学员跨校学习
        byte[] DUP_MSG_REJECT_INTV_sec = new byte[2];           //响应平台同类消息时间间隔

        @Override
        public String toString() {
            return "Class8501{" +
                    "parameterId=" + parameterId +
                    ", PIC_INTV_min=" + PIC_INTV_min +
                    ", UPLOAD_GBN=" + UPLOAD_GBN +
                    ", ADDMSG_YN=" + ADDMSG_YN +
                    ", STOP_DELAY_TIME_min=" + STOP_DELAY_TIME_min +
                    ", STOP_GNSS_UPLOAD_INTV_sec=" + Arrays.toString(STOP_GNSS_UPLOAD_INTV_sec) +
                    ", STOP_COACH_DELAY_TIME_min=" + Arrays.toString(STOP_COACH_DELAY_TIME_min) +
                    ", USER_CHK_TIME_min=" + Arrays.toString(USER_CHK_TIME_min) +
                    ", COACH_TRANS_YN=" + COACH_TRANS_YN +
                    ", STU_TRANS_YN=" + STU_TRANS_YN +
                    ", DUP_MSG_REJECT_INTV_sec=" + Arrays.toString(DUP_MSG_REJECT_INTV_sec) +
                    '}';
        }

    }

    public static Class8501 getClass8501(byte[] data) {
        int index = 0;
        Class8501 class8501 = new Class8501();
        class8501.parameterId = data[index++];
        class8501.PIC_INTV_min = data[index++];
        class8501.UPLOAD_GBN = data[index++];
        class8501.ADDMSG_YN = data[index++];
        class8501.STOP_DELAY_TIME_min = data[index++];
        System.arraycopy(data, index, class8501.STOP_GNSS_UPLOAD_INTV_sec, 0, class8501.STOP_GNSS_UPLOAD_INTV_sec.length);
        index += class8501.STOP_GNSS_UPLOAD_INTV_sec.length;
        System.arraycopy(data, index, class8501.STOP_COACH_DELAY_TIME_min, 0, class8501.STOP_COACH_DELAY_TIME_min.length);
        index += class8501.STOP_COACH_DELAY_TIME_min.length;
        System.arraycopy(data, index, class8501.USER_CHK_TIME_min, 0, class8501.USER_CHK_TIME_min.length);
        index += class8501.USER_CHK_TIME_min.length;
        class8501.COACH_TRANS_YN = data[index++];
        class8501.STU_TRANS_YN = data[index++];
        System.arraycopy(data, index, class8501.DUP_MSG_REJECT_INTV_sec, 0, class8501.DUP_MSG_REJECT_INTV_sec.length);
        return class8501;
    }

    public static class Class8502 {
        public byte state;
        public byte dataLength;
        public String data;
    }

    static Class8502 getClass8502(byte[] data) {
        Class8502 class8502 = new Class8502();
        class8502.state = data[0];
        class8502.dataLength = data[1];
        byte[] data1 = new byte[class8502.dataLength];
        System.arraycopy(data, 2, data1, 0, data1.length);
        class8502.data = ByteUtil.getString(data1);

        return class8502;
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
