package com.android.framework.jc.recyclerview;

import android.support.annotation.LayoutRes;
import android.view.View;

import java.util.List;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/4/3 17:32
 * @describe
 * @update
 */

public abstract class RvItemView<T> {

    public RvItemView() {
    }


    public abstract @LayoutRes
    int getLayoutId();

    public View getLayoutView() {
        return null;
    }

    public abstract void convert(ViewHolder holder, T t, int position);


    public  boolean checkViewType(T item, int position){return true;};

    public abstract void notifyPayloads(ViewHolder holder,T item, int position, List<Object> payloads);
}
