package com.android.framework_test.module;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.framework.jc.FkScheduler;
import com.android.framework.jc.JcFramework;
import com.android.framework.jc.NetworkManager;
import com.android.framework.jc.adapter.recyclerview.BaseViewHolder;
import com.android.framework.jc.adapter.recyclerview.NormalRvAdapter;
import com.android.framework.jc.base.AppStateManager;
import com.android.framework.jc.data.network.interceptor.FkLogInterceptor;
import com.android.framework.jc.util.LogUtils;
import com.android.framework.jc.widget.NumberInputFilter;
import com.android.framework_test.R;
import com.android.framework_test.base.BaseActivity;
import com.android.framework_test.base.BaseFragment;
import com.android.framework_test.data.bean.ListBean;
import com.android.framework_test.module.util.UtilListActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

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
//         FkDownload.builder()
//                .cache(false)
//                .downloadUrl("http://192.168.4.180:9903/react_native.zip")
//                .setOnFinishListener(LogUtils::i)
//                .setOnErrorListener(throwable -> ToastUtils.toast(this,throwable.getMessage()))
//                .build()
//                .onStartDownload(this);
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
        private NormalRvAdapter<ListBean> mAdapter;
        private TextView tvAddItem;

        public static MainFragment newFragment() {
            return new MainFragment();
        }

        @Override
        public void onAttach(Context context) {
//            addHeadWrapper(new FkTitleViewWrapper("工具类列表"));
            super.onAttach(context);
        }

        @Override
        protected View onCreateRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.activity_main, null);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            mRecyclerView = view.findViewById(R.id.recycler_view);
            tvAddItem = (TextView) view.findViewById(R.id.tv_add_item);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            mRecyclerView.setAdapter(mAdapter = new NormalRvAdapter<ListBean>(R.layout.item_list_choose, R.layout.item_empty, ListBean.class) {
                @Override
                protected void onConvert(BaseViewHolder<ListBean> viewHolder, ListBean t, int position) {
                    viewHolder.setText(R.id.tv_name, t.getName());
                }

            });
            EditText et = view.findViewById(R.id.et_input_test);
            et.setFilters(new InputFilter[]{new NumberInputFilter(2)});
            network();
            mRecyclerView.setOnClickListener(v -> network());
            mAdapter.setOnItemClickListener((view1, holder, listBean, position) -> startActivity(new Intent(mContext, listBean.getActivityClass())));
            DefaultItemAnimator itemAnimator = new DefaultItemAnimator();
            itemAnimator.setChangeDuration(800);
            itemAnimator.setAddDuration(800);
            mRecyclerView.setItemAnimator(itemAnimator);
            final boolean[] notify = {true};
            tvAddItem.setOnClickListener(v -> {
                mAdapter.showLoadingView(R.layout.item_loading);
                FkScheduler.runOnDelay(() -> {
                    List<ListBean> list = new ArrayList<>();
                    if (notify[0]) {
                        list.add(new ListBean("工具" + Math.random(), UtilListActivity.class));
                        list.add(new ListBean("工具" + Math.random(), UtilListActivity.class));
                    }
                    mAdapter.setList(list).notifyDataSetChanged();
                    notify[0] = !notify[0];
                }, 3000);


            });
            LogUtils.i(MainFragment.class, Environment.getExternalStorageDirectory().getAbsolutePath());
        }


        private void network() {
            HashMap<String, String> params = new HashMap<>();
            params.put("appKey", "qfTI9eawMbOtN18EKSiQaOuYUO6EZPK3KRYlor9BXqCnX2iJkrArrA2PxEtOxcRiXE9cZOh6zblwvICTXn7BYN5G");
            params.put("lang", "1");
            params.put("osType", "2");
            params.put("sign", "bbc1f6d5e16bfed1614bcdf8c05ec8d7");
            NetworkManager.getInstance().createService("http://tmain.exxstar.com/", Service.class)
                    .test(params)
                    .observeOn(Schedulers.io())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Consumer<ResponseBody>() {
                        @Override
                        public void accept(ResponseBody responseBody) throws Exception {
                            LogUtils.i(FkLogInterceptor.class, responseBody.string());
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {

                        }
                    });
        }

    }


    interface Service {
        @POST("darkcore/m/banner/H5Url/getH5Url")
        Flowable<ResponseBody> test(@QueryMap Map<String, String> params);
    }

}
