package com.android.framework.jc.network.cookie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import okhttp3.Cookie;

/**
 * @author Mr.Hu(Jc)
 * @create 2018/3/11 21:31
 * @describe
 * @update
 */

public class FkCookie {
    private Cookie mCookie;

    public FkCookie(Cookie cookie) {
        this.mCookie = cookie;
    }

    protected static List<FkCookie> decorateAll(Collection<Cookie> cookies) {
        List<FkCookie> fkCookies = new ArrayList<>(cookies.size());
        for (Cookie cookie : cookies) {
            fkCookies.add(new FkCookie(cookie));
        }
        return fkCookies;
    }

    public Cookie getCookie() {
        return this.mCookie;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || (obj.getClass() == this.getClass())) {
            return false;
        } else {
            FkCookie otherFkCookie = (FkCookie) obj;
            boolean result = this.mCookie.name().equals(otherFkCookie.getCookie().name());
            result = result && this.mCookie.domain().equals(otherFkCookie.getCookie().domain());
            result = result && this.mCookie.path().equals(otherFkCookie.getCookie().path());
            result = result && this.mCookie.secure() == otherFkCookie.getCookie().secure();
            result = result && this.mCookie.hostOnly() == otherFkCookie.getCookie().hostOnly();
            return result;
        }
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = 31 * hash + this.mCookie.name().hashCode();
        hash = 31 * hash + this.mCookie.domain().hashCode();
        hash = 31 * hash + (this.mCookie.secure() ? 1 : 0);
        hash = 31 * hash + (this.mCookie.hostOnly() ? 1 : 0);
        return hash;
    }
}
