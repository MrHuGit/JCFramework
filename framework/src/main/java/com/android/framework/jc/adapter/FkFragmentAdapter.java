package com.android.framework.jc.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/8/3 18:09
 * @describe FragmentAdapter
 * @update
 */
public class FkFragmentAdapter<T extends Fragment> extends FragmentPagerAdapter {
    private final List<T> mFragmentList;

    public FkFragmentAdapter(FragmentManager fm) {
        super(fm);
        this.mFragmentList = new ArrayList<>();

    }

    @Override
    public T getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    /**
     * 添加Fragment
     *
     * @param t
     *         Fragment
     *
     * @return 当前对象
     */
    public FkFragmentAdapter<T> addFragment(@NonNull T t) {
        mFragmentList.add(t);
        return this;
    }



    /**
     * 根据position获取对应的Fragment
     *
     * @param position
     *         position
     *
     * @return 对应的Fragment
     */
    public T getFragment(int position) {
        return mFragmentList.size() > position ? mFragmentList.get(position) : null;
    }

    /**
     * 获取Fragment集合
     *
     * @return Fragment集合
     */
    public List<T> getFragmentList() {
        return mFragmentList;
    }


}
