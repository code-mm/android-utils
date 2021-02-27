package com.shuangyangad.sdk.mta.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.shuangyangad.sdk.mta.common.CommonData;


public class UmidUtils {

    private static volatile UmidUtils instance;

    public static synchronized UmidUtils getInstance() {
        if (instance == null) {
            instance = new UmidUtils();
        }
        return instance;

    }

    public String getUmid() {
        SharedPreferences sharedPreferences = CommonData.getInstance().getContext().getSharedPreferences("com.ms.module.umid", Context.MODE_PRIVATE);
        String umid = sharedPreferences.getString("umid", null);
        if (umid == null) {
            String s = MD5Utils.getInstance().md5(SystemUtils.getInstance().getMac());
            sharedPreferences.edit().putString("umid", s).commit();
            return s;
        }
        return umid;
    }
}
