package com.android.framework.jc.base.mvp;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.os.Bundle;
import android.support.annotation.UiThread;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2019-05-29 10:18
 * @describe
 * @update
 */
public interface IFkLifecycle extends LifecycleObserver {
    /**
     * {@link android.support.v4.app.SupportActivity# onCreate(Bundle)}
     * {@link android.support.v4.app.Fragment#onCreate(Bundle)}
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    @UiThread
    default void onCreate(LifecycleOwner owner) {

    }

    /**
     * {@link android.support.v4.app.SupportActivity# onStart()
     * {@link android.support.v4.app.Fragment#onStart()}
     */
    @UiThread
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    default void onStart(LifecycleOwner owner) {

    }

    /**
     * {@link android.support.v4.app.SupportActivity# onResume()}
     * {@link android.support.v4.app.Fragment#onResume()}
     */
    @UiThread
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    default void onResume(LifecycleOwner owner) {

    }

    /**
     * {@link android.support.v4.app.SupportActivity# onPause()}
     * {@link android.support.v4.app.Fragment#onPause()}
     */
    @UiThread
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    default void onPause(LifecycleOwner owner) {

    }

    /**
     * {@link android.support.v4.app.SupportActivity# onStop()}
     * {@link android.support.v4.app.Fragment#onStop()}
     */
    @UiThread
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    default void onStop(LifecycleOwner owner) {

    }

    /**
     * {@link android.support.v4.app.SupportActivity# onDestroy()}
     * {@link android.support.v4.app.Fragment#onDestroy()}
     */
    @UiThread
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    default void onDestroy(LifecycleOwner owner) {

    }
}
