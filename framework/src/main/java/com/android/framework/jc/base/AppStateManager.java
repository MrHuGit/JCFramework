package com.android.framework.jc.base;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/8/3 10:40
 * @describe APP状态管理类（主要用于检测APP的状态，如果APP被系统回收了就重启APP）
 * @update
 */
public class AppStateManager {
    private final static int RUNNING=2;
    private final static int KILLED=-1;
    private int mState=KILLED;
    private AppStateManager() {
    }


    public static AppStateManager getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * 类加载器形式的单例模式
     */
    private static class Holder {
        private static AppStateManager INSTANCE = new AppStateManager();
    }

    /**
     * 设置APP为运行状态（一般要在Launcher设置）
     */
    public void setAppRunningState(){
        this.mState=RUNNING;
    }


    /**
     * 检测APP是否被系统回收
     * @param activity 当前Activity
     */
    public void checkAppState(@NonNull Activity activity){
      if (mState!=RUNNING){
          Intent intent=activity.getBaseContext().getPackageManager().getLaunchIntentForPackage(activity.getBaseContext().getPackageName());
          if (intent!=null){
              intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
              activity.startActivity(intent);
              activity.finish();
          }
      }
    }
}
