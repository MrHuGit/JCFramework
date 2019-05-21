package com.android.framework.jc;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.NonNull;

import com.android.framework.jc.exception.RepeatInitializeException;
import com.android.framework.jc.message.MessageManager;
import com.android.framework.jc.message.plugs.BasePlug;
import com.android.framework.jc.util.AppUtils;
import com.android.framework.jc.util.LogUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import io.reactivex.plugins.RxJavaPlugins;

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


    private final static Class TAG = JcFramework.class;
    private IFramework.FrameworkConfig mFrameworkConfig;

    private JcFramework() {
        mActivityList = new LinkedList<>();
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
     * @param frameworkCreate
     *         配置文件创建接口
     */
    private void initialize(Application application, IFramework frameworkCreate) {
        if (!AppUtils.checkMainProcess(application)) {
            return;
        }
        if (mApplication != null) {
            throw new RepeatInitializeException(JcFramework.class);
        }
        this.mApplication = application;
        ConfigManager.getInstance().parseXml(application);
        setFrameworkConfig(frameworkCreate);
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
        setRxJavaErrorHandler();

    }

    /**
     * RxJava2 当取消订阅后(dispose())，RxJava抛出的异常后续无法接收(此时后台线程仍在跑，可能会抛出IO等异常),全部由RxJavaPlugin接收
     */
    private void setRxJavaErrorHandler() {
        RxJavaPlugins.setErrorHandler(throwable -> LogUtils.i(TAG, throwable.toString()));
    }

    public Application getApplication() {
        return mApplication;
    }

    public Activity getTopActivity() {
        return mTopActivity;
    }

    /**
     * 退出app
     */
    public static void exitApp() {
        JcFramework framework = getInstance();
        NetworkManager.getInstance().clearAllDisposable();
        FkDownload.Cache.getInstance().updateAll();
        final List<Activity> list = framework.mActivityList;
        for (Activity activity : list) {
            activity.finish();
        }
        list.clear();
        MessageManager.getInstance().clear();
        framework.mApplication.onTerminate();
        Process.killProcess(Process.myPid());
    }

    /**
     * 初始化
     *
     * @param application
     *         application
     *
     * @return JcFramework
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
     * @return JcFramework
     */
    public static JcFramework init(Application application, IFramework frameworkConfigCreate) {
        JcFramework framework = getInstance();
        framework.initialize(application, frameworkConfigCreate);
        return framework;
    }

    /**
     * 设置用户配置
     *
     * @param frameworkCreate
     *         配置
     */
    private void setFrameworkConfig(IFramework frameworkCreate) {
        if (frameworkCreate != null) {
            mFrameworkConfig = frameworkCreate.createFramework();
        }
        if (mFrameworkConfig == null) {
            mFrameworkConfig = IFramework.FrameworkConfig.builder().build();
        }
        FkUrlManager.IAdapter adapter = mFrameworkConfig.getUrlAdapter();
        if (adapter != null) {
            FkUrlManager.getInstance().setAdapter(adapter);
        }

        Map<Integer, Class<? extends BasePlug>> addPlugsMap = mFrameworkConfig.getAddPlugsMap();
        if (addPlugsMap != null) {
            MessageManager.getInstance().addNormalMessage(addPlugsMap);
        }
        LogUtils.i(TAG, "FrameworkConfig," + mFrameworkConfig);

    }

    @NonNull
    public IFramework.FrameworkConfig getFrameworkConfig() {
        return mFrameworkConfig == null ? mFrameworkConfig = IFramework.FrameworkConfig.builder().build() : mFrameworkConfig;
    }


}
