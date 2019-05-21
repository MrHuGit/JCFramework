package com.android.framework.jc.data.network.cookie;

import okhttp3.CookieJar;

/**
 * @author Mr.Hu(Jc)
 * @create 2018/3/11 22:10
 * @describe
 * @update
 */

public interface FkCookieJar extends CookieJar{

    /**
     * 清除cookie
     */
    void clear();
}
