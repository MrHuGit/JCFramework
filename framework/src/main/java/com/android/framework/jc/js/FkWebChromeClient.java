package com.android.framework.jc.js;

import android.webkit.JsPromptResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.android.framework.jc.js.listener.IProgressChangedListener;
import com.android.framework.jc.js.listener.IReceivedTitleListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/9/13 11:23
 * @describe
 * @update
 */
public class FkWebChromeClient extends WebChromeClient {
    private FkWebViewProgressBar mProgressBar;
    private IReceivedTitleListener mReceivedTitleListener;
    private IProgressChangedListener mProgressChangedListener;

    public FkWebChromeClient() {

    }

    public FkWebChromeClient(FkWebViewProgressBar progressBar) {
        this.mProgressBar = progressBar;
    }
    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        return super.onJsPrompt(view, url, message, defaultValue, result);
    }

    private synchronized JSONObject dispatchMessage(String action, String message) {
        JSONObject jsonObject = new JSONObject();
        if ("sendMessage".equalsIgnoreCase(action)) {
            try {
                JSONObject messageJson = new JSONObject(message);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return jsonObject;
    }

    @Override
    public void onReceivedTitle(WebView webView, String title) {
        super.onReceivedTitle(webView, title);
        if (mReceivedTitleListener != null) {
            mReceivedTitleListener.onReceivedTitle(webView, title);
        }
    }

    @Override
    public void onProgressChanged(WebView webView, int newProgress) {
        super.onProgressChanged(webView, newProgress);
        if (mProgressChangedListener != null) {
            mProgressChangedListener.onProgressChanged(webView, newProgress);
        }
        if (mProgressBar != null) {
            mProgressBar.onProgressChange(newProgress);
        }
    }

    protected void setReceivedTitleListener(IReceivedTitleListener listener) {
        this.mReceivedTitleListener = listener;
    }

    protected void setProgressChangedListener(IProgressChangedListener listener) {
        this.mProgressChangedListener = listener;
    }
}
