package com.android.framework.jc.base.mvp;

import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;

import com.android.framework.jc.JcFramework;

import java.lang.ref.WeakReference;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/9/19 18:25
 * @describe
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

    static String getResString(@StringRes int stringResId){
        return JcFramework.getInstance().getApplication().getResources().getString(stringResId);
    }

    static int getColor(@ColorRes int colorResId){
        return JcFramework.getInstance().getApplication().getResources().getColor(colorResId);
    }
}
