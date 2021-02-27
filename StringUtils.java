package com.shuangyangad.sdk.mta.utils;


public class StringUtils {

    private static volatile StringUtils instance;

    public static synchronized StringUtils getInstance() {

        if (instance == null) {
            instance = new StringUtils();
        }
        return instance;
    }

    public String encode(String src) {
        return EncryptionUtils.encode(src);
    }


    public String decode(String src) {
        return EncryptionUtils.decode(src);
    }
}
