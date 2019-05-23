//package com.android.framework.jc.adapter.recyclerview.deprecated;
//
//import android.support.annotation.LayoutRes;
//import android.support.annotation.NonNull;
//import android.view.ViewGroup;
//
//import java.util.List;
//
///**
// * @author Mr.Hu(Jc) JCFramework
// * @create 2018/4/6 19:43
// * @describe recyclerView的普通adapter
// * @update
// */
//@Deprecated
//public abstract class NormalRvAdapter<T> extends RvTypeAdapter<T> {
//    private boolean emptyType = false;
//
//    private final static int EMPTY_ITEM_TYPE = 1;
//    private final static int DATA_ITEM_TYPE = 2;
//
//    public NormalRvAdapter(@LayoutRes int dataLayoutId, @LayoutRes int emptyLayoutId) {
//        this(dataLayoutId, emptyLayoutId, null);
//
//    }
//
//    public NormalRvAdapter(@LayoutRes int layoutId) {
//        this(layoutId, 0, null);
//    }
//
//
//    public NormalRvAdapter(@LayoutRes int layoutId, int emptyLayoutId, List<T> list) {
//        super(list);
//        if (emptyLayoutId > 0) {
//            emptyType = true;
//            addRvItemView(EMPTY_ITEM_TYPE, new RvItemView<T>() {
//                @Override
//                public int getLayoutId() {
//                    return emptyLayoutId;
//                }
//
//                @Override
//                public boolean checkViewType(T item, int position) {
//                    return super.checkViewType(item, position);
//                }
//                @Override
//                public void convert(ViewHolder holder, T t, int position) {
//                }
//            });
//        }
//        addRvItemView(DATA_ITEM_TYPE, new RvItemView<T>() {
//            @Override
//            public int getLayoutId() {
//                return layoutId;
//            }
//
//            @Override
//            public void convert(ViewHolder holder, T t, int position) {
//                NormalRvAdapter.this.convert(holder, t, position);
//            }
//        });
//    }
//
//    @Override
//    public RvTypeAdapter<T> setList(List<T> list) {
//        return super.setList(list);
//    }
//
//    @Override
//    public int getItemCount() {
//        if (emptyType) {
//            if (mList == null || mList.size() < 1) {
//                return 1;
//            }
//        }
//        return super.getItemCount();
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        if (emptyType) {
//            if (mList == null || mList.size() < 1) {
//                return EMPTY_ITEM_TYPE;
//            }
//        }
//        return DATA_ITEM_TYPE;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return super.onCreateViewHolder(parent, viewType);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        if (getItemViewType(position) == EMPTY_ITEM_TYPE) {
//            convertEmpty(holder);
//        } else {
//            super.onBindViewHolder(holder, position);
//        }
//
//
//    }
//
//    protected void convertEmpty(ViewHolder holder) {
//
//    }
//
//    /**
//     * 绑定数据
//     *
//     * @param holder
//     *         holder
//     * @param t
//     *         数据bean
//     * @param position
//     *         position
//     */
//    protected abstract void convert(ViewHolder holder, T t, int position);
//
//
//}
