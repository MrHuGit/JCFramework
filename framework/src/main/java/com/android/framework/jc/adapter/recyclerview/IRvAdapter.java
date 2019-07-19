package com.android.framework.jc.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2019-07-01 15:00
 * @describe
 * @update
 */
public interface IRvAdapter {


    /**
     * @param <D>
     */
    interface OnItemClickListener<D> {
        /**
         * @param view
         * @param holder
         * @param data
         * @param position
         */
        void onItemClick(View view, RecyclerView.ViewHolder holder, D data, int position);
    }

    /**
     * @param <D>
     */
    interface OnItemLongClickListener<D> {
        /**
         * @param view
         * @param holder
         * @param data
         * @param position
         *
         * @return
         */
        boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, D data, int position);
    }
}
