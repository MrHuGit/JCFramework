package com.android.framework_test.adapter;

import com.android.framework.jc.recyclerview.NormalRvAdapter;
import com.android.framework.jc.recyclerview.ViewHolder;
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
        super(R.layout.item_list_choose);
    }

    @Override
    public void convert(ViewHolder holder, ListBean bean, int position) {
        holder.setText(R.id.tv_name, bean.getName());
    }
}
