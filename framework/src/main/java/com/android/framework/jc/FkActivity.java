package com.android.framework.jc;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.android.framework.jc.util.ToastUtils;

import io.reactivex.disposables.Disposable;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/7/16 18:20
 * @describe
 * @update
 */
public class FkActivity extends AppCompatActivity {


    protected void toast(String message) {
        ToastUtils.toast(this, message);
    }

    protected void toast(@StringRes int messageRes) {
        ToastUtils.toast(this, messageRes);
    }

    protected void toast(Throwable throwable) {
        ToastUtils.toast(this, throwable);
    }

    protected <T extends Fragment> T findFragment(Class<T> tClass) {
        Fragment oldFragment = getSupportFragmentManager().findFragmentById(R.id.frame_layout);
        T t = null;
        if (oldFragment != null) {
            if (oldFragment.getClass().getName().equals(tClass.getName())) {
                try {
                    t = (T) oldFragment;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                getSupportFragmentManager().beginTransaction().remove(oldFragment).commit();
            }


        }
        return t;
    }

    protected void addFragment(Fragment fragment) {
        setContentView(R.layout.activity_fk_framework);
        getSupportFragmentManager().beginTransaction().add(R.id.frame_layout, fragment).commit();
    }

    protected void addDispose(@NonNull Disposable disposable) {
        NetworkManager.getInstance().addDispose(this, disposable);
    }

    @Override
    protected void onDestroy() {
        NetworkManager.getInstance().dispose(this);
        super.onDestroy();
    }
}
