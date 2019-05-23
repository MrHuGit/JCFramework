package com.android.framework.jc;

import android.os.Build;
import android.text.TextUtils;

import com.android.framework.jc.js.FkWebView;
import com.android.framework.jc.message.IMessageCallback;
import com.android.framework.jc.message.ModuleType;
import com.android.framework.jc.message.body.MessageBody;
import com.android.framework.jc.util.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2019/4/27 16:28
 * @describe 消息处理适配器
 * @update
 */
public interface IMessageAdapter {
    int DEFAULT_RESPONSE_MSG_ID = -1;

    /**
     * 接收到H5的消息{@link Default#onReceiveJsMessage(FkWebView, String)}
     *
     * @param fkWebView
     *         webView
     * @param message
     *         消息
     */
    void onReceiveJsMessage(FkWebView fkWebView, String message);


    /**
     * 消息回调处理{@link Default#createCallbackResult(JSONObject, boolean, String)}
     *
     * @param resultContentJson
     *         回调内容
     * @param isSuccess
     *         是否成功
     * @param failMessage
     *         失败原因
     *
     * @return 回调数据
     */
    JSONObject createCallbackResult(JSONObject resultContentJson, boolean isSuccess, String failMessage);

    /**
     * 分发消息处理{@link Default#dispatchMessage(MessageBody)}
     *
     * @param messageBody
     *         消息体
     */
    void dispatchMessage(MessageBody messageBody);

    /**
     * 发送消息给H5{@link Default#sendMessageToJs(FkWebView, MessageBody)}
     *
     * @param fkWebView
     *         fkWebView
     * @param messageBody
     *         消息内容
     */
    void sendMessageToJs(FkWebView fkWebView, MessageBody messageBody);

    /**
     * 默认的消息适配器
     */
    class Default implements IMessageAdapter {
        @Override
        public void onReceiveJsMessage(FkWebView fkWebView, String message) {
            if (TextUtils.isEmpty(message)) {
                throw new RuntimeException("H5 send message content is empty");
            } else {
                LogUtils.i(IMessageAdapter.class, "onReceiveJsMessage->" + message);
                try {
                    JSONObject messageJson = new JSONObject(message);
                    int msgId = messageJson.optInt("msgId", -1);
                    String targetModuleName = messageJson.optString("targetModuleName", MessageBody.DEFAULT_TARGET_MODULE);
                    String comeFromModuleName = messageJson.optString("comeFromModuleName");
                    String messageTypeValue = messageJson.optString("moduleType", "");
                    ModuleType targetModuleType = ModuleType.getMessageTypeByValue(messageTypeValue);
                    JSONObject paramsJson = messageJson.optJSONObject("params");
                    MessageBody messageBody = MessageBody.builder()
                            .setMessageId(msgId)
                            .setComeFromModuleName(comeFromModuleName)
                            .setTargetModuleName(targetModuleName)
                            .setMessageCallback((isSuccess, resultJson) -> sendJsResult(fkWebView, messageJson, resultJson))
                            .setParamsJson(paramsJson)
                            .setComeFromModuleType(ModuleType.JS)
                            .setTargetModuleType(targetModuleType)
                            .build();
                    MessageManager.getInstance().sendMessage(messageBody);
                } catch (JSONException e) {
                    e.printStackTrace();
                    throw new RuntimeException("H5 send message is not json");
                }
            }
        }

