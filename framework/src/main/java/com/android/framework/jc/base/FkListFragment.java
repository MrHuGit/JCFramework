package com.android.framework.jc.base;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.framework.jc.R;
import com.android.framework.jc.adapter.recyclerview.RvTypeAdapter;
import com.android.framework.jc.wrapper.IViewWrapper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

/**
 * @author Mr.Hu(Jc)
 * @create 2018/9/18 14:43
 * @describe 列表fragment封装
 * @update
 */
public abstract class FkListFragment<D> extends FkFragment implements OnRefreshLoadMoreListener {
    private SmartRefreshLayout smartRefresh;
    private LinearLayout llHeadParent;
    private LinearLayout llLoadingParent;
    private LinearLayout llNoDataParent;
    private RecyclerView recyclerView;
    private LinearLayout llFootParent;
    private RvTypeAdapter<D> mAdapter;
    private int mPageIndex=1;

    @Override
    protected View onCreateRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fk_list, null);
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        smartRefresh = (SmartRefreshLayout) view.findViewById(R.id.smart_refresh);
        llHeadParent = (LinearLayout) view.findViewById(R.id.ll_head_parent);
        llLoadingParent = (LinearLayout) view.findViewById(R.id.ll_loading_parent);
        llNoDataParent = (LinearLayout) view.findViewById(R.id.ll_no_data_parent);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        llFootParent = (LinearLayout) view.findViewById(R.id.ll_foot_parent);
        initData();
        setListener();
        loadData(mPageIndex);
    }




    @CallSuper
    protected void initData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(mAdapter = createAdapter());
        llLoadingParent.addView(setLoadingView());
        llNoDataParent.addView(setNoDataView());
        showLoading();


    }
    @CallSuper
    protected void setListener() {
        smartRefresh.setEnableRefresh(setEnableRefresh());
        smartRefresh.setEnableLoadMore(setEnableLoadMore());
        smartRefresh.setOnRefreshLoadMoreListener(this);
    }


    /**
     * 在列表上面添加布局（此布局添加后在下拉刷新布局里面，
     * 下拉刷新、上拉加载的时候会跟随一起动，如果不想跟随一起动请调用{@link FkFragment#addHeadWrapper(IViewWrapper)}）
     * @param view 列表上面的布局
     */
    protected void setHeadView(View view) {
        if (view != null) {
            llHeadParent.setVisibility(View.VISIBLE);
            llHeadParent.addView(view);
        }
    }

    /**
     * 在列表下面添加布局（此布局添加后在下拉刷新布局里面，
     * 下拉刷新、上拉加载的时候会跟随一起动如果不想跟随一起动请调用{@link FkFragment#addFootWrapper(IViewWrapper)} }）
     * @param view 列表下面的布局
     */
    protected void setFootView(View view) {
        if (view != null) {
            llFootParent.addView(view);
            llFootParent.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置是否允许下拉刷新
     * @return 是否允许下拉刷新
     */
    protected boolean setEnableRefresh(){
        return true;
    }

    /**
     * 设置是否允许上拉加载更多
     * @return 是否允许上拉加载更多
     */
    protected boolean setEnableLoadMore(){
        return true;
    }

    /**
     * 加载数据
     * @param pageIndex 一般列表都是分页加载，表示页面id
     */
    protected abstract void loadData(int pageIndex);

    /**
     * 设置正在加载显示的View
     * @return 正在加载显示的View
     */
    protected abstract @NonNull View setLoadingView();

    /**
     * 设置没有数据的时候显示的View
     * @return 没有数据的时候显示的View
     */
    protected abstract @NonNull View setNoDataView();

    /**
     * 设置数据显示的适配器
     * @return adapter
     */
    protected abstract RvTypeAdapter<D> createAdapter();

    /**
     * 加载数据的时候显示的布局（只有第一次进来的时候才会显示）
     */
    private void showLoading() {
        llLoadingParent.setVisibility(View.VISIBLE);
        llNoDataParent.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
    }

    /**
     * 没有数据的时候显示的布局
     */
    private void showNoData() {
        llLoadingParent.setVisibility(View.GONE);
        llNoDataParent.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    /**
     * 显示数据布局
     */
    private void showData() {
        llLoadingParent.setVisibility(View.GONE);
        llNoDataParent.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    @CallSuper
    public void onLoadMore(RefreshLayout refreshLayout) {
        mPageIndex++;
        loadData(mPageIndex);
    }

    @Override
    @CallSuper
    public void onRefresh(RefreshLayout refreshLayout) {
        smartRefresh.setNoMoreData(false);
        mPageIndex=1;
        loadData(mPageIndex);

    }

    /**
     * 首次加载数据或者下拉刷新设置数据调用此方法
     * @param list 数据集合
     */
    protected void notifyRefreshData(List<D> list) {
        if (list == null || list.size() <= 0) {
            showNoData();
        } else {
            showData();
        }
        finisRefresh();
        mAdapter.setList(list);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 上拉加载更多调用此方法设置数据
     * @param list 数据集合
     */
    protected void notifyLoadMoreData(List<D> list){
        boolean noMoreData;
        if (list==null|| list.size() <= 0){
            noMoreData=true;
        }else{
            noMoreData=false;
            mAdapter.getList().addAll(list);
            mAdapter.notifyDataSetChanged();
        }

        finisLoadMore(noMoreData);
    }

    private void finisRefresh(){
        if (smartRefresh!=null&&smartRefresh.isRefreshing()){
            smartRefresh.finishRefresh();
        }
    }

    private void finisLoadMore(boolean noMoreData){
        if (smartRefresh!=null&&smartRefresh.isLoading()){
            smartRefresh.finishLoadMore();
            smartRefresh.setNoMoreData(noMoreData);
        }
    }
}
