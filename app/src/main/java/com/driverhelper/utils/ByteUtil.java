package com.driverhelper.utils;

import android.util.Log;

import com.driverhelper.beans.MessageBean;
import com.driverhelper.config.TcpBody;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static com.driverhelper.config.ConstantInfo.isDebug;

/**
 * Created by Administrator on 2017/6/6.
 */

public class ByteUtil {

    public static String TAG = "show bytes";

    private final static char[] mChars = "0123456789ABCDEF".toCharArray();
    private final static String mHexStr = "0123456789ABCDEF";

    /***
     * 将指定byte数组以16进制的形式打印到控制台
     *
     * @param b
     */
    public static void printHexString(byte[] b) {
        if (isDebug) {
            String printResult = "";
            for (int i = 0; i < b.length; i++) {
                String hex = Integer.toHexString(b[i] & 0xFF);
                if (hex.length() == 1) {
                    hex = '0' + hex;
                }
                printResult = printResult + hex.toUpperCase() + " ";
            }
            Log.e(TAG, printResult);
        }
    }

    public static void printHexString(String str, byte[] b) {
        if (isDebug) {
            String printResult = "";
            for (int i = 0; i < b.length; i++) {
                String hex = Integer.toHexString(b[i] & 0xFF);
                if (hex.length() == 1) {
                    hex = '0' + hex;
                }
                printResult = printResult + hex.toUpperCase() + " ";
            }
            Log.e(TAG, str + printResult);
        }
    }

    public static void printRecvHexString(byte[] b) {
        if (isDebug) {
            String printResult = "";
            for (int i = 0; i < b.length; i++) {
                String hex = Integer.toHexString(b[i] & 0xFF);
                if (hex.length() == 1) {
                    hex = '0' + hex;
                }
                printResult = printResult + hex.toUpperCase() + " ";
            }
            Log.d(TAG, "recrive data = " + printResult);
        }
    }

    public static void printHex(String str, byte b) {
        if (isDebug) {
            String printResult = "";
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            printResult = printResult + hex.toUpperCase() + " ";
            Log.d(TAG, str + printResult);
        }
    }

    private static String getString(byte[] bytes, String charsetName) {
        return new String(bytes, Charset.forName(charsetName));
    }

    public static String getString(byte[] bytes) {
        return getString(bytes, "GBK");
    }

    private static byte[] getBytes(String data, String charsetName) {
        Charset charset = Charset.forName(charsetName);
        return data.getBytes(charset);
    }

    public static byte[] str2Word(String data) {
        return getBytes(data, "GBK");
    }

    /**
     * 字符串转换成十六进制字符串
     *
     * @param str String 待转换的ASCII字符串
     * @return String 每个Byte之间空格分隔，如: [61 6C 6B]
     */
    public static String str2HexStr(String str) {
        StringBuilder sb = new StringBuilder();
        byte[] bs = str.getBytes();

        for (int i = 0; i < bs.length; i++) {
            sb.append(mChars[(bs[i] & 0xFF) >> 4]);
            sb.append(mChars[bs[i] & 0x0F]);
            sb.append(' ');
        }
        return sb.toString().trim();
    }

    /****
     * "111101" ----> "61" 二进制转十六进制
     *
     * @param bString
     * @return
     */
    public static String binaryString2hexString(String bString) {
        return Integer.parseInt(bString, 2) + "";
    }

    /****
     * "1A" ----> "26" 十进制字符串转十六进制字符串
     *
     * @param dString
     * @return
     */
    public static String decString2hexString(long dString) {
        return Long.toHexString(dString);
    }

    public static byte[] add(byte[] data1, byte[] data2) {

        byte[] result = new byte[data1.length + data2.length];
        System.arraycopy(data1, 0, result, 0, data1.length);
        System.arraycopy(data2, 0, result, data1.length, data2.length);
        return result;
    }

    public static byte[] add(byte data1, byte[] data2) {

        byte[] result = new byte[1 + data2.length];
        result[0] = data1;
        System.arraycopy(data2, 0, result, 1, data2.length);
        return result;
    }

    public static byte[] add(byte[] data1, byte data2) {

        byte[] result = new byte[data1.length + 1];
        System.arraycopy(data1, 0, result, 0, data1.length);
        result[result.length - 1] = data2;
        return result;
    }


    /****
     * 按数据长度自动左补齐"0"
     *
     * @param str
     *            需要补齐的内容
     * @param length
     *            补齐完毕后整个String的长度
     * @return
     */
    public static String autoAddZeroByLength(String str, int length) {
        if (str.length() < length) {
            int index = length - str.length();
            for (int i = 0; i < index; i++) {
                str = "0" + str;
            }
        }
        return str;
    }

