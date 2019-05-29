package com.android.framework.jc.wrapper;

import android.arch.lifecycle.LifecycleObserver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/3/15 18:28
 * @describe
 * @update
 */

public interface IViewWrapper extends LifecycleObserver {

    /**
     * 动态添加View
     *
     * @param inflater
     *         inflater
     * @param container
     *         container
     * @param savedInstanceState
     *         savedInstanceState
     *
     * @return view
     */
    View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);


}
