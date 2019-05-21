package com.android.framework.jc.data.network.cookie;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.Cookie;

/**
 * @author Mr.Hu(Jc)
 * @create 2018/3/12 10:01
 * @describe
 * @update
 */

public class SharedPrefsCookieCache implements ICookieCache {
    private final static String SHARED_PREFERENCES_NAME="persistentCookie";
    private final MemoryCookieCache mMemoryCookieCache=new MemoryCookieCache();
    private final SharedPreferences mPreferences;
    public SharedPrefsCookieCache(Context context){
        mPreferences=context.getSharedPreferences(SHARED_PREFERENCES_NAME,Context.MODE_PRIVATE);
        mMemoryCookieCache.saveAll(loadAll());
    }
    @Override
    public void saveAll(Collection<Cookie> cookies) {
        mMemoryCookieCache.saveAll(cookies);
        SharedPreferences.Editor editor = this.mPreferences.edit();
        for (Cookie cookie:cookies){
            if (cookie.persistent()){
                editor.putString(createCookieKey(cookie),new SerializableCookie().encodeCookie(cookie));
            }
        }
        editor.apply();
    }

    @Override
    public void removeAll(Collection<Cookie> cookies) {
        mMemoryCookieCache.removeAll(cookies);
        SharedPreferences.Editor editor = this.mPreferences.edit();
        for (Cookie cookie:cookies){
            editor.remove(createCookieKey(cookie));
        }
        editor.apply();
    }

    @Override
    public void clear() {
        mMemoryCookieCache.clear();
        this.mPreferences.edit().clear().apply();
    }

    @NonNull
    @Override
    public Iterator<Cookie> iterator() {
        return mMemoryCookieCache.iterator();
    }

    private static String createCookieKey(Cookie cookie) {
        return (cookie.secure()?"https":"http") + "://" + cookie.domain() + cookie.path() + "|" + cookie.name();
    }

    private List<Cookie> loadAll() {
        List<Cookie> cookies = new ArrayList<>();
        for (Object o : this.mPreferences.getAll().entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            String serializedCookie = (String) entry.getValue();
            Cookie cookie = (new SerializableCookie()).decodeCookie(serializedCookie);
            cookies.add(cookie);
        }

        return cookies;
    }
}
