package com.android.framework.jc.base.mvp;

import android.arch.lifecycle.LifecycleOwner;

import com.android.framework.jc.NetworkManager;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.Disposable;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/9/19 18:25
 * @describe Presenter基类实现
 * @update
 */
public class BaseFkPresenter<V> implements IFkContract.IPresenter {
    private WeakReference<V> mViewReference;

    public BaseFkPresenter(V view) {
        mViewReference = new WeakReference<>(view);
    }




    protected void addDispose(Disposable disposable) {
        NetworkManager.getInstance().addDispose(this, disposable);
    }

    @Override
    public void onDestroy(LifecycleOwner owner) {
        if (mViewReference != null) {
            mViewReference.clear();
        }
        NetworkManager.getInstance().clearDispose(this);
    }


    /**
     * 检测view是否绑定且未被销毁
     *
     * @return true表示可以继续调用View的方法
     */
    protected boolean checkView() {
        return getView() != null;
    }

    /**
     * 获取view
     *
     * @return view
     */
    protected V getView() {
        return mViewReference == null ? null : mViewReference.get();
    }
}
