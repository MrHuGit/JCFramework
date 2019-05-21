package com.android.framework.jc.message;


import com.android.framework.jc.message.body.MessageBody;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/9/13 15:32
 * @describe
 * @update
 */
public interface IModule {
    /**
     * 消息接收处理方法
     * @param messageBody 消息体
     * @return 是否接收并处理当前消息
     */
    boolean onMessageReceive(MessageBody messageBody);


}
