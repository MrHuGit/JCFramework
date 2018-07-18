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

public class JcCookie {
    private Cookie mCookie;

    public JcCookie(Cookie cookie) {
        this.mCookie = cookie;
    }

    protected static List<JcCookie> decorateAll(Collection<Cookie> cookies) {
        List<JcCookie> jcCookies = new ArrayList<>(cookies.size());
        for (Cookie cookie : cookies) {
            jcCookies.add(new JcCookie(cookie));
        }
        return jcCookies;
    }

    public Cookie getCookie() {
        return this.mCookie;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || (obj.getClass() == this.getClass())) {
            return false;
        } else {
            JcCookie otherJcCookie = (JcCookie) obj;
            boolean result = this.mCookie.name().equals(otherJcCookie.getCookie().name());
            result = result && this.mCookie.domain().equals(otherJcCookie.getCookie().domain());
            result = result && this.mCookie.path().equals(otherJcCookie.getCookie().path());
            result = result && this.mCookie.secure() == otherJcCookie.getCookie().secure();
            result = result && this.mCookie.hostOnly() == otherJcCookie.getCookie().hostOnly();
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
