package com.android.framework.jc.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/4/3 17:36
 * @describe
 * @update
 */

public interface IRvAdapterAgent<T> {
    /**
     * Adapter item设置点击监听
     *
     * @param onItemClickListener
     *         监听
     *
     * @return 当前代理
     */
    IRvAdapterAgent<T> setOnItemClickListener(RvTypeAdapter.OnItemClickListener<T> onItemClickListener);

    /**
     * Adapter item设置长按监听
     *
     * @param onItemLongClickListener
     *         监听
     *
     * @return 当前代理
     */
    IRvAdapterAgent<T> setOnItemLongClickListener(RvTypeAdapter.OnItemLongClickListener<T> onItemLongClickListener);


    /**
     * 添加数据
     *
     * @param list 数据集合
     *
     * @return 当前代理
     */
    IRvAdapterAgent<T> setList(List<T> list);

    /**
     * 获取数据集合
     *
     * @return 数据集合
     */
    List<T> getList();

    /**
     * 添加视图
     *
     * @param itemView
     *         视图
     *
     * @return 当前代理
     */
    IRvAdapterAgent<T> addRvItemView(RvItemView<T> itemView);

    /**
     * @param holder
     *         viewHolder
     *
     * @return 当前代理
     */
    IRvAdapterAgent<T> onAfterViewHolderCreate(ViewHolder holder);

    /**
     * @param <T>
     */
    interface OnItemClickListener<T> {
        /**
         * @param view
         * @param holder
         * @param t
         * @param position
         */
        void onItemClick(View view, RecyclerView.ViewHolder holder, T t, int position);
    }

    /**
     * @param <T>
     */
    interface OnItemLongClickListener<T> {
        /**
         * @param view
         * @param holder
         * @param t
         * @param position
         *
         * @return
         */
        boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, T t, int position);
    }
}
