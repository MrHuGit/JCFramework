package com.android.framework_test.kline;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.framework.jc.base.FkActivity;
import com.android.framework_test.R;

/**
 * @author Mr.Hu(Jc)
 * @create 2018/7/16 14:44
 * @organize 卓世达科
 * @describe
 * @update
 */
public class KlineMainActivity extends FkActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kline_main);
    }
}