    /****
     * 按数据长度自动右补齐"0"
     *
     * @param str
     *            需要补齐的内容
     * @param length
     *            补齐完毕后整个String的长度
     * @return
     */
    public static String autoAddZeroByLengthOnRight(String str, int length) {
        if (str.length() < length) {
            int index = length - str.length();
            for (int i = 0; i < index; i++) {
                str = str + "0";
            }
        }
        return str;
    }

    /**
     * @功能: 10进制串转为BCD码
     * @参数: 10进制串
     * @结果: BCD码 String
     * "3031323334"转化成{(byte)0x48,(byte)0x49,(byte)0x50,(byte)0x51
     * ,(byte)0x52,}
     */
    public static byte[] str2Bcd(String asc) {
        int len = asc.length();
        int mod = len % 2;
        if (mod != 0) {
            asc = "0" + asc;
            len = asc.length();
        }
        byte abt[] = new byte[len];
        if (len >= 2) {
            len = len / 2;
        }
        byte bbt[] = new byte[len];
        abt = asc.getBytes();
        int j, k;
        for (int p = 0; p < asc.length() / 2; p++) {
            if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
                j = abt[2 * p] - '0';
            } else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
                j = abt[2 * p] - 'a' + 0x0a;
            } else {
                j = abt[2 * p] - 'A' + 0x0a;
            }
            if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
                k = abt[2 * p + 1] - '0';
            } else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
                k = abt[2 * p + 1] - 'a' + 0x0a;
            } else {
                k = abt[2 * p + 1] - 'A' + 0x0a;
            }
            int a = (j << 4) + k;
            byte b = (byte) a;
            bbt[p] = b;
        }
        return bbt;
    }

    /**
     * @功能: BCD码转为10进制串(阿拉伯数据)
     * @参数: BCD码
     * @结果: 10进制串 { (byte) 0x11, (byte) 0x01, (byte) 0xa1 } --->"1101101"
     */
    public static String bcd2Str(byte[] bytes) {
        StringBuffer temp = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            temp.append((byte) ((bytes[i] & 0xf0) >>> 4));
            temp.append((byte) (bytes[i] & 0x0f));
        }
        return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp
                .toString().substring(1) : temp.toString();
    }

    /********
     *          4字节byte[] 转化成int
     * @param res
     * @return
     */
