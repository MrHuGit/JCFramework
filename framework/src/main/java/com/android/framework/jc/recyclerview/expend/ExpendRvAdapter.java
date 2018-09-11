package com.android.framework.jc.recyclerview.expend;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.framework.jc.recyclerview.IRvAdapterAgent;
import com.android.framework.jc.recyclerview.RvAdapterTool;
import com.android.framework.jc.recyclerview.RvItemView;
import com.android.framework.jc.recyclerview.ViewHolder;

import java.util.List;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/9/5 14:27
 * @describe
 * @update
 */
public abstract class ExpendRvAdapter<G, T> extends RecyclerView.Adapter<ViewHolder> implements IRvAdapterAgent<ExpendBean<G, T>> {
    private final RvAdapterTool<ExpendBean<G, T>> mTool;
    private List<ExpendBean<G, T>> mList;
    private LayoutInflater inflater;
    private OnItemClickListener<ExpendBean<G, T>> mClickListener;
    private OnItemLongClickListener<ExpendBean<G, T>> mLongClickListener;

    public ExpendRvAdapter(int groupLayoutId, int itemLayoutId) {
        mTool = new RvAdapterTool<>();
        addRvItemView(new RvItemView<ExpendBean<G, T>>() {
            @Override
            public int getLayoutId() {
                return groupLayoutId;
            }

            @Override
            public void convert(ViewHolder holder, ExpendBean<G, T> gtExpendBean, int position) {
            }

            @Override
            public void notifyPayloads(ViewHolder holder, ExpendBean<G, T> item, int position, List<Object> payloads) {

            }

            @Override
            public boolean checkViewType(ExpendBean<G, T> item, int position) {
                return "1".equalsIgnoreCase(checkItemViewType(item, position));
            }
        });
        addRvItemView(new RvItemView<ExpendBean<G, T>>() {
            @Override
            public int getLayoutId() {
                return itemLayoutId;
            }

            @Override
            public void convert(ViewHolder holder, ExpendBean<G, T> gtExpendBean, int position) {

            }

            @Override
            public void notifyPayloads(ViewHolder holder, ExpendBean<G, T> item, int position, List<Object> payloads) {

            }

            @Override
            public boolean checkViewType(ExpendBean<G, T> item, int position) {
                return "2".equalsIgnoreCase(checkItemViewType(item, position));
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ExpendBean<G, T> item = mList.get(position);
        mTool.convert(holder, item, position);
        if (mClickListener != null) {
            holder.itemView.setOnClickListener(v -> mClickListener.onItemClick(v, holder, item, position));
        }

        if (mLongClickListener != null) {
            holder.itemView.setOnLongClickListener(v -> mLongClickListener.onItemLongClick(v, holder, item, position));
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }


    @Override
    public ExpendRvAdapter<G, T> setOnItemClickListener(OnItemClickListener<ExpendBean<G, T>> onItemClickListener) {
        this.mClickListener = onItemClickListener;
        return this;
    }

    @Override
    public ExpendRvAdapter<G, T> setOnItemLongClickListener(OnItemLongClickListener<ExpendBean<G, T>> onItemLongClickListener) {
        this.mLongClickListener = onItemLongClickListener;
        return this;
    }

    @Override
    public ExpendRvAdapter<G, T> setList(List<ExpendBean<G, T>> list) {
        this.mList = list;
        return this;
    }

    @Override
    public List<ExpendBean<G, T>> getList() {
        return mList;
    }


    @Override
    public ExpendRvAdapter<G, T> addRvItemView(RvItemView<ExpendBean<G, T>> itemView) {
        mTool.addRvItemView(itemView);
        return this;
    }


    @Override
    public ExpendRvAdapter<G, T> onAfterViewHolderCreate(ViewHolder holder) {
        return this;
    }

    private String checkItemViewType(ExpendBean<G, T> item, int position) {
        return "";
    }

    protected abstract void convertGroup(ViewHolder holder, G groupBean, int position);

    protected abstract void convertItem(ViewHolder holder, T itemBean, int position);
}
