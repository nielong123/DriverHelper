package com.driverhelper.other.jiaminew;

import com.driverhelper.utils.ByteUtil;
import com.orhanobut.logger.Logger;

//import java.io.ByteArrayInputStream;
//import java.security.KeyStore;
//import java.security.PrivateKey;
//import java.util.Enumeration;
//
import Decoder.BASE64Decoder;


import java.io.ByteArrayInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

//import sun.misc.BASE64Decoder;


public class TestSignAndVerify {

    /***
     *
     * @param data
     * @param passWord      短
     * @param key           长
     * @param timestamp     鉴权需要时间戳，其他不需要
     * @return
     * @throws Exception
     */
    public static byte[] encryption(byte[] data1, String passWord, String key, long timestamp1) throws Exception {

        String data = "6972159358655724";
        String cadata = "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";
        String pas = "GgSiLEtTNDS2";
        char[] password = pas.toCharArray();
        byte[] cabuf = new BASE64Decoder().decodeBuffer(cadata);
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(new ByteArrayInputStream(cabuf), password);
        Enumeration<String> aliases = keyStore.aliases();
        if (!aliases.hasMoreElements()) {
            throw new RuntimeException("no alias found");
        }
        String alias = aliases.nextElement();
        PrivateKey privatekey = (PrivateKey) keyStore.getKey(alias, password);
        X509Certificate cert = (X509Certificate) keyStore.getCertificate(alias);// X509Certificate֤˩הг
        //long timestamp = new Date().getTime();
        long timestamp = 1500433671;
        ISign sign = new Sign();
        String sign_hex = sign.sign(data, timestamp, privatekey);
        System.out.println(sign_hex);
//        IVerify verify = new Verify();
//        boolean ok = verify.verify(data, timestamp, sign_hex, cert);
//        System.out.println(ok);
        return null;
    }
}
