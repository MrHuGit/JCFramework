package com.android.framework_test.js;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.framework.jc.base.FkActivity;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/9/13 17:28
 * @describe
 * @update
 */
public class JsTestActivity extends FkActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        TestFragment fragment = findFragment(TestFragment.class);
//        if (fragment == null) {
//            fragment = new TestFragment();
//            putFragment(fragment);
//        }
    }

//    public static class TestFragment extends FkWebFragment {
//
//
//        @Override
//        public void onAttach(Context context) {
//            super.onAttach(context);
//        }
//
//        @Override
//        public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
//            super.onViewCreated(view, savedInstanceState);
//            mFkWebView.loadUrl("http://192.168.1.64:8803/grabredenvelope");
////            mFkWebView.loadUrl("http://www.baidu.com");
//        }
//
//        @Override
//        protected String setWebViewName() {
//            return "test";
//        }
//
//
//        @Override
//        public boolean onMessageReceive(MessageBody messageBody) {
//            return false;
//        }
//    }
}
