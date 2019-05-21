package com.android.framework.jc.data.network.interceptor;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2019/1/7 14:18
 * @describe
 * @update
 */
public class FkCacheInterceptor implements Interceptor {
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        //todo 添加缓存策略
        return null;
    }
}
