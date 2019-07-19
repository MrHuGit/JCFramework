package com.android.framework.jc.adapter.recyclerview;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.List;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2019-07-01 16:36
 * @describe 通用adapter
 * @update
 */
public abstract class NormalRvAdapter<D> extends FkRvAdapter {
    private final static int EMPTY_ITEM_TYPE = 100;
    private final static int LOADING_ITEM_TYPE = 101;
    private final static int DATA_ITEM_TYPE = 1;
    private final int mEmptyLayoutId;
    private final int mLoadingLayoutId;
    private List<D> mList;

    /**
     * @param layoutId
     *         数据的布局
     * @param emptyLayout
     *         暂无数据显示的布局
     * @param loadingLayout
     *         加载数据中的布局
     */
    public NormalRvAdapter(@LayoutRes int layoutId, @LayoutRes int emptyLayout, @LayoutRes int loadingLayout) {
        registerItemType(DATA_ITEM_TYPE, new FkRvTypeItemView<D>() {
            @Override
            public int getLayoutId() {
                return layoutId;
            }

            @Override
            public void onBindData(FkRvViewHolder holder, @NonNull D data, int position) {
                NormalRvAdapter.this.onBindData(holder, data, position);
            }
        });
        mLoadingLayoutId = loadingLayout;
        mEmptyLayoutId = emptyLayout;
        showLoading();
    }

    /**
     * 显示正在加载
     */
    public void showLoading() {
        mItemViewArray.remove(LOADING_ITEM_TYPE);
        registerItemType(LOADING_ITEM_TYPE, new FkRvTypeItemView<String>() {
            @Override
            public int getLayoutId() {
                return mLoadingLayoutId;
            }

            @Override
            public void onBindData(FkRvViewHolder holder, @NonNull String data, int position) {

            }
        });
        mDataArray.clear();
        mDataArray.put(LOADING_ITEM_TYPE, Collections.singletonList("加载中...."));
        notifyDataSetChanged();
    }

    /**
     * 显示暂无数据
     */
    public void showEmpty() {
        mItemViewArray.remove(EMPTY_ITEM_TYPE);
        registerItemType(EMPTY_ITEM_TYPE, new FkRvTypeItemView<String>() {
            @Override
            public int getLayoutId() {
                return mEmptyLayoutId;
            }

            @Override
            public void onBindData(FkRvViewHolder holder, @NonNull String data, int position) {

            }
        });
        mDataArray.clear();
        mDataArray.put(EMPTY_ITEM_TYPE, Collections.singletonList("暂无数据"));
        notifyDataSetChanged();
    }

    /**
     * 设置数据
     *
     * @param typeList
     *         数据list
     *
     * @return 当前对象
     */
    public NormalRvAdapter setList(List<D> typeList) {
        mDataArray.remove(LOADING_ITEM_TYPE);
        mDataArray.remove(EMPTY_ITEM_TYPE);
        mList = typeList;
        super.setList(DATA_ITEM_TYPE, mList);

        if (typeList == null || typeList.size() < 1) {
            showEmpty();
        }
        return this;
    }

    /**
     * 获取数据列表
     * @return 数据列表
     */
    public List<D> getList() {
        return mList;
    }

    /**
     * 绑定数据
     *
     * @param holder
     *         ViewHolder
     * @param data
     *         数据
     * @param position
     *         position
     */
    abstract void onBindData(FkRvViewHolder holder, @NonNull D data, int position);
}
