package com.android.framework.jc;

import android.text.TextUtils;

import io.reactivex.disposables.Disposable;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/7/21 16:22
 * @describe url获取
 * @update
 */
public class FkUrlManager {
    private IAdapter mAdapter;

    private FkUrlManager() {
    }

    private static class Holder {
        private final static FkUrlManager INSTANCE = new FkUrlManager();
    }

    public static FkUrlManager getInstance() {
        return Holder.INSTANCE;
    }

    public interface IAdapter {
        /**
         * 根据urlName获取url地址，{@link #update()}更新地址之后可以保存下来，然后这里再返回
         *
         * @param configUrlName
         *         framework_configuration配置的地址
         *
         * @return 修改之后的地址url
         */
        String getUrl(String configUrlName);

        /**
         * 设置适配器之后就会调用此方法，这里可以进行网络访问获取最新地址
         *
         * @return disposable
         */
        Disposable update();
    }

    /**
     * 设置适配器动态更改地址
     *
     * @param adapter
     *         适配器
     */
    protected void setAdapter(IAdapter adapter) {
        Disposable disposable = adapter.update();
        if (disposable != null) {
            NetworkManager.getInstance().addDispose(FkUrlManager.this, disposable);
        }
        mAdapter = adapter;
    }


    /**
     * 根据配置文件配置的名字获取对应的配置地址
     *
     * @param configUrlName
     *         配置文件中配置的地址名称
     *
     * @return 实际url地址（如果设置了适配器，这里返回的就不再是配置文件中配置的地址）
     */
    public String getUrl(String configUrlName) {
        String url = null;
        if (mAdapter != null) {
            NetworkManager.getInstance().clearDispose(FkUrlManager.this);
            url = mAdapter.getUrl(configUrlName);
        }
        if (TextUtils.isEmpty(url)) {
            return ConfigManager.getInstance().getValue(configUrlName);
        }
        return url;
    }


}
