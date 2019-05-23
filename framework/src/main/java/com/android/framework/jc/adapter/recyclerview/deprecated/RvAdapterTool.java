//package com.android.framework.jc.adapter.recyclerview.deprecated;
//
//import android.support.v4.util.SparseArrayCompat;
//
///**
// * @author Mr.Hu(Jc) JCFramework
// * @create 2018/4/3 18:10
// * @describe adapter工具管理
// * @update
// */
//@Deprecated
//class RvAdapterTool<T> {
//    private SparseArrayCompat<RvItemView<T>> mRvItemViews;
////    private RvItemView<Void> emptyItemView;
//    RvAdapterTool() {
//        mRvItemViews = new SparseArrayCompat<>();
//    }
//
//    SparseArrayCompat<RvItemView<T>> getRvItemViews() {
//        return mRvItemViews;
//    }
//    void addRvItemView(int itemType,RvItemView<T> itemView) {
//        mRvItemViews.put(itemType, itemView);
//    }
//
//
//    RvItemView<T> getItemView(int type) {
//        return mRvItemViews.get(type);
//    }
//
//    /**
//     * 获取item的viewType
//     *
//     * @param item
//     *         item
//     * @param position
//     *         position
//     *
//     * @return item的viewType
//     */
//    int getItemViewType(T item, int position) {
//        int itemCount = mRvItemViews.size();
//        for (int i = itemCount - 1; i >= 0; i--) {
//            RvItemView<T> itemView = mRvItemViews.valueAt(i);
//            if (itemView.checkViewType(item, position)) {
//                return mRvItemViews.keyAt(i);
//            }
//        }
//        throw new IllegalArgumentException(
//                "No RvItemView added that matches position=" + position + " in data source");
//    }
//
//}
