package com.android.framework.jc.module;

import org.json.JSONObject;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/7/16 15:17
 * @describe
 * @update
 */
public class ModuleMessage {
    public final static String DEFAULT_TARGET_MODULE="*";
    private int msgId;
    private String targetModuleName=DEFAULT_TARGET_MODULE;
    private String comeFromModuleName;
    private JSONObject messageJson;

    public ModuleMessage(int msgId) {
        this.msgId = msgId;
    }

    public ModuleMessage(int msgId, JSONObject messageJson) {
        this.msgId = msgId;
        this.messageJson = messageJson;
    }

    public ModuleMessage(int msgId, String targetModuleName, JSONObject messageJson) {
        this.msgId = msgId;
        this.targetModuleName = targetModuleName;
        this.messageJson = messageJson;
    }

    public int getMsgId() {
        return msgId;
    }

    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }

    public String getTargetModuleName() {
        return targetModuleName;
    }

    public void setTargetModuleName(String targetModuleName) {
        this.targetModuleName = targetModuleName;
    }

    public String getComeFromModuleName() {
        return comeFromModuleName;
    }

    public void setComeFromModuleName(String comeFromModuleName) {
        this.comeFromModuleName = comeFromModuleName;
    }

    public JSONObject getMessageJson() {
        return messageJson;
    }

    public void setMessageJson(JSONObject messageJson) {
        this.messageJson = messageJson;
    }
}