        /**
         * 结果回调给H5
         *
         * @param fkWebView
         *         fkWebView
         * @param comeFromJson
         *         H5发送过来的参数
         * @param resultJson
         *         结果集
         */
        void sendJsResult(FkWebView fkWebView, JSONObject comeFromJson, JSONObject resultJson) {
            if (fkWebView != null && comeFromJson != null) {
                JSONObject jsonObject = new JSONObject();
                int msgId = comeFromJson.optInt("msgId", -1);
                int responseMsgId = comeFromJson.optInt("responseMsgId", DEFAULT_RESPONSE_MSG_ID);
                if (responseMsgId == DEFAULT_RESPONSE_MSG_ID) {
                    responseMsgId = msgId * 10;
                }
                try {
                    jsonObject.put("msgId", responseMsgId);
                    jsonObject.put("requestParams", comeFromJson);
                    jsonObject.put("result", resultJson);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String message = jsonObject.toString();
                sendMessageToJs(fkWebView, message, null);
            }
        }


        @Override
        public JSONObject createCallbackResult(JSONObject resultContentJson, boolean isSuccess, String failMessage) {
            JSONObject resultJson = new JSONObject();
            if (TextUtils.isEmpty(failMessage)) {
                failMessage = "调用失败";
            }
            try {
                resultJson.put("codeMessage", isSuccess ? "调用成功" : failMessage);
                resultJson.put("resultContent", resultContentJson);
                resultJson.put("isSuccess", isSuccess ? "1" : "0");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return resultJson;
        }

        @Override
        public void dispatchMessage(MessageBody messageBody) {
            if (messageBody != null) {
                if (FkScheduler.checkMainThread()) {
                    LogUtils.i(IMessageAdapter.class, "dispatchMessage->" + messageBody);
                    String targetModuleName = messageBody.getTargetModuleName();
                    String failMessage;
                    if (TextUtils.isEmpty(targetModuleName)) {
                        failMessage = "没有注册任何模块去接收当前消息";
                    } else {
                        failMessage = "指定模块" + targetModuleName + "不存在或没有处理对应消息";
                    }

                    ModuleType targetModuleType = messageBody.getTargetModuleType();
                    //没有指定模块类型
                    if (targetModuleType == null) {
                        ModuleType comeFromModuleType = messageBody.getComeFromModuleType();
                        //如果没有指定接收消息的模块类型此处根据来源默认设置
                        //来源是H5此处默认设置接收消息模块类型为APP
                        if (comeFromModuleType == ModuleType.JS) {
                            if (checkIsPlugMessage(messageBody)){
                                if (!MessageManager.getInstance().sendPlugMessage(messageBody)){
                                    IMessageCallback.onResultFail(messageBody.getCallback(), failMessage);
                                }
                            }else if (!MessageManager.getInstance().sendAppMessage(messageBody)) {
                                IMessageCallback.onResultFail(messageBody.getCallback(), failMessage);
                            }
                        }
                        //来源模块类型为APP,此处优先发送消息给H5再发送消息给APP
                        else {
                            if (checkIsPlugMessage(messageBody)){
                                if (!MessageManager.getInstance().sendPlugMessage(messageBody)){
                                    IMessageCallback.onResultFail(messageBody.getCallback(), failMessage);
                                }
                            }else  {
                                boolean jsResult = MessageManager.getInstance().sendJsMessage(messageBody);
                                boolean appResult = MessageManager.getInstance().sendAppMessage(messageBody);
                                if (!(jsResult || appResult )) {
                                    IMessageCallback.onResultFail(messageBody.getCallback(), failMessage);
                                }
                            }
                        }

                    } else {
                        //指定接收消息模块类型
                        switch (targetModuleType) {
                            case JS:
                                if (!MessageManager.getInstance().sendJsMessage(messageBody)) {
                                    IMessageCallback.onResultFail(messageBody.getCallback(), failMessage);
                                }
                                break;
                            case APP:
                                if (!MessageManager.getInstance().sendAppMessage(messageBody)) {
                                    IMessageCallback.onResultFail(messageBody.getCallback(), failMessage);
                                }
                                break;
                            default:
                                break;
                        }
                    }
                } else {
                    FkScheduler.runOnUiThread(() -> dispatchMessage(messageBody));
                }
            }

        }

        boolean checkIsPlugMessage(MessageBody messageBody) {
            return MessageManager.getInstance().checkPlugMessage(messageBody);
        }

        @Override
        public void sendMessageToJs(FkWebView fkWebView, MessageBody messageBody) {
            if (messageBody == null) {
                return;
            }
            if (fkWebView == null) {
                IMessageCallback.onResultFail(messageBody.getCallback(), "fkWebView is null");
            } else {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("msgId", messageBody.getMsgId());
                    jsonObject.put("targetModuleName", messageBody.getTargetModuleName());
                    jsonObject.put("comeFromModuleName", messageBody.getComeFromModuleName());
                    jsonObject.put("params", messageBody.getParamJson());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String message = jsonObject.toString();
                sendMessageToJs(fkWebView, message, messageBody.getCallback());
            }

        }

        /**
         * 发送消息给H5
         *
         * @param fkWebView
         *         fkWebView
         * @param message
         *         消息内容
         * @param callback
         *         回调（如果是回调给H5的消息,不会再走回调,要求H5接收到回调消息不再返回值）
         */
        void sendMessageToJs(FkWebView fkWebView, String message, IMessageCallback callback) {
                LogUtils.i(IMessageAdapter.class, "sendMessageToJs->" + message);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    fkWebView.evaluateJavascript("javascript:onAppMessage(" + message + ")", resultMessage -> {
                        //此处为 js 返回的结果
                        LogUtils.i(IMessageAdapter.class, "onReceiveJsResult->" + resultMessage);
                        if (callback != null) {
                            onReceiveJsResult(callback, resultMessage);
                        }
                    });
                } else {
                    fkWebView.loadUrl("javascript:onAppMessage(" + message + ")");
                }

        }

        void onReceiveJsResult(IMessageCallback callback, String resultMessage) {
            try {
                JSONObject resultJson = new JSONObject(resultMessage);
                IMessageCallback.onResult(callback, resultJson, true, "");
            } catch (JSONException e) {
//                e.printStackTrace();
            }
        }
    }


}
