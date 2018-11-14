package com.android.framework.jc.data.bean;

import com.android.framework.jc.FkUrlManager;
import com.android.framework.jc.module.IModule;

import java.util.HashMap;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/9/12 20:25
 * @describe 框架的配置
 * @update
 */
public class FrameworkConfig {
    /**
     * 自定义OkHttpClient
     */
    private OkHttpClient customOkHttpClient;
    /**
     * 自定义Retrofit
     */
    private Retrofit customRetrofit;
    /**
     * 地址适配器
     */
    private FkUrlManager.IAdapter urlAdapter;
    /**
     * 通用组件（插件）
     */
    private HashMap<Integer, Class<? extends IModule>> normalMsgMap;

    public OkHttpClient getCustomOkHttpClient()  {
        return customOkHttpClient;
    }

    public void setCustomOkHttpClient(OkHttpClient customOkHttpClient) {
        this.customOkHttpClient = customOkHttpClient;
    }

    public Retrofit getCustomRetrofit() {
        return customRetrofit;
    }

    public void setCustomRetrofit(Retrofit customRetrofit) {
        this.customRetrofit = customRetrofit;
    }

    public FkUrlManager.IAdapter getUrlAdapter() {
        return urlAdapter;
    }

    public void setUrlAdapter(FkUrlManager.IAdapter urlAdapter) {
        this.urlAdapter = urlAdapter;
    }

    public HashMap<Integer, Class<? extends IModule>> getNormalMsgMap() {
        return normalMsgMap;
    }

    public void setNormalMsgMap(HashMap<Integer, Class<? extends IModule>> normalMsgMap) {
        this.normalMsgMap = normalMsgMap;
    }

    @Override
    public String toString() {
        return "FrameworkConfig{" +
                "customOkHttpClient=" + customOkHttpClient +
                ", customRetrofit=" + customRetrofit +
                ", urlAdapter=" + urlAdapter +
                ", normalMsgMap=" + normalMsgMap +
                '}';
    }
}
