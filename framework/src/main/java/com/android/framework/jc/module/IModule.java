package com.android.framework.jc.module;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/7/16 15:20
 * @describe
 * @update
 */
public interface IModule {
    /**
     * 消息接收
     * @param message 消息
     */
    void onMessage(ModuleMessage message);
}
