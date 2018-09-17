package com.android.framework.jc.module;


import com.android.framework.jc.module.body.MessageBody;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/9/13 15:32
 * @describe
 * @update
 */
public interface IModule {
    /**
     * 消息接收
     *
     * @param message
     *         消息
     */
    void onMessageReceive(MessageBody message);



}
