package com.android.framework_test.data;

import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/7/18 16:19
 * @describe
 * @update
 */
public interface TestService {
    String TEST_URL="https://blog.csdn.net/bunny1024/article/details/";

    /**
     * test
     * @return
     */
    @POST("53504556")
    Flowable<ResponseBody> testLog(@QueryMap Map<String,String> map);
}
