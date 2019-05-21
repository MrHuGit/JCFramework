package com.android.framework_test.tinker;

import com.tencent.tinker.entry.ApplicationLike;
import com.tencent.tinker.lib.listener.PatchListener;
import com.tencent.tinker.lib.patch.AbstractPatch;
import com.tencent.tinker.lib.patch.UpgradePatch;
import com.tencent.tinker.lib.reporter.LoadReporter;
import com.tencent.tinker.lib.reporter.PatchReporter;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.lib.util.UpgradePatchRetry;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2019/5/16 18:05
 * @describe
 * @update
 */
public class TinkerManager {

    private static ApplicationLike applicationLike;
    private static boolean isInstalled = false;



    public static ApplicationLike getTinkerApplicationLike() {
        return applicationLike;
    }


    /**
     * you can specify all class you want.
     * sometimes, you can only install tinker in some process you want!
     *
     * @param appLike appLike
     */
    public static void installTinker(ApplicationLike appLike, boolean isRetryEnable) {
        if (isInstalled) {
            return;
        }
        applicationLike = appLike;
        UpgradePatchRetry.getInstance(applicationLike.getApplication()).setRetryEnable(isRetryEnable);
        LoadReporter loadReporter = new TinkerLoadReporter(appLike.getApplication());
        PatchReporter patchReporter = new TinkerPatchReporter(appLike.getApplication());
        PatchListener patchListener = new TinkerPatchListener(appLike.getApplication());
        AbstractPatch upgradePatchProcessor = new UpgradePatch();

        TinkerInstaller.install(appLike,
                loadReporter, patchReporter, patchListener,
                JcTinkerResultService.class, upgradePatchProcessor);
        isInstalled = true;
    }
}
