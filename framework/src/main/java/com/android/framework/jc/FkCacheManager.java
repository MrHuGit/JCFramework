package com.android.framework.jc;

import com.android.framework.jc.data.cache.DatabaseCache;
import com.android.framework.jc.data.cache.IFkCache;
import com.android.framework.jc.data.cache.MemoryCache;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/9/12 17:35
 * @describe 缓存管理类
 * @update
 */
public class FkCacheManager {
    private final IFkCache mMemoryCache;
    private final IFkCache mDatabaseCache;

    private FkCacheManager() {
        mMemoryCache = new MemoryCache();
        mDatabaseCache = new DatabaseCache(FkRealmManager.getInstance());

    }

    private static class Holder {
        private final static FkCacheManager INSTANCE = new FkCacheManager();
    }

    public static FkCacheManager getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * 保存到内存
     *
     * @param key
     *         key
     * @param value
     *         value
     */
    public void saveToMemory(String key, String value) {
        mMemoryCache.save(key, value);
    }

    /**
     * 从内存获取数据
     *
     * @param key
     *         key
     *
     * @return value
     */
    public String getFromMemory(String key) {
        return mMemoryCache.get(key);
    }

    /**
     * 从内存移除数据
     *
     * @param key
     *         key
     */
    public void removeFromMemory(String key) {
        mMemoryCache.remove(key);
    }

    /**
     * 保存到数据库
     *
     * @param key
     *         key
     * @param value
     *         value
     */
    public void saveToDatabase(String key, String value) {
        mDatabaseCache.save(key, value);
    }


    /**
     * 从数据库获取数据
     *
     * @param key
     *         key
     *
     * @return value
     */
    public String getFromDatabase(String key) {
        return mDatabaseCache.get(key);
    }

    /**
     * 从数据库移除数据
     *
     * @param key
     *         key
     */
    public void removeFromDatabase(String key) {
        mDatabaseCache.remove(key);
    }
}
