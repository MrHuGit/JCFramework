package com.android.framework.jc.data.cache;

import android.text.TextUtils;

import java.util.HashMap;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/9/12 17:24
 * @describe 内存缓存
 * @update
 */
public class MemoryCache implements IFkCache {
    private final HashMap<String,String> mMemoryMap;

    public MemoryCache() {
        mMemoryMap = new HashMap<>();
    }

    @Override
    public void clear() {
        mMemoryMap.clear();
    }

    @Override
    public void save(String key, String value) {
        if (!TextUtils.isEmpty(key)) {
          mMemoryMap.put(key, value);
        }
    }

    @Override
    public void remove(String key) {
        mMemoryMap.remove(key);
    }

    @Override
    public String get(String key) {
        return mMemoryMap.get(key);
    }
}
