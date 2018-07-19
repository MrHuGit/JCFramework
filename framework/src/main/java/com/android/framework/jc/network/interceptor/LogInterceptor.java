package com.android.framework.jc.network.interceptor;

import android.support.annotation.NonNull;

import com.android.framework.jc.util.LogUtils;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/7/18 15:48
 * @describe log拦截器
 * @update
 */
public class LogInterceptor implements Interceptor {
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        // 拦截请求，获取到该次请求的request
        Request request = chain.request();
        long t1 = System.nanoTime();
        // 执行本次网络请求操作，返回response信息 耗时
        Response response = chain.proceed(request);
        LogUtils.d(LogInterceptor.class,"Request{ \n"+request.toString()+"\n"+request.headers().toString()+"\n }");
        long t2 = System.nanoTime();
        LogUtils.d(LogInterceptor.class,String.format(Locale.getDefault(), "Received {%n%s Time consuming %.1fms%n%s%n}",
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));
        ResponseBody responseBody = response.body();
        if (responseBody != null) {
            MediaType mediaType = responseBody.contentType();
            String content = responseBody.string();
            LogUtils.d(LogInterceptor.class,"Response{ \n "+content+"\n }");
            return response.newBuilder()
                    .body(ResponseBody.create(mediaType, content))
                    .build();
        }
        return response;
    }
}
