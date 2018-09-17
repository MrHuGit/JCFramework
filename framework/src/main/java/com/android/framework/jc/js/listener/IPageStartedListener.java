package com.android.framework.jc.js.listener;

import android.graphics.Bitmap;
import android.webkit.WebView;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/9/17 15:30
 * @describe
 * @update
 */
public interface IPageStartedListener {
    void onPageStarted(WebView view, String url, Bitmap favicon);
}
