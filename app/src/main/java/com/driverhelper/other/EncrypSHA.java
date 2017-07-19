package com.driverhelper.other;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2017/7/19.
 */

public class EncrypSHA {

    //byte字节转换成16进制的字符串MD5Utils.hexString
    public byte[] eccrypt(String info, String shaType) throws NoSuchAlgorithmException {
        MessageDigest sha = MessageDigest.getInstance(shaType);
        byte[] srcBytes = info.getBytes();
        // 使用srcBytes更新摘要
        sha.update(srcBytes);
        // 完成哈希计算，得到result
        byte[] resultBytes = sha.digest();
        return resultBytes;
    }

    public byte[] eccryptSHA256(String info) throws NoSuchAlgorithmException {
        return eccrypt(info, "SHA-256");
    }

    public static String hexString(byte[] bytes) {
        StringBuffer hexValue = new StringBuffer();

        for (int i = 0; i < bytes.length; i++) {
            int val = ((int) bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
}
