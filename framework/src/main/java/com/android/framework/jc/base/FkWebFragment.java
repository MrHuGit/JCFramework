package com.android.framework.jc.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;

import com.android.framework.jc.R;
import com.android.framework.jc.js.FkJsInterface;
import com.android.framework.jc.js.FkWebChromeClient;
import com.android.framework.jc.js.FkWebView;
import com.android.framework.jc.message.IModule;
import com.android.framework.jc.message.MessageManager;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/9/13 16:25
 * @describe
 * @update
 */
public abstract class FkWebFragment extends FkFragment implements IModule {
    protected FkWebView mFkWebView;

    @Override
    protected View onCreateRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fk_base_webview,null);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFkWebView = (FkWebView) view.findViewById(R.id.fk_web_view);
        initWebView();

    }


    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView(){
        WebSettings webSettings=mFkWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//        mFkWebView.setWebViewClient(new FkWebViewClient(mFkWebView));
        mFkWebView.setWebChromeClient(new FkWebChromeClient());
        mFkWebView.addJavascriptInterface(new FkJsInterface(mFkWebView),"exx");
        MessageManager.getInstance().register(setWebViewName(),mFkWebView);
    }

    /**
     * 设置webViewName
     * @return webViewName
     */
    protected abstract String setWebViewName();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MessageManager.getInstance().unRegister(mFkWebView);
    }
}
