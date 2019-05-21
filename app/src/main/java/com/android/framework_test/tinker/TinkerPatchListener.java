package com.android.framework_test.tinker;

import android.content.Context;

import com.tencent.tinker.lib.listener.DefaultPatchListener;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2019/5/16 18:18
 * @describe
 * @update
 */
public class TinkerPatchListener extends DefaultPatchListener {
    public TinkerPatchListener(Context context) {
        super(context);
//        maxMemory = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
    }

//    protected static final long NEW_PATCH_RESTRICTION_SPACE_SIZE_MIN = 60 * 1024 * 1024;
//    private final int maxMemory;


//
//    /**
//     * because we use the defaultCheckPatchReceived method
//     * the error code define by myself should after {@code ShareConstants.ERROR_RECOVER_INSERVICE
//     *
//     * @param path
//     * @param newPatch
//     * @return
//     */
//    @Override
//    public int patchCheck(String path, String patchMd5) {
//        File patchFile = new File(path);
//        int returnCode = super.patchCheck(path, patchMd5);
//        if (returnCode == ShareConstants.ERROR_PATCH_OK) {
//            returnCode = Utils.checkForPatchRecover(NEW_PATCH_RESTRICTION_SPACE_SIZE_MIN, maxMemory);
//        }
//        if (returnCode == ShareConstants.ERROR_PATCH_OK) {
//            SharedPreferences sp = context.getSharedPreferences(ShareConstants.TINKER_SHARE_PREFERENCE_CONFIG, Context.MODE_MULTI_PROCESS);
//            //optional, only disable this patch file with md5
//            int fastCrashCount = sp.getInt(patchMd5, 0);
//            if (fastCrashCount >= SampleUncaughtExceptionHandler.MAX_CRASH_COUNT) {
//                returnCode = Utils.ERROR_PATCH_CRASH_LIMIT;
//            }
//        }
//        // Warning, it is just a sample case, you don't need to copy all of these
//        // Interception some of the request
//        if (returnCode == ShareConstants.ERROR_PATCH_OK) {
//            Properties properties = ShareTinkerInternals.fastGetPatchPackageMeta(patchFile);
//            if (properties == null) {
//                returnCode = Utils.ERROR_PATCH_CONDITION_NOT_SATISFIED;
//            } else {
//                String platform = properties.getProperty(Utils.PLATFORM);
//                TinkerLog.i(TAG, "get platform:" + platform);
//                // check patch platform require
//                if (platform == null || !platform.equals(BuildInfo.PLATFORM)) {
//                    returnCode = Utils.ERROR_PATCH_CONDITION_NOT_SATISFIED;
//                }
//            }
//        }
//
//        SampleTinkerReport.onTryApply(returnCode == ShareConstants.ERROR_PATCH_OK);
//        return returnCode;
//    }
}
