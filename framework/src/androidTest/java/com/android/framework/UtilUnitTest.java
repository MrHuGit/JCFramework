package com.android.framework;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.android.framework.jc.JcFramework;
import com.android.framework.jc.util.FormatUtils;
import com.android.framework.jc.util.ScreenUtils;
import com.android.framework.jc.util.ToastUtils;

import org.junit.Test;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/7/17 13:32
 * @describe
 * @update
 */
public class UtilUnitTest {
    @Test
    public void parseUtilTest(){
        System.out.println(FormatUtils.parseTime("33333333333333"));
        System.out.println(FormatUtils.parseDouble("33333333333333","#.#"));
        System.out.println(FormatUtils.parseDouble("33333333333333","#0.00"));
        System.out.println(FormatUtils.parseTime("33333333333333","yyyy-MM-dd"));
    }

    @Test
    public void toastUtilTest(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        JcFramework.runOnMainThread(() -> ToastUtils.toast(appContext,"测试Toast"));

    }

    @Test
    public void screenUtilTest(){
        System.out.println(ScreenUtils.getScreenHeight());
        System.out.println(ScreenUtils.getScreenWidth());
        System.out.println(ScreenUtils.getStatusHeight());
    }
}
