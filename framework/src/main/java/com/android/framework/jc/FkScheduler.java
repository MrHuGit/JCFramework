package com.android.framework.jc;

import android.os.Looper;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/10/27 17:04
 * @describe 线程调度工具
 * @update
 */
public class FkScheduler {

    /**
     * 运行在主线程
     *
     * @param runnable
     *         执行方法
     *
     * @return disposable
     */
    public static Disposable runOnUiThread(@NonNull Runnable runnable) {
        return runOnDelay(runnable, 0);
    }

    /**
     * 运行在子线程
     *
     * @param runnable
     *         执行方法
     *
     * @return disposable
     */
    public static Disposable runOnIoThread(@NonNull Runnable runnable) {
        return runOnDelay(Schedulers.io(), runnable, 0, TimeUnit.MILLISECONDS);
    }

    /**
     * 主线程延时执行
     *
     * @param runnable
     *         执行方法
     * @param delayTime
     *         延时时间（ms）
     *
     * @return disposable
     */
    public static Disposable runOnDelay(@NonNull Runnable runnable, long delayTime) {
        return runOnDelay(runnable, delayTime, TimeUnit.MILLISECONDS);
    }

    /**
     * 主线程延时执行
     *
     * @param runnable
     *         执行方法
     * @param delayTime
     *         延时时间
     * @param timeUnit
     *         时间单位
     *
     * @return disposable
     */
    public static Disposable runOnDelay(@NonNull Runnable runnable, long delayTime, @NonNull TimeUnit timeUnit) {
        return runOnDelay(AndroidSchedulers.mainThread(), runnable, delayTime, timeUnit);
    }

    /**
     * 延时执行
     *
     * @param scheduler
     *         指定执行线程
     * @param runnable
     *         执行方法
     * @param delayTime
     *         延时时间
     * @param timeUnit
     *         时间单位
     *
     * @return disposable
     */
    public static Disposable runOnDelay(@NonNull Scheduler scheduler, @NonNull Runnable runnable, long delayTime, @NonNull TimeUnit timeUnit) {
        return Flowable.just(runnable)
                .delay(delayTime, timeUnit)
                .observeOn(scheduler)
                .subscribe(Runnable::run, Throwable::printStackTrace);
    }

    /**
     * 循环执行
     *
     * @param runnable
     *         执行方法
     * @param period
     *         循环时间(ms)
     *
     * @return disposable
     */
    public static Disposable runOnLoop(@NonNull Runnable runnable, long period) {
        return runOnLoop(AndroidSchedulers.mainThread(), runnable, 0, period, TimeUnit.MILLISECONDS);
    }

    /**
     * 循环执行
     *
     * @param runnable
     *         执行方法
     * @param initialDelay
     *         循环执行首次开始延时时间
     * @param period
     *         循环时间(ms)
     *
     * @return disposable
     */
    public static Disposable runOnLoop(@NonNull Runnable runnable, long period, long initialDelay) {
        return runOnLoop(AndroidSchedulers.mainThread(), runnable, initialDelay, period, TimeUnit.MILLISECONDS);
    }

    /**
     * 循环执行
     *
     * @param scheduler
     *         指定执行线程
     * @param runnable
     *         执行方法
     * @param initialDelay
     *         循环执行首次开始延时时间
     * @param period
     *         循环时间
     * @param unit
     *         时间单位
     *
     * @return disposable
     */
    public static Disposable runOnLoop(@NonNull Scheduler scheduler, @NonNull Runnable runnable, long initialDelay, long period, @NonNull TimeUnit unit) {
        return Flowable.interval(initialDelay, period, unit)
                .observeOn(scheduler)
                .subscribe(aLong -> runnable.run(), Throwable::printStackTrace);
    }


    /**
     * 检测当前是否是主线程
     *
     * @return 是否是主线程
     */
    public static boolean checkMainThread() {
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }
}
