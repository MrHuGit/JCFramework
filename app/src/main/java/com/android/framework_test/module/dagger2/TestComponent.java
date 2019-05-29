package com.android.framework_test.module.dagger2;

import com.android.framework_test.module.MainActivity;

import dagger.Component;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2019-05-29 10:23
 * @describe
 * @update
 */
@Component(modules = {TestModule.class})
public interface TestComponent {
    //第三步  写一个方法 绑定Activity /Fragment
    void inject(MainActivity activity);
}
