package com.android.framework.jc.message.plugs;

import com.android.framework.jc.FkCacheManager;
import com.android.framework.jc.message.IMessageCallback;

import org.json.JSONObject;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/9/12 15:44
 * @describe 保存数据到数据库
 * @update
 */
public class Plug10002 extends BasePlug {

    @Override
    void disposeMessage(JSONObject messageJson, IMessageCallback callback) {
        String key = messageJson.optString("key");
        String value = messageJson.optString("value");
        FkCacheManager.getInstance().saveToDatabase(key, value);
        IMessageCallback.onResultSuccess(callback);
    }
}
