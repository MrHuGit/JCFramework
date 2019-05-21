package com.android.framework_test.module.dagger2;

import javax.inject.Inject;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2019/1/4 16:14
 * @describe
 * @update
 */
public class Test {
    @Inject
    Person mPerson;
    public  void test(){
        mPerson.toString();
    }
}
