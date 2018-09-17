package com.android.framework.jc.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/7/19 10:44
 * @describe
 * @update
 */
public class RetrofitManager {
    private final Retrofit.Builder mDefaultRetrofitBuilder;
    private static class Holder {
        private final static RetrofitManager INSTANCE = new RetrofitManager();
    }

    public static RetrofitManager getInstance() {
        return Holder.INSTANCE;
    }

    private RetrofitManager(){
        mDefaultRetrofitBuilder=new Retrofit.Builder()
                .client(OkHttpManager.getInstance().getDefaultOkHttpClient())
                .addConverterFactory(FkJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
    }

    public Retrofit.Builder getRetrofitBuilder(){
        return mDefaultRetrofitBuilder;
    }


}
