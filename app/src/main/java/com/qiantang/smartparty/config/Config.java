package com.qiantang.smartparty.config;

import com.orhanobut.logger.LogLevel;
import com.qiantang.smartparty.MyApplication;

public class Config {

    static {
//        System.loadLibrary("somicAuth-lib");
    }

    /**
     * 0是不接入其他平台更新策略
     * 1 是接入360自动更新
     */
    public static int PLATEID = 0;
    public static int RECOMMENDTOP = 2;
    public static int RECOMMENDBOTTOM = 2;

    public static final String HTML_HOST = ".qtcem.cn";
    public static int TabColorBarHeight = MyApplication.heightPixels / 340;
    public static int BadgeViewSize = 8 * 1080 / MyApplication.widthPixels;

    public static final int SPLASH_TIME = 500;

    public static final String PLATE_ID = "plateId";
    public static final String DETAIL_ID = "detailId";
    public static final String PLATE_NAME = "plateName";
    public static final String KEYWORD = "keyword";
    public static final String USER_ID = "userId";
    public static final int PIC_REQUEST = 10;
    public static final int PUBLISH_REQUEST = 101;
    public static final String PRODUCTORDER = "productOrder";
    public static final int MIEN_TYPE = 1; //党建风采搜索
    public static final int PARTY_ACTIVITY = 2;//党建活动
    public static final int SPEECH_STUDY = 3;//系列讲话
    public static final boolean isLoadMore=false;

    public static String[] getAuthKey() {
        return new String[]{"wxcf7500ef8e821ee4", "58d211ef47461b1d08c75ca3bb30bbfe", "101445135", "1f2753cdb7cd8b8de261d78c73908ca3  ", "", "", "", "", "http://api.esomic.com/weibo/callback.do"};
    }


    private static String[] authKey = getAuthKey();

    /**
     * 第三方平台密钥
     */

    public static final String WX_APP_ID = authKey[0];
    public static final String WX_APP_SECRET = authKey[1];
    public static final String QQ_APP_ID = authKey[2];
    public static final String QQ_APP_SECRET = authKey[3];
    public static final String SINA_APP_ID = authKey[4];
    public static final String SINA_APP_SECRET = authKey[5];
    public static final String LINJU_APP_KEY = authKey[6];
    public static final String SINA_REDICT_URL = authKey[7];

    public static final String LOG_TAG = "QIANTANG";
    public static final String SERVER_HOST;
    public static final String IMAGE_HOST;
    public static final String HTTP_MALL_ROOT_URL;
    public static final String HTTP_BBS_ROOT_URL;
    public static final String HTTP_CONTENT_TYPE = "Content-Type";
    public static final String HTTP_CONTENT_TYPE_VALUE = "json";
    public static final long HTTP_READ_TIMEOUT_MILLIS = 20 * 1000;//20s

    private static final String APP_VERSION_CODE = "0";
    private static final String APP_VERSION_TYPE = "com.qiantang.smartparty";

    public static final String APP_VERSION = "Laimi-Client-Version";
    public static final String APP_VERSION_NAME = APP_VERSION_TYPE + ":" + APP_VERSION_CODE;

    public static final LogLevel LOG_LEVEL;
    /**
     * 环境配置
     */
    private static final Environment ENVIRONMENT = Environment.DEVELOP;


    static {
        switch (ENVIRONMENT) {
            case DEVELOP: //开发环境
                SERVER_HOST = "http://zhdj.qtcem.cn/";
                HTTP_BBS_ROOT_URL = "";
                HTTP_MALL_ROOT_URL = "";
                IMAGE_HOST = "http://zhdj.qtcem.cn/imgfile/";
                LOG_LEVEL = LogLevel.FULL;
                break;
            case ONLINE: //生产环境
            default:
                SERVER_HOST = "http://zhdj.qtcem.cn/";
                HTTP_BBS_ROOT_URL = "http://172.16.88.158:8092/bbs/";
                HTTP_MALL_ROOT_URL = "http://172.16.88.158:8092/mall/";
                IMAGE_HOST = "http://zhdj.qtcem.cn/imgfile/";
                LOG_LEVEL = LogLevel.NONE;
                break;
        }
    }

    private enum Environment {DEVELOP, ONLINE}

    public static boolean isIsOnline() {
        return ENVIRONMENT.equals(Environment.ONLINE);
    }
}
