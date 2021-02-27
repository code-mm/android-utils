package com.shuangyangad.sdk.mta.utils;


import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;


import com.shuangyangad.sdk.mta.common.CommonData;


public class ApkUtils {
    private static final String TAG = "ApkUtilsImpl::Java";
    private volatile static ApkUtils instance;

    public synchronized static ApkUtils getInstance() {
        if (instance == null) {
            instance = new ApkUtils();
        }
        return instance;
    }


    public String getAppName() {

        try {
            PackageManager packageManager = CommonData.getInstance().getContext().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    CommonData.getInstance().getContext().getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return CommonData.getInstance().getContext().getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public String getVersionCode() {
        try {
            PackageManager packageManager = CommonData.getInstance().getContext().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    CommonData.getInstance().getContext().getPackageName(), 0);
            return packageInfo.versionCode + "";

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0 + "";
    }


    public String getVersionName() {
        try {
            PackageManager packageManager = CommonData.getInstance().getContext().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    CommonData.getInstance().getContext().getPackageName(), 0);
            return packageInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public boolean isPackageInstalled(String s) {

        if (s == null || "".equals(s))
            return false;
        ApplicationInfo info = null;
        try {
            info = CommonData.getInstance().getContext().getPackageManager().getApplicationInfo(s, 0);
            return info != null;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }


    public String getMeta(String s) {
        try {
            ApplicationInfo appInfo = CommonData.getInstance().getContext().getPackageManager()
                    .getApplicationInfo(CommonData.getInstance().getContext().getPackageName(),
                            PackageManager.GET_META_DATA);
            return appInfo.metaData.getString(s);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String getMetaString(String s) {
        try {
            ApplicationInfo appInfo = CommonData.getInstance().getContext().getPackageManager()
                    .getApplicationInfo(CommonData.getInstance().getContext().getPackageName(),
                            PackageManager.GET_META_DATA);
            String values = appInfo.metaData.getString(s);
            return values;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String getMetaInteger(String s) {
        try {
            ApplicationInfo appInfo = CommonData.getInstance().getContext().getPackageManager()
                    .getApplicationInfo(CommonData.getInstance().getContext().getPackageName(),
                            PackageManager.GET_META_DATA);
            String values = appInfo.metaData.getInt(s) + "";
            return values;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String getMetaBoolean(String s) {
        try {
            ApplicationInfo appInfo = CommonData.getInstance().getContext().getPackageManager()
                    .getApplicationInfo(CommonData.getInstance().getContext().getPackageName(),
                            PackageManager.GET_META_DATA);
            String values = appInfo.metaData.getBoolean(s) + "";
            return values;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String getMetaLong(String s) {
        try {
            ApplicationInfo appInfo = CommonData.getInstance().getContext().getPackageManager()
                    .getApplicationInfo(CommonData.getInstance().getContext().getPackageName(),
                            PackageManager.GET_META_DATA);
            String values = appInfo.metaData.getLong(s) + "";
            return values;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public boolean isDebug() {
        try {
            ApplicationInfo info = CommonData.getInstance().getContext().getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return false;
    }


    public String getPackageName() {
        try {
            ApplicationInfo appInfo = CommonData.getInstance().getContext().getPackageManager()
                    .getApplicationInfo(CommonData.getInstance().getContext().getPackageName(),
                            PackageManager.GET_META_DATA);

            return appInfo.packageName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getSign() {
        return AppInfoUtils.getSingInfo(CommonData.getInstance().getContext(), getPackageName(), "SHA1");
    }

    public String getSign(String s) {
        return AppInfoUtils.getSingInfo(CommonData.getInstance().getContext(), getPackageName(), s);
    }
}
