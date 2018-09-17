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
 * @describe 通过key从数据库获取数据
 * @update
 */
public class Plug10003 implements IModule {

    @Override
    public void onMessageReceive(MessageBody message) {
        JSONObject jsonObject = message.getMessageJson();
        String key = jsonObject.optString("key");
        JSONObject result=new JSONObject();
        try {
            result.put("key",FkCacheManager.getInstance().getFromDatabase(key));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        IMessageCallback.resultSuccess(message.getCallback(),result);

    }
}
