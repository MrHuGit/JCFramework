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
 * @author Mr.Hu(Jc)
 * @create 2018/3/16 10:34
 * @describe
 * @update
 */

public class TitleViewWrapper implements IViewWrapper {
    private final String mTitle;
    private Context mContext;

    public TitleViewWrapper(String title) {
        this.mTitle = title;
    }

    @Override
    public void onWrapperAttach(Context context) {
        mContext=context;
    }

    @Override
    public View onCreateWrapperView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.wrapper_view_title,container);
        TextView tvTitle=view.findViewById(R.id.tv_wrapper_view_title);
        if (!TextUtils.isEmpty(mTitle)){
            tvTitle.setText(mTitle);
        }
        ImageView ivBack=view.findViewById(R.id.iv_back);
        ivBack.setOnClickListener(v -> {
            if (mContext instanceof Activity){
                Activity activity=(Activity)mContext;
                if (!activity.isFinishing()){
                    activity.finish();
                }
            }
        });
        return view;
    }

    @Override
    public void onWrapperDestroy() {

    }
}
