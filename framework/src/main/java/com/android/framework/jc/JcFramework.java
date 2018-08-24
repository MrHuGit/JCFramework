package com.android.framework.jc;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.android.framework.jc.exception.RepeatInitializeException;
import com.android.framework.jc.util.AppUtils;

import java.util.LinkedList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/7/16 15:53
 * @describe
 * @update
 */
public class JcFramework {
    private Application mApplication;
    private Activity mTopActivity;
    private final LinkedList<Activity> mActivityList;

    private final Handler mHandler;

    private JcFramework() {
        mActivityList = new LinkedList<>();
        mHandler = new Handler();
    }

    private static class Holder {
        @SuppressLint("StaticFieldLeak")
        private static JcFramework INSTANCE = new JcFramework();
    }

    public static JcFramework getInstance() {
        return Holder.INSTANCE;
    }

    private void initialize(Application application) {
        if (!AppUtils.checkMainProcess(application)) {
            return;
        }
        if (mApplication != null) {
            throw new RepeatInitializeException(JcFramework.class);
        }
        this.mApplication = application;
        ConfigManager.getInstance().parseXml(application);
        this.mApplication.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                mActivityList.add(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                mTopActivity = activity;
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                mActivityList.remove(activity);
            }
        });
    }

    public Application getApplication() {
        return mApplication;
    }

    public Activity getTopActivity() {
        return mTopActivity;
    }

    public Handler getHandler() {
        return mHandler;
    }

    public static void runOnMainThread(Runnable runnable) {
        JcFramework framework = getInstance();
        if (runnable != null && framework != null) {
            framework.mHandler.post(runnable);
        }
    }

    /**
     * 退出app
     */
    public static void exitApp() {
        JcFramework framework = getInstance();
        NetworkManager.getInstance().clearDisposable();
        final List<Activity> list = framework.mActivityList;
        for (Activity activity : list) {
            activity.finish();
        }
        list.clear();
        ModuleManager.getInstance().clear();
        framework.mApplication.onTerminate();
    }

    /**
     * 初始化
     *
     * @param application
     *         application
     *
     * @return
     */
    public static JcFramework init(Application application) {
        JcFramework framework = getInstance();
        framework.initialize(application);
        return framework;
    }

    /**
     * 设置自定义okHttpClient
     *
     * @param okHttpClient
     *         自定义
     */
    public JcFramework setCustomOkHttpClient(@NonNull OkHttpClient okHttpClient) {
        NetworkManager.getInstance().initCustomOkHttp(okHttpClient);
        return this;
    }

    /**
     * 设置自定义retrofit
     *
     * @param retrofit
     *         自定义
     */
    public JcFramework setCustomRetrofit(@NonNull Retrofit retrofit) {
        NetworkManager.getInstance().initCustomRetrofit(retrofit);
        return this;
    }
}
