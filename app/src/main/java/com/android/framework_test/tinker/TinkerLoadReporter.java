package com.android.framework_test.tinker;

import android.content.Context;

import com.tencent.tinker.lib.reporter.DefaultLoadReporter;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2019/5/16 18:10
 * @describe Load补丁包时候的监听
 * @update
 */
public class TinkerLoadReporter extends DefaultLoadReporter {

    public TinkerLoadReporter(Context context) {
        super(context);
    }
}
