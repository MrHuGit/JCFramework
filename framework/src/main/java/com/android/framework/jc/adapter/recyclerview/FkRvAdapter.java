package com.android.framework.jc.adapter.recyclerview;

import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2019-07-01 14:53
 * @describe
 * @update
 */
public class FkRvAdapter extends RecyclerView.Adapter<FkRvViewHolder> {
     final SparseArrayCompat<FkRvTypeItemView<?>> mItemViewArray;
     final SparseArrayCompat<List<?>> mDataArray;
    /**
     * item单击监听集合
     */
    private final SparseArrayCompat<IRvAdapter.OnItemClickListener> mItemClickListeners;
    /**
     * item长按监听集合
     */
    private final SparseArrayCompat<IRvAdapter.OnItemLongClickListener> mItemLongClickListeners;

    public FkRvAdapter() {
        mItemViewArray = new SparseArrayCompat<>();
        mDataArray = new SparseArrayCompat<>();
        mItemClickListeners = new SparseArrayCompat<>();
        mItemLongClickListeners = new SparseArrayCompat<>();
    }


    @NonNull
    @Override
    public FkRvViewHolder onCreateViewHolder(@NonNull ViewGroup parentView, int viewType) {
        FkRvTypeItemView<?> itemView = mItemViewArray.get(viewType);
        int layoutId = itemView.getLayoutId();
        View layoutView = itemView.getLayoutView();
        FkRvViewHolder viewHolder;
        if (layoutView != null) {
            viewHolder = FkRvViewHolder.create(layoutView);
        } else {
            viewHolder = FkRvViewHolder.create(parentView, layoutId);

        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FkRvViewHolder holder, int position) {
        int dataListCount = mDataArray.size();
        int itemCount = 0;
        for (int i = dataListCount - 1; i >= 0; i--) {
            List<?> list = mDataArray.valueAt(i);
            itemCount += list.size();
            if (position < itemCount) {
                int itemViewType = mDataArray.keyAt(i);
                FkRvTypeItemView<?> typeItemView = mItemViewArray.get(itemViewType);
                //noinspection unchecked
                typeItemView.convert(holder, list.get(position - (itemCount - list.size())), position, mItemClickListeners.get(itemViewType), mItemLongClickListeners.get(itemViewType));
                return;


            }
        }
    }

    @Override
    public int getItemCount() {
        int dataListCount = mDataArray.size();
        int itemCount = 0;
        for (int i = dataListCount - 1; i >= 0; i--) {
            List<?> list = mDataArray.valueAt(i);
            itemCount += list.size();
        }
        return itemCount;
    }

    @Override
    public int getItemViewType(int position) {
        int dataListCount = mDataArray.size();
        int itemCount = 0;
        for (int i = dataListCount - 1; i >= 0; i--) {
            List<?> list = mDataArray.valueAt(i);
            itemCount += list.size();
            if (position < itemCount) {
                return mDataArray.keyAt(i);
            }
        }
        return super.getItemViewType(position);
    }

    public <D> void registerItemType(int itemType, FkRvTypeItemView<D> typeItemView) {
        if (mItemViewArray.indexOfKey(itemType) < 0) {
            mItemViewArray.put(itemType, typeItemView);
        } else {
            throw new RuntimeException("重复注册itemType=" + itemType);
        }
    }

    public <D> FkRvAdapter setList(int itemType, List<D> typeList) {
        mDataArray.put(itemType, typeList);
        return this;
    }


    public List<?> getList(int itemType) {
        return mDataArray.get(itemType);
    }

    /**
     * 设置长按监听
     *
     * @param itemType
     *         itemType
     * @param longClickListener
     *         longClickListener
     * @param <D>
     *         数据泛型
     */
    public <D> void setOnItemLongClickListener(int itemType, IRvAdapter.OnItemLongClickListener<D> longClickListener) {
        if (longClickListener != null) {
            mItemLongClickListeners.put(itemType, longClickListener);
        }
    }

    /**
     * 设置单击简体
     *
     * @param itemType
     *         itemType
     * @param clickListener
     *         clickListener
     * @param <D>
     *         数据泛型
     */
    public <D> void setOnItemClickListener(int itemType, IRvAdapter.OnItemClickListener<D> clickListener) {
        if (clickListener != null) {
            mItemClickListeners.put(itemType, clickListener);
        }
    }
}
