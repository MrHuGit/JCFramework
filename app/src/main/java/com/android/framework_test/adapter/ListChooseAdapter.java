package com.android.framework_test.adapter;

import com.android.framework.jc.adapter.recyclerview.BaseViewHolder;
import com.android.framework.jc.adapter.recyclerview.NormalRvAdapter;
import com.android.framework_test.R;
import com.android.framework_test.data.bean.ListBean;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/11/9 09:39
 * @describe
 * @update
 */
public class ListChooseAdapter extends NormalRvAdapter<ListBean> {
    public ListChooseAdapter() {
        super(R.layout.item_list_choose,R.layout.item_empty,ListBean.class);
    }


    @Override
    protected void onConvert(BaseViewHolder<ListBean> viewHolder, ListBean t, int position) {

    }
}
