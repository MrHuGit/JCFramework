package com.android.jc_framework;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.widget.Toast;

import com.android.jc_framework.exception.RepeatInitializeException;
import com.android.jc_framework.utils.AppUtils;

/**
 * @author Mr.Hu(Jc)
 * @create 2018/3/9 14:04
 * @describe
 * @update
 */

public class JcFrameworkInitialize {
    private boolean hasInitialize = false;
    private Application mApplication;
    private Activity mTopActivity;

    private JcFrameworkInitialize() {
    }

    /**
     * 获取单例
     * @return
     */
    public static JcFrameworkInitialize getInstance() {
        return Holder.sInstance;
    }

    /**
     * 初始化
     */
    public void initialize(Application application) {
        this.initialize(application,false);
    }

    /**
     * 初始化
     *
     * @param application
     */
    public void initialize(Application application, final boolean runBackgroundTips) {
        if (AppUtils.checkMainProcess(application)) {
            if (hasInitialize) {
                throw new RepeatInitializeException(JcFrameworkInitialize.class);
            }
            hasInitialize = true;
            mApplication = application;
            mApplication.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
                @Override
                public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

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
                    if (runBackgroundTips) {
                        boolean runInBackground = AppUtils.checkAppRunInBackground(activity);
                        if (!runInBackground) {
                            Toast.makeText(activity, AppUtils.getAppName(activity)+"应用在后台运行，请注意！", Toast.LENGTH_LONG).show();
                        }
                    }

                }

                @Override
                public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

                }

                @Override
                public void onActivityDestroyed(Activity activity) {

                }
            });
        }
    }


    /**
     * 获取全局Application
     *
     * @return
     */
    public Application getApplication() {
        return mApplication;
    }

    public Activity getTopActivity() {
        return mTopActivity;
    }


    /**
     * 内部类单例创建
     */
    private static class Holder {
        private static JcFrameworkInitialize sInstance = new JcFrameworkInitialize();
    }


}
