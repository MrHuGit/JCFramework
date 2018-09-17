package com.android.framework.jc;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;

import com.android.framework.jc.data.bean.FrameworkConfig;
import com.android.framework.jc.exception.RepeatInitializeException;
import com.android.framework.jc.module.IModule;
import com.android.framework.jc.util.AppUtils;
import com.android.framework.jc.util.LogUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/7/16 15:53
 * @describe 简单功能的封装
 * @update
 */
public class JcFramework {
    private Application mApplication;
    private Activity mTopActivity;
    private final LinkedList<Activity> mActivityList;

    private final Handler mHandler;

    private final static Class TAG=JcFramework.class;
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

    /**
     * 初始化
     *
     * @param application
     *         application
     * @param frameworkConfigCreate
     *         配置文件创建接口
     */
    private void initialize(Application application, FrameworkConfigCreate frameworkConfigCreate) {
        if (!AppUtils.checkMainProcess(application)) {
            return;
        }
        if (mApplication != null) {
            throw new RepeatInitializeException(JcFramework.class);
        }
        this.mApplication = application;
        ConfigManager.getInstance().parseXml(application);

        if (frameworkConfigCreate != null) {
            setFrameworkConfig(frameworkConfigCreate.createFrameworkCreate());
        }
        //内存保存一个平台标识，以备以后H5使用
        FkCacheManager.getInstance().saveToMemory("appPlatform", "2");
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
        Process.killProcess(Process.myPid());
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
        return init(application, null);
    }

    /**
     * 初始化
     *
     * @param application
     *         application
     *
     * @return
     */
    public static JcFramework init(Application application, FrameworkConfigCreate frameworkConfigCreate) {
        JcFramework framework = getInstance();
        framework.initialize(application, frameworkConfigCreate);
        return framework;
    }

    /**
     * 设置用户配置
     *
     * @param frameworkConfig
     *         配置
     */
    private void setFrameworkConfig(FrameworkConfig frameworkConfig) {
        if (frameworkConfig != null) {
            OkHttpClient okHttpClient = frameworkConfig.getCustomOkHttpClient();
            if (okHttpClient != null) {
                NetworkManager.getInstance().initCustomOkHttp(okHttpClient);
            }
            Retrofit retrofit = frameworkConfig.getCustomRetrofit();
            if (retrofit != null) {
                NetworkManager.getInstance().initCustomRetrofit(retrofit);
            }
            FkUrlManager.IAdapter adapter = frameworkConfig.getUrlAdapter();
            if (adapter != null) {
                FkUrlManager.getInstance().setAdapter(adapter);
            }

            HashMap<Integer, Class<? extends IModule>> normalMsgMap = frameworkConfig.getNormalMsgMap();
            if (normalMsgMap != null) {
                ModuleManager.getInstance().addNormalMessage(normalMsgMap);
            }

            LogUtils.i(TAG,"setFrameworkConfig:FrameworkConfig,"+frameworkConfig);

        }
    }

    public interface FrameworkConfigCreate {
        /**
         * 创建框架配置
         *
         * @return 配置
         */
        FrameworkConfig createFrameworkCreate();
    }

}
