package com.android.framework_test.module;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.android.framework.jc.FkDownload;
import com.android.framework.jc.MessageManager;
import com.android.framework.jc.adapter.recyclerview.BaseViewHolder;
import com.android.framework.jc.adapter.recyclerview.NormalRvAdapter;
import com.android.framework.jc.base.AppStateManager;
import com.android.framework.jc.message.IModule;
import com.android.framework.jc.message.body.MessageBody;
import com.android.framework.jc.widget.WebViewWrap;
import com.android.framework_test.R;
import com.android.framework_test.base.BaseActivity;
import com.tencent.tinker.lib.tinker.TinkerInstaller;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2019/5/16 17:50
 * @describe
 * @update
 */
public class TinkerTestActivity extends BaseActivity implements IModule {
    private TextView tvTinker;
    private RecyclerView recyclerView;
    private NormalRvAdapter<String> mAdapter;
    private WebViewWrap webViewWrap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MessageManager.getInstance().register("test",this);
        AppStateManager.getInstance().setAppRunningState();
        setContentView(R.layout.activity_tinker_test);
        tvTinker = (TextView) findViewById(R.id.tv_tinker);
        webViewWrap = (WebViewWrap) findViewById(R.id.web_view_wrap);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        initData();
        setListener();

    }

    /**
     * 初始化数据
     */
    private void initData() {
        webViewWrap.loadUrl("http://tnode.exxstar.com/Exx_Double_Festival_h5?lan=zh");
        webViewWrap.registerModule("activityCenter","exx");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter = new NormalRvAdapter<String>(R.layout.item_list_choose, String.class) {
            @Override
            protected void onConvert(BaseViewHolder<String> viewHolder, String t, int position) {

            }
        });
        List<String> list = new ArrayList<>();
        list.add("1111111");
        list.add("1111111");
        list.add("1111111");
        list.add("1111111");
        list.add("1111111");
        list.add("1111111");
        list.add("1111111");
        mAdapter.setList(list).notifyItemRangeChanged(0, list.size());
    }

    /**
     * 设置监听
     */
    private void setListener() {
        tvTinker.setOnClickListener(v ->
                FkDownload.builder()
                        .setOnFinishListener(savePath -> TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(), savePath))
                        .downloadUrl("")
                        .build()
                        .onStartDownload(TinkerTestActivity.this));
    }

    @Override
    public boolean onMessageReceive(MessageBody messageBody) {
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MessageManager.getInstance().unRegister("activityCenter");
    }
}
