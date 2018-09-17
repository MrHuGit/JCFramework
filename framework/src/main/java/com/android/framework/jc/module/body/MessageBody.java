package com.android.framework.jc.module.body;

import com.android.framework.jc.module.IMessageCallback;

import org.json.JSONObject;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/9/13 13:44
 * @describe 模块消息体
 * @update
 */
public class MessageBody {
    public final static String DEFAULT_TARGET_MODULE = "*";
    private int msgId;
    private String targetModuleName = DEFAULT_TARGET_MODULE;
    private String comeFromModuleName;
    private JSONObject messageJson;
    private IMessageCallback callback;
    public IMessageCallback getCallback() {
        return callback;
    }
    public void setCallback(IMessageCallback callback) {
        this.callback = callback;
    }
    public MessageBody(int msgId) {
        this.msgId = msgId;
    }

    public MessageBody(int msgId, JSONObject messageJson) {
        this.msgId = msgId;
        this.messageJson = messageJson;
    }

    public MessageBody(int msgId, JSONObject messageJson, IMessageCallback callback) {
        this.msgId = msgId;
        this.messageJson = messageJson;
        this.callback = callback;
    }

    public MessageBody(int msgId, String targetModuleName, JSONObject messageJson) {
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

    @Override
    public String toString() {
        return "MessageBody{" +
                "msgId=" + msgId +
                ", targetModuleName='" + targetModuleName + '\'' +
                ", comeFromModuleName='" + comeFromModuleName + '\'' +
                ", messageJson=" + messageJson +
                ", callback=" + callback +
                '}';
    }
}
