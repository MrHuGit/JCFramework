package com.android.framework.jc.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2019-05-21 19:55
 * @describe
 * @update
 */
public interface IAdapterListener<D> {
    interface OnItemClickListener<D> {
        /**
         * item单击监听
         *
         * @param view
         *         view
         * @param holder
         *         holder
         * @param d
         *         t
         * @param position
         *         position
         */
        void onItemClick(View view, RecyclerView.ViewHolder holder, D d, int position);

    }
    interface OnItemLongClickListener<D> {
        /**
         * item长按点击监听
         *
         * @param view
         *         view
         * @param holder
         *         holder
         * @param d
         *         数据
         * @param position
         *         position
         *
         * @return 是否处理
         */
        boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, D d, int position);
    }

    /**
     * Adapter item设置点击监听
     *
     * @param onItemClickListener
     *         监听
     *
     */
    void setOnItemClickListener(OnItemClickListener<D> onItemClickListener);

    /**
     * Adapter item设置长按监听
     *
     * @param onItemLongClickListener
     *         监听
     *
     */
    void setOnItemLongClickListener(OnItemLongClickListener<D> onItemLongClickListener);
}
