package com.android.framework_test.js;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.android.framework.jc.base.FkActivity;
import com.android.framework.jc.base.FkWebFragment;
import com.android.framework.jc.module.body.MessageBody;

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
        TestFragment fragment = findFragment(TestFragment.class);
        if (fragment == null) {
            fragment = new TestFragment();
            addFragment(fragment);
        }

    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        JSONObject jsonObject=new JSONObject();
//        try {
//            jsonObject.put("test","content");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        MessageBody messageBody=new MessageBody(10005,jsonObject);
//
//
//        return true;
//    }

    public static class TestFragment extends FkWebFragment {


        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
//            addHeadWrapper(new IViewWrapper() {
//                @Override
//                public void onWrapperAttach(Context context) {
//
//                }
//
//                @Override
//                public View onCreateWrapperView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//                    return null;
//                }
//
//                @Override
//                public void onWrapperDestroy() {
//
//                }
//            });
        }

        @Override
        public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            mFkWebView.loadUrl("http://192.168.1.64:8803/grabredenvelope");
//            mFkWebView.loadUrl("http://www.baidu.com");
        }

        @Override
        protected String setWebViewName() {
            return "test";
        }


        @Override
        public void onMessageReceive(MessageBody message) {

        }
    }
}
