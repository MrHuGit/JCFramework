package com.android.framework_test.tinker;

import android.content.Context;

import com.tencent.tinker.lib.reporter.DefaultPatchReporter;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2019/5/16 18:11
 * @describe 补丁包加载时候的监听
 * @update
 */
public class TinkerPatchReporter extends DefaultPatchReporter {
    public TinkerPatchReporter(Context context) {
        super(context);
    }
}
