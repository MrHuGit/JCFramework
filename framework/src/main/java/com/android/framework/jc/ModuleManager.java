//package com.android.framework.jc;
//
//import android.annotation.SuppressLint;
//import android.os.Build;
//import android.support.annotation.NonNull;
//import android.text.TextUtils;
//
//import com.android.framework.jc.js.FkWebView;
//import com.android.framework.jc.message.IMessageCallback;
//import com.android.framework.jc.message.IModule;
//import com.android.framework.jc.message.ModuleType;
//import com.android.framework.jc.message.body.MessageBody;
//import com.android.framework.jc.message.plugs.Plug10000;
//import com.android.framework.jc.message.plugs.Plug10001;
//import com.android.framework.jc.message.plugs.Plug10002;
//import com.android.framework.jc.message.plugs.Plug10003;
//import com.android.framework.jc.util.LogUtils;
//
//import org.json.JSONObject;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @author Mr.Hu(Jc)
// * @create 2018/7/16 15:14
// * @describe 消息管理类
// * @update
// */
//public class ModuleManager {
//    private final HashMap<String, IModule> mModuleMaps;
//    private final Map<Integer, Class<? extends IModule>> mPlugMaps;
//    private final HashMap<String, FkWebView> mWebViewMap;
//    private final static Class TAG = ModuleManager.class;
//
//    @SuppressLint("UseSparseArrays")
//    private ModuleManager() {
//        mModuleMaps = new HashMap<>();
//        mWebViewMap = new HashMap<>();
//        mPlugMaps = new HashMap<>();
//        mPlugMaps.put(10000, Plug10000.class);
//        mPlugMaps.put(10001, Plug10001.class);
//        mPlugMaps.put(10002, Plug10002.class);
//        mPlugMaps.put(10003, Plug10003.class);
//    }
//
//
//    private static class Holder {
//        private static ModuleManager INSTANCE = new ModuleManager();
//    }
//
//
//    public static ModuleManager getInstance() {
//        return Holder.INSTANCE;
//    }
//
//    /**
//     * 注册模块
//     *
//     * @param moduleName
//     *         模块名称
//     * @param module
//     *         模块
//     */
//    public void register(String moduleName, @NonNull IModule module) {
//        if (TextUtils.isEmpty(moduleName)) {
//            throw new RuntimeException("ModuleManager register error");
//        }
//        mModuleMaps.put(moduleName, module);
//        LogUtils.i(TAG, "register：moduleName" + moduleName);
//    }
//
//    /**
//     * 取消注册
//     *
//     * @param moduleName
//     *         模块名
//     */
//    public void unRegister(@NonNull String moduleName) {
//        mModuleMaps.remove(moduleName);
//    }
//
//    public void register(String webViewName, FkWebView fkWebView) {
//        if (TextUtils.isEmpty(webViewName) || fkWebView == null) {
//            throw new RuntimeException("WebView register error");
//        }
//        mWebViewMap.put(webViewName, fkWebView);
//        LogUtils.i(TAG, "webView注册：webViewName" + webViewName, "webView:" + fkWebView);
//    }
//
//    public void unRegisterWebView(String webViewName) {
//        if (mWebViewMap.containsKey(webViewName)) {
//            mWebViewMap.remove(webViewName);
//            LogUtils.i(TAG, "webView取消注册：webViewName" + webViewName);
//        }
//    }
//
//
//    /**
//     * 发送消息
//     *
//     * @param messageType
//     *         消息类型
//     * @param messageBody
//     *         消息内容
//     */
//    public void sendMessage(ModuleType messageType, MessageBody messageBody) {
//        if (messageType == null || messageBody == null) {
//            return;
//        }
//        LogUtils.i(TAG, "sendMessage：messageType:" + messageType, "messageBody:" + messageBody);
//        switch (messageType) {
//            case JS:
//                String targetModule = messageBody.getTargetModuleName();
//                if (MessageBody.DEFAULT_TARGET_MODULE.equals(targetModule)) {
//                    for (FkWebView fkWebView : mWebViewMap.values()) {
//                        sendToJs(fkWebView, messageBody);
//                    }
//                } else {
//                    if (mWebViewMap.containsKey(targetModule)) {
//                        FkWebView fkWebView = mWebViewMap.get(targetModule);
//                        if (fkWebView != null) {
//                            sendToJs(fkWebView, messageBody);
//                        } else {
//                            mWebViewMap.remove(targetModule);
//                        }
//                    }
//                }
//                break;
//            case APP:
//                sendAppMessage(messageBody);
//                break;
//            case PLUG:
//                int messageId = messageBody.getMsgId();
//                Class<? extends IModule> normalMsgClass = mPlugMaps.get(messageId);
//                if (normalMsgClass != null) {
//                    IModule plug = create(normalMsgClass);
//                    if (plug != null) {
//                        plug.onMessageReceive(messageBody);
//                        return;
//                    }
//                }
//                IMessageCallback.onResultFail(messageBody.getCallback(), "插件不存在");
//
//                break;
//            default:
//                break;
//        }
//    }
//
//
//    /**
//     * 发送消息
//     *
//     * @param messageBody
//     *         消息体
//     */
//    private void sendAppMessage(@NonNull MessageBody messageBody) {
//        String targetModule = messageBody.getTargetModuleName();
//        boolean result = false;
//        if (MessageBody.DEFAULT_TARGET_MODULE.equals(targetModule)) {
//            for (IModule module : mModuleMaps.values()) {
//                module.onMessageReceive(messageBody);
//                result = true;
//            }
//        } else {
//            if (mModuleMaps.containsKey(targetModule)) {
//                IModule module = mModuleMaps.get(targetModule);
//                if (module != null) {
//                    module.onMessageReceive(messageBody);
//                    result = true;
//                } else {
//                    mModuleMaps.remove(targetModule);
//                }
//            }
//        }
//        if (!result) {
//            LogUtils.i(TAG, "指定的targetModuleName不存在，请检查模块targetModuleName");
//            IMessageCallback.onResultFail(messageBody.getCallback(), "指定的targetModuleName不存在，请检查模块targetModuleName");
//        }
//
//    }
//
//
//    /**
//     * 发送消息给H5
//     *
//     * @param fkWebView
//     *         接收消息的WebView
//     * @param messageBody
//     *         消息体
//     */
//    private static void sendToJs(FkWebView fkWebView, @NonNull MessageBody messageBody) {
//        JSONObject messageJson = JcFramework.getInstance().getFrameworkConfig().getMessageBuilder().createJsMessage(messageBody);
//        if (messageJson != null) {
//            String message = messageJson.toString();
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                fkWebView.evaluateJavascript("javascript:onAppMessage(" + message + ")", value -> {
//                    //此处为 js 返回的结果
//                    LogUtils.i(TAG, "onReceiveValue：value" + value);
//                });
//            } else {
//                fkWebView.loadUrl("javascript:onAppMessage(" + message + ")");
//            }
//        }
//
//
//    }
//
//
//    /**
//     * 检测是否是通用组件
//     *
//     * @param messageBody
//     *         message
//     *
//     * @return 是否是通用组件
//     */
//    public boolean checkPlugMessage(@NonNull MessageBody messageBody) {
//        boolean result = false;
//        int messageId = messageBody.getMsgId();
//        if (mPlugMaps.containsKey(messageId)) {
//            result = true;
//        }
//        return result;
//    }
//
//    /**
//     * 动态添加通用组件
//     *
//     * @param map
//     *         map
//     */
//    void addNormalMessage(Map<Integer, Class<? extends IModule>> map) {
//        mPlugMaps.putAll(map);
//    }
//
//    /**
//     * 清除数据
//     */
//    protected void clear() {
//        mModuleMaps.clear();
//        mPlugMaps.clear();
//    }
//
//
//    /**
//     * 利用反射创建对象
//     *
//     * @param normalMsgClass
//     *         normalMsgClass
//     *
//     * @return INormalMessage对象
//     */
//    private static IModule create(final Class<? extends IModule> normalMsgClass) {
//        try {
//            return normalMsgClass.newInstance();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//}
