package com.android.framework.jc.adapter.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2019-05-21 19:14
 * @describe
 * @update
 */
public class RvTypeAdapter extends RecyclerView.Adapter<BaseViewHolder<?>> {
    /**
     * 默认添加到队列最后
     */
    private final static int INDEX_DEFAULT = Integer.MAX_VALUE;
    /**
     * 数据集合（在原始数据基础上进行了封装）
     */
    @NonNull
    protected final List<WrapData<?>> mDataList;
    /**
     * ViewHolder构造器集合
     */
    private final SparseArray<IViewHolderCreator<?, ? extends BaseViewHolder<?>>> mViewHolderCreatorMap;
    /**
     * item单击监听集合
     */
    private final SparseArray<IAdapterListener.OnItemClickListener> mItemClickListeners;
    /**
     * item长按监听集合
     */
    private final SparseArray<IAdapterListener.OnItemLongClickListener> mItemLongClickListeners;

    RvTypeAdapter() {
        mViewHolderCreatorMap = new SparseArray<>();
        mItemClickListeners = new SparseArray<>();
        mItemLongClickListeners = new SparseArray<>();
        mDataList = new ArrayList<>();
    }

    @NonNull
    @Override
    public BaseViewHolder<?> onCreateViewHolder(@NonNull ViewGroup viewGroup, int itemType) {
        IViewHolderCreator viewHolderCreator = mViewHolderCreatorMap.get(itemType);
        if (viewHolderCreator != null) {
            return viewHolderCreator.create(viewGroup).onAttachAdapter(this);
        }
        // 没有找到数据类型对应的ViewHolder抛出异常。
        throw new RuntimeException("当前数据类型没有注册对应的ViewHolder类型，itemType = " + itemType);
    }

    /**
     * 注册ViewHolder构造器
     *
     * @param itemType
     *         itemType
     * @param viewHolderCreator
     *         viewHolderCreator
     * @param <D>
     *         数据泛型
     * @param <ViewHolder>
     *         viewHolder泛型
     */
    public <D, ViewHolder extends BaseViewHolder<D>> void registerViewHolder(int itemType, @NonNull IViewHolderCreator<D, ViewHolder> viewHolderCreator) {
        if (mViewHolderCreatorMap.indexOfKey(itemType) < 0) {
            mViewHolderCreatorMap.put(itemType, viewHolderCreator);
        } else {
            throw new RuntimeException("重复注册itemType=" + itemType);
        }
    }

    /**
     * 取消ViewHolder注册
     *
     * @param itemType
     *         itemType
     */
    public void unRegisterViewHolder(int itemType) {
        mViewHolderCreatorMap.remove(itemType);
    }

    /**
     * 设置长按监听
     *
     * @param itemType
     *         itemType
     * @param longClickListener
     *         longClickListener
     * @param <D>
     *         数据泛型
     */
    public <D> void setOnItemLongClickListener(int itemType, IAdapterListener.OnItemLongClickListener<D> longClickListener) {
        if (longClickListener != null) {
            mItemLongClickListeners.put(itemType, longClickListener);
        }
    }

