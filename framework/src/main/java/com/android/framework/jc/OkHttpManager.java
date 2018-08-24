package com.android.framework.jc;

import android.annotation.SuppressLint;

import com.android.framework.jc.network.cookie.FkCookieJarImpl;
import com.android.framework.jc.network.cookie.MemoryCookieCache;
import com.android.framework.jc.network.interceptor.LogInterceptor;
import com.android.framework.jc.util.FormatUtils;

import java.lang.reflect.Field;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/7/18 18:01
 * @describe
 * @update
 */
public class OkHttpManager {
    private final static String CONNECT_TIMEOUT_KEY = "okHttpConnectTimeout";
    private final static String READ_TIMEOUT_KEY = "okHttpReadTimeout";
    private final static String WRITE_TIMEOUT_KEY = "okHttpWriteTimeout";
    private final OkHttpClient mDefaultOkHttpClient;
    private final FkCookieJarImpl mCookieJar;

    private OkHttpManager() {
        mCookieJar = new FkCookieJarImpl(new MemoryCookieCache());
        final long connectTimeout = FormatUtils.parseLong(ConfigManager.getInstance().getValue(CONNECT_TIMEOUT_KEY));
        final long readTimeout = FormatUtils.parseLong(ConfigManager.getInstance().getValue(READ_TIMEOUT_KEY));
        final long writeTimeout = FormatUtils.parseLong(ConfigManager.getInstance().getValue(WRITE_TIMEOUT_KEY));
        mDefaultOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(connectTimeout <= 0 ? 10000 : connectTimeout, TimeUnit.MILLISECONDS)
                .readTimeout(readTimeout <= 0 ? 10000 : readTimeout, TimeUnit.MILLISECONDS)
                .writeTimeout(writeTimeout <= 0 ? 10000 : writeTimeout, TimeUnit.MILLISECONDS)
                .addInterceptor(new LogInterceptor())
                .cookieJar(mCookieJar)
                .build();
    }

    private static class Holder {
        private final static OkHttpManager INSTANCE = new OkHttpManager();
    }

    protected static OkHttpManager getInstance() {
        return Holder.INSTANCE;
    }

    protected OkHttpClient getDefaultOkHttpClient() {
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[]{new X509TrustManager() {
                @SuppressLint("TrustAllX509TrustManager")
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {

                }

                @SuppressLint("TrustAllX509TrustManager")
                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {

                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            }}, new SecureRandom());
        } catch (Exception e) {
            e.printStackTrace();
        }

        HostnameVerifier hv1 = (hostname, session) -> true;

        String workerClassName = "okhttp3.OkHttpClient";
        try {
            Class workerClass = Class.forName(workerClassName);
            Field hostnameVerifier = workerClass.getDeclaredField("hostnameVerifier");
            hostnameVerifier.setAccessible(true);
            hostnameVerifier.set(mDefaultOkHttpClient, hv1);

            Field sslSocketFactory = workerClass.getDeclaredField("sslSocketFactory");
            sslSocketFactory.setAccessible(true);
            if (sc != null) {
                sslSocketFactory.set(mDefaultOkHttpClient, sc.getSocketFactory());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mDefaultOkHttpClient;
    }

    protected void clearCookie() {
        mCookieJar.clear();
    }
}
