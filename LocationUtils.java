package com.shuangyangad.sdk.mta.utils;

import android.location.Location;

import com.shuangyangad.sdk.mta.common.CommonData;

public class LocationUtils {
    private static LocationUtils instance;

    public static LocationUtils getInstance() {
        if (instance == null) {
            instance = new LocationUtils();
        }
        return instance;
    }

    private LocationUtils() {
        try {
            res[0] = LocationUtils1.getNetWorkLocation(CommonData.getInstance().getContext()).getLatitude();
            res[1] = LocationUtils1.getNetWorkLocation(CommonData.getInstance().getContext()).getLongitude();
            accuracy = LocationUtils1.getNetWorkLocation(CommonData.getInstance().getContext()).getAccuracy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private double[] res = new double[2];
    private double accuracy = 0.0;

    public double getAccuracy() {
        return accuracy;
    }

    public double[] getRes() {
        try {
            Location netWorkLocation = LocationUtils1.getNetWorkLocation(CommonData.getInstance().getContext());
            if (netWorkLocation != null) {
                res[0] = netWorkLocation.getLatitude();
                res[1] = netWorkLocation.getLongitude();
                accuracy = netWorkLocation.getAccuracy();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}
