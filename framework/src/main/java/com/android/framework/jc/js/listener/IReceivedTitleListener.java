package com.android.framework.jc.js.listener;

import android.webkit.WebView;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/9/13 11:46
 * @describe
 * @update
 */
public interface IReceivedTitleListener {
    /**
     * 接收到H5标题
     *
     * @param webView
     *         webView
     * @param title
     *         标题
     */
    void onReceivedTitle(WebView webView, String title);
}
