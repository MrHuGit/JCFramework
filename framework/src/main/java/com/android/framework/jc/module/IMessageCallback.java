package com.android.framework.jc.module;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/9/13 15:54
 * @describe
 * @update
 */
public interface IMessageCallback {

    void onCallback(IMessageCallback.Result result);


     class Result{
        private boolean isSuccess;
        private String resultMessage;
        private JSONObject contentJson;

        public boolean isSuccess() {
            return isSuccess;
        }

        public void setSuccess(boolean success) {
            isSuccess = success;
        }

        public String getResultMessage() {
            return resultMessage;
        }

        public void setResultMessage(String resultMessage) {
            this.resultMessage = resultMessage;
        }

        public JSONObject getContentJson() {
            return contentJson;
        }

        public void setContentJson(JSONObject contentJson) {
            this.contentJson = contentJson;
        }
    }
    static void resultSuccess(IMessageCallback callback,JSONObject contentJson){
        if (callback!=null){
            Result result=new Result();
            result.setSuccess(true);
            result.setResultMessage("调用成功");
            if (contentJson==null){
                contentJson=new JSONObject();
                try {
                    contentJson.put("value","");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            result.setContentJson(contentJson);
            callback.onCallback(result);
        }
    }
     static void resultSuccess(IMessageCallback callback){
         resultSuccess(callback,null);
    }
    static void resultFail(IMessageCallback callback,String failMessage){
        if (callback!=null){
            Result result=new Result();
            result.setSuccess(false);
            if (TextUtils.isEmpty(failMessage)){
                result.setResultMessage("调用失败");
            }else{
                result.setResultMessage(failMessage);
            }
            JSONObject contentJson=new JSONObject();
            try {
                contentJson.put("value","");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            result.setContentJson(contentJson);
            callback.onCallback(result);
        }
    }
}
