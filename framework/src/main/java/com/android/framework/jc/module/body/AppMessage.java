//package com.android.framework.jc.module.body;
//
//import com.android.framework.jc.module.IModule;
//import com.android.framework.jc.module.IMessageCallback;
//
//import org.json.JSONObject;
//
///**
// * @author Mr.Hu(Jc) JCFramework
// * @create 2018/9/13 15:51
// * @describe
// * @update
// */
//public class AppMessage {
//    private int msgId;
//    private Class<? extends IModule> targetModule ;
//    private String comeFromModuleName;
//    private JSONObject messageJson;
//    private IMessageCallback callback;
//
//    public AppMessage(int msgId, JSONObject messageJson) {
//        this.msgId = msgId;
//        this.messageJson = messageJson;
//    }
//
//    public AppMessage(int msgId, Class<? extends IModule> targetModule) {
//        this.msgId = msgId;
//        this.targetModule = targetModule;
//    }
//
//    public AppMessage(int msgId, Class<? extends IModule> targetModule, JSONObject messageJson, IMessageCallback callback) {
//        this.msgId = msgId;
//        this.targetModule = targetModule;
//        this.messageJson = messageJson;
//        this.callback = callback;
//    }
//
//    public int getMsgId() {
//        return msgId;
//    }
//
//    public void setMsgId(int msgId) {
//        this.msgId = msgId;
//    }
//
//    public Class<? extends IModule> getTargetModule() {
//        return targetModule;
//    }
//
//    public void setTargetModule(Class<? extends IModule> targetModule) {
//        this.targetModule = targetModule;
//    }
//
//    public String getComeFromModuleName() {
//        return comeFromModuleName;
//    }
//
//    public void setComeFromModuleName(String comeFromModuleName) {
//        this.comeFromModuleName = comeFromModuleName;
//    }
//
//    public JSONObject getMessageJson() {
//        return messageJson;
//    }
//
//    public void setMessageJson(JSONObject messageJson) {
//        this.messageJson = messageJson;
//    }
//
//    public IMessageCallback getCallback() {
//        return callback;
//    }
//
//    public void setCallback(IMessageCallback callback) {
//        this.callback = callback;
//    }
//}
