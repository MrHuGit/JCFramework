package com.android.framework.jc;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.text.TextUtils;

import com.android.framework.jc.data.bean.ConfigBean;
import com.android.framework.jc.exception.NoConfigException;
import com.android.framework.jc.util.FormatUtils;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/7/17 15:10
 * @describe 配置文件管理
 * @update
 */
public class ConfigManager {
    private final static String XML_NAME = BuildConfig.frameworkCongigurationXmlName;
    private final Set<ConfigBean> configSet;

    private ConfigManager() {
        configSet = new HashSet<>();
    }


    public static ConfigManager getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * 类加载器形式的单例模式
     */
    private static class Holder {
        private static ConfigManager INSTANCE = new ConfigManager();
    }

    /**
     * 解析配置文件
     *
     * @param context
     *         context
     */
    void parseXml(Context context) {
        int resourceId = context.getResources().getIdentifier(XML_NAME, "xml", context.getPackageName());
        XmlResourceParser xmlParser = context.getResources().getXml(resourceId);
        configSet.clear();
        ConfigBean bean;
        try {
            int eventType = xmlParser.getEventType();
            for (; eventType != XmlPullParser.END_DOCUMENT; eventType = xmlParser.next()) {
                String tagName;
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        tagName = xmlParser.getName();
                        if ("item".equals(tagName)) {
                            bean = new ConfigBean();
                            bean.setDescription(xmlParser.getAttributeValue(null, "description"));
                            bean.setKey(xmlParser.getAttributeValue(null, "key"));
                            bean.setValue(xmlParser.getAttributeValue(null, "value"));
                            configSet.add(bean);
                        }
                        break;
                    default:
                        break;
                }

            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据配置文件中配置的key获取对应的value
     *
     * @param key
     *         配置文件xml中的key
     *
     * @return value
     */
    public String getValue(String key) {
        if (TextUtils.isEmpty(key)) {
            throw new RuntimeException("key is empty");
        }
        for (ConfigBean bean : configSet) {
            if (key.equals(bean.getKey())) {
                return bean.getValue();
            }
        }
        throw new NoConfigException(key);
    }

    public boolean getBooleanValue(String key) {
        return "true".equalsIgnoreCase(getValue(key));
    }

    public long getLongValue(String key) {
        return FormatUtils.parseLong(getValue(key));
    }

    /**
     * 根据配置文件中配置的key获取对应的description
     *
     * @param key
     *         配置文件xml中的key
     *
     * @return description
     */
    public String getDescription(String key) {
        if (TextUtils.isEmpty(key)) {
            return null;
        }
        for (ConfigBean bean : configSet) {
            if (key.equals(bean.getKey())) {
                return bean.getDescription();
            }
        }
        return null;
    }
}
