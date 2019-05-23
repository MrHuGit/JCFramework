//package com.android.framework.jc.adapter.recyclerview.deprecated;
//
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//
//import com.android.framework.jc.adapter.recyclerview.RvTypeAdapter;
//
//import java.util.List;
//
///**
// * @author Mr.Hu(Jc) JCFramework
// * @create 2018/4/3 17:36
// * @describe
// * @update
// */
//@Deprecated
//public interface IRvAdapterAgent<T> {
//    /**
//     * Adapter item设置点击监听
//     *
//     * @param onItemClickListener
//     *         监听
//     *
//     * @return 当前代理
//     */
//    IRvAdapterAgent<T> setOnItemClickListener(RvTypeAdapter.OnItemClickListener<T> onItemClickListener);
//
//    /**
//     * Adapter item设置长按监听
//     *
//     * @param onItemLongClickListener
//     *         监听
//     *
//     * @return 当前代理
//     */
//    IRvAdapterAgent<T> setOnItemLongClickListener(RvTypeAdapter.OnItemLongClickListener<T> onItemLongClickListener);
//
//
//    /**
//     * 添加数据
//     *
//     * @param list 数据集合
//     *
//     * @return 当前代理
//     */
//    IRvAdapterAgent<T> setList(List<T> list);
//
//    /**
//     * 获取数据集合
//     *
//     * @return 数据集合
//     */
//    List<T> getList();
//
//
//
//    /**
//     * @param <T>
//     */
//    interface OnItemClickListener<T> {
//        /**
//         * @param  view view
//         * @param holder holder
//         * @param t t
//         * @param position position
//         */
//        void onItemClick(View view, RecyclerView.ViewHolder holder, T t, int position);
//    }
//
//    /**
//     * @param <T>
//     */
//    interface OnItemLongClickListener<T> {
//        /**
//         * @param  view view
//         * @param holder holder
//         * @param t t
//         * @param position position
//         * @return 是否处理
//         */
//        boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, T t, int position);
//    }
//}
