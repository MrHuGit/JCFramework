package com.android.framework.jc.base;

import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2019/5/7 17:26
 * @describe TabLayoutFragmentAdapter
 * @update
 */
public class TabLayoutFragmentAdapter<T extends FkFragment & ITabListener> extends FkFragmentAdapter<T> {
    public TabLayoutFragmentAdapter(FragmentManager fm) {
        super(fm);
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
                if ( getFragment(tab.getPosition())!=null) {
                    getFragment(tab.getPosition()).onTabSelected();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if ( getFragment(tab.getPosition())!=null) {
                    getFragment(tab.getPosition()).onTabUnselected();
                }
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
