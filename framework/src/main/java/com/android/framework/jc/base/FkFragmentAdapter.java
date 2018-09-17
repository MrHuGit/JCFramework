package com.android.framework.jc.base;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.android.framework.jc.util.ResourcesUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/8/3 18:09
 * @describe FragmentAdapter
 * @update
 */
public class FkFragmentAdapter<T extends Fragment> extends FragmentPagerAdapter {
    private final List<CharSequence> mTitleList;
    private final List<Integer> mTitleResList;
    private final List<T> mFragmentList;

    public FkFragmentAdapter(FragmentManager fm) {
        super(fm);
        this.mFragmentList = new ArrayList<>();
        this.mTitleList = new ArrayList<>();
        this.mTitleResList = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(@StringRes int position) {
        CharSequence result;
        if (mTitleList.size() == 0) {
            if (mTitleResList.size() == 0) {
                result = super.getPageTitle(position);
            } else {
                result = ResourcesUtils.getString(mTitleResList.get(position));
            }
        } else {
            result = mTitleList.get(position);
        }
        return result;
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
     * 添加Fragment
     *
     * @param f
     *         Fragment
     * @param title
     *         标题
     *
     * @return 当前对象
     */
    public FkFragmentAdapter<T> addFragment(T f, CharSequence title) {
        mTitleList.add(title);
        mFragmentList.add(f);
        return this;
    }

    /**
     * 添加Fragment
     *
     * @param f
     *         Fragment
     * @param titleRes
     *         标题资源id
     *
     * @return 当前对象
     */
    public FkFragmentAdapter<T> addFragment(T f, int titleRes) {
        mTitleResList.add(titleRes);
        mFragmentList.add(f);
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

    /**
     * 获取标题集合
     *
     * @return 标题集合
     */
    public List<CharSequence> getTitleList() {
        return mTitleList;
    }
}
