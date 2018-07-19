package com.android.framework.jc.network.cookie;

import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import okhttp3.Cookie;

/**
 * @author Mr.Hu(Jc)
 * @create 2018/3/11 21:29
 * @describe
 * @update
 */

public class MemoryCookieCache implements ICookieCache {
    private final Set<FkCookie> mCookieSets = new HashSet<>();

    @Override
    public void saveAll(Collection<Cookie> collection) {
        List<FkCookie> cookieList = FkCookie.decorateAll(collection);
        this.mCookieSets.removeAll(cookieList);
        this.mCookieSets.addAll(cookieList);
    }

    @Override
    public void removeAll(Collection<Cookie> cookies) {
        this.mCookieSets.removeAll(cookies);
    }

    @Override
    public void clear() {
        this.mCookieSets.clear();
    }

    @NonNull
    @Override
    public Iterator<Cookie> iterator() {
        return new SetCookieCacheIterator(mCookieSets.iterator());
    }

   private final class SetCookieCacheIterator implements Iterator<Cookie> {
        private final Iterator<FkCookie> mCookies;

        public SetCookieCacheIterator(Iterator<FkCookie> cookies) {
            this.mCookies = cookies;
        }

        @Override
        public boolean hasNext() {
            return this.mCookies.hasNext();
        }

        @Override
        public Cookie next() {
            return this.mCookies.next().getCookie();
        }

        @Override
        public void remove() {
            this.mCookies.remove();
        }
    }

}
