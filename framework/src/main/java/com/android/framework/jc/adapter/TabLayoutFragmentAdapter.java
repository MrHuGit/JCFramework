package com.android.framework.jc.adapter;

import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.android.framework.jc.base.FkFragment;
import com.android.framework.jc.util.ResourcesUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2019/5/7 17:26
 * @describe
 * @update
 */
public class TabLayoutFragmentAdapter<T extends FkFragment> extends FkFragmentAdapter<T> {
    private final ArrayList<CharSequence> mTitleList;

    public TabLayoutFragmentAdapter(FragmentManager fm) {
        super(fm);
        this.mTitleList = new ArrayList<>();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(@StringRes int position) {
        CharSequence result;
        if (mTitleList.size() == 0) {
            result = super.getPageTitle(position);
        } else {
            result = mTitleList.get(position);
        }
        return result;
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
        addFragment(f);
        mTitleList.add(title);
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
        addFragment(f);
        mTitleList.add(ResourcesUtils.getString(titleRes));
        return this;
    }

    /**
     * 获取标题集合
     *
     * @return 标题集合
     */
    public List<CharSequence> getTitleList() {
        return mTitleList;
    }


    /**
     * 关联tabLayout和viewPager
     *
     * @param viewPager
     *         viewPager
     * @param tabLayout
     *         tabLayout
     */
    public void prepare(ViewPager viewPager, TabLayout tabLayout) {
        this.prepare(viewPager, tabLayout, null);
    }

    /**
     * 关联tabLayout和viewPager
     *
     * @param viewPager
     *         viewPager
     * @param tabLayout
     *         tabLayout
     * @param tabViewAgent
     *         tabViewAgent
     */
    public void prepare(ViewPager viewPager, TabLayout tabLayout, TabViewAgent tabViewAgent) {
        viewPager.setAdapter(this);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                getFragment(tab.getPosition()).onViewPagerSelected();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                getFragment(tab.getPosition()).onViewPagerUnSelected();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        if (tabViewAgent != null) {
            tabViewAgent.prepare(this);
        }
    }

}
