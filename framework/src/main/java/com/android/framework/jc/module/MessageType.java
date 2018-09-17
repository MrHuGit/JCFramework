package com.android.framework.jc.module;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/9/17 12:41
 * @describe 发送消息的类型
 * @update
 */
public enum MessageType {
    /**
     * 发送消息给JS
     */
    JS,
    /**
     * 发送消息给APP其它模块
     */
    APP,
    /**
     * 调用插件
     */
    PLUG
}
