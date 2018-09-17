package com.android.framework.jc.configuration;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/7/17 16:33
 * @describe 配置文件实体类
 * @update
 */
public class ConfigBean {
    /**
     * key
     */
    private String key;
    /**
     * value
     */
    private String value;
    /**
     * 描述
     */
    private String description;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConfigBean)) {
            return false;
        }
        ConfigBean bean = (ConfigBean) o;
        if (!key.equals(bean.getKey())) {
            return false;
        }
        return value.equals(bean.getValue()) && description.equals(bean.getDescription());

    }

    @Override
    public int hashCode() {
        int result = key.hashCode();
        result = 31 * result + value.hashCode();
        result = 31 * result + description.hashCode();
        return result;
    }
}
