package com.android.framework.jc.adapter.recyclerview;

import android.view.ViewGroup;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2019-05-22 13:50
 * @describe ViewHolder构造器
 * @update
 */
public interface IViewHolderCreator<D, ViewHolder extends BaseViewHolder<D>> {


    /**
     * 构建ViewHolder
     *
     * @param viewGroup
     *         viewGroup
     *
     * @return viewHolder
     */
    ViewHolder create(ViewGroup viewGroup);

    /**
     * 获取数据的Class对象
     *
     * @return 数据的Class对象
     */
    Class<D> getDataClass();
}
