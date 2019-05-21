package com.android.framework.jc.js;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.framework.jc.js.listener.IPageStartedListener;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/9/13 16:33
 * @describe
 * @update
 */
public class FkWebViewClient extends WebViewClient {
    private FkWebViewProgressBar mProgressBar;

    private IPageStartedListener mPageStartedListener;

    public FkWebViewClient() {

    }

    public FkWebViewClient(FkWebViewProgressBar progressBar) {
        this.mProgressBar = progressBar;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
    }


    @Override
    public void onPageStarted(WebView webView, String url, Bitmap favicon) {
        super.onPageStarted(webView, url, favicon);
        if (mPageStartedListener != null) {
            mPageStartedListener.onPageStarted(webView, url, favicon);
        }
        if (mProgressBar!=null){
            mProgressBar.onProgressStart();
        }
    }

    void setOnPageStartedListener(IPageStartedListener listener) {
        this.mPageStartedListener = listener;
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        handler.proceed();
        super.onReceivedSslError(view, handler, error);
    }
}
