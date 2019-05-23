package com.android.framework.jc.js;

import android.webkit.JavascriptInterface;

import com.android.framework.jc.MessageManager;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/9/12 19:49
 * @describe 原生-js交互接口
 * @update
 */
public class FkJsInterface {
    private final FkWebView mWebView;

    public FkJsInterface(FkWebView fkWebView) {
        this.mWebView = fkWebView;
    }

    @JavascriptInterface
    public void sendMessage(String message) {
        MessageManager.getInstance().getMessageAdapter().onReceiveJsMessage(mWebView, message);
    }
}
