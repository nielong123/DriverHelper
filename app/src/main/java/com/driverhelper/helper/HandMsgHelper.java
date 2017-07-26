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

    public static class Class8201 {
        byte[] result = new byte[1];
        byte[] coachNum = new byte[16];
    }

    public static Class8201 getClass8201(byte[] data) {
        Class8201 class8201 = new Class8201();
        int index = 0;
        System.arraycopy(data, index, class8201.result, 0, class8201.result.length);
        index = index + class8201.result.length;
        System.arraycopy(data, index, class8201.coachNum, 0, class8201.coachNum.length);
        return class8201;
    }


}
