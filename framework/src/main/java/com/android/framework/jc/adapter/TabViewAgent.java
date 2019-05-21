package com.android.framework.jc.adapter;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2019/5/10 11:20
 * @describe tabLayout中tab的自定义view代理
 * @update
 */
public class TabViewAgent implements TabLayout.BaseOnTabSelectedListener {
    private final Context mContext;
    private final TabLayout mTabLayout;
    private final List<Integer> mSelectedImgList;
    private final List<Integer> mUnselectedImgList;
    private final List<CharSequence> mTitleList;

    public TabViewAgent(Context context, TabLayout tabLayout, List<Integer> selectImgList, List<Integer> unselectedImgList) {
        this(context, tabLayout, selectImgList, unselectedImgList, null);
    }

    public TabViewAgent(Context context, TabLayout tabLayout, List<Integer> selectImgList, List<Integer> unselectedImgList, List<CharSequence> titleList) {
        this.mContext = context;
        this.mTabLayout = tabLayout;
        this.mTitleList = titleList;
        this.mSelectedImgList = selectImgList;
        this.mUnselectedImgList = unselectedImgList;
        init();
    }

    private void init() {
        this.mTabLayout.addOnTabSelectedListener(this);
    }

    View getCustomView(int position, CharSequence title) {
        return new DefaultView(mContext, title, mSelectedImgList.get(position), mUnselectedImgList.get(position));
    }


    public void prepare(PagerAdapter adapter) {
        boolean initTitle = mTitleList != null;
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);

            CharSequence title;
            if (initTitle) {
                title = mTitleList.get(i);
            } else {
                title = adapter.getPageTitle(i);
            }
            if (tab != null) {
                tab.setCustomView(getCustomView(i, title));
            }
        }
    }

    /**
     * Tab获取焦点
     *
     * @param tab
     *         tab
     */
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        changeTabSelect(tab);
    }

    /**
     * Tab失去焦点
     *
     * @param tab
     *         tab
     */
    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        changeTabUnselected(tab);
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    class DefaultView extends LinearLayout {
        private final Context mContext;
        private final int mSelectedImgRes;
        private final int mUnselectedImgRes;
        private final CharSequence mTitle;
        private ImageView mImageView;
        private TextView mTextView;

        public DefaultView(Context context, CharSequence title, @DrawableRes int selectedImgRes, @DrawableRes int unselectedImgRes) {
            super(context);
            this.mContext = context;
            this.mTitle = title;
            this.mSelectedImgRes = selectedImgRes;
            this.mUnselectedImgRes = unselectedImgRes;
            initView();
        }

        private void initView() {
            mImageView = new ImageView(mContext);
            mImageView.setImageResource(mUnselectedImgRes);
            mTextView = new TextView(mContext);
            mTextView.setTextColor(mTabLayout.getTabTextColors());
            mTextView.setText(mTitle);
            setGravity(Gravity.CENTER);
            setOrientation(VERTICAL);
            addView(mImageView);
            addView(mTextView);
        }

        @Override
        public void setSelected(boolean selected) {
            super.setSelected(selected);
            if (selected) {
                mImageView.setImageResource(mSelectedImgRes);
            } else {
                mImageView.setImageResource(mUnselectedImgRes);
            }


        }
    }

    /**
     * 改变TabLayout的View到选中状态
     * 使用属性动画改编Tab中View的状态
     */
     void changeTabSelect(TabLayout.Tab tab) {
        final View view = tab.getCustomView();
        if (view != null) {
            @SuppressLint("ObjectAnimatorBinding")
            ObjectAnimator anim = ObjectAnimator
                    .ofFloat(view, "", 1.0F, 1.1F)
                    .setDuration(200);
            anim.start();
            anim.addUpdateListener(animation -> {
                float cVal = (Float) animation.getAnimatedValue();
                view.setAlpha(0.5f + (cVal - 1f) * (0.5f / 0.1f));
                view.setScaleX(cVal);
                view.setScaleY(cVal);
            });
        }


    }

    /**
     * 改变TabLayout的View到未选中状态
     */
     void changeTabUnselected(TabLayout.Tab tab) {
        final View view = tab.getCustomView();
        if (view != null) {
            @SuppressLint("ObjectAnimatorBinding")
            ObjectAnimator anim = ObjectAnimator
                    .ofFloat(view, "", 1.0F, 0.9F)
                    .setDuration(200);
            anim.start();
            anim.addUpdateListener(animation -> {
                float cVal = (Float) animation.getAnimatedValue();
                view.setAlpha(1f - (1f - cVal) * (0.5f / 0.1f));
                view.setScaleX(cVal);
                view.setScaleY(cVal);
            });
        }

    }
}
