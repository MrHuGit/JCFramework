package com.android.framework.jc;

import io.reactivex.disposables.Disposable;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/7/21 16:22
 * @describe url获取
 * @update
 */
public class UrlManager {
    private IAdapter mAdapter;

    private UrlManager() {
    }

    private static class Holder {
        private final static UrlManager INSTANCE = new UrlManager();
    }

    public interface IAdapter {
        /**
         * 根据urlName获取url地址，{@link #update()}更新地址之后可以保存下来，然后这里再返回
         *
         * @param configUrlName
         *         framework_configuration配置的地址
         *
         * @return 可以根据
         */
        String getUrl(String configUrlName);

        /**
         * 更新地址
         *
         * @return disposable
         */
        Disposable update();
    }

    public void setAdapter(IAdapter adapter) {
        Disposable disposable = adapter.update();
        if (disposable != null) {
            NetworkManager.getInstance().addDispose(UrlManager.this, disposable);
        }
        mAdapter = adapter;
    }

    public static UrlManager getInstance() {
        return Holder.INSTANCE;
    }

    public String getUrl(String configUrlName) {
        if (mAdapter != null) {
            NetworkManager.getInstance().dispose(UrlManager.this);
            return mAdapter.getUrl(configUrlName);
        }

        return ConfigManager.getInstance().getValue(configUrlName);
    }


}
