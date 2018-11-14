package com.android.framework_test.module;

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
import android.widget.Toast;

import com.android.framework.jc.JcFramework;
import com.android.framework.jc.base.AppStateManager;
import com.android.framework_test.R;
import com.android.framework_test.adapter.ListChooseAdapter;
import com.android.framework_test.base.BaseActivity;
import com.android.framework_test.base.BaseFragment;
import com.android.framework_test.data.bean.ListBean;
import com.android.framework_test.module.util.UtilListActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/11/8 19:15
 * @describe
 * @update
 */
public class MainActivity extends BaseActivity {
    private long mFirstPressedBackKeyTime = 0;
    private final static int INTERVAL_TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        AppStateManager.getInstance().setAppRunningState();
        MainFragment fragment = findFragment(MainFragment.class);
        if (fragment == null) {
            fragment = MainFragment.newFragment();
            putFragment(fragment);
        }

    }
    @Override
    public void onBackPressed() {
        long secondPressBackKeyTime = System.currentTimeMillis();
        if (secondPressBackKeyTime - mFirstPressedBackKeyTime > INTERVAL_TIME) {
            Toast.makeText(this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
            mFirstPressedBackKeyTime = secondPressBackKeyTime;
        } else {
            mFirstPressedBackKeyTime = 0;
            JcFramework.exitApp();
        }
    }
    public static class MainFragment extends BaseFragment {
        private RecyclerView mRecyclerView;
        private ListChooseAdapter mAdapter;

        public static MainFragment newFragment() {
            return new MainFragment();
        }

        @Override
        public void onAttach(Context context) {
//            addHeadWrapper(new TitleViewWrapper("工具类列表"));
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
            list.add(new ListBean("工具", UtilListActivity.class));
            mAdapter.setList(list).notifyDataSetChanged();
        }

    }

}
