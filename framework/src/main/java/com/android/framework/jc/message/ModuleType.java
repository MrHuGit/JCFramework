package com.android.framework.jc.message;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/9/17 12:41
 * @describe 发送消息的类型
 * @update
 */
public enum ModuleType {
    /**
     * 发送消息给JS
     */
    JS("2"),
    /**
     * 发送消息给APP其它模块
     */
    APP("1");
    private String value;

    private ModuleType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    /**
     * 根据具体值获取类型
     *
     * @param value
     *         值
     *
     * @return 类型
     */
    public static ModuleType getMessageTypeByValue(String value) {
        for (ModuleType messageType : ModuleType.values()) {
            if (messageType.value.equalsIgnoreCase(value)) {
                return messageType;
            }
        }
        return null;
    }

}
