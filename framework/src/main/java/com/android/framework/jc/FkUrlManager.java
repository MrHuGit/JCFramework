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
            NetworkManager.getInstance().addDispose(FkUrlManager.this, disposable);
        }
        mAdapter = adapter;
    }

    public static FkUrlManager getInstance() {
        return Holder.INSTANCE;
    }

    public String getUrl(String configUrlName) {
        String url = null;
        if (mAdapter != null) {
            NetworkManager.getInstance().dispose(FkUrlManager.this);
            url = mAdapter.getUrl(configUrlName);
        }
        if (TextUtils.isEmpty(url)) {
            return ConfigManager.getInstance().getValue(configUrlName);
        }
        return url;
    }


}
