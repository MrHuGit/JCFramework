package com.android.framework.jc.base;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;

import com.android.framework.jc.ConfigManager;
import com.android.framework.jc.R;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/7/16 18:20
 * @describe
 * @update
 */
public class FkActivity extends AppCompatActivity {

    private Bundle savedInstanceState;

    @CallSuper
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        if (ConfigManager.getInstance().getBooleanValue("restartAfterRecover")){
            AppStateManager.getInstance().checkAppState(this);
        }
    }

    protected <T extends FkFragment> T findFragment(Class<T> tClass) {
        T t = null;
        if (this.savedInstanceState != null) {
            Fragment oldFragment = getSupportFragmentManager().findFragmentById(R.id.content);
            if (oldFragment != null) {
                try {
                    //noinspection unchecked
                    t = (T) oldFragment;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return t;
    }

    /**
     * 关闭系统键盘
     */
    protected void closeKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    protected void putFragment(FkFragment fragment) {
        if (fragment == null) {
            return;
        }
        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content, fragment)
                .commit();
    }

    @Override
    protected void onDestroy() {
        closeKeyboard();
        super.onDestroy();
    }
}
