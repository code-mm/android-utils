package com.shuangyangad.sdk.mta.utils;


import org.json.JSONException;
import org.json.JSONObject;

public class TTGUtils {


    private static volatile TTGUtils instance;

    public static synchronized TTGUtils getInstance() {
        if (instance == null) {
            instance = new TTGUtils();
        }
        return instance;
    }

    JSONObject jsonObject = new JSONObject();

    private String ttg;

    public TTGUtils() {
        super();
        try {
            jsonObject.put("os", "android");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public String getTTG() {

        if (ttg == null || "".equals(ttg)) {
            ttg = jsonObject.toString();
        }

        return ttg;
    }


    public boolean verify(String ttg) {
        try {
            JSONObject jsonObject = new JSONObject(ttg);
            String os = jsonObject.getString("os");
            if (os == null || "".equals(os)) {
                return false;
            }
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
}