//    public static int byte2int(byte[] res) {
//
//        int intValue = 0;
//        for (int i = 0; i < res.length; i++) {
//            intValue += (res[i] & 0xFF) << (8 * (3 - i));
//        }
//        return intValue;
//    }
    public static int byte2int(byte[] data) {
        int intValue = 0;
        for (int i = 0; i < data.length; i++) {
            intValue += data[i] * Math.pow(256, data.length - i - 1);
        }
        return intValue;
    }

    public static int byte2int(byte data) {
        return data & 0xff;
    }

    /**
     * @功能: BCD码转为BCD16进制字符串(阿拉伯数据)
     * @参数: BCD码
     * @结果: 10进制串 { (byte) 0x11, (byte) 0x01, (byte) 0xa1 } --->"1101a1"
     */
    public static String bcdByte2bcdString(byte[] data) {

        String strResult = "";
        for (int j = 0; j < data.length; j++) {
            String str;
            if (data[j] == 0x00) {
                str = "00";
            } else {
                str = Integer.toHexString(data[j] & 0xFF);
                if (str.length() == 1) {
                    str = "0" + str;
                }
            }
            strResult = strResult + str;
        }
        return strResult;
    }

    /**
     * 十六转二进制
     *
     * @param hex 十六进制字符串
     * @return 二进制字符串
     */
    public static String hexStringToBinary(String hex) {
        hex = hex.toUpperCase();
        String result = "";
        int max = hex.length();
        for (int i = 0; i < max; i++) {
            char c = hex.charAt(i);
            switch (c) {
                case '0':
                    result += "0000";
                    break;
                case '1':
                    result += "0001";
                    break;
                case '2':
                    result += "0010";
                    break;
                case '3':
                    result += "0011";
                    break;
                case '4':
                    result += "0100";
                    break;
                case '5':
                    result += "0101";
                    break;
                case '6':
                    result += "0110";
                    break;
                case '7':
                    result += "0111";
                    break;
                case '8':
                    result += "1000";
                    break;
                case '9':
                    result += "1001";
                    break;
                case 'A':
                    result += "1010";
                    break;
                case 'B':
                    result += "1011";
                    break;
                case 'C':
                    result += "1100";
                    break;
                case 'D':
                    result += "1101";
                    break;
                case 'E':
                    result += "1110";
                    break;
                case 'F':
                    result += "1111";
                    break;
            }
        }
        return result;
    }

    public static String binString2DexString(String binString) {
        return Integer.parseInt(binString, 2) + "";
    }

    public static byte[] TimeToBytes(long paramLong) {
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Long.valueOf(paramLong);
        if (String.format("%d", arrayOfObject).length() >= 13) {
            byte[] arrayOfByte2 = new byte[8];
            arrayOfByte2[0] = ((byte) (int) (paramLong >> 56));
            arrayOfByte2[1] = ((byte) (int) (paramLong >> 48));
            arrayOfByte2[2] = ((byte) (int) (paramLong >> 40));
            arrayOfByte2[3] = ((byte) (int) (paramLong >> 32));
            arrayOfByte2[4] = ((byte) (int) (paramLong >> 24));
            arrayOfByte2[5] = ((byte) (int) (paramLong >> 16));
            arrayOfByte2[6] = ((byte) (int) (paramLong >> 8));
            arrayOfByte2[7] = ((byte) (int) (paramLong >> 0));
            return arrayOfByte2;
        }
        byte[] arrayOfByte1 = new byte[4];
        arrayOfByte1[0] = ((byte) (int) (paramLong >> 24));
        arrayOfByte1[1] = ((byte) (int) (paramLong >> 16));
        arrayOfByte1[2] = ((byte) (int) (paramLong >> 8));
        arrayOfByte1[3] = ((byte) (int) (paramLong >> 0));
        return arrayOfByte1;
    }

    /**
     * 二进制字符串转十进制
     *
     * @param binary 二进制字符串
     * @return 十进制数值
     */
    public static int binaryToAlgorism(String binary) {
        int max = binary.length();
        int result = 0;
        for (int i = max; i > 0; i--) {
            char c = binary.charAt(i - 1);
            int algorism = c - '0';
            result += Math.pow(2, max - i) * algorism;
        }
        return result;
    }

    /**
     * 十六进制字符串转为Byte数组,每两个十六进制字符转为一个Byte
     *
     * @param hexString 十六进制字符串
     * @return byte 转换结果
     */
    public static byte[] hexString2BCD(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * Convert char to byte
     *
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }


    /***
     *          计算sum的结果
     * @param data
     * @return
     */
    private static byte getSum(byte[] data) {
        byte result = 0;
        for (int i = 0; i < data.length; i++) {
            result += data[i];
        }
        result = (byte) (result ^ 0xff);
        return result;
    }

    /*****
     * 检查 sum的结果  是否相同
     * @param data
     * @param sumResult
     * @return
     */
    public static boolean checkSum(byte[] data, byte sumResult) {
        if (getSum(data) == sumResult) {
            return true;
        }
        return false;
    }

    /****
     * 亦或运算
     *
     * @param data
     * @return
     */
    private static byte[] getXor(byte[] data) {

        byte[] temp = new byte[]{data[0]};
        for (int i = 1; i < data.length; i++) {
            temp[0] ^= data[i];
        }
        byte[] res = new byte[1];
        res[0] = temp[0];
        return res;
    }

    public static byte[] addXor(byte[] data) {
        byte[] xorValue = new byte[data.length - 1];
        System.arraycopy(data, 1, xorValue, 0, data.length - 1);
        Log.d(TAG, "addXor: xorValue");
        printHexString(xorValue);
        byte[] res = add(data, getXor(xorValue));
        return res;
    }

    /***
     * 添加尾部
     *
     * @param data
     * @return
     */
    public static byte[] addEND(byte[] data) {
        byte[] result = ByteUtil.add(data, TcpBody.END);
        return result;
    }

    /**
     * int 转 byte[]
     *
     * @param value int
     * @param len   转byte【】 长度
     * @return
     */
    public static byte[] int2Bytes(int value, int len) {
        byte[] b = new byte[len];
        for (int i = 0; i < len; i++) {
            b[len - i - 1] = (byte) ((value >> 8 * i) & 0xff);
        }
        return b;
    }

    /*****
     * 检查数据中的特殊字符串 0x7e<——>0x7d后紧跟一个0x02； 0x7d<——>0x7d后紧跟一个0x01。
     *
     * @param data
     * @return
     */
    public static byte[] checkMark(byte[] data) {

        byte[] data1 = new byte[data.length - 2];
        System.arraycopy(data1, 1, data, data.length - 2, 0);
        List<Byte> dataList = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            dataList.add(data[i]);
        }
        List<Byte> resultList = new ArrayList();
        for (int i = 0; i < dataList.size(); i++) {
            byte byte1 = dataList.get(i);
            if (byte1 != (byte) 0x7e && byte1 != (byte) 0x7d || i == 0
                    || i == (dataList.size() - 1)) {
                resultList.add(byte1);
            } else if (byte1 == (byte) 0x7e) {
                resultList.add((byte) 0x7d);
                resultList.add((byte) 0x02);
            } else if (byte1 == (byte) 0x7d) {
                resultList.add((byte) 0x7d);
                resultList.add((byte) 0x01);
            }
        }

        int length = resultList.size();
        byte[] result = new byte[length];
        for (int i = 0; i < length; i++) {
            result[i] = resultList.get(i);
        }
        // printHexString(result);
        return result;
    }

    /****
     * 将服务端传来的数据还原
     * @param data
     * @return
     */
    public static byte[] rebackData(byte[] data) {
        List<Byte> dataList = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            if (i < data.length - 1) {
                if (data[i] == (byte) 0x7d && data[i + 1] == (byte) 0x02) {
                    dataList.add((byte) 0x7e);
                } else if (data[i] == (byte) 0x7d && data[i + 1] == (byte) 0x01) {
                    dataList.add((byte) 0x7d);
                } else {
                    dataList.add(data[i]);
                }
            } else {
                dataList.add(data[i]);
            }
        }

        byte[] result = new byte[dataList.size()];
        for (int i = 0; i < dataList.size(); i++) {
            result[i] = dataList.get(i);
        }
        return result;
    }

    /****
     * 检查XOR是否正确
     * @param data
     * @return
     */
    public static boolean checkXOR(byte[] data) {

        byte[] data0 = new byte[data.length - 1];
        System.arraycopy(data, 0, data0, 0, data.length - 1);
        byte xor = getXor(data0)[0];
        if (xor == data[data.length - 1]) {
            return true;
        } else {
            return false;
        }
    }

    /****
     * 处理消息头的信息
     * @param data
     * @return
     */
    public static MessageBean handlerInfo(byte[] data) {
        MessageBean messageBean = new MessageBean();
        byte[] data0 = new byte[2];         //消息id
        System.arraycopy(data, 1, data0, 0, 2);
        messageBean.headBean.messageId = ByteUtil.bcd2Str(data0);
        byte[] data1 = new byte[2];         //消息体属性
        System.arraycopy(data, 3, data1, 0, 2);
        messageBean.headBean.messageAttribute = ByteUtil.bcdByte2bcdString(data1);
        messageBean.headBean.benAttribute = autoAddZeroByLength(hexStringToBinary(messageBean.headBean.messageAttribute), 10);      //由消息体得到二进制的消息体
        messageBean.headBean.bodyLength = Integer.valueOf(binString2DexString(messageBean.headBean.benAttribute.substring(6, messageBean.headBean.benAttribute.length())));
        messageBean.headBean.isPart = Integer.valueOf(messageBean.headBean.benAttribute.substring(2, 3));

        if (messageBean.headBean.isPart == 1) {               //分包  当消息体属性中第13位为1时表示消息体为长消息，进行分包发送处理，具体分包信息由消息包封装项决定
            messageBean.bodyBean = new byte[messageBean.headBean.bodyLength];
            System.arraycopy(data, data.length - messageBean.headBean.bodyLength - 1, messageBean.bodyBean, 0, messageBean.headBean.bodyLength);
            byte[] totle = new byte[2];
            System.arraycopy(data, 16, totle, 0, 2);
            messageBean.headBean.encapsulationInfo.totle = Integer.valueOf(ByteUtil.bcd2Str(totle));
            byte[] index = new byte[2];
            System.arraycopy(data, 18, index, 0, 2);
            messageBean.headBean.encapsulationInfo.index = Integer.valueOf(ByteUtil.bcd2Str(index));
        }
        messageBean.bodyBean = new byte[messageBean.headBean.bodyLength];
        System.arraycopy(data, data.length - messageBean.headBean.bodyLength - 1, messageBean.bodyBean, 0, messageBean.headBean.bodyLength);
        Log.d(TAG, "handlerInfo: ");
//    }
        return messageBean;
    }
}
