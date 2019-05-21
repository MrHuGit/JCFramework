package com.android.framework_test.module.network;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.framework.jc.wrapper.FkTitleViewWrapper;
import com.android.framework_test.R;
import com.android.framework_test.adapter.ListChooseAdapter;
import com.android.framework_test.base.BaseActivity;
import com.android.framework_test.base.BaseFragment;
import com.android.framework_test.data.bean.ListBean;
import com.android.framework_test.module.util.AppUtilsActivity;
import com.android.framework_test.module.util.Base64UtilsActivity;
import com.android.framework_test.module.util.ConvertUtilsActivity;
import com.android.framework_test.module.util.FileUtilsActivity;
import com.android.framework_test.module.util.FormatUtilsActivity;
import com.android.framework_test.module.util.LogUtilsActivity;
import com.android.framework_test.module.util.NetworkUtilsActivity;
import com.android.framework_test.module.util.PackageUtilsActivity;
import com.android.framework_test.module.util.RSAUtilsActivity;
import com.android.framework_test.module.util.ResourcesUtilsActivity;
import com.android.framework_test.module.util.ScreenUtilsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/11/8 19:15
 * @describe 网络相关列表
 * @update
 */
public class NetworkListActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NetworkListFragment fragment = findFragment(NetworkListFragment.class);
        if (fragment == null) {
            fragment = NetworkListFragment.newFragment();
            putFragment(fragment);
        }
    }

    public static class NetworkListFragment extends BaseFragment {

        private RecyclerView mRecyclerView;
        private ListChooseAdapter mAdapter;

        public static NetworkListFragment newFragment() {
            return new NetworkListFragment();
        }

        @Override
        public void onAttach(Context context) {
            addHeadWrapper(new FkTitleViewWrapper("网络相关列表"));
            super.onAttach(context);
        }

        @Override
        protected View onCreateRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_recycler_view, null);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            mRecyclerView = view.findViewById(R.id.recycler_view);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            mRecyclerView.setAdapter(mAdapter = new ListChooseAdapter());
            mAdapter.setOnItemClickListener((view1, holder, listBean, position) -> startActivity(new Intent(mContext, listBean.getActivityClass())));
            addList();
        }

        private void addList() {
            List<ListBean> list = new ArrayList<>();
            list.add(new ListBean("AppUtils", AppUtilsActivity.class));
            list.add(new ListBean("Base64Utils", Base64UtilsActivity.class));
            list.add(new ListBean("ConvertUtils", ConvertUtilsActivity.class));
            list.add(new ListBean("FileUtils", FileUtilsActivity.class));
            list.add(new ListBean("FormatUtils", FormatUtilsActivity.class));
            list.add(new ListBean("LogUtils", LogUtilsActivity.class));
            list.add(new ListBean("NetworkUtils", NetworkUtilsActivity.class));
            list.add(new ListBean("PackageUtils", PackageUtilsActivity.class));
            list.add(new ListBean("ResourcesUtils", ResourcesUtilsActivity.class));
            list.add(new ListBean("RSAUtils", RSAUtilsActivity.class));
            list.add(new ListBean("ScreenUtils", ScreenUtilsActivity.class));
            mAdapter.setList(list).notifyDataSetChanged();
        }
    }
}
