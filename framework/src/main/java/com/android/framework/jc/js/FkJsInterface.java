package com.android.framework.jc.js;

import android.os.Build;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;

import com.android.framework.jc.ModuleManager;
import com.android.framework.jc.module.IMessageCallback;
import com.android.framework.jc.module.MessageType;
import com.android.framework.jc.module.body.MessageBody;
import com.android.framework.jc.util.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/9/12 19:49
 * @describe
 * @update
 */
public class FkJsInterface {
    private final static Class TAG=FkJsInterface.class;
    private final FkWebView mWebView;

    public FkJsInterface(FkWebView fkWebView) {
        this.mWebView= fkWebView;
    }

    @JavascriptInterface
    public void sendMessage(String message) {
        LogUtils.i(TAG,"H5 sendMessage:" + message);
        JSONObject resultJson=new JSONObject();
        if (TextUtils.isEmpty(message)) {
            IMessageCallback.Result result=new IMessageCallback.Result();
            result.setSuccess(false);
            result.setResultMessage("发送消息不能为空");
            sendResultToJS(mWebView,null,result);
        } else {
            try {
                JSONObject messageJson = new JSONObject(message);
                int msgId = messageJson.optInt("msgId");
                String targetModuleName = messageJson.optString("targetModuleName");
                String comeFromModuleName = messageJson.optString("comeFromModuleName");
                JSONObject content = messageJson.optJSONObject("params");
                MessageBody messageBody = new MessageBody(msgId, content);
                messageBody.setComeFromModuleName(comeFromModuleName);
                if (!TextUtils.isEmpty(targetModuleName)) {
                    messageBody.setTargetModuleName(targetModuleName);
                }
                messageBody.setCallback(result -> sendResultToJS(mWebView,messageJson,result));
                MessageType messageType;
                if (ModuleManager.getInstance().checkPlugMessage(messageBody)) {
                    messageType=MessageType.PLUG;
                } else {
                    messageType=MessageType.APP;
                }
                ModuleManager.getInstance().sendMessage(messageType,messageBody);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            LogUtils.i(TAG,"return H5:" + resultJson.toString());

        }
    }


    private static void sendResultToJS(FkWebView fkWebView,JSONObject messageJson,IMessageCallback.Result result){
        if (messageJson==null){
            messageJson=new JSONObject();
        }
        JSONObject resultJson=new JSONObject();
        if (result!=null){
            try {
                resultJson.put("isSuccess",result.isSuccess()?"1":"0");
                resultJson.put("resultMessage",result.getResultMessage());
                resultJson.put("content",result.getContentJson());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        try {
            messageJson.put("result",resultJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String message = messageJson.toString();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            fkWebView.evaluateJavascript("javascript:onAppMessage(" + message + ")", value -> {
                //此处为 js 返回的结果
                LogUtils.i(TAG, "onReceiveValue：value" + value);
            });
        } else {
            fkWebView.loadUrl("javascript:onAppMessage(" + message + ")");
        }
    }
}
