package com.shuangyangad.sdk.mta.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.webkit.WebSettings;


import com.shuangyangad.sdk.mta.common.CommonData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import static android.content.Context.WIFI_SERVICE;
import static android.os.Build.BRAND;
import static android.os.Build.CPU_ABI;
import static android.os.Build.PRODUCT;
import static android.os.Build.VERSION;
import static android.os.Build.VERSION_CODES;

public class SystemUtils {

    private static SystemUtils systemUtils = new SystemUtils();

    public static SystemUtils getInstance() {
        return systemUtils;
    }


    public boolean isMobilePhone() {
        return true;
    }


    public String getIp() {
        final WifiManager wifiManager = (WifiManager) CommonData.getInstance().getContext()
                .getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        String ipStr = intToIp(ip);
        return ipStr;
    }

    private String intToIp(int i) {

        return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF)
                + "." + (i >> 24 & 0xFF);
    }


    public String getSysInfo() {
        String phoneInfo = "Product: " + Build.PRODUCT;
        phoneInfo += ", CPU_ABI: " + Build.CPU_ABI;
        phoneInfo += ", TAGS: " + Build.TAGS;
        phoneInfo += ", VERSION_CODES.BASE: "
                + VERSION_CODES.BASE;
        phoneInfo += ", MODEL: " + Build.MODEL;
        phoneInfo += ", SDK: " + VERSION.SDK;
        phoneInfo += ", VERSION.RELEASE: " + VERSION.RELEASE;
        phoneInfo += ", DEVICE: " + Build.DEVICE;
        phoneInfo += ", DISPLAY: " + Build.DISPLAY;
        phoneInfo += ", BRAND: " + Build.BRAND;
        phoneInfo += ", BOARD: " + Build.BOARD;
        phoneInfo += ", FINGERPRINT: " + Build.FINGERPRINT;
        phoneInfo += ", ID: " + Build.ID;
        phoneInfo += ", MANUFACTURER: " + Build.MANUFACTURER;
        phoneInfo += ", USER: " + Build.USER;

        return phoneInfo;
    }


    /**
     * 获取手机手机号
     *
     * @return
     */
    public String getPhoneNum() {

        TelephonyManager tm = (TelephonyManager) CommonData.getInstance().getContext()
                .getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission")
        String phoneId = tm.getLine1Number();
        return phoneId;
    }


    public String getImei() {

        if (VERSION.SDK_INT >= VERSION_CODES.Q) {
            return "";
        }
        if (!PermissionUtils.getInstance().checkPermission(Manifest.permission.READ_PHONE_STATE)) {
            return "";
        }

        TelephonyManager manager = (TelephonyManager) CommonData.getInstance().getContext().getSystemService(Context.TELEPHONY_SERVICE);
        try {
            Method method = manager.getClass().getMethod("getImei", int.class);
            String imei1 = (String) method.invoke(manager, 0);
            String imei2 = (String) method.invoke(manager, 1);
            if (TextUtils.isEmpty(imei2)) {
                return imei1;
            }
            if (!TextUtils.isEmpty(imei1)) {
                //因为手机卡插在不同位置，获取到的imei1和imei2值会交换，所以取它们的最小值,保证拿到的imei都是同一个
                String imei = "";
                if (imei1.compareTo(imei2) <= 0) {
                    imei = imei1;
                } else {
                    imei = imei2;
                }
                return imei;
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                return manager.getDeviceId();
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
        return "";
    }


    public String getImei1() {
        if (VERSION.SDK_INT >= VERSION_CODES.Q) {
            return "";
        }
        if (!PermissionUtils.getInstance().checkPermission(Manifest.permission.READ_PHONE_STATE)) {
            return "";
        }
        TelephonyManager manager = (TelephonyManager) CommonData.getInstance().getContext().getSystemService(Context.TELEPHONY_SERVICE);
        try {
            Method method = manager.getClass().getMethod("getImei", int.class);
            String imei = (String) method.invoke(manager, 0);
            return imei;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                return manager.getDeviceId();
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
        return "";
    }


    public String getImei2() {
        if (VERSION.SDK_INT >= VERSION_CODES.Q) {
            return "";
        }

        if (!PermissionUtils.getInstance().checkPermission(Manifest.permission.READ_PHONE_STATE)) {
            return "";
        }

        TelephonyManager manager = (TelephonyManager) CommonData.getInstance().getContext().getSystemService(Context.TELEPHONY_SERVICE);
        try {
            Method method = manager.getClass().getMethod("getImei", int.class);
            String imei = (String) method.invoke(manager, 1);
            return imei;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                return manager.getDeviceId();
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
        return "";
    }


    public String getAndroidId() {
        String ANDROID_ID = Settings.System.getString(
                CommonData.getInstance().getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        return ANDROID_ID;
    }

    /**
     * 默认的MAC
     */
    private static final String marshmallowMacAddress = "02:00:00:00:00:00";

    /**
     * MAC 位置
     */
    private static final String fileAddressMac = "/sys/class/net/wlan0/address";


    public String getMac() {
        try {
            WifiManager wifiMan = (WifiManager) CommonData.getInstance().getContext().getApplicationContext()
                    .getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInf = wifiMan.getConnectionInfo();

            if (wifiInf != null
                    && marshmallowMacAddress.equals(wifiInf.getMacAddress())) {
                String result = null;
                try {
                    result = getAdressMacByInterface();
                    if (result != null) {
                        return result;
                    } else {
                        result = getAddressMacByFile(wifiMan);
                        return result;
                    }
                } catch (IOException e) {
                    Log.e("MobileAccess", "Erreur lecture propriete Adresse MAC");
                } catch (Exception e) {
                    Log.e("MobileAcces", "Erreur lecture propriete Adresse MAC ");
                }
            } else {
                if (wifiInf != null && wifiInf.getMacAddress() != null) {
                    return wifiInf.getMacAddress();
                } else {
                    return "";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return marshmallowMacAddress;
    }

    @SuppressLint("NewApi")
    private static String getAdressMacByInterface() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface
                    .getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (nif.getName().equalsIgnoreCase("wlan0")) {
                    byte[] macBytes = nif.getHardwareAddress();
                    if (macBytes == null) {
                        return null;
                    }

                    StringBuilder res1 = new StringBuilder();
                    for (byte b : macBytes) {
                        res1.append(String.format("%02X:", b));
                    }

                    if (res1.length() > 0) {
                        res1.deleteCharAt(res1.length() - 1);
                    }
                    return res1.toString();
                }
            }

        } catch (Exception e) {
            Log.e("MobileAcces", "Erreur lecture propriete Adresse MAC ");
        }
        return null;
    }

    private static String getAddressMacByFile(WifiManager wifiMan)
            throws Exception {
        String ret;
        int wifiState = wifiMan.getWifiState();

        wifiMan.setWifiEnabled(true);
        File fl = new File(fileAddressMac);
        FileInputStream fin = new FileInputStream(fl);
        ret = crunchifyGetStringFromStream(fin);
        fin.close();

        boolean enabled = WifiManager.WIFI_STATE_ENABLED == wifiState;
        wifiMan.setWifiEnabled(enabled);
        return ret;
    }

    private static String crunchifyGetStringFromStream(
            InputStream crunchifyStream) throws IOException {
        if (crunchifyStream != null) {
            Writer crunchifyWriter = new StringWriter();

            char[] crunchifyBuffer = new char[2048];
            try {
                Reader crunchifyReader = new BufferedReader(
                        new InputStreamReader(crunchifyStream, "UTF-8"));
                int counter;
                while ((counter = crunchifyReader.read(crunchifyBuffer)) != -1) {
                    crunchifyWriter.write(crunchifyBuffer, 0, counter);
                }
            } finally {
                crunchifyStream.close();
            }
            return crunchifyWriter.toString();
        } else {
            return "No Contents";
        }
    }


    public String getIpV6() {
        return IPv6AddressUtils.getInstance().getIpv6AddrString();
    }


    public List<Map<String, String>> getAppList() {
        List<Map<String, String>> list = new ArrayList<>();
        List<PackageInfo> packages = CommonData.getInstance().getContext().getPackageManager().getInstalledPackages(0);

        for (int j = 0; j < packages.size(); j++) {
            PackageInfo packageInfo = packages.get(j);
            // 显示非系统软件
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                String appName = packageInfo.applicationInfo.loadLabel(CommonData.getInstance().getContext().getPackageManager()).toString();
                String packageName = packageInfo.packageName;
                Drawable appIcon = packageInfo.applicationInfo.loadIcon(CommonData.getInstance().getContext().getPackageManager()).getCurrent();

                Map<String, String> map = new HashMap<>();
                map.put(packageName, appName);
                list.add(map);
            }
        }
        return list;
    }


    public List<Map<String, String>> getRunningProcess() {

        List<Map<String, String>> list = new ArrayList<>();
        PackageManager pm = CommonData.getInstance().getContext().getApplicationContext().getPackageManager();
        List<ApplicationInfo> applications = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);

        ActivityManager activityManager = (ActivityManager) CommonData.getInstance().getContext().getSystemService(Context.ACTIVITY_SERVICE);
        // 获取正在运行的应用
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo ra : runningAppProcesses) {
            String processName = ra.processName;
            for (ApplicationInfo applicationInfo : applications) {
                if (processName.equals(applicationInfo.processName)) {
                    String appName = applicationInfo.loadLabel(CommonData.getInstance().getContext().getPackageManager()).toString();
                    String packageName = applicationInfo.packageName;
                    Drawable appIcon = applicationInfo.loadIcon(CommonData.getInstance().getContext().getPackageManager()).getCurrent();
                    HashMap<String, String> stringStringHashMap = new HashMap<>();
                    stringStringHashMap.put(packageName, appName);
                    list.add(stringStringHashMap);
                }
            }
        }
        return list;
    }


    public String getCpuName() {
        String str1 = "/proc/cpuinfo";
        String str2 = "";

        try {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr);
            while ((str2 = localBufferedReader.readLine()) != null) {
                if (str2.contains("Hardware")) {
                    return str2.split(":")[1];
                }
            }
            localBufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String printResolution() {
        DisplayMetrics dm = CommonData.getInstance().getContext().getResources().getDisplayMetrics();
        int height = dm.heightPixels;
        int width = dm.widthPixels;
        int sw = CommonData.getInstance().getContext().getResources().getConfiguration().smallestScreenWidthDp;
        return width + "x" + height;
    }


    public String getCpuAbi() {
        String cpuAbi = CPU_ABI;
        return cpuAbi;
    }


    public String getProduct() {
        String product = PRODUCT;
        return product;
    }


    public String getOsType() {
        return "Android";
    }


    public String getOSVersion() {
        String version = VERSION.CODENAME;
        return version;
    }


    public String getBrand() {
        String brand = BRAND;
        return brand;
    }


    public String getSSID() {
        WifiManager wm = (WifiManager) CommonData.getInstance().getContext().getApplicationContext().getSystemService(WIFI_SERVICE);
        if (wm != null) {
            WifiInfo winfo = wm.getConnectionInfo();
            if (winfo != null) {
                String s = winfo.getSSID();
                if (s.length() > 2 && s.charAt(0) == '"' && s.charAt(s.length() - 1) == '"') {
                    return s.substring(1, s.length() - 1);
                }
            }
        }
        return null;
    }


    public String getCurrentLanguage() {
        Locale locale = CommonData.getInstance().getContext().getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        String country = locale.getCountry();
        String lc = language + "_" + country;
        return lc;
    }


    public String getCurrentTimeZone() {
        TimeZone tz = TimeZone.getDefault();
        String strTz = tz.getDisplayName(false, TimeZone.SHORT);
        return strTz;
    }


    public String getProcessName() {

        return ProcessUtils.getCurrentProcessName(CommonData.getInstance().getContext());
//        BufferedReader reader = null;
//        try {
//            reader = new BufferedReader(new FileReader("/proc/" + android.os.Process.myPid() + "/cmdline"));
//            String processName = reader.readLine();
//            if (!TextUtils.isEmpty(processName)) {
//                processName = processName.trim();
//            }
//            return processName;
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        } finally {
//            try {
//                if (reader != null) {
//                    reader.close();
//                }
//            } catch (IOException exception) {
//                exception.printStackTrace();
//            }
//        }
//        return null;
    }


    public String getIMSI() {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) CommonData.getInstance().getContext().getSystemService(Context.TELEPHONY_SERVICE);
            //获取IMSI号
            String imsi = telephonyManager.getSubscriberId();
            if (null == imsi) {
                imsi = "";
            }
            return imsi;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    public String getUa() {
        String userAgent = "";
        if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                userAgent = WebSettings.getDefaultUserAgent(CommonData.getInstance().getContext());
            } catch (Exception e) {
                userAgent = System.getProperty("http.agent");
            }
        } else {
            userAgent = System.getProperty("http.agent");
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
