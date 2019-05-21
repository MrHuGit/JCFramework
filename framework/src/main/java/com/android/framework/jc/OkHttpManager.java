package com.android.framework.jc;

import com.android.framework.jc.data.network.cookie.FkCookieJarImpl;
import com.android.framework.jc.data.network.cookie.ICookieCache;
import com.android.framework.jc.data.network.interceptor.FkCacheInterceptor;
import com.android.framework.jc.data.network.interceptor.FkLogInterceptor;
import com.android.framework.jc.util.NetworkUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/7/18 18:01
 * @describe OkHttp管理类
 * @update
 */
public class OkHttpManager {
    private final static String CONNECT_TIMEOUT_KEY = "okHttpConnectTimeout";
    private final static String READ_TIMEOUT_KEY = "okHttpReadTimeout";
    private final static String WRITE_TIMEOUT_KEY = "okHttpWriteTimeout";
    private final static String CACHE_SIZE = "okHttpCacheSize";
    private final static String IGNORE_SSL = "ignoreSsl";
    private final static String LOG_DEBUG = "logDebug";
    private final static String CACHE_PATH_NAME = "okHttpCache";
    private volatile OkHttpClient mDefaultOkHttpClient;
    private final FkCookieJarImpl mCookieJar;

    private OkHttpManager() {
        ICookieCache cookieCache = JcFramework.getInstance().getFrameworkConfig().getCookieCache();
        mCookieJar = new FkCookieJarImpl(cookieCache);
        final long cacheSize = ConfigManager.getInstance().getLongValue(CACHE_SIZE);
        FkCacheInterceptor cacheInterceptor = new FkCacheInterceptor();
        OkHttpClient.Builder build = getDefaultOkHttpClientBuilder()
                //设置缓存路径及缓存大小，缓存大小默认为10M
//                .cache(new Cache(new File(FileUtils.getCacheDir() + File.separator + CACHE_PATH_NAME), cacheSize <= 0 ? 10 * 1024 * 1024 : cacheSize * 1024 * 1024))
//                .addInterceptor(cacheInterceptor)
//                .addNetworkInterceptor(cacheInterceptor)
                .cookieJar(mCookieJar);
        //根据配置文件的log日志开关判断是否需要添加日志拦截器
        if (ConfigManager.getInstance().getBooleanValue(LOG_DEBUG)) {
            build.addInterceptor(new FkLogInterceptor());
        }
        mDefaultOkHttpClient = build.build();
    }

    private static class Holder {
        private final static OkHttpManager INSTANCE = new OkHttpManager();
    }

    public static OkHttpManager getInstance() {
        return Holder.INSTANCE;
    }


    public  OkHttpClient getOkHttpClient() {
        return ConfigManager.getInstance().getBooleanValue(IGNORE_SSL) ? NetworkUtils.ignoreOkHttpSsl(mDefaultOkHttpClient) : mDefaultOkHttpClient;
    }

    /**
     * 清除cookie
     */
     void clearCookie() {
        mCookieJar.clear();
    }


    static OkHttpClient.Builder getDefaultOkHttpClientBuilder() {
        final long connectTimeout = ConfigManager.getInstance().getLongValue(CONNECT_TIMEOUT_KEY);
        final long readTimeout = ConfigManager.getInstance().getLongValue(READ_TIMEOUT_KEY);
        final long writeTimeout = ConfigManager.getInstance().getLongValue(WRITE_TIMEOUT_KEY);
        return new OkHttpClient.Builder()
                //链接超时时间
                .connectTimeout(connectTimeout <= 0 ? 10000 : connectTimeout, TimeUnit.MILLISECONDS)
                .readTimeout(readTimeout <= 0 ? 10000 : readTimeout, TimeUnit.MILLISECONDS)
                .writeTimeout(writeTimeout <= 0 ? 10000 : writeTimeout, TimeUnit.MILLISECONDS);
    }
}
