package com.android.framework_test.base;

import com.android.framework.jc.base.mvp.IFkContract;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/11/8 19:18
 * @describe
 * @update
 */
public interface IBaseContract {

    interface IPresenter<V extends IBaseContract.IView> extends IFkContract.IPresenter<V> {

    }

    interface IView<P extends IBaseContract.IPresenter> extends IFkContract.IView<P> {

    }
}