    /**
     * 设置单击简体
     *
     * @param itemType
     *         itemType
     * @param clickListener
     *         clickListener
     * @param <D>
     *         数据泛型
     */
    public <D> void setOnItemClickListener(int itemType, IAdapterListener.OnItemClickListener<D> clickListener) {
        if (clickListener != null) {
            mItemClickListeners.put(itemType, clickListener);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<?> holder, int position) {
        WrapData<?> wrapData = mDataList.get(position);
        Object data = wrapData.getData();
        int itemType = wrapData.getItemType();
        //noinspection unchecked
        holder.bindData(data, position, mItemClickListeners.get(itemType), mItemLongClickListeners.get(itemType));
    }


    @Override
    public int getItemViewType(int position) {
        return mDataList.get(position).getItemType();
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    /**
     * 检测数据是否匹配
     *
     * @param itemType
     *         itemType
     * @param d
     *         数据
     * @param <D>
     *         数据类型
     */
    <D> void checkData(int itemType, D d) {
        int dataClassKey = getKey(d.getClass());
        int holderDataClassKey = getKey(mViewHolderCreatorMap.get(itemType).getDataClass());
        if (dataClassKey != holderDataClassKey) {
            throw new RuntimeException("添加数据bean的类型与注册ViewHolder的数据bean的类型不匹配");
        }
    }

    /**
     * 本质还是取HashCode，这里主要是为了防止类复写HashCode，引发无法准确定位数据类型。
     *
     * @param clazz
     *         类类型
     *
     * @return HashCode
     */
    private static int getKey(Class<?> clazz) {
        return System.identityHashCode(clazz);
    }


    /**
     * 设置新数据（清除之前的所有数据）
     *
     * @param itemType
     *         itemType
     * @param list
     *         list
     * @param <T>
     *         数据泛型
     */
    protected <T> void setList(int itemType, List<T> list) {
        mDataList.clear();
        if (list != null && list.size() > 0) {
            checkData(itemType, list.get(0));
            int count = list.size();
            List<WrapData<T>> addList = new ArrayList<>(count);
            for (T t : list) {
                addList.add(new WrapData<>(itemType, t));
            }
            mDataList.addAll(addList);
        }
        onAfterDataChanged();
    }


    /**
     * 添加数据集合
     *
     * @param itemType
     *         itemType
     * @param list
     *         list
     * @param <T>
     *         数据泛型
     */
    protected <T> void addItemList(int itemType, List<T> list) {
        addItemList(itemType, INDEX_DEFAULT, list);
    }

    /**
     * 添加数据集合
     *
     * @param itemType
     *         itemType
     * @param position
     *         位置
     * @param list
     *         list
     * @param <T>
     *         数据泛型
     */
    protected <T> void addItemList(int itemType, int position, List<T> list) {
        if (list != null && list.size() > 0) {
            checkData(itemType, list.get(0));
            int count = list.size();
            List<WrapData<T>> addList = new ArrayList<>(count);
            for (T t : list) {
                addList.add(new WrapData<>(itemType, t));
            }
            if (position == INDEX_DEFAULT) {
                mDataList.addAll(addList);
            } else {
                mDataList.addAll(position, addList);
            }
            onAfterDataChanged();
        }
    }

    /**
     * 添加单个数据
     *
     * @param itemType
     *         itemType
     * @param t
     *         数据bean
     * @param <T>
     *         数据泛型
     */
    protected <T> void addItem(int itemType, T t) {
        addItem(itemType, INDEX_DEFAULT, t);
    }

    /**
     * 添加单个数据
     *
     * @param itemType
     *         itemType
     * @param position
     *         position
     * @param t
     *         数据bean
     * @param <T>
     *         数据泛型
     */
    protected <T> void addItem(int itemType, int position, T t) {
        if (t != null) {
            checkData(itemType, t);
            if (position == INDEX_DEFAULT) {
                mDataList.add(new WrapData<>(itemType, t));
            } else {
                mDataList.add(position, new WrapData<>(itemType, t));
            }
            onAfterDataChanged();
        }
    }

    /**
     * 移除指定类型的所有数据
     *
     * @param itemType
     *         类型
     */
    protected void removeItemType(int itemType) {
        boolean change = false;
        Iterator<WrapData<?>> iterator = mDataList.iterator();
        while (iterator.hasNext()) {
            WrapData<?> wrapData = iterator.next();
            if (itemType == wrapData.getItemType()) {
                iterator.remove();
                change = true;
            }
        }
        if (change) {
            onAfterDataChanged();
        }
    }

    /**
     * 移除指定位置
     *
     * @param position
     *         position
     */
    protected void remove(int position) {
        if (position < mDataList.size()) {
            mDataList.remove(position);
            onAfterDataChanged();
        }
    }


    /**
     * 调用数据处理之后调用此方法
     */
    protected void onAfterDataChanged() {

    }

}
