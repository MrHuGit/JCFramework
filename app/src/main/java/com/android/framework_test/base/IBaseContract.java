package com.android.framework_test.base;

import com.android.framework.jc.base.mvp.IFkContract;

import dagger.BindsInstance;
import dagger.Component;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/11/8 19:18
 * @describe
 * @update
 */
public interface IBaseContract {
    @Component
    interface IComponent {

        void inject(IView view);


        @Component.Builder
        interface Builder {
            @BindsInstance
            IComponent.Builder setView(IView view);

            IComponent build();

        }
    }

    interface IPresenter extends IFkContract.IPresenter {

    }

    interface IView extends IFkContract.IView {

    }


}
