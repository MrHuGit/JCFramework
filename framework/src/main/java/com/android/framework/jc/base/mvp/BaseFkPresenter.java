package com.android.framework.jc.base.mvp;

import java.lang.ref.WeakReference;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/9/19 18:25
 * @describe Presenter基类实现
 * @update
 */
public class BaseFkPresenter<V extends IFkContract.IView> implements IFkContract.IPresenter<V> {
    private WeakReference<V> viewWeakReference;

    @Override
    public void onAttachView(V view) {
        viewWeakReference = new WeakReference<>(view);
    }

    @Override
    public void onDetachView() {
        if (viewWeakReference != null) {
            viewWeakReference.clear();
        }
    }

    protected boolean checkView() {
        return getView() != null;
    }


    protected V getView() {
        return viewWeakReference != null ? viewWeakReference.get() : null;
    }


}
