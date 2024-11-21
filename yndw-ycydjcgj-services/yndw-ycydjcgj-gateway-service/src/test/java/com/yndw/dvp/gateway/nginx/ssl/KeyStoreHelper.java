package com.yndw.dvp.gateway.nginx.ssl;

import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;

/**
 * Create By Carlos
 * 2020/6/15
 */
public class KeyStoreHelper {
    public static void main(String[] args) {
        try {
            BASE64Encoder encoder = new BASE64Encoder();
            //读取文件内容
            FileInputStream is = new FileInputStream("E:/dvp.keystore");
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(is, "tbkj1@3#21".toCharArray());
            PrivateKey key = (PrivateKey) ks.getKey("dvp", "tbkj1@3#21".toCharArray());
            String encoded = encoder.encode(key.getEncoded());
            System.out.println("-----BEGIN RSA PRIVATE KEY-----");
            System.out.println(encoded);
            System.out.println("-----END RSA PRIVATE KEY-----");
            is.close();
        } catch (Exception e){
        }
    }
}