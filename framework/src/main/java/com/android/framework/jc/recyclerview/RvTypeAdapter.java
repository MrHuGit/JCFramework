package com.android.framework.jc.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author Mr.Hu(Jc)
 * @create 2018/4/3 16:45
 * @describe
 * @update
 */

public class RvTypeAdapter<T> extends RecyclerView.Adapter<ViewHolder> implements IRvAdapterAgent<T> {
    List<T> mList;
    private final RvAdapterTool<T> mTool;
    private LayoutInflater inflater;
    private OnItemClickListener<T> mClickListener;
    private OnItemLongClickListener<T> mLongClickListener;

    public RvTypeAdapter() {
        mTool = new RvAdapterTool<>();
    }

    public RvTypeAdapter( List<T> list) {
        mList=list;
        mTool = new RvAdapterTool<>();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }

        RvItemView itemView = mTool.getItemView(viewType);
        int layoutId = itemView.getLayoutId();
        View layoutView = itemView.getLayoutView();
        ViewHolder viewHolder;
        if (layoutView != null) {
            viewHolder = ViewHolder.create(inflater.getContext(), layoutView);
        } else {
            viewHolder = ViewHolder.create(inflater.getContext(), parent, layoutId);

        }
        onAfterViewHolderCreate(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final T item = mList.get(position);
        mTool.convert(holder, item, position);
        if (mClickListener != null) {
            holder.itemView.setOnClickListener(v -> mClickListener.onItemClick(v, holder, item, position));
        }

        if (mLongClickListener != null) {
            holder.itemView.setOnLongClickListener(v -> mLongClickListener.onItemLongClick(v, holder, item, position));
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
        if (payloads==null || payloads.isEmpty()){
            onBindViewHolder(holder, position);
        }else{
            final T item = mList.get(position);
            mTool.notifyPayloads(holder, item, position,payloads);
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }


    @Override
    public int getItemViewType(int position) {
        if (mTool.getRvItemViews().size() <= 1) {
            return super.getItemViewType(position);
        }
        return mTool.getItemViewType(mList.get(position), position);
    }

    @Override
    public RvTypeAdapter<T> setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.mClickListener = onItemClickListener;
        return this;
    }

    @Override
    public RvTypeAdapter<T> setOnItemLongClickListener(OnItemLongClickListener<T> onItemLongClickListener) {
        this.mLongClickListener = onItemLongClickListener;
        return this;
    }

    @Override
    public RvTypeAdapter<T> setList(List<T> list) {
        this.mList = list;
        return this;
    }

    @Override
    public List<T> getList() {
        return mList;
    }


    @Override
    public RvTypeAdapter<T> addRvItemView(RvItemView<T> itemView) {
        mTool.addRvItemView(itemView);
        return this;
    }


    @Override
    public IRvAdapterAgent<T> onAfterViewHolderCreate(ViewHolder holder) {
        return null;
    }


}
