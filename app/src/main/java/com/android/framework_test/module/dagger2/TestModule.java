package com.android.framework_test.module.dagger2;

import dagger.Module;
import dagger.Provides;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2019/1/4 16:18
 * @describe
 * @update
 */
@Module
public class TestModule {
    @Provides
    Person providePerson(){
        return new Person();
    }
}
