package com.android.framework.jc.adapter.recyclerview;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2019-05-21 18:50
 * @describe ViewHolder基类
 * @update
 */
public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {
    protected final Context mContext;
    private final SparseArray<View> mItemChildViewArray;
    protected RvTypeAdapter mAdapter;


    public BaseViewHolder(ViewGroup parentView, @LayoutRes int layoutRes) {
        this(LayoutInflater.from(parentView.getContext()).inflate(layoutRes, parentView, false));
    }

    public BaseViewHolder(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        mItemChildViewArray = new SparseArray<>();
    }


    /**
     * 通过viewId获取控件
     *
     * @param viewId
     *         viewId
     *
     * @return 控件
     */
    public <V extends View> V getView(@IdRes int viewId) {
        View view = mItemChildViewArray.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mItemChildViewArray.put(viewId, view);
        }
        //noinspection unchecked
        return (V) view;
    }

    /**
     * 绑定数据
     *
     * @param data
     *         数据bean
     * @param position
     *         position
     * @param clickListener
     *         单击item监听
     * @param longClickListener
     *         长按item监听
     */
    final void bindData(@NonNull Object data, int position, IAdapterListener.OnItemClickListener<T> clickListener, IAdapterListener.OnItemLongClickListener<T> longClickListener) {
        T t;
        try {
            //noinspection unchecked
            t = (T) data;
        } catch (ClassCastException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("无法将" + data.getClass().toString() + "绑定到" + this.getClass().toString());
        }
        if (clickListener != null) {
            itemView.setOnClickListener(v -> clickListener.onItemClick(itemView, BaseViewHolder.this, t, position));
        }
        if (longClickListener != null) {
            itemView.setOnLongClickListener(v -> longClickListener.onItemLongClick(itemView, BaseViewHolder.this, t, position));
        }

        onConvert(t, position);

    }

    @CallSuper
    BaseViewHolder<T> onAttachAdapter(RvTypeAdapter adapter) {
        this.mAdapter = adapter;
        return this;
    }

    /**
     * 处理数据
     *
     * @param t
     *         数据Bean
     * @param position
     *         position
     */
    abstract void onConvert(T t, int position);

    /**
     * 给TextView设置数据
     *
     * @param viewId
     *         textView控件id
     * @param value
     *         值
     *
     * @return 当前对象
     */
    public BaseViewHolder<T> setText(@IdRes int viewId, String value) {
        TextView textView = getView(viewId);
        textView.setText(value);
        return this;
    }

    /**
     * 给TextView设置数据
     *
     * @param viewId
     *         textView控件id
     * @param valueId
     *         值
     *
     * @return 当前对象
     */
    public BaseViewHolder<T> setText(@IdRes int viewId, @StringRes int valueId) {
        TextView textView = getView(viewId);
        textView.setText(valueId);
        return this;
    }

    /**
     * ImageView通过Glide加载网络图片
     *
     * @param viewId
     *         ImageView控件id
     * @param imageUrl
     *         值
     *
     * @return 当前对象
     */
    public BaseViewHolder<T> setImage(@IdRes int viewId, String imageUrl) {
        ImageView imageView = getView(viewId);
        Glide.with(mContext).load(imageUrl).into(imageView);
        return this;
    }
}
