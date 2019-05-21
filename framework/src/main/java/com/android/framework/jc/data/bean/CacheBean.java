package com.android.framework.jc.data.bean;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2019/1/3 11:12
 * @describe
 * @update
 */
public class CacheBean extends RealmObject {
    @PrimaryKey
    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


}
