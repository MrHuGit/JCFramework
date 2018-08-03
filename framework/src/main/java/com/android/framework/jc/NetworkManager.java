package com.android.framework.jc;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.LinkedHashMap;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.internal.Util;
import retrofit2.Retrofit;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/7/17 10:31
 * @describe 网络请求
 * @update
 */
public class NetworkManager {
    private final LinkedHashMap<HttpUrl, Retrofit> mRetrofitMaps;
    private final HashMap<Object, CompositeDisposable> mDisposableMap;
    private final static String HTTP="http:";
    private final static String HTTPS="https:";
    private Retrofit mCustomRetrofit;
    private final Retrofit.Builder mDefaultRetrofitBuild;

    private NetworkManager() {
        mRetrofitMaps = new LinkedHashMap<>();
        mDisposableMap = new HashMap<>();
        mDefaultRetrofitBuild=RetrofitManager.getInstance().getRetrofitBuilder();

    }

    /**
     * 根据url创建service
     *
     * @param url
     *         url or urlName
     * @param service
     *         需要创建的service
     * @param <T>
     *         泛型
     *
     * @return service
     */
    public <T> T createService(@NonNull String url, @NonNull final Class<T> service) {
        int pos = Util.skipLeadingAsciiWhitespace(url, 0, url.length());
        boolean isUrl=url.regionMatches(true, pos, HTTPS, 0, 6)||url.regionMatches(true, pos, HTTP, 0, 5);
        if (!isUrl) {
            url= FkUrlManager.getInstance().getUrl(url);
        }
        HttpUrl httpUrl = HttpUrl.parse(url);
        if (httpUrl == null) {
            throw new IllegalArgumentException("Illegal URL: " + url);
        }
        if (mRetrofitMaps.containsKey(httpUrl)) {
            return mRetrofitMaps.get(httpUrl).create(service);
        }
        Retrofit retrofit;
        synchronized (NetworkManager.class) {
            if (mCustomRetrofit != null) {
                retrofit = mCustomRetrofit.newBuilder().baseUrl(url).build();
            } else {
                retrofit = mDefaultRetrofitBuild.baseUrl(url).build();
            }
            mRetrofitMaps.put(httpUrl, retrofit);
        }
        return retrofit.create(service);
    }




    /**
     * 根据tag移除网络请求
     *
     * @param tag
     *         tag{@link #addDispose(Object, Disposable)}
     */
    public void dispose(Object tag) {
        if (mDisposableMap.containsKey(tag)) {
            CompositeDisposable compositeDisposable = mDisposableMap.remove(tag);
            compositeDisposable.clear();
        }
    }

    /**
     * 添加网络请求,可以用{@link #dispose(Object)}移除请求
     *
     * @param tag
     *         tag
     * @param disposable
     *         disposable
     */
    public void addDispose(Object tag, Disposable disposable) {
        if (tag == null || disposable == null) {
            return;
        }
        CompositeDisposable compositeDisposable = null;
        if (mDisposableMap.containsKey(tag)) {
            compositeDisposable = mDisposableMap.get(tag);
        }
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
            mDisposableMap.put(tag, compositeDisposable);
        }
        compositeDisposable.add(disposable);

    }



    /**
     * 清除所有请求
     */
    protected void clearDisposable() {
        for (CompositeDisposable compositeDisposable : mDisposableMap.values()) {
            compositeDisposable.clear();
        }
        OkHttpManager.getInstance().clearCookie();
        mDisposableMap.clear();
    }

    private static class Holder {
        private final static NetworkManager INSTANCE = new NetworkManager();
    }

    public static NetworkManager getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * 设置自定义okHttpClient
     *
     * @param okHttpClient
     *         自定义
     */
    protected synchronized void initCustomOkHttp(@NonNull OkHttpClient okHttpClient) {
        mDefaultRetrofitBuild.client(okHttpClient);
    }

    /**
     * 设置自定义retrofit
     *
     * @param retrofit
     *         自定义
     */
    protected synchronized void initCustomRetrofit(@NonNull Retrofit retrofit) {
        mRetrofitMaps.put(retrofit.baseUrl(), retrofit);
        mCustomRetrofit = retrofit;
    }


}
