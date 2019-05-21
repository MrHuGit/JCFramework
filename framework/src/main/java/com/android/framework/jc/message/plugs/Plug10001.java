package com.android.framework.jc.message.plugs;

import android.text.TextUtils;

import com.android.framework.jc.FkCacheManager;
import com.android.framework.jc.message.IMessageCallback;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/9/12 15:44
 * @describe 通过key从内存获取数据
 * @update
 */
public class Plug10001 extends BasePlug {
    @Override
    void disposeMessage(JSONObject messageJson, IMessageCallback callback) {
        String key = messageJson.optString("key");
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
        IMessageCallback.onResultSuccess(callback,result);
    }
}
