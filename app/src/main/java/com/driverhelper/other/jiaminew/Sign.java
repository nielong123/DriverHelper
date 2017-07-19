package com.driverhelper.other.jiaminew;


import android.util.Log;

import com.driverhelper.utils.ByteUtil;

import java.security.MessageDigest;
import java.security.PrivateKey;

import javax.crypto.Cipher;

public class Sign implements ISign {

    public String sign(String data, long timestamp, PrivateKey key) throws Exception {
        return sign(data.getBytes("utf-8"), timestamp, key);
    }

    public String sign(String data, PrivateKey key) throws Exception {
        return sign(data.getBytes("utf-8"), 0, key);
    }

    public String sign(byte[] data, PrivateKey key) throws Exception {
        return sign(data, 0, key);
    }

    public String sign(byte[] data, long timestamp, PrivateKey key) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(data);
        if (timestamp > 0) {
            md.update(EncodeUtil.toBE(timestamp));
        }

        byte[] hash = md.digest();
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encrypted = cipher.doFinal(hash);
        Log.e("11", "/****************************11********************************/");
        ByteUtil.printHexString(encrypted);
        Log.e("22", "/*****************************22*******************************/");
        return ByteUtil.getString(encrypted);
//        return HexBin.encode(encrypted);
    }
}
