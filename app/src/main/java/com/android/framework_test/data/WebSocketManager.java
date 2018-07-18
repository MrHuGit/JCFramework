package com.android.framework_test.data;

/**
 * @author Mr.Hu(Jc)
 * @create 2018/7/16 11:04
 * @describe
 * @update
 */
public class WebSocketManager {
    private WebSocketManager(){}


    private static class Holder{
        private static WebSocketManager INSTANCE=new WebSocketManager();
    }

    public static WebSocketManager getInstance(){
        return Holder.INSTANCE;
    }

    
}
