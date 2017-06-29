package com.driverhelper.utils;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;

/**
 * Created by Administrator on 2017/6/28.
 */

public final class df {
    protected static final char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static byte[] BCDString2bytes(String paramString, int paramInt) {
        String str = "00000000000000000000" + paramString;
        byte[] arrayOfByte1 = str.substring(str.length() - paramInt * 2).getBytes();
        byte[] arrayOfByte2 = new byte[paramInt];
        for (int i = 0; i < paramInt; i++) {
            int j = (short) (byte) (0xF & -48 + bytes2ubyte(arrayOfByte1, i * 2));
            arrayOfByte2[i] = ((byte) ((short) (byte) (0xF & -48 + bytes2ubyte(arrayOfByte1, 1 + i * 2)) | j << 4));
        }
        return arrayOfByte2;
    }

    public static byte[] EncodeGBK(String paramString) {
        ByteBuffer localByteBuffer = Charset.forName("GBK").encode(CharBuffer.wrap(paramString.toCharArray()));
        byte[] arrayOfByte = new byte[localByteBuffer.limit()];
        System.arraycopy(localByteBuffer.array(), 0, arrayOfByte, 0, localByteBuffer.limit());
        return arrayOfByte;
    }

    public static byte[] HexString2bytes(String paramString) {
        byte[] arrayOfByte = new byte[paramString.length() / 2];
        int i = 0;
        for (int j = 0; j < paramString.length(); j += 2)
            if (paramString.length() >= j + 2) {
                String str = new String(paramString.substring(j, j + 2));
                int k = i + 1;
                arrayOfByte[i] = ((byte) (0xFF & Short.parseShort(str, 16)));
                i = k;
            }
        return arrayOfByte;
    }

    public static int InsertBytes(byte[] paramArrayOfByte1, int paramInt, byte[] paramArrayOfByte2) {
        System.arraycopy(paramArrayOfByte2, 0, paramArrayOfByte1, paramInt, paramArrayOfByte2.length);
        return -1 + (paramInt + paramArrayOfByte2.length);
    }

    public static long Now1970sec() {
        return System.currentTimeMillis() / 1000L;
    }

