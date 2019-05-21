package com.android.framework.jc.message.plugs;

import com.android.framework.jc.message.IMessageCallback;
import com.android.framework.jc.message.IModule;
import com.android.framework.jc.message.body.MessageBody;

import org.json.JSONObject;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2019/5/20 17:31
 * @describe
 * @update
 */
public abstract class BasePlug implements IModule {
    @Override
    public boolean onMessageReceive(MessageBody messageBody) {
        JSONObject jsonObject = messageBody.getParamJson();
        disposeMessage(jsonObject,messageBody.getCallback());
        return true;
    }

    /**
     * 处理插件消息
     *
     * @param messageJson
     *         消息内容
     * @param callback
     *         回调
     */
    abstract void disposeMessage(JSONObject messageJson, IMessageCallback callback);

}
