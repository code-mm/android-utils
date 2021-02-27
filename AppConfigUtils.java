package com.shuangyangad.sdk.mta.utils;


public class AppConfigUtils {


    private static volatile AppConfigUtils instance;

    public static synchronized AppConfigUtils getInstance() {
        if (instance == null) {
            instance = new AppConfigUtils();
        }
        return instance;
    }

    private static final String CLIENT_ID_KEY_NAME = "com.shuangyandad.client_id";
    private static final String CLIENT_SECRET_KEY_NAME = "com.shuangyandad.client_secret";
    private static final String SERVER_KEY_NAME = "com.shuangyandad.server";
    private static final String CHANNEL_NAME_KEY_NAME = "com.shuangyandad.channel_name";
    private static final String CHANNEL_ID_KEY_NAME = "com.shuangyandad.channel_id";
    private static final String CHANNEL_CODE_KEY_NAME = "com.shuangyandad.channel_code";
    private static final String WECHAT_APP_ID = "com.shuangyandad.wechat_appid";
    private static final String WECHAT_APP_SECRET = "com.shuangyandad.wechat_secret";

    private static final String TOPON_APP_ID = "com.shuangyandad.topon_appid";
    private static final String TOPON_APP_KEY = "com.shuangyandad.topon_appkey";
    private static final String RANGERSAPPLOG_APPID = "com.shuangyandad.rangersapplog_appid";
    private static final String RANGERSAPPLOG_CHANNEL = "com.shuangyandad.rangersapplog_channel";
    private static final String TTAD_APPID = "com.shuangyandad.ttad_appid";
    private static final String TTAD_M_APPID = "com.shuangyandad.ttad_m_appid";


    public AppConfigUtils() {
        super();
    }


    public String getClientId() {
        return ApkUtils.getInstance().getMeta(CLIENT_ID_KEY_NAME);
    }


    public String getClientSecret() {
        return ApkUtils.getInstance().getMeta(CLIENT_SECRET_KEY_NAME);
    }


    public String getServer() {
        String server = ApkUtils.getInstance().getMeta(SERVER_KEY_NAME);
        if (server.endsWith("/")) {
            return server;
        }
        server += "/";
        return server;
    }


    public String getChannelName() {
        return ApkUtils.getInstance().getMeta(CHANNEL_NAME_KEY_NAME);
    }


    public String getChannelId() {
        return ApkUtils.getInstance().getMeta(CHANNEL_ID_KEY_NAME);
    }


    public String getWeChatAppId() {
        return ApkUtils.getInstance().getMeta(WECHAT_APP_ID);
    }


    public String getWeChatAppSecret() {
        return ApkUtils.getInstance().getMeta(WECHAT_APP_SECRET);
    }


    public String getRangersAppLogAppId() {
        return ApkUtils.getInstance().getMeta(RANGERSAPPLOG_APPID);
    }


    public String getRangersAppLogChannel() {
        return ApkUtils.getInstance().getMeta(RANGERSAPPLOG_CHANNEL);
    }


    public String getTopOnAppId() {
        return ApkUtils.getInstance().getMeta(TOPON_APP_ID);
    }


    public String getTopOnAppKey() {
        return ApkUtils.getInstance().getMeta(TOPON_APP_KEY);
    }


    public String getTTAdAppId() {
        return ApkUtils.getInstance().getMeta(TTAD_APPID);
    }


    public String getTTAdMAppId() {
        return ApkUtils.getInstance().getMetaInteger(TTAD_M_APPID);
    }


    public String getChannelCode() {
        return ApkUtils.getInstance().getMetaString(CHANNEL_CODE_KEY_NAME);
    }
}