    public static byte[] String2Bytes(String paramString, int paramInt) {
        if (paramInt == 0)
            return paramString.getBytes();
        byte[] arrayOfByte1 = new byte[paramInt];
        byte[] arrayOfByte2 = paramString.getBytes();
        System.arraycopy(arrayOfByte2, 0, arrayOfByte1, 0, arrayOfByte2.length);
        return arrayOfByte1;
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

    public static String bytes2BCDString(byte[] paramArrayOfByte, int paramInt1, int paramInt2) {
        char[] arrayOfChar = new char[2 * paramArrayOfByte.length];
        for (int i = 0; i < paramInt2; i++) {
            int j = 0xFF & paramArrayOfByte[(paramInt1 + i)];
            arrayOfChar[(i * 2)] = hexArray[(j >>> 4)];
            arrayOfChar[(1 + i * 2)] = hexArray[(j & 0xF)];
        }
        return new String(arrayOfChar);
    }

    public static String bytes2HexString(byte[] paramArrayOfByte) {
        char[] arrayOfChar = new char[2 * paramArrayOfByte.length];
        for (int i = 0; i < paramArrayOfByte.length; i++) {
            int j = 0xFF & paramArrayOfByte[i];
            arrayOfChar[(i * 2)] = hexArray[(j >>> 4)];
            arrayOfChar[(1 + i * 2)] = hexArray[(j & 0xF)];
        }
        return new String(arrayOfChar);
    }

    public static String bytes2HexString(byte[] paramArrayOfByte, int paramInt) {
        char[] arrayOfChar = new char[paramInt * 2];
        for (int i = 0; i < paramInt; i++) {
            int j = 0xFF & paramArrayOfByte[i];
            arrayOfChar[(i * 2)] = hexArray[(j >>> 4)];
            arrayOfChar[(1 + i * 2)] = hexArray[(j & 0xF)];
        }
        return new String(arrayOfChar);
    }

    public static String bytes2HexStringB(byte[] paramArrayOfByte) {
        char[] arrayOfChar = new char[3 * paramArrayOfByte.length];
        for (int i = 0; i < paramArrayOfByte.length; i++) {
            int j = 0xFF & paramArrayOfByte[i];
            arrayOfChar[(i * 3)] = hexArray[(j >>> 4)];
            arrayOfChar[(1 + i * 3)] = hexArray[(j & 0xF)];
            arrayOfChar[(2 + i * 3)] = ' ';
        }
        return new String(arrayOfChar);
    }

//    public static String bytes2String(byte[] paramArrayOfByte, int paramInt1, int paramInt2) {
//        byte[] arrayOfByte = cutBytes2Bytes(paramArrayOfByte, paramInt1, paramInt2);
//        try {
//            String str = Charset.forName("GBK").newDecoder().decode(ByteBuffer.wrap(arrayOfByte)).toString();
//            return str;
//        } catch (CharacterCodingException localCharacterCodingException) {
//        }
//        return null;
//    }

    public static short bytes2ubyte(byte[] paramArrayOfByte) {
        return bytes2ubyte(paramArrayOfByte, 0);
    }

    public static short bytes2ubyte(byte[] paramArrayOfByte, int paramInt) {
        return (short) (0xFF & paramArrayOfByte[paramInt]);
    }

    public static long bytes2udword(byte[] paramArrayOfByte) {
        return bytes2udword(paramArrayOfByte, 0);
    }

    public static long bytes2udword(byte[] paramArrayOfByte, int paramInt) {
        return (0xFF & paramArrayOfByte[paramInt]) << 24 | (0xFF & paramArrayOfByte[(paramInt + 1)]) << 16 | (0xFF & paramArrayOfByte[(paramInt + 2)]) << 8 | 0xFF & paramArrayOfByte[(paramInt + 3)];
    }

    public static int bytes2uword(byte[] paramArrayOfByte) {
        return bytes2uword(paramArrayOfByte, 0);
    }

    public static int bytes2uword(byte[] paramArrayOfByte, int paramInt) {
        return (0xFF & paramArrayOfByte[paramInt]) << 8 | 0xFF & paramArrayOfByte[(paramInt + 1)];
    }

//    public static byte[] cutBytes2Bytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2) {
//        if (paramInt2 == -1) {
//            for (int i = paramInt1; (i < paramArrayOfByte.length) && (paramArrayOfByte[i] != 0); i++)
////                ;
//            byte[] arrayOfByte3 = new byte[i - paramInt1];
//            System.arraycopy(paramArrayOfByte, paramInt1, arrayOfByte3, 0, i - paramInt1);
//            return arrayOfByte3;
//        }
//        if (paramInt2 == 0) {
//            byte[] arrayOfByte2 = new byte[paramArrayOfByte.length - paramInt1];
//            System.arraycopy(paramArrayOfByte, paramInt1, arrayOfByte2, 0, paramArrayOfByte.length - paramInt1);
//            return arrayOfByte2;
//        }
//        byte[] arrayOfByte1 = new byte[paramInt2];
//        System.arraycopy(paramArrayOfByte, paramInt1, arrayOfByte1, 0, paramInt2);
//        return arrayOfByte1;
//    }

    public static int insertBytes(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2) {
        System.arraycopy(paramArrayOfByte2, 0, paramArrayOfByte1, paramInt1, paramInt2);
        return paramInt1 + paramInt2;
    }

    public static byte[] sumBytes(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2) {
        byte[] arrayOfByte = new byte[paramArrayOfByte1.length + paramArrayOfByte2.length];
        System.arraycopy(paramArrayOfByte1, 0, arrayOfByte, 0, paramArrayOfByte1.length);
        System.arraycopy(paramArrayOfByte2, 0, arrayOfByte, paramArrayOfByte1.length, paramArrayOfByte2.length);
        return arrayOfByte;
    }

    public static byte[] ubyte2bytes(short paramShort) {
        byte[] arrayOfByte = new byte[1];
        arrayOfByte[0] = ((byte) (paramShort & 0xFF));
        return arrayOfByte;
    }

    public static byte[] udword2bytes(long paramLong) {
        byte[] arrayOfByte = new byte[4];
        arrayOfByte[0] = ((byte) (int) (0xFF & paramLong >> 24));
        arrayOfByte[1] = ((byte) (int) (0xFF & paramLong >> 16));
        arrayOfByte[2] = ((byte) (int) (0xFF & paramLong >> 8));
        arrayOfByte[3] = ((byte) (int) (paramLong & 0xFF));
        return arrayOfByte;
    }

    public static byte[] uword2bytes(int paramInt) {
        byte[] arrayOfByte = new byte[2];
        arrayOfByte[0] = ((byte) (0xFF & paramInt >> 8));
        arrayOfByte[1] = ((byte) (paramInt & 0xFF));
        return arrayOfByte;
    }
}
