package com.android.framework.jc.cache;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/9/12 17:17
 * @describe 缓存接口
 * @update
 */
public interface IFkCache {
    /**
     * 清除所有
     */
    void clear();

    /**
     * 保存
     *
     * @param key
     *         key
     * @param value
     *         value
     */
    void save(String key, String value);

    /**
     * 移除
     *
     * @param key
     *         key
     */
    void remove(String key);


    /**
     * 获取数据
     *
     * @param key
     *         key
     *
     * @return value
     */
    String get(String key);
}
