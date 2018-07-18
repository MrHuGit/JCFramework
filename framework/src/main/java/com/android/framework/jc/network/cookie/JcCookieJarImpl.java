package com.android.framework.jc.network.cookie;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * @author Mr.Hu(Jc)
 * @create 2018/3/11 22:15
 * @describe
 * @update
 */

public class JcCookieJarImpl implements JcCookieJar {
    private final ICookieCache mCookieCache;

    public JcCookieJarImpl(ICookieCache cookieCache) {
        this.mCookieCache = cookieCache;
    }

    @Override
    public void clear() {
        this.mCookieCache.clear();
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        synchronized (JcCookieJarImpl.this) {
            this.mCookieCache.saveAll(cookies);
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        synchronized (JcCookieJarImpl.this) {
            List<Cookie> removeCookies = new ArrayList<>();
            List<Cookie> result = new ArrayList<>();
            for (Cookie cookie : this.mCookieCache) {
                if (checkCookieExpired(cookie)) {
                    removeCookies.add(cookie);
                } else if (cookie.matches(url)) {
                    result.add(cookie);
                }
            }
            this.mCookieCache.removeAll(removeCookies);
            return result;
        }

    }

    /**
     * 判断cookie是否已过期
     *
     * @param cookie
     *
     * @return
     */
    private static boolean checkCookieExpired(Cookie cookie) {
        return cookie.expiresAt() < System.currentTimeMillis();
    }
}
