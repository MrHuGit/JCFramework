package com.android.framework_test.module.network.logger;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2019/1/7 15:46
 * @describe
 * @update
 */
public interface ILoggerService {

    String GET_URL="https://blog.csdn.net/u013488627/article/details/79683085";

    @GET
    Flowable<ResponseBody> getTest();
}
