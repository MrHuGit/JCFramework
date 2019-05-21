package com.android.framework.jc.message;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.android.framework.jc.JcFramework;
import com.android.framework.jc.js.FkWebView;
import com.android.framework.jc.message.body.MessageBody;
import com.android.framework.jc.message.plugs.BasePlug;
import com.android.framework.jc.message.plugs.Plug10000;
import com.android.framework.jc.message.plugs.Plug10001;
import com.android.framework.jc.message.plugs.Plug10002;
import com.android.framework.jc.message.plugs.Plug10003;
import com.android.framework.jc.util.LogUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2019/5/18 17:15
 * @describe 消息管理类
 * @update
 */
public class MessageManager {
    private final IMessageAdapter mMessageAdapter;
    private final HashMap<String, IModule> mModuleMaps;
    private final Map<Integer, Class<? extends BasePlug>> mPlugMaps;
    private final HashMap<String, FkWebView> mWebViewMap;
    private final static Class TAG = MessageManager.class;

    @SuppressLint("UseSparseArrays")
    private MessageManager() {
        mMessageAdapter = JcFramework.getInstance().getFrameworkConfig().getMessageAdapter();
        mModuleMaps = new HashMap<>();
        mWebViewMap = new HashMap<>();
        mPlugMaps = new HashMap<>();
        mPlugMaps.put(10000, Plug10000.class);
        mPlugMaps.put(10001, Plug10001.class);
        mPlugMaps.put(10002, Plug10002.class);
        mPlugMaps.put(10003, Plug10003.class);
    }

    private static class Holder {
        private static MessageManager INSTANCE = new MessageManager();
    }

    public static MessageManager getInstance() {
        return Holder.INSTANCE;
    }

    public IMessageAdapter getMessageAdapter() {
        return mMessageAdapter;
    }

    /**
     * 注册模块
     *
     * @param moduleName
     *         模块名称
     * @param module
     *         模块
     */
    public void register(String moduleName, @NonNull IModule module) {
        if (TextUtils.isEmpty(moduleName)) {
            throw new RuntimeException("ModuleManager register error");
        }
        mModuleMaps.put(moduleName, module);
        LogUtils.i(TAG, "register：moduleName" + moduleName);
    }

    /**
     * 取消注册
     *
     * @param moduleName
     *         模块名
     */
    public void unRegister(@NonNull String moduleName) {
        mModuleMaps.remove(moduleName);
    }

    /**
     * 注册webView(如果webView需要接收或发送消息)
     *
     * @param webViewName
     *         webView名称
     * @param fkWebView
     *         webView
     */
    public void register(String webViewName, FkWebView fkWebView) {
        if (TextUtils.isEmpty(webViewName) || fkWebView == null) {
            throw new RuntimeException("WebView register error");
        }
        mWebViewMap.put(webViewName, fkWebView);
        LogUtils.i(TAG, "webView注册：webViewName" + webViewName, "webView:" + fkWebView);
    }

    /**
     * webView所在页面销毁时要取消注册，否则或出现内存泄露
     *
     * @param fkWebView
     *         fkWebView
     */
    public void unRegister(FkWebView fkWebView) {
        if (fkWebView == null) {
            return;
        }
        Iterator<FkWebView> valuesIterator = mWebViewMap.values().iterator();
        while (valuesIterator.hasNext()) {
            if (fkWebView == valuesIterator.next()) {
                valuesIterator.remove();

            }
        }
    }


    /**
     * 发送消息
     *
     * @param messageBody
     *         消息体
     */
    public void sendMessage(MessageBody messageBody) {
        mMessageAdapter.dispatchMessage(messageBody);
    }


    /**
     * 发送消息
     *
     * @param messageBody
     *         消息体
     */
    boolean sendAppMessage(MessageBody messageBody) {
        boolean result = false;
        if (messageBody != null) {
            String targetModule = messageBody.getTargetModuleName();
            if (MessageBody.DEFAULT_TARGET_MODULE.equals(targetModule)) {
                for (IModule module : mModuleMaps.values()) {
                    result |= sendMessage(module, messageBody);
                }
            } else {
                if (mModuleMaps.containsKey(targetModule)) {
                    IModule module = mModuleMaps.get(targetModule);
                    if (module != null) {
                        return sendMessage(module, messageBody);
                    } else {
                        mModuleMaps.remove(targetModule);
                    }
                }
            }
        }

        return result;
    }

    private static boolean sendMessage(IModule module, MessageBody messageBody) {
        if (module == null || messageBody == null) {
            return false;
        }
        return module.onMessageReceive(messageBody);

    }

    /**
     * 发送消息给H5
     *
     * @param messageBody
     *         消息体
     *
     * @return 是否找到对应的接收消息模块
     */
    boolean sendJsMessage(MessageBody messageBody) {
        boolean result = false;
        if (messageBody != null) {
            String targetModule = messageBody.getTargetModuleName();
            if (MessageBody.DEFAULT_TARGET_MODULE.equals(targetModule)) {
                for (FkWebView fkWebView : mWebViewMap.values()) {
                    mMessageAdapter.sendMessageToJs(fkWebView, messageBody);
                    result = true;
                }
            } else {
                if (mWebViewMap.containsKey(targetModule)) {
                    FkWebView fkWebView = mWebViewMap.get(targetModule);
                    if (fkWebView != null) {
                        mMessageAdapter.sendMessageToJs(fkWebView, messageBody);
                        result = true;
                    } else {
                        mWebViewMap.remove(targetModule);
                    }
                }
            }
        }

        return result;
    }

    /**
     * 发送消息给插件
     *
     * @param messageBody
     *         消息体
     *
     * @return 是否找到对应的接收消息模块
     */
    boolean sendPlugMessage(MessageBody messageBody) {
        if (messageBody != null) {
            int messageId = messageBody.getMsgId();
            Class<? extends IModule> normalMsgClass = mPlugMaps.get(messageId);
            if (normalMsgClass != null) {
                IModule plug = create(normalMsgClass);
                if (plug != null) {
                    return sendMessage(plug, messageBody);
                }
            }
        }
        return false;
    }

    /**
     * 动态添加通用组件
     *
     * @param map
     *         map
     */
    public void addNormalMessage(Map<Integer, Class<? extends BasePlug>> map) {
        mPlugMaps.putAll(map);
    }

    /**
     * 清除数据
     */
    public void clear() {
        mModuleMaps.clear();
        mPlugMaps.clear();
    }


    /**
     * 利用反射创建对象
     *
     * @param normalMsgClass
     *         normalMsgClass
     *
     * @return INormalMessage对象
     */
    private static IModule create(final Class<? extends IModule> normalMsgClass) {
        try {
            return normalMsgClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 检测是否是通用组件
     *
     * @param messageBody
     *         message
     *
     * @return 是否是通用组件
     */
     boolean checkPlugMessage(MessageBody messageBody) {
        boolean result = false;
        int messageId = messageBody.getMsgId();
        if (mPlugMaps.containsKey(messageId)) {
            result = true;
        }
        return result;
    }
}
