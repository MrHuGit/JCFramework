package com.android.framework.jc.js;

import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/9/13 16:33
 * @describe
 * @update
 */
public class FkWebViewClient extends WebViewClient {


    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
    }


    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
    }
}
