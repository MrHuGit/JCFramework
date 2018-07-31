package com.android.framework.jc.recyclerview;

import android.support.v4.util.SparseArrayCompat;

import java.util.List;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/4/3 18:10
 * @describe
 * @update
 */

public class RvAdapterTool<T> {
   private SparseArrayCompat<RvItemView<T>> mRvItemViews;
   public RvAdapterTool(){
       mRvItemViews=new SparseArrayCompat<>();
   }

   public SparseArrayCompat<RvItemView<T>> getRvItemViews(){
       return mRvItemViews;
   }

   public RvAdapterTool<T> addRvItemView(RvItemView<T> itemView){
       mRvItemViews.put(mRvItemViews.size(),itemView);
       return this;
   }


   public RvItemView<T> getItemView(int type){
       return mRvItemViews.get(type);
   }
    /**
     * 获取item的viewType
     *
     * @param item
     * @param position
     * @return
     */
    public int getItemViewType(T item, int position) {
        int itemCount = mRvItemViews.size();
        for (int i = itemCount - 1; i >= 0; i--) {
            RvItemView<T> itemView = mRvItemViews.valueAt(i);
            if (itemView.checkViewType(item, position)) {
                return mRvItemViews.keyAt(i);
            }
        }
        throw new IllegalArgumentException(
                "No RvItemView added that matches position=" + position + " in data source");
    }
    public void convert(ViewHolder holder, T item, int position) {
        int delegatesCount = mRvItemViews.size();
        for (int i = 0; i < delegatesCount; i++) {
            RvItemView<T> itemView = mRvItemViews.valueAt(i);
            if (itemView.checkViewType(item, position)) {
                itemView.convert(holder, item, position);
                return;
            }
        }
        throw new IllegalArgumentException(
                "No RvItemView added that matches position=" + position + " in data source");
    }

    public void notifyPayloads(ViewHolder holder,T item, int position, List<Object> payloads){
        int delegatesCount = mRvItemViews.size();
        for (int i = 0; i < delegatesCount; i++) {
            RvItemView<T> itemView = mRvItemViews.valueAt(i);
            if (itemView.checkViewType(item, position)) {
                itemView.notifyPayloads(holder, item, position,payloads);
                return;
            }
        }
    }
}
