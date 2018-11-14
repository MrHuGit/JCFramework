package com.android.framework_test.data.bean;

import com.android.framework_test.base.BaseActivity;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/11/8 19:31
 * @describe
 * @update
 */
public class ListBean {
   private String name;
    private Class<? extends BaseActivity> aClass;

    public ListBean(String name, Class<? extends BaseActivity> aClass) {
        this.name = name;
        this.aClass = aClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<? extends BaseActivity> getActivityClass() {
        return aClass;
    }

    public void setActivityClass(Class<? extends BaseActivity> aClass) {
        this.aClass = aClass;
    }
}
