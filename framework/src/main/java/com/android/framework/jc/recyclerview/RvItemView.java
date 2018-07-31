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
//    private final List<RvItemView<T>> mList;

    public RvItemView() {
//        mList = new ArrayList<>();
    }

//    public RvItemView<T> addChildView(RvItemView<T> childView) {
//        mList.add(childView);
//        return this;
//    }

//    public boolean checkHaveChild() {
//        return !mList.isEmpty();
//    }

//    public List<RvItemView<T>> getList() {
//        return mList;
//    }

//    public void onViewAttachedToWindow(RecyclerView.ViewHolder viewHolder) {
//    }

    public abstract @LayoutRes
    int getLayoutId();

    public View getLayoutView() {
        return null;
    }

    public abstract void convert(ViewHolder holder, T t, int position);


    public  boolean checkViewType(T item, int postion){return true;};

    public abstract void notifyPayloads(ViewHolder holder,T item, int position, List<Object> payloads);
}
