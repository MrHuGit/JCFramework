package com.android.framework.jc.widget;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.android.framework.jc.R;
import com.android.framework.jc.js.FkJsInterface;
import com.android.framework.jc.js.FkWebChromeClient;
import com.android.framework.jc.js.FkWebView;
import com.android.framework.jc.js.FkWebViewClient;
import com.android.framework.jc.js.FkWebViewProgressBar;
import com.android.framework.jc.MessageManager;
import com.android.framework.jc.util.ConvertUtils;
import com.android.framework.jc.util.WebViewUtils;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2019/5/20 15:07
 * @describe
 * @update
 */
public class WebViewWrap extends LinearLayout {
    FkWebView mFkWebView;

    public WebViewWrap(Context context) {
        this(context, null);
    }

    public WebViewWrap(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WebViewWrap(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public WebViewWrap(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        //获取自定义属性。
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WebViewWrap);
        Drawable progressDrawable = typedArray.getDrawable(R.styleable.WebViewWrap_webViewProgressDrawable);
        typedArray.recycle();
        setGravity(Gravity.CENTER);
        setOrientation(VERTICAL);
        ProgressBar progressBar = new ProgressBar(context);
        progressBar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, ConvertUtils.dp2px(2f)));
        if (progressDrawable != null) {
            progressBar.setProgressDrawable(progressDrawable);
        }
        FkWebViewProgressBar fkWebViewProgressBar = new FkWebViewProgressBar(progressBar);
        addView(progressBar);
        mFkWebView = new FkWebView(context);
        mFkWebView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(mFkWebView);
        WebViewUtils.initSetting(mFkWebView);
        mFkWebView.setWebChromeClient(new FkWebChromeClient(fkWebViewProgressBar));
        mFkWebView.setWebViewClient(new FkWebViewClient(fkWebViewProgressBar));

    }

    public FkWebView getFkWebView() {
        return this.mFkWebView;
    }

    public void loadUrl(String url) {
        if (!TextUtils.isEmpty(url)) {
            mFkWebView.loadUrl(url);
        }
    }

    @SuppressLint("AddJavascriptInterface")
    public void registerModule(String webViewName, String jsName) {
        if (mFkWebView != null) {
            mFkWebView.addJavascriptInterface(new FkJsInterface(mFkWebView), jsName);
            MessageManager.getInstance().register(webViewName, mFkWebView);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        MessageManager.getInstance().unRegister(mFkWebView);

    }


}
