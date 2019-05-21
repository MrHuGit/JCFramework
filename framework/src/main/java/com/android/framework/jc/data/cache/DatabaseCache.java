package com.android.framework.jc.data.cache;

import android.text.TextUtils;

import com.android.framework.jc.FkRealmManager;
import com.android.framework.jc.data.bean.CacheBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/9/12 17:32
 * @describe 数据库缓存
 * @update
 */
public class DatabaseCache implements IFkCache {
    private final FkRealmManager mFkRealmManager;
    private final List<CacheBean> mCacheList;

    public DatabaseCache(FkRealmManager fkRealmManager) {
        this.mFkRealmManager = fkRealmManager;
        mCacheList = new ArrayList<>();
        List<CacheBean> list = mFkRealmManager.query(CacheBean.class).findAll();
        if (list != null) {
            mCacheList.addAll(mFkRealmManager.copyFromRealm(list));
        }
    }

    @Override
    public void clear() {
        mCacheList.clear();
        mFkRealmManager.copyToRealmOrUpdate(mCacheList);
    }

    @Override
    public void save(String key, String value) {
        CacheBean cacheBean = getCacheBean(key);
        if (cacheBean != null) {
            cacheBean.setValue(value);
        } else {
            cacheBean = new CacheBean();
            cacheBean.setValue(value);
            cacheBean.setKey(key);
            mCacheList.add(cacheBean);
        }
        mFkRealmManager.copyToRealmOrUpdate(mCacheList);
    }

    @Override
    public void remove(String key) {
        CacheBean cacheBean = getCacheBean(key);
        if (cacheBean != null) {
            mCacheList.remove(cacheBean);
            mFkRealmManager.copyToRealmOrUpdate(mCacheList);
        }
    }

    @Override
    public String get(String key) {
        CacheBean cacheBean = getCacheBean(key);
        return cacheBean == null ? null : cacheBean.getKey();
    }


    private CacheBean getCacheBean(String key) {
        if (!TextUtils.isEmpty(key) && mCacheList.size() > 0) {
            for (CacheBean bean : mCacheList) {
                if (key.equals(bean.getKey())) {
                    return bean;
                }
            }
        }
        return null;
    }

}
