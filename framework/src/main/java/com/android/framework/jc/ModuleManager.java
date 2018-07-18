package com.android.framework.jc;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.android.framework.jc.module.IModule;
import com.android.framework.jc.module.ModuleMessage;

import java.util.HashMap;

/**
 * @author Mr.Hu(Jc)
 * @create 2018/7/16 15:14
 * @describe
 * @update
 */
public class ModuleManager {
    private final HashMap<String, IModule> mModuleMaps;

    private ModuleManager() {
        mModuleMaps = new HashMap<>();
    }

    private static class Holder {
        private static ModuleManager INSTANCE = new ModuleManager();
    }


    public static ModuleManager getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * 注册模块
     * @param name 模块名称
     * @param module 模块
     */
    public void register(@NonNull String name, @NonNull IModule module) {
        if (TextUtils.isEmpty(name) || mModuleMaps.containsKey(name)) {
            throw new RuntimeException("ModuleManager register error");
        }
        mModuleMaps.put(name, module);
    }

    /**
     * 取消注册
     * @param name 模块名
     */
    public void unRegister(@NonNull String name) {
        if (mModuleMaps.containsKey(name)) {
            mModuleMaps.remove(name);
        }
    }

    /**
     * 发送消息
     * @param moduleMessage 模块消息封装类
     */
    public void sendMessage(@NonNull ModuleMessage moduleMessage) {
        String targetModule = moduleMessage.getTargetModuleName();
        if (ModuleMessage.DEFAULT_TARGET_MODULE.equals(targetModule)) {
            for (IModule module : mModuleMaps.values()) {
                module.onMessage(moduleMessage);
            }
        } else {
            if (mModuleMaps.containsKey(targetModule)) {
                IModule module = mModuleMaps.get(targetModule);
                if (module != null) {
                    module.onMessage(moduleMessage);
                } else {
                    mModuleMaps.remove(targetModule);
                }

            }

        }
    }
}
