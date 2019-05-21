package com.android.framework.jc.message.plugs;

import com.android.framework.jc.FkCacheManager;
import com.android.framework.jc.message.IMessageCallback;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/9/12 15:44
 * @describe 通过key从数据库获取数据
 * @update
 */
public class Plug10003 extends BasePlug {
    @Override
    void disposeMessage(JSONObject messageJson, IMessageCallback callback) {
        String key = messageJson.optString("key");
        JSONObject result = new JSONObject();
        try {
            result.put("value", FkCacheManager.getInstance().getFromDatabase(key));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        IMessageCallback.onResultSuccess(callback, result);
    }
}
