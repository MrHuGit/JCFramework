package com.android.framework_test.tinker;

import android.app.Application;
import android.content.Intent;

import com.android.framework.jc.IFramework;
import com.android.framework.jc.JcFramework;
import com.android.framework.jc.util.LogUtils;
import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.entry.DefaultApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2019/5/16 17:30
 * @describe
 * @update
 */
@DefaultLifeCycle(application = "com.android.framework_test.MineApplication",
        flags = ShareConstants.TINKER_ENABLE_ALL,
        loadVerifyFlag = false)
public class TinkerApplicationLike extends DefaultApplicationLike {
    private final Application mApplication;

    public TinkerApplicationLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag,
                                 long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
        this.mApplication = application;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        JcFramework.init(mApplication, () -> IFramework.FrameworkConfig.builder()
                .setAppLifecycleListener(isAppShow -> LogUtils.i(TinkerApplicationLike.class, "isAppShow->" + isAppShow))
                .build());
        // 初始化TinkerPatch SDK, 更多配置可参照API章节中的,初始化 SDK
        TinkerManager.installTinker(this, true);

    }
}
