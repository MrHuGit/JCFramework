package com.android.framework.jc.adapter.recyclerview;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2019-07-01 14:57
 * @describe
 * @update
 */
public abstract class FkRvTypeItemView<D> {


    public abstract @LayoutRes
    int getLayoutId();

    public View getLayoutView() {
        return null;
    }

    void convert(FkRvViewHolder holder, @NonNull Object data, int position, IRvAdapter.OnItemClickListener<D> onItemClickListener, IRvAdapter.OnItemLongClickListener<D> onItemLongClickListener) {
        D d;
        try {
            //noinspection unchecked
            d = (D) data;
        } catch (ClassCastException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("无法将" + data.getClass().toString() + "绑定到" + this.getClass().toString());
        }
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(v, holder, d, position));
        }

        if (onItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(v -> onItemLongClickListener.onItemLongClick(v, holder, d, position));
        }
        onBindData(holder, d, position);

    }

    public abstract void onBindData(FkRvViewHolder holder, @NonNull D data, int position);
}
