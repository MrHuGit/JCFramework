package com.android.framework.jc;

import android.support.annotation.NonNull;

import com.android.framework.jc.data.network.cookie.ICookieCache;
import com.android.framework.jc.data.network.cookie.MemoryCookieCache;
import com.android.framework.jc.message.plugs.BasePlug;

import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2019/5/18 10:27
 * @describe
 * @update
 */
public interface IFramework {
    interface AppLifecycleListener {
        /**
         * 当前APP是否可见（运行在前台）
         *
         * @param isAppShow
         *         是否可见（运行在前台）
         */
        void onLifecycle(boolean isAppShow);
    }

    /**
     * 创建框架配置
     *
     * @return 配置
     */
    FrameworkConfig createFramework();

    final class FrameworkConfig {
        private final Builder builder;

        private FrameworkConfig(Builder builder) {
            this.builder = builder;
        }

        public static Builder builder() {
            return new Builder();
        }

        @NonNull
        OkHttpClient getOkHttpClient() {
            return builder.okHttpClient == null ? OkHttpManager.getInstance().getOkHttpClient() : builder.okHttpClient;
        }

        @NonNull
        Retrofit.Builder getRetrofitBuilder() {
            return builder.retrofitBuilder == null ? RetrofitManager.getInstance().getRetrofitBuilder() : builder.retrofitBuilder;
        }

        FkUrlManager.IAdapter getUrlAdapter() {
            return builder.urlAdapter;
        }

        ICookieCache getCookieCache() {
            return builder.cookieCache == null ? new MemoryCookieCache() : builder.cookieCache;
        }

        @NonNull
        IMessageAdapter getMessageAdapter() {
            return builder.messageAdapter == null ? new IMessageAdapter.Default() : builder.messageAdapter;
        }

        Map<Integer, Class<? extends BasePlug>> getAddPlugsMap() {
            return builder.addPlugsMap;
        }

        AppLifecycleListener getAppLifecycleListener() {
            return builder.appLifecycleListener;
        }
    }

    final class Builder {
        /**
         * 自定义OkHttpClient
         */
        private OkHttpClient okHttpClient;
        /**
         * 自定义Retrofit
         */
        private Retrofit.Builder retrofitBuilder;
        /**
         * 地址适配器
         */
        private FkUrlManager.IAdapter urlAdapter;
        /**
         * cookie缓存策略
         */
        private ICookieCache cookieCache;

        /**
         * 消息封装接口
         */
        private IMessageAdapter messageAdapter;
        /**
         * 通用组件（插件）
         */
        private Map<Integer, Class<? extends BasePlug>> addPlugsMap;
        /**
         * APP的生命周期监听
         */
        private IFramework.AppLifecycleListener appLifecycleListener;

        private Builder() {

        }

        public IFramework.Builder setOkHttpClient(OkHttpClient okHttpClient) {
            this.okHttpClient = okHttpClient;
            return this;
        }

        public IFramework.Builder setRetrofitBuilder(Retrofit.Builder retrofitBuilder) {
            this.retrofitBuilder = retrofitBuilder;
            return this;
        }

        public IFramework.Builder setNetworkUrlAdapter(FkUrlManager.IAdapter urlAdapter) {
            this.urlAdapter = urlAdapter;
            return this;
        }

        public IFramework.Builder setCookieCache(ICookieCache cookieCache) {
            this.cookieCache = cookieCache;
            return this;
        }

        public IFramework.Builder setMessageAdapter(IMessageAdapter messageAdapter) {
            this.messageAdapter = messageAdapter;
            return this;
        }

        public IFramework.Builder addPlugsMap(Map<Integer, Class<? extends BasePlug>> addPlugsMap) {
            this.addPlugsMap = addPlugsMap;
            return this;
        }

        public IFramework.Builder setAppLifecycleListener(IFramework.AppLifecycleListener appLifecycleListener) {
            this.appLifecycleListener = appLifecycleListener;
            return this;
        }

        public IFramework.FrameworkConfig build() {
            return new IFramework.FrameworkConfig(this);
        }
    }
}
