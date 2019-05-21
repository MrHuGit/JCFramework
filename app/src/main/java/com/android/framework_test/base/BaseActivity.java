package com.android.framework_test.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.android.framework.jc.base.FkActivity;
import com.android.framework_test.screen.ScreenShotListenManager;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/11/8 19:16
 * @describe
 * @update
 */
public class BaseActivity extends FkActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenShotListenManager.getInstance().setListener(imagePath -> {
            Toast.makeText(BaseActivity.this,"截图成功", Toast.LENGTH_LONG).show();
        }).startListen(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        ScreenShotListenManager.getInstance().stopListen(this);
    }
}
