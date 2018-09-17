package com.android.framework.jc.module.normal;

import com.android.framework.jc.FkCacheManager;
import com.android.framework.jc.module.IMessageCallback;
import com.android.framework.jc.module.IModule;
import com.android.framework.jc.module.body.MessageBody;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/9/12 15:44
 * @describe 保存数据到数据库
 * @update
 */
public class Plug10002 implements IModule {


    @Override
    public void onMessageReceive(MessageBody message) {
        JSONObject jsonObject = message.getMessageJson();
        String key = jsonObject.optString("key");
        String value = jsonObject.optString("value");
        FkCacheManager.getInstance().saveToDatabase(key,value);
        IMessageCallback.resultSuccess(message.getCallback());

    }
}
