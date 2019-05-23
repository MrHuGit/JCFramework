//package com.android.framework.jc.adapter.recyclerview.deprecated;
//
//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.ViewGroup;
//
//import java.util.List;
//
///**
// * @author Mr.Hu(Jc) JCFramework
// * @create 2018/4/3 16:45
// * @describe
// * @update
// */
//@Deprecated
//public class RvTypeAdapter<T> extends RecyclerView.Adapter<ViewHolder> implements IRvAdapterAgent<T> {
//     List<T> mList;
//     final RvAdapterTool<T> mTool;
//    private LayoutInflater inflater;
//    private OnItemClickListener<T> mClickListener;
//    private OnItemLongClickListener<T> mLongClickListener;
//
//    public RvTypeAdapter() {
//        mTool = new RvAdapterTool<>();
//    }
//
//    public RvTypeAdapter(List<T> list) {
//        mList = list;
//        mTool = new RvAdapterTool<>();
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        if (inflater == null) {
//            inflater = LayoutInflater.from(parent.getContext());
//        }
//        RvItemView itemView = mTool.getItemView(viewType);
//        int layoutId = itemView.getLayoutId();
//        ViewHolder viewHolder = ViewHolder.create(inflater.getContext(), parent, layoutId);
//        onAfterViewHolderCreate(viewHolder,viewType);
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        final T item = mList.get(position);
//        mTool.getItemView(getItemViewType(position)).convert(holder,item,position);
//        if (mClickListener != null) {
//            holder.itemView.setOnClickListener(v -> mClickListener.onItemClick(v, holder, item, position));
//        }
//
//        if (mLongClickListener != null) {
//            holder.itemView.setOnLongClickListener(v -> mLongClickListener.onItemLongClick(v, holder, item, position));
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return mList == null ? 0 : mList.size();
//    }
//
//
//    @Override
//    public int getItemViewType(int position) {
//        if (mTool.getRvItemViews().size() <= 1) {
//            return super.getItemViewType(position);
//        }
//        return mTool.getItemViewType(mList.get(position), position);
//    }
//
//    @Override
//    public RvTypeAdapter<T> setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
//        this.mClickListener = onItemClickListener;
//        return this;
//    }
//
//    @Override
//    public RvTypeAdapter<T> setOnItemLongClickListener(OnItemLongClickListener<T> onItemLongClickListener) {
//        this.mLongClickListener = onItemLongClickListener;
//        return this;
//    }
//
//    @Override
//    public RvTypeAdapter<T> setList(List<T> list) {
//        this.mList = list;
//        return this;
//    }
//
//    @Override
//    public List<T> getList() {
//        return mList;
//    }
//
//
//    void addRvItemView(int itemViewType,RvItemView<T> itemView) {
//        mTool.addRvItemView(itemViewType,itemView);
//    }
//
//
//
//
//    /**
//     * viewHolder构建完成
//     * @param holder holder
//     * @param viewType viewType
//     */
//    void onAfterViewHolderCreate(ViewHolder holder, int viewType) {
//    }
//
//
//}
