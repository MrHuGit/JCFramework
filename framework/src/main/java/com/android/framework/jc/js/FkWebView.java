package com.android.framework.jc.js;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.framework.jc.js.listener.IProgressChangedListener;
import com.android.framework.jc.js.listener.IReceivedTitleListener;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/9/12 15:29
 * @describe
 * @update
 */
public class FkWebView extends WebView {
    private FkWebChromeClient mFkWebChromeClient;

    public FkWebView(Context context) {
        super(context);
    }

    public FkWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FkWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public FkWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void setWebChromeClient(WebChromeClient client) {
        if (client instanceof FkWebChromeClient) {
            mFkWebChromeClient = (FkWebChromeClient) client;
        }
        super.setWebChromeClient(client);
    }

    @Override
    public void setWebViewClient(WebViewClient client) {
        super.setWebViewClient(client);
    }

    @SuppressLint({"JavascriptInterface", "AddJavascriptInterface"})
    @Override
    public void addJavascriptInterface(Object object, String name) {
        super.addJavascriptInterface(object, name);
    }

    public void setReceivedTitleListener(IReceivedTitleListener listener) {
        if (mFkWebChromeClient != null) {
            this.mFkWebChromeClient.setReceivedTitleListener(listener);
        }
    }

    public void setProgressChangedListener(IProgressChangedListener listener) {
        if (mFkWebChromeClient != null) {
            this.mFkWebChromeClient.setProgressChangedListener(listener);
        }
    }

}
