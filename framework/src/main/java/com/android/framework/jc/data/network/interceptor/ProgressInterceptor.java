package com.android.framework.jc.data.network.interceptor;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Timeout;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/5/14 08:54
 * @describe 进度拦截器（下载时用）
 * @update
 */
public class ProgressInterceptor implements Interceptor {

    private final ProgressListener mListener;

    public ProgressInterceptor(ProgressListener listener) {
        this.mListener = listener;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        ProgressResponseBody responseBody = new ProgressResponseBody(response.body(), this.mListener);
        return response.newBuilder().body(responseBody).build();
    }

    private class ProgressResponseBody extends ResponseBody {
        private final ProgressListener mListener;
        private final ResponseBody mResponseBody;
        private BufferedSource mBufferedSource;

        private ProgressResponseBody(ResponseBody responseBody, ProgressListener listener) {
            this.mListener = listener;
            this.mResponseBody = responseBody;
        }

        @Override
        public MediaType contentType() {
            return this.mResponseBody.contentType();
        }

        @Override
        public long contentLength() {
            return this.mResponseBody.contentLength();
        }

        @Override
        public BufferedSource source() {
            if (mBufferedSource == null) {
                mBufferedSource = createBufferedSource();
            }
            return mBufferedSource;
        }

        private BufferedSource createBufferedSource() {
            return Okio.buffer(new ForwardingSource(mResponseBody.source()) {
                private int mTotalBytesRead;

                @Override
                public long read(@NonNull Buffer sink, long byteCount) throws IOException {
                    long bytesRead = super.read(sink, byteCount);
                    mTotalBytesRead += bytesRead != -1 ? bytesRead : 0;
                    mListener.onProgress(mTotalBytesRead, mResponseBody.contentLength(), bytesRead, bytesRead == -1);
                    return bytesRead;
                }

                @Override
                public Timeout timeout() {
                    return super.timeout();
                }

                @Override
                public void close() throws IOException {
                    super.close();
                }

                @NonNull
                @Override
                public String toString() {
                    return super.toString();
                }
            });
        }
    }


    public interface ProgressListener {
        /**
         * 进度监听
         *
         * @param progress
         *         已经下载或上传字节数
         * @param total
         *         总字节数
         * @param bytesRead
         *         此次下载大小
         * @param done
         *         是否完成
         */
        void onProgress(long progress, long total, long bytesRead, boolean done);
    }
}


