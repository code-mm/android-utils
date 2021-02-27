package com.shuangyangad.sdk.mta.utils;

import android.content.pm.PackageManager;

import com.shuangyangad.sdk.mta.common.CommonData;

public class PermissionUtils {


    private static PermissionUtils instance;

    public static PermissionUtils getInstance() {

        if (instance == null) {
            instance = new PermissionUtils();
        }

        return instance;
    }


    private PackageManager packageManager;

    public boolean checkPermission(String permission) {
        if (packageManager == null) {
            packageManager = CommonData.getInstance().getContext().getPackageManager();
        }
        return PackageManager.PERMISSION_GRANTED == packageManager.checkPermission(permission, CommonData.getInstance().getContext().getPackageName());
    }
}
