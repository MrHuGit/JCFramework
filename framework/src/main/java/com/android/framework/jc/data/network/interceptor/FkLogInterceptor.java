package com.android.framework.jc.data.network.interceptor;

import android.support.annotation.NonNull;

import com.android.framework.jc.util.LogUtils;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import okio.BufferedSource;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/7/18 15:48
 * @describe log拦截器
 * @update
 */
public class FkLogInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        RequestBody requestBody = request.body();
        boolean hasRequestBody = requestBody != null;
        //打印请求体
        Connection connection = chain.connection();
        String requestStartMessage = "--> "
                + request.method()
                + ' ' + request.url()
                + (connection != null ? " " + connection.protocol() : "");
        if (hasRequestBody) {
            requestStartMessage += " (" + requestBody.contentLength() + "-byte body)";
        }
        LogUtils.i(FkLogInterceptor.class, requestStartMessage);
        long startNs = System.nanoTime();
        StringBuilder responseMsgSb = new StringBuilder();
        Response response;
        //网络请求失败
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            responseMsgSb.append("<-- ")
                    .append(request.method())
                    .append(' ')
                    .append(request.url())
                    .append(" HTTP FAILED:")
                    .append(e.toString());
            LogUtils.i(FkLogInterceptor.class, responseMsgSb.toString());
            throw e;
        }
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
        ResponseBody responseBody = response.body();
        //返回结果为空
        if (responseBody == null) {
            String errorTips = "responseBody is null!";
            responseMsgSb.append("<-- ")
                    .append(request.method())
                    .append(' ')
                    .append(request.url())
                    .append(" HTTP FAILED:")
                    .append(errorTips);
            LogUtils.i(FkLogInterceptor.class, responseMsgSb.toString());
            throw new NullPointerException(errorTips);
        }
        long contentLength = responseBody.contentLength();
        responseMsgSb.append("\n")
                .append(response.code())
                .append(response.message().isEmpty() ? "" : ' ' + response.message())
                .append(' ')
                .append(response.request().url())
                .append(" (")
                .append(tookMs)
                .append("ms")
                .append(')');
        Headers headers = response.headers();
        for (int i = 0, count = headers.size(); i < count; i++) {
            responseMsgSb.append("\n")
                    .append("<-- ")
                    .append(headers.name(i))
                    .append(": ")
                    .append(headers.value(i));
        }
        if (!HttpHeaders.hasBody(response)) {
            responseMsgSb.append("\n").append("<-- END HTTP");
        } else if (bodyEncoded(response.headers())) {
            responseMsgSb.append("\n").append("<-- END HTTP (encoded body omitted)");
        } else {
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE);
            Buffer buffer = source.buffer();
            Charset charset = UTF8;
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }
            if (!isPlaintext(buffer)) {
                responseMsgSb.append("\n")
                        .append("<-- END HTTP (binary ")
                        .append(buffer.size())
                        .append("-byte body omitted)");
                LogUtils.i(FkLogInterceptor.class, responseMsgSb.toString());
                return response;
            }
            if (contentLength != 0 && charset != null) {
                responseMsgSb.append("\n").append("<-- ").append(buffer.clone().readString(charset));
            }
            responseMsgSb.append("\n").append("<-- END HTTP (").append(buffer.size()).append("-byte body)");
        }
        LogUtils.i(FkLogInterceptor.class, responseMsgSb.toString());
        return response;
    }


    private static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !"identity".equalsIgnoreCase(contentEncoding);
    }
}
