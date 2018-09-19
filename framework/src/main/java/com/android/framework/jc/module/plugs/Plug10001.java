package com.android.framework.jc.module.plugs;

import android.text.TextUtils;

import com.android.framework.jc.FkCacheManager;
import com.android.framework.jc.module.IMessageCallback;
import com.android.framework.jc.module.IModule;
import com.android.framework.jc.module.body.MessageBody;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/9/12 15:44
 * @describe 通过key从内存获取数据
 * @update
 */
public class Plug10001 implements IModule {
    @Override
    public void onMessageReceive(MessageBody message) {
        JSONObject jsonObject = message.getMessageJson();
        String key = jsonObject.optString("key");
        JSONObject result=new JSONObject();
        String value=FkCacheManager.getInstance().getFromMemory(key);
        if (TextUtils.isEmpty(value)){
            try {
                result.put("value","");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            try {
                JSONObject valueJson=new JSONObject(value);
                result.put("value",valueJson);
            } catch (JSONException e) {
                try {
                    result.put("value",value);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }
        }
        IMessageCallback.resultSuccess(message.getCallback(),result);

    }
}
