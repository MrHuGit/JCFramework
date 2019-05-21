package com.android.framework.jc.message.body;

import android.support.annotation.NonNull;

import com.android.framework.jc.message.IMessageCallback;
import com.android.framework.jc.message.ModuleType;

import org.json.JSONObject;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/9/13 13:44
 * @describe 模块消息体
 * @update
 */
public class MessageBody {
    /**
     * 默认模块名（广播形式，所有对应模块都会接收）
     */
    public final static String DEFAULT_TARGET_MODULE = "*";
    private final Builder builder;

    private MessageBody(Builder builder) {
        this.builder = builder;
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * 获取消息号
     *
     * @return 消息号
     */
    public int getMsgId() {
        return builder.messageId;
    }

    /**
     * 获取目标模块名称
     *
     * @return 目标模块名称
     */
    public String getTargetModuleName() {
        return builder.targetModuleName;
    }

    /**
     * 获取来源模块名称
     *
     * @return 来源模块名称
     */
    public String getComeFromModuleName() {
        return builder.comeFromModuleName;
    }

    /**
     * 参数json
     *
     * @return 参数json
     */
    public JSONObject getParamJson() {
        return builder.paramJson;
    }

    /**
     * 获取目标模块类型
     *
     * @return 目标模块类型
     */
    public ModuleType getTargetModuleType() {
        return builder.targetModuleType;
    }

    /**
     * 获取来源模块类型
     *
     * @return 来源模块名称
     */
    public ModuleType getComeFromModuleType() {
        return builder.comeFromModuleType;
    }

    /**
     * 获取回调
     *
     * @return 回调
     */
    public IMessageCallback getCallback() {
        return builder.callback;
    }


    public final static class Builder {
        /**
         * 发送过来的消息号
         */
        private int messageId = -1;

        /**
         * 消息类型
         */
        private ModuleType targetModuleType;
        /**
         * 消息类型
         */
        private ModuleType comeFromModuleType = ModuleType.APP;
        /**
         * 目标模块名
         */
        private String targetModuleName = DEFAULT_TARGET_MODULE;
        /**
         * 来源模块名
         */
        private String comeFromModuleName;
        /**
         * 发送消息的参数
         */
        private JSONObject paramJson;
        /**
         * 回调
         */
        private IMessageCallback callback;


        private Builder() {
        }

        public Builder setMessageId(int messageId) {
            this.messageId = messageId;
            return this;
        }

        public Builder setTargetModuleType(ModuleType targetModuleType) {
            this.targetModuleType = targetModuleType;
            return this;
        }

        public Builder setComeFromModuleType(ModuleType comeFromModuleType) {
            this.comeFromModuleType = comeFromModuleType;
            return this;
        }

        public Builder setTargetModuleName(String targetModuleName) {
            this.targetModuleName = targetModuleName;
            return this;
        }

        public Builder setComeFromModuleName(String comeFromModuleName) {
            this.comeFromModuleName = comeFromModuleName;
            return this;
        }

        public Builder setParamsJson(JSONObject paramJson) {
            this.paramJson = paramJson;
            return this;

        }

        public Builder setMessageCallback(IMessageCallback callback) {
            this.callback = callback;
            return this;
        }

        public MessageBody build() {
            return new MessageBody(this);
        }

    }


    @NonNull
    @Override
    public String toString() {
        String targetModuleTypeValue = "";
        String comeFromModuleTypeValue = "";
        if (builder.targetModuleType != null) {
            targetModuleTypeValue = builder.targetModuleType.getValue();
        }
        if (builder.comeFromModuleType != null) {
            comeFromModuleTypeValue = builder.comeFromModuleType.getValue();
        }
        return "MessageBody{" +
                "msgId=" + builder.messageId +
                ", targetModuleName='" + builder.targetModuleName + '\'' +
                ", comeFromModuleName='" + builder.comeFromModuleName + '\'' +
                ", paramJson=" + builder.paramJson +
                ", callback=" + builder.callback +
                ", targetModuleType=" + targetModuleTypeValue +
                ", comeFromModuleType=" + comeFromModuleTypeValue +
                '}';
    }
}
