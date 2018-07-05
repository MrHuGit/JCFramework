package com.android.jc_framework.network;

import com.android.jc_framework.utils.StringUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import okhttp3.Cookie;

/**
 * @author Mr.Hu(Jc)
 * @create 2018/3/12 10:14
 * @describe
 * @update
 */

public class SerializableCookie implements Serializable{
    private transient Cookie mCookie;
    private static long NON_VALID_EXPIRES_AT = -1L;
    private static final long serialVersionUID = -8594045714036645534L;
    public String encodeCookie(Cookie cookie){
        this.mCookie=cookie;
        String result;
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        ObjectOutputStream outputStream= null;
        try {
            outputStream = new ObjectOutputStream(byteArrayOutputStream);
            outputStream.writeObject(this);
            result= StringUtils.byteArrayToHexString(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            result="";
        }finally {
            if (outputStream!=null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     *
     * @param encodeCookie
     * @return
     */
    public Cookie decodeCookie(String encodeCookie){
        Cookie cookie;
        byte[] cookieBytes= StringUtils.hexStringToByteArray(encodeCookie);
        ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(cookieBytes);
        ObjectInputStream inputStream= null;
        try {
            inputStream = new ObjectInputStream(byteArrayInputStream);
            cookie=((SerializableCookie)inputStream.readObject()).mCookie;
        } catch (IOException e) {
            e.printStackTrace();
            cookie=null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            cookie=null;
        }finally {
            if (inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return cookie;
    }
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeObject(this.mCookie.name());
        out.writeObject(this.mCookie.value());
        out.writeLong(this.mCookie.persistent()?this.mCookie.expiresAt():NON_VALID_EXPIRES_AT);
        out.writeObject(this.mCookie.domain());
        out.writeObject(this.mCookie.path());
        out.writeBoolean(this.mCookie.secure());
        out.writeBoolean(this.mCookie.httpOnly());
        out.writeBoolean(this.mCookie.hostOnly());
    }
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        Cookie.Builder builder = new Cookie.Builder();
        builder.name((String)in.readObject());
        builder.value((String)in.readObject());
        long expiresAt = in.readLong();
        if(expiresAt != NON_VALID_EXPIRES_AT) {
            builder.expiresAt(expiresAt);
        }
        String domain = (String)in.readObject();
        builder.domain(domain);
        builder.path((String)in.readObject());
        if(in.readBoolean()) {
            builder.secure();
        }

        if(in.readBoolean()) {
            builder.httpOnly();
        }

        if(in.readBoolean()) {
            builder.hostOnlyDomain(domain);
        }

        this.mCookie = builder.build();
    }

}
