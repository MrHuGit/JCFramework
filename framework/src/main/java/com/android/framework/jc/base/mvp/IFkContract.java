package com.android.framework.jc.base.mvp;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/9/19 17:36
 * @describe
 * @update
 */
public interface IFkContract {
    interface IPresenter<V extends IView> {
        void onAttachView(V view);

        /**
         *
         */
        void onDetachView();
    }

    interface IView<P extends IPresenter> {
        /**
         * 关联Presenter
         * @param presenter presenter
         */
        void setPresenter(P presenter);
    }
}
