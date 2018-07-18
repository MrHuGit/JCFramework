package com.android.framework.jc;

import android.Manifest;
import android.app.Activity;
import android.support.annotation.NonNull;

import com.android.framework.jc.network.FkJsonConverterFactory;
import com.android.framework.jc.util.FileUtils;
import com.android.framework.jc.util.FormatUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/7/17 10:31
 * @describe 网络请求
 * @update
 */
public class NetworkManager {
    private final static String CONNECT_TIMEOUT_KEY = "okHttpConnectTimeout";
    private final static String READ_TIMEOUT_KEY = "okHttpReadTimeout";
    private final static String WRITE_TIMEOUT_KEY = "okHttpWriteTimeout";
    private final Retrofit.Builder mDefaultRetrofitBuild;
    private final LinkedHashMap<HttpUrl, Retrofit> mRetrofitMaps;
    private final HashMap<Object, CompositeDisposable> mDisposableMap;
    private Retrofit mCustomRetrofit;

    private NetworkManager() {
        mRetrofitMaps = new LinkedHashMap<>();
        mDisposableMap = new HashMap<>();
        final long connectTimeout = FormatUtils.parseLong(ConfigManager.getInstance().getValue(CONNECT_TIMEOUT_KEY));
        final long readTimeout = FormatUtils.parseLong(ConfigManager.getInstance().getValue(READ_TIMEOUT_KEY));
        final long writeTimeout = FormatUtils.parseLong(ConfigManager.getInstance().getValue(WRITE_TIMEOUT_KEY));
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(connectTimeout <= 0 ? 10000 : connectTimeout, TimeUnit.MILLISECONDS)
                .readTimeout(readTimeout <= 0 ? 10000 : readTimeout, TimeUnit.MILLISECONDS)
                .writeTimeout(writeTimeout <= 0 ? 10000 : writeTimeout, TimeUnit.MILLISECONDS)
                .build();
        mDefaultRetrofitBuild = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(FkJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

    }

    /**
     * 根据url创建service
     *
     * @param url
     *         url
     * @param service
     *         需要创建的service
     * @param <T>
     *         泛型
     *
     * @return service
     */
    public <T> T createService(@NonNull String url, @NonNull final Class<T> service) {
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
     * 下载文件
     *
     * @param url
     *         下载文件的url
     * @param savePath
     *         保存文件的路径
     * @param listener
     *         监听
     */
    public void downloadFile(String url, String savePath, Listener listener) {

        Activity activity = JcFramework.getInstance().getTopActivity();
        FkPermission.with(activity)
                .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .listener(new FkPermission.IPermissionListener() {
                    @Override
                    public void onGrant() {
                        Disposable disposable = download(activity, url, savePath, listener);
                        addDispose(url, disposable);

                    }

                    @Override
                    public void onRationale(String[] permissions) {

                    }

                    @Override
                    public void onDenied(String[] permissions) {

                    }
                }).request();

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

    private Disposable download(Activity activity, String url, String savePath, Listener listener) {
        return createService(url, Service.class)
                .downloadFile(url)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(ResponseBody::byteStream)
                .observeOn(Schedulers.computation())
                .doOnNext(inputStream -> FileUtils.write(inputStream, savePath))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(inputStream -> {
                    if (listener != null) {
                        listener.onFinish();
                    }
                }, throwable -> {
                    if (listener != null) {
                        listener.onError(throwable);
                    }
                });

    }

    /**
     * 清除所有请求
     */
    protected void clearDisposable() {
        for (CompositeDisposable compositeDisposable : mDisposableMap.values()) {
            compositeDisposable.clear();
        }
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

    private interface Service {
        /**
         * 下载文件
         *
         * @param url
         *         下载文件的url
         *
         * @return flowable
         */
        @GET
        Flowable<ResponseBody> downloadFile(@Url String url);
    }

    public interface Listener {


        /**
         * 下载进度监听
         *
         * @param currentLength
         *         当前已经下载好的长度
         * @param totalLength
         *         需要下载文件的总长度
         * @param bytesRead
         *         当前读取的长度
         */
        void onProgress(long currentLength, long totalLength, long bytesRead);

        /**
         * 下载异常
         *
         * @param throwable
         *         异常
         */
        void onError(Throwable throwable);

        /**
         * 下载完成
         */
        void onFinish();

    }
}
