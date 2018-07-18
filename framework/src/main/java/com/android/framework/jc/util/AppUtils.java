package com.android.framework.jc.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import com.android.framework.jc.JcFramework;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/7/17 13:48
 * @describe App相关工具
 * @update
 */
public class AppUtils {
    /**
     * 是否是主进程
     *
     * @param context
     *         context
     *
     * @return 是否是主进程
     */
    public static boolean checkMainProcess(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am != null) {
            List<ActivityManager.RunningAppProcessInfo> appProcessInfoList = am.getRunningAppProcesses();
            String packageName = context.getPackageName();
            int pid = android.os.Process.myPid();
            Iterator<ActivityManager.RunningAppProcessInfo> it = appProcessInfoList.iterator();
            ActivityManager.RunningAppProcessInfo processInfo;
            do {
                if (!it.hasNext()) {
                    return false;
                }
                processInfo = it.next();
            } while (processInfo.pid != pid || !packageName.equals(processInfo.processName));

        }
        return true;
    }

    /**
     * 安装apk
     *
     * @param apkPath
     *         apk路径
     */
    public static void installApk(String apkPath) {
        if (TextUtils.isEmpty(apkPath)) {
            return;
        }
        Context context = JcFramework.getInstance().getApplication().getApplicationContext();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(getInstallUri(context, apkPath), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
    /**
     * 检测当前App是否运行在后台
     *
     * @param context
     *
     * @return 是否运行在后台
     */
    public static boolean checkAppRunInBackground(Context context) {
        boolean result = false;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String runningActivityPackageName;
        int sdkVersion;
        try {
            sdkVersion = Integer.valueOf(Build.VERSION.SDK);
        } catch (NumberFormatException e) {
            sdkVersion = 0;
        }
        //获取系统api版本号,如果是5x系统就用这个方法获取当前运行的包名
        if (sdkVersion >= Build.VERSION_CODES.LOLLIPOP) {
            runningActivityPackageName = getCurrentPkgName(context);
        } else {
            runningActivityPackageName = activityManager.getRunningTasks(1).get(0).topActivity.getPackageName();
        }
        //有些情况下在5x的手机中可能获取不到当前运行的包名，所以要非空判断
        if (runningActivityPackageName != null) {
            if (runningActivityPackageName.equals(context.getPackageName())) {
                result = true;
            }
        }
        return result;
    }

    /**
     * 获取应用程序名称
     *
     * @param context context
     *
     * @return
     */
    public static String getAppName(Context context) {
        String appName = "";
        PackageInfo packageInfo = PackageUtils.getPackageInfo(context);
        if (packageInfo != null) {
            int labelRes = packageInfo.applicationInfo.labelRes;
            appName = context.getResources().getString(labelRes);
        }
        return appName;
    }

    /**
     * 获取应用程序版本名称信息
     *
     * @param context context
     *
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context) {
        String versionName = "";
        PackageInfo packageInfo = PackageUtils.getPackageInfo(context);
        if (packageInfo != null) {
            versionName = packageInfo.versionName;
        }
        return versionName;
    }

    /**
     * [获取应用程序版本号]
     *
     * @param context
     *
     * @return 当前应用的版本号
     */
    public static int getVersionCode(Context context) {
        int versionCode = 0;
        PackageInfo packageInfo = PackageUtils.getPackageInfo(context);
        if (packageInfo != null) {
            versionCode = packageInfo.versionCode;
        }
        return versionCode;
    }



    /**
     * 5x系统以后利用反射获取当前栈顶activity的包名
     *
     * @param context
     *
     * @return 包名
     */
    private static String getCurrentPkgName(Context context) {
        ActivityManager.RunningAppProcessInfo currentInfo = null;
        Field field = null;
        int startTaskToFront = 2;
        String pkgName = null;

        try {
            field = ActivityManager.RunningAppProcessInfo.class.getDeclaredField("processState");
        } catch (Exception e) {
            e.printStackTrace();
        }
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am != null) {
            List appList = am.getRunningAppProcesses();
            ActivityManager.RunningAppProcessInfo app;

            for (int i = 0; i < appList.size(); i++) {
                app = (ActivityManager.RunningAppProcessInfo) appList.get(i);
                if (app.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    Integer state = null;
                    try {
                        if (field != null) {
                            state = field.getInt(app);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (state != null && state == startTaskToFront) {
                        currentInfo = app;
                        break;
                    }
                }
            }
        }


        if (currentInfo != null) {
            pkgName = currentInfo.processName;
        }
        return pkgName;
    }
    private static Uri getInstallUri(Context context, String filePath) {
        Uri resultUri = null;
        if (context != null && !TextUtils.isEmpty(filePath)) {
            File file = new File(filePath);
            if (file.exists()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    resultUri = FileProvider.getUriForFile(context, context.getPackageName() + ".frameworkFileProvider", file);
                } else {
                    resultUri = Uri.fromFile(file);
                }
            }
        }
        return resultUri;
    }
}
