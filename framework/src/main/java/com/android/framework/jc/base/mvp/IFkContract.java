package com.android.framework.jc.base.mvp;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/9/19 17:36
 * @describe 基类契约类
 * @update
 */
public interface IFkContract {
    interface IPresenter<V extends IView> {
        /**
         * 关联View
         *
         * @param view
         */
        void onAttachView(V view);

        /**
         * 解除View绑定
         */
        void onDetachView();
    }

    interface IView<P extends IPresenter> {
        /**
         * 关联Presenter
         *
         * @param presenter
         *         presenter
         */
        void setPresenter(P presenter);
    }
}
