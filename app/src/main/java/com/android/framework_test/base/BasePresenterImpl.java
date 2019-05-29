package com.android.framework_test.base;

import com.android.framework.jc.base.mvp.BaseFkPresenter;

import javax.inject.Inject;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2019-05-29 14:10
 * @describe
 * @update
 */
public class BasePresenterImpl extends BaseFkPresenter<IBaseContract.IView> implements IBaseContract.IPresenter {

    @Inject
    public BasePresenterImpl(IBaseContract.IView view) {
        super(view);
    }
}
