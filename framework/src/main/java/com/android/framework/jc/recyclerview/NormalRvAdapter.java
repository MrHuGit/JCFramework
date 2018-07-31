package com.android.framework.jc.recyclerview;

import android.support.annotation.LayoutRes;
import android.view.View;

import java.util.List;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/4/6 19:43
 * @describe recyclerView的普通adapter
 * @update
 */

public abstract class NormalRvAdapter<T> extends RvTypeAdapter<T> {

    public NormalRvAdapter(@LayoutRes int layoutId) {
        this(layoutId, null);
    }

    public NormalRvAdapter(@LayoutRes int layoutId, List<T> list) {
        super(list);
        addRvItemView(new RvItemView<T>() {
            @Override
            public int getLayoutId() {
                return layoutId;
            }

            @Override
            public void convert(ViewHolder holder, T t, int position) {
                NormalRvAdapter.this.convert(holder, t, position);
            }

            @Override
            public void notifyPayloads(ViewHolder holder, T item, int position, List<Object> payloads) {
                NormalRvAdapter.this.notifyPayloads(holder, item, position, payloads);
            }

        });

    }

    /**
     * @param view
     */
    public NormalRvAdapter(View view) {
        this(view, null);
    }

    public NormalRvAdapter(View view, List<T> list) {
        super(list);
        addRvItemView(new RvItemView<T>() {
            @Override
            public int getLayoutId() {
                return -1;
            }

            @Override
            public View getLayoutView() {
                return view;
            }

            @Override
            public void convert(ViewHolder holder, T t, int position) {
                NormalRvAdapter.this.convert(holder, t, position);
            }

            @Override
            public void notifyPayloads(ViewHolder holder, T item, int position, List<Object> payloads) {
                NormalRvAdapter.this.notifyPayloads(holder, item, position, payloads);
            }


        });


    }

    public abstract void convert(ViewHolder holder, T bean, int position);

    public void notifyPayloads(ViewHolder holder, T item, int position, List<Object> payloads) {

    }
}
