package com.android.framework.jc.message;

import org.json.JSONObject;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/9/13 15:54
 * @describe 消息回调接口统一处理
 * @update
 */
public interface IMessageCallback {

    /**
     * 发送消息回调
     *
     * @param isSuccess
     *         是否成功
     * @param resultJson
     *         返回的结果数据
     */
    void onCallback(boolean isSuccess, JSONObject resultJson);


    /**
     * 成功回调
     *
     * @param callback
     *         回调接口
     * @param resultContentJson
     *         成功回调的数据
     */
    static void onResultSuccess(IMessageCallback callback, JSONObject resultContentJson) {
        onResult(callback, resultContentJson, true, "");
    }

    /**
     * 成功回调
     *
     * @param callback
     *         回调接口
     */
    static void onResultSuccess(IMessageCallback callback) {
        onResultSuccess(callback, null);
    }


    /**
     * 失败回调
     *
     * @param callback
     *         回调接口
     */
    static void onResultFail(IMessageCallback callback) {
        onResultFail(callback, "");
    }

    /**
     * 失败回调
     *
     * @param callback
     *         回调接口
     * @param failMessage
     *         失败回调的数据
     */
    static void onResultFail(IMessageCallback callback, String failMessage) {
        onResult(callback, null, false, failMessage);
    }

    /**
     * 消息回调（成功可能有resultContentJson，失败可能要传入失败原因{@link @param failMessage},默认只写调用失败）
     *
     * @param callback
     *         回调接口
     * @param resultContentJson
     *         回调数据
     * @param isSuccess
     *         是否成功
     * @param failMessage
     *         失败的描述
     */
    static void onResult(IMessageCallback callback, JSONObject resultContentJson, boolean isSuccess, String failMessage) {
        if (callback != null) {
            callback.onCallback(isSuccess, MessageManager.getInstance().getMessageAdapter().createCallbackResult(resultContentJson, isSuccess, failMessage));
        }
    }

}
