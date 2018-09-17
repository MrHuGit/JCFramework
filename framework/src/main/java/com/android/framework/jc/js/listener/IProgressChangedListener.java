package com.android.framework.jc.js.listener;

import android.webkit.WebView;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/9/13 16:29
 * @describe
 * @update
 */
public interface IProgressChangedListener {

    /**
     * webView加载进度变化回调此方法
     * @param webView WebView
     * @param newProgress newProgress
     */
    void onProgressChanged(WebView webView, int newProgress);
}
