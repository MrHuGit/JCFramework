package com.android.framework.jc.wrapper;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.framework.jc.R;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/3/16 10:34
 * @describe 默认的标准的标题栏
 * @update
 */

public class FkTitleViewWrapper implements IViewWrapper {
    private final String mTitle;

    public FkTitleViewWrapper(String title) {
        this.mTitle = title;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Context context=inflater.getContext();
        View view = inflater.inflate(R.layout.wrapper_view_title, null);
        TextView tvTitle = view.findViewById(R.id.tv_wrapper_view_title);
        if (!TextUtils.isEmpty(mTitle)) {
            tvTitle.setText(mTitle);
        }
        ImageView ivBack = view.findViewById(R.id.iv_back);
        ivBack.setOnClickListener(v -> {
            if (context instanceof Activity) {
                Activity activity = (Activity) context;
                if (!activity.isFinishing()) {
                    activity.finish();
                }
            }
        });
        return view;
    }

}
