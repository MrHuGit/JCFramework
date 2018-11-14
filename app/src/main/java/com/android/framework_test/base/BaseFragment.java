package com.android.framework_test.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.framework.jc.base.FkFragment;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/11/8 19:23
 * @describe
 * @update
 */
public abstract class BaseFragment<P extends IBaseContract.IPresenter> extends FkFragment<P> implements IBaseContract.IView<P> {
//    @Override
//    protected View onCreateRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return null;
//    }



}
