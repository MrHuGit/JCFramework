package com.android.framework.jc.adapter.recyclerview;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2019-05-22 19:22
 * @describe 普通通用RecyclerView的Adapter
 * @update
 */
public abstract class NormalRvAdapter<D> extends RvTypeAdapter implements IAdapterListener<D> {
    public final static int NORMAL_ITEM_TYPE = 1;
    public final static int EMPTY_ITEM_TYPE = 2;
    public final static int LOADING_ITEM_TYPE = 3;
    private boolean showEmptyLayout = false;
    private boolean showLoadingLayout = false;

    private List<D> mList;

    public NormalRvAdapter(@LayoutRes int layoutId, Class<D> dClass) {
        this(layoutId, -1, dClass);
    }

    public NormalRvAdapter(int layoutId, @LayoutRes int emptyLayout, Class<D> dClass) {
        this(layoutId, emptyLayout, -1, dClass);
    }

    public NormalRvAdapter(@LayoutRes int layoutId, @LayoutRes int emptyLayout, @LayoutRes int loadingLayout, Class<D> dClass) {
        registerViewHolder(NORMAL_ITEM_TYPE, new IViewHolderCreator<D, BaseViewHolder<D>>() {
            @Override
            public BaseViewHolder<D> create(ViewGroup parent) {
                return new BaseViewHolder<D>(parent, layoutId) {
                    @Override
                    void onConvert(D t, int position) {
                        NormalRvAdapter.this.onConvert(this, t, position);
                    }
                };
            }

            @Override
            public Class<D> getDataClass() {
                return dClass;
            }
        });
        //注册没有数据的时候显示的ViewHolder
        if (emptyLayout != -1) {
            registerViewHolder(EMPTY_ITEM_TYPE, new IViewHolderCreator<String, BaseViewHolder<String>>() {
                @Override
                public BaseViewHolder<String> create(ViewGroup viewGroup) {
                    return new BaseViewHolder<String>(viewGroup, emptyLayout) {
                        @Override
                        void onConvert(String s, int position) {

                        }
                    };
                }

                @Override
                public Class<String> getDataClass() {
                    return String.class;
                }
            });
        }
        //注册首次显示的正在加载的ViewHolder
        if (loadingLayout != -1) {
            addLoadingHolder(loadingLayout);
        }

    }

    /**
     * 显示正在加载的布局(先添加正在加载的布局，移除所有原始数据)
     *
     * @param loadingLayout
     *         正在加载的布局
     */
    public void showLoadingView(@LayoutRes int loadingLayout) {
        if (showLoadingLayout) {
            return;
        }
        mDataList.clear();
        showEmptyLayout = false;
        addLoadingHolder(loadingLayout);
        notifyDataSetChanged();
    }

    /**
     * 添加正在加载的ViewHolder
     *
     * @param loadingLayout
     *         正在加载的布局
     */
    private void addLoadingHolder(@LayoutRes int loadingLayout) {
        registerViewHolder(LOADING_ITEM_TYPE, new IViewHolderCreator<String, BaseViewHolder<String>>() {
            @Override
            public BaseViewHolder<String> create(ViewGroup viewGroup) {
                return new BaseViewHolder<String>(viewGroup, loadingLayout) {
                    @Override
                    void onConvert(String s, int position) {
                    }
                };
            }

            @Override
            public Class<String> getDataClass() {
                return String.class;
            }
        });
        showLoadingLayout = true;
        mDataList.add(new WrapData<>(LOADING_ITEM_TYPE, ""));

    }

    @Override
    protected void onAfterDataChanged() {
        super.onAfterDataChanged();
        if (showLoadingLayout) {
            removeItemType(LOADING_ITEM_TYPE);
            unRegisterViewHolder(LOADING_ITEM_TYPE);
            showLoadingLayout = false;
        }
        if (getNormalListSize() == 0) {
            if (!showEmptyLayout) {
                checkData(EMPTY_ITEM_TYPE, "");
                mDataList.add(new WrapData<>(EMPTY_ITEM_TYPE, ""));
                showEmptyLayout = true;
                notifyDataSetChanged();
            }

        } else {
            showEmptyLayout = false;
            removeItemType(EMPTY_ITEM_TYPE);
        }
    }

    /**
     * 设置数据列表
     *
     * @param list
     *         数据列表
     *
     * @return 当前对象
     */
    public NormalRvAdapter<D> setList(List<D> list) {
        mList = list;
        setList(NORMAL_ITEM_TYPE, list);
        return this;
    }

    /**
     * 获取数据列表（此处的数据不是绑定到adapter的原始数据，修改此处数据adapter不会有变化需要重新调用{@link #setList(List)}）
     *
     * @return 数据列表
     */
    public List<D> getList() {
        return mList;
    }

    /**
     * 获取正式数据（不包含暂无数据及正在加载数据两项）的列表长度
     *
     * @return 正式数据的列表长度
     */
    private int getNormalListSize() {
        int size = 0;
        for (WrapData<?> wrapData : mDataList) {
            if (wrapData.getItemType() == NORMAL_ITEM_TYPE) {
                size++;
            }
        }
        return size;
    }

    /**
     * 数据处理
     *
     * @param viewHolder
     *         viewHolder
     * @param t
     *         数据bean
     * @param position
     *         position
     */
    protected abstract void onConvert(BaseViewHolder<D> viewHolder, D t, int position);


    /**
     * 此处只是添加正常数据的监听，
     * 如果需要监听正在加载单击可以调用{@link #setOnItemClickListener(int, OnItemClickListener)}}int->{@link #LOADING_ITEM_TYPE}
     * 如果需要监听暂无数据单击可以调用{@link #setOnItemClickListener(int, OnItemClickListener)}}int->{@link #EMPTY_ITEM_TYPE}
     *
     * @param onItemClickListener
     *         单击item监听
     */
    @Override
    public void setOnItemClickListener(OnItemClickListener<D> onItemClickListener) {
        setOnItemClickListener(NORMAL_ITEM_TYPE, onItemClickListener);
    }

    @Override
    public void setOnItemLongClickListener(OnItemLongClickListener<D> onItemLongClickListener) {
        setOnItemLongClickListener(NORMAL_ITEM_TYPE, onItemLongClickListener);
    }
}
