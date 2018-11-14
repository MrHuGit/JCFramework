package com.android.framework.jc.util;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.android.framework.jc.JcFramework;

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
}
