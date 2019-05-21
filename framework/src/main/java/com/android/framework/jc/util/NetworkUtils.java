package com.android.framework.jc.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.android.framework.jc.JcFramework;

import java.lang.reflect.Field;
import java.security.SecureRandom;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/7/20 14:02
 * @describe 网络相关工具
 * @update
 */
public class NetworkUtils {

    private static class Holder {
        private final static ConnectivityManager CONNECTIVITY_MANAGER = (ConnectivityManager) JcFramework.getInstance().getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    /**
     * 判断网络状态是否可用的
     *
     * @return 网络是否可用
     */
    public static boolean checkConnect() {
        boolean result = false;
        ConnectivityManager conManager = Holder.CONNECTIVITY_MANAGER;
        if (conManager != null) {
            NetworkInfo network = conManager.getActiveNetworkInfo();
            if (network != null && network.getState() == NetworkInfo.State.CONNECTED) {
                result = true;
            }
        }
        return result;
    }

    /**
     * 判断是否是wifi连接
     */
    public static boolean checkWifi() {
        ConnectivityManager conManager = Holder.CONNECTIVITY_MANAGER;
        return conManager != null && conManager.getActiveNetworkInfo() != null && conManager.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * 判断网络类型
     *
     * @return 网络类型字符串
     */
    public static String getType() {
        ConnectivityManager conManager = Holder.CONNECTIVITY_MANAGER;
        if (conManager != null) {
            NetworkInfo network = conManager.getActiveNetworkInfo();
            if (network == null) {
                return "unknown";
            }
            if (ConnectivityManager.TYPE_WIFI == network.getType()) {
                return "WIFI";
            }
            if (ConnectivityManager.TYPE_MOBILE == network.getType()) {
                switch (network.getSubtype()) {
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                        return "HSUPA";
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                        return "UMTS";
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                        return "GPRS";
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                        return "EDGE";
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                        return "CDMA";
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        return "EVDO_0";
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                        return "HSPA";
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                        return "HSDPA";
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                        return "EVDO_A";
                    default:
                        return "unknown";
                }
            }
        }
        return "unknown";
    }

    /**
     * 打开网络设置界面
     */
    public static void openSetting() {
        Intent intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
        JcFramework.getInstance().getTopActivity().startActivity(intent);
    }


    /**
     * 忽略OkHttp HTTPS证书
     * @param okHttpClient okHttpClient
     * @return okHttpClient
     */
    public static OkHttpClient ignoreOkHttpSsl(OkHttpClient okHttpClient){
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[]{new X509TrustManager() {
                @SuppressLint("TrustAllX509TrustManager")
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                }

                @SuppressLint("TrustAllX509TrustManager")
                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
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
            hostnameVerifier.set(okHttpClient, hv1);

            Field sslSocketFactory = workerClass.getDeclaredField("sslSocketFactory");
            sslSocketFactory.setAccessible(true);
            if (sc != null) {
                sslSocketFactory.set(okHttpClient, sc.getSocketFactory());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return okHttpClient;
    }
}
