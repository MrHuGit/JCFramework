package com.android.framework.jc.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.android.framework.jc.R;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/7/16 18:20
 * @describe
 * @update
 */
public class FkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppStateManager.getInstance().checkAppState(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        AppStateManager.getInstance().checkAppState(this);
    }

    protected <T extends FkFragment> T findFragment(Class<T> tClass) {
        Fragment oldFragment = getSupportFragmentManager().findFragmentById(R.id.frame_layout);
        T t = null;
        if (oldFragment != null) {
            try {
                //noinspection unchecked
                t = (T) oldFragment;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return t;
    }

    protected void putFragment(FkFragment fragment) {
        if (fragment == null) {
            return;
        }
        setContentView(R.layout.activity_fk_framework);
        String tag = fragment.getFragmentTag();
        if (TextUtils.isEmpty(tag)) {
            tag = fragment.getClass().getCanonicalName();
            fragment.setFragmentTag(tag);
        }
        commitFragment(fragment,tag);
    }

    private void commitFragment(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment, tag).commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
