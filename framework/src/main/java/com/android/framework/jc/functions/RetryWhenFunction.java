package com.android.framework.jc.functions;

import org.reactivestreams.Publisher;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/11/1 15:35
 * @describe retry操作符封装
 * @update
 */
public class RetryWhenFunction<T extends Flowable<Throwable>> implements Function<T, Publisher<?>> {
    private long mDelay = 3000;
    private int mRetryCount = 3;

    public RetryWhenFunction() {

    }

    public RetryWhenFunction(int retryCount, long delay) {
        mRetryCount = retryCount;
        mDelay = delay;
    }

    @Override
    public Publisher<?> apply(T t) {
        return t.zipWith(Flowable.range(1, mRetryCount + 1), (throwable, integer) -> new Wrapper(integer, throwable))
                .flatMap((Function<Wrapper, Publisher<?>>) wrapper -> {
                    boolean needRetry = wrapper.throwable instanceof ConnectException;
                    needRetry |= wrapper.throwable instanceof SocketTimeoutException;
                    needRetry |= wrapper.throwable instanceof TimeoutException;
                    needRetry &= wrapper.index < mRetryCount + 1;
                    if (needRetry) {
                        return Flowable.timer(mDelay, TimeUnit.MILLISECONDS);

                    }
                    return Flowable.error(wrapper.throwable);
                });
    }


    private final static class Wrapper {
        private int index;
        private Throwable throwable;

        private Wrapper(int index, Throwable throwable) {
            this.index = index;
            this.throwable = throwable;
        }
    }
}
