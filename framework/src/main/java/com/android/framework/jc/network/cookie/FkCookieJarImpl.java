package com.android.framework.jc.network.cookie;

import android.support.annotation.NonNull;

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

public class FkCookieJarImpl implements FkCookieJar {
    private final ICookieCache mCookieCache;

    public FkCookieJarImpl(ICookieCache cookieCache) {
        this.mCookieCache = cookieCache;
    }

    @Override
    public void clear() {
        this.mCookieCache.clear();
    }

    @Override
    public void saveFromResponse(@NonNull HttpUrl url, @NonNull List<Cookie> cookies) {
        synchronized (FkCookieJarImpl.this) {
            this.mCookieCache.saveAll(cookies);
        }
    }

    @Override
    public List<Cookie> loadForRequest(@NonNull HttpUrl url) {
        synchronized (FkCookieJarImpl.this) {
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
