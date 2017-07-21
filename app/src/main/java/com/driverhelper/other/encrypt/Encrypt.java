package com.driverhelper.other.encrypt;

import android.util.Log;

import com.driverhelper.other.jiaminew.EncodeUtil;
import com.driverhelper.utils.ByteUtil;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Enumeration;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import Decoder.BASE64Decoder;


public class Encrypt {

    /**
     * 传入文本内容，返回 SHA-256 串
     *
     * @param data
     * @return
     */
    static public byte[] SHA256(final byte[] data, final String certificate,
                                final String password, final long timeStamp) {
        try {
            char[] pas = password.toCharArray();
            byte[] cabuf = new BASE64Decoder().decodeBuffer(certificate);
            ByteUtil.printHexString("cabuf = ", cabuf);
            ByteUtil.printHexString("加密内容 : ", data);
//            Log.d("11", "证书 : " + certificate);
//            Log.d("11", "证书秘钥 : " + password);
//            Log.d("11", "timeStamp = " + timeStamp);
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(new ByteArrayInputStream(cabuf), pas);
            Enumeration<String> aliases = keyStore.aliases();
            if (!aliases.hasMoreElements()) {
                throw new RuntimeException("no alias found");
            }
            String alias = aliases.nextElement();
            PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, pas);

            return SHA(data, timeStamp, privateKey, "SHA-256");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (KeyStoreException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (CertificateException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /***
     *
     * @param data
     * @param timeStamp
     * @param key
     * @param strType
     * @return
     */
    private static byte[] SHA(final byte[] data, final long timeStamp,
                              PrivateKey key, final String strType) {
        // 是否是有效字符串
        if (data.length > 0) {
            try {
                // SHA 加密开始
                // 创建加密对象 并傳入加密類型
                MessageDigest messageDigest = MessageDigest
                        .getInstance(strType);
                // 传入要加密的字符串
                messageDigest.update(data);
                messageDigest.update(EncodeUtil.toBE(timeStamp));
                // 得到 byte 類型结果
                byte byteBuffer[] = messageDigest.digest();

                try {
                    Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
                    cipher.init(Cipher.ENCRYPT_MODE, key);
                    byte[] encrypted = cipher.doFinal(byteBuffer);
                    ByteUtil.printHexString("加密结果为  ", encrypted);
                    return encrypted;
                } catch (NoSuchPaddingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
