package com.android.framework.jc;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import okhttp3.HttpUrl;
import okhttp3.internal.Util;
import retrofit2.Retrofit;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/7/17 10:31
 * @describe 网络请求
 * @update
 */
public final class NetworkManager {
    private final LinkedHashMap<HttpUrl, Retrofit> mRetrofitMaps;
    private final HashMap<Object, CompositeDisposable> mDisposableMap;
    private final static String HTTP="http:";
    private final static String HTTPS="https:";
    private final Retrofit.Builder mRetrofitBuild;

    private NetworkManager() {
        mRetrofitMaps = new LinkedHashMap<>();
        mDisposableMap = new HashMap<>();
        mRetrofitBuild= JcFramework.getInstance().getFrameworkConfig().getRetrofitBuilder();

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
            return Objects.requireNonNull(mRetrofitMaps.get(httpUrl)).create(service);
        }
        Retrofit retrofit;
        synchronized (NetworkManager.class) {
            retrofit = mRetrofitBuild.baseUrl(url).build();
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
    public void clearDispose(Object tag) {
        if (mDisposableMap.containsKey(tag)) {
            CompositeDisposable compositeDisposable = mDisposableMap.remove(tag);
            if (compositeDisposable != null) {
                compositeDisposable.clear();
            }
        }
    }

    /**
     * 添加网络请求,可以用{@link #clearDispose(Object)}移除请求
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
     void clearAllDisposable() {
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




}
