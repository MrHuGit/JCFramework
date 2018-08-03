package com.android.framework.jc;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/8/3 10:40
 * @describe
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

    public void setAppRunningState(){
        this.mState=RUNNING;
    }


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
