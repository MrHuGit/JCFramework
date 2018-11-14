package com.android.framework_test.module.util;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.framework.jc.util.ScreenUtils;
import com.android.framework_test.base.BaseActivity;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/11/9 10:03
 * @describe Screen相关工具
 * @update
 */
public class ScreenUtilsActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("屏幕高度：" + ScreenUtils.getScreenHeight());
        System.out.println("屏幕宽度：" + ScreenUtils.getScreenWidth());
        System.out.println("状态栏高度：" + ScreenUtils.getStatusHeight());
        System.out.println("全屏高度：" + ScreenUtils.getFullScreenHeight());
        System.out.println("虚拟按键高度：" + ScreenUtils.getNavigationBarHeight());
    }
}
