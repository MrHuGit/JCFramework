package com.android.framework.jc.recyclerview;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/3/23 15:31
 * @describe
 * @update
 */

public class ViewHolder extends RecyclerView.ViewHolder {

    private final Context mContext;
    private final View mConvertView;
    private final SparseArray<View> mViews;

    private ViewHolder(Context context, View itemView) {
        super(itemView);
        mContext = context;
        mConvertView = itemView;
        mViews = new SparseArray<>();
    }

    public static ViewHolder create(Context context, View itemView) {
        return new ViewHolder(context, itemView);
    }

    public static ViewHolder create(Context context, ViewGroup parent, @LayoutRes int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new ViewHolder(context, itemView);
    }

    public View getConvertView() {
        return mConvertView;
    }

    /**
     * 通过viewId获取控件
     *
     * @param viewId
     *
     * @return
     */
    public <T extends View> T getView(@IdRes int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public ViewHolder setText(@IdRes int viewId,String value){
        TextView textView=getView(viewId);
        textView.setText(value);
        return this;
    }
    public ViewHolder setText(@IdRes int viewId,@StringRes int valueId){
        TextView textView=getView(viewId);
        textView.setText(valueId);
        return this;
    }

    public ViewHolder setVisibility(@IdRes int viewId, int visibility){
        getView(viewId).setVisibility(visibility);
        return this;
    }

}
