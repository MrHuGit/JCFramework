package com.android.framework.jc.message.plugs;

import com.android.framework.jc.FkCacheManager;
import com.android.framework.jc.message.IMessageCallback;
import com.android.framework.jc.message.body.MessageBody;

import org.json.JSONObject;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/9/12 15:44
 * @describe 保存数据到内存
 * @update
 */
public class Plug10000 extends BasePlug {
    @Override
    void disposeMessage(JSONObject messageJson, IMessageCallback callback) {
        String key = messageJson.optString("key");
        String value = messageJson.optString("value");
        FkCacheManager.getInstance().saveToMemory(key, value);
        IMessageCallback.onResultSuccess(callback);
    }
}
