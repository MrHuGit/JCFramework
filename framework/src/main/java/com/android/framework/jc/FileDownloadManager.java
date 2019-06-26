package com.android.framework.jc;

import android.Manifest;
import android.app.Activity;

import com.android.framework.jc.base.FkPermission;
import com.android.framework.jc.exception.NetworkErrorException;
import com.android.framework.jc.network.OkHttpManager;
import com.android.framework.jc.network.RetrofitManager;
import com.android.framework.jc.network.interceptor.ProgressInterceptor;
import com.android.framework.jc.util.FileUtils;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/7/18 15:24
 * @describe 文件下载管理类
 * @update
 */
public class FileDownloadManager {

    private FileDownloadManager() {
    }

    public static FileDownloadManager getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static FileDownloadManager INSTANCE = new FileDownloadManager();
    }

    /**
     * 下载文件
     *
     * @param url
     *         下载文件的url
     * @param savePath
     *         保存文件的路径
     * @param listener
     *         监听
     */
    public void downloadFile(String url, String savePath, Listener listener) {
        Activity activity = JcFramework.getInstance().getTopActivity();
        FkPermission.with(activity)
                .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .listener(new FkPermission.IPermissionListener() {
                    @Override
                    public void onGrant() {
                        NetworkManager.getInstance().addDispose(url, download(url, savePath, listener));
                    }

                    @Override
                    public void onRationale(String[] permissions) {
                        listener.onError(new NetworkErrorException(activity.getString(R.string.Authorized_refused_to)));
                    }

                    @Override
                    public void onDenied(String[] permissions) {
                        listener.onError(new NetworkErrorException(activity.getString(R.string.Authorized_refused_to)));
                    }
                }).request();

    }

    private Disposable download(String url, String savePath, Listener listener) {
        return RetrofitManager.getInstance().getRetrofitBuilder()
                .client(OkHttpManager.getDefaultOkHttpClientBuilder()
                        .addInterceptor(new ProgressInterceptor((progress, total, bytesRead, done) -> {
                            if (listener != null) {
                                JcFramework.runOnMainThread(() -> listener.onProgress(progress, total, bytesRead));

                            }
                        })).build())
                .baseUrl("https://github.com/MrHuGit/")
                .build()
                .create(Service.class)
                .downloadFile(url)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(ResponseBody::byteStream)
                .observeOn(Schedulers.computation())
                .doOnNext(inputStream -> FileUtils.write(inputStream, savePath))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(inputStream -> {
                    if (listener != null) {
                        listener.onFinish();
                    }
                }, throwable -> {
                    if (listener != null) {
                        listener.onError(throwable);
                    }
                });

    }

    private interface Service {
        /**
         * 下载文件
         *
         * @param url
         *         下载文件的url
         *
         * @return flowable
         */
        @GET
        Flowable<ResponseBody> downloadFile(@Url String url);
    }

    public interface Listener {


        /**
         * 下载进度监听
         *
         * @param currentLength
         *         当前已经下载好的长度
         * @param totalLength
         *         需要下载文件的总长度
         * @param bytesRead
         *         当前读取的长度
         */
        void onProgress(long currentLength, long totalLength, long bytesRead);

        /**
         * 下载异常
         *
         * @param throwable
         *         异常
         */
        void onError(Throwable throwable);

        /**
         * 下载完成
         */
        void onFinish();

    }

}
