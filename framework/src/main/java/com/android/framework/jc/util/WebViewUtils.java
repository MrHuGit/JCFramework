package com.android.framework.jc.util;

import android.annotation.SuppressLint;
import android.os.Build;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2019/5/18 18:30
 * @describe
 * @update
 */
public class WebViewUtils {

    /**
     * webView基础设置 更多设置查看{@link #settingOtherMethods(WebSettings)}
     *
     * @param webView
     *         webView
     *
     * @return webView
     */
    @SuppressLint("SetJavaScriptEnabled")
    public static WebView initSetting(WebView webView) {
        if (webView != null) {
            WebSettings setting = webView.getSettings();
            //设置WebView是否支持使用屏幕控件或手势进行缩放，默认是true，支持缩放。
            setting.setSupportZoom(false);
            //设置WebView是否使用其内置的变焦机制，该机制集合屏幕缩放控件使用，默认是false，不使用内置变焦机制。
            setting.setBuiltInZoomControls(true);
            //设置WebView使用内置缩放机制时，是否展现在屏幕缩放控件上，默认true，展现在控件上。
            setting.setDisplayZoomControls(false);
            //设置在WebView内部是否允许访问文件，默认允许访问true。
            setting.setAllowFileAccess(true);
            setting.setAllowContentAccess(true);
            //设置WebView是否使用预览模式加载界面,默认false
            setting.setLoadWithOverviewMode(true);
//            setting.setAcceptThirdPartyCookies();
            //设置WebView是否使用viewport，当该属性被设置为false时，加载页面的宽度总是适应WebView控件宽度；
            // 当被设置为true，当前页面包含viewport属性标签，在标签中指定宽度值生效，
            // 如果页面不包含viewport标签，无法提供一个宽度值，这个时候该方法将被使用。
            setting.setUseWideViewPort(true);
            //设置WebView是否支持多屏窗口，参考WebChromeClient#onCreateWindow，默认false，不支持
            setting.setSupportMultipleWindows(false);
            //设置WebView底层的布局算法，参考WebSettings.LayoutAlgorithm，将会重新生成WebView布局
            setting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
            //设置WebView是否加载图片资源，默认true，自动加载图片
            setting.setLoadsImagesAutomatically(true);
            //设置WebView是否以http、https方式访问从网络加载图片资源，默认false
            setting.setBlockNetworkImage(false);
            //设置WebView是否从网络加载资源，Application需要设置访问网络权限，否则报异常,默认false
            setting.setBlockNetworkLoads(false);
            //设置WebView是否允许执行JavaScript脚本，默认false，不允许
            setting.setJavaScriptEnabled(true);
            //设置WebView运行中的脚本可以是否访问任何原始起点内容，默认true
            setting.setAllowUniversalAccessFromFileURLs(true);
            //设置WebView运行中的一个文件方案被允许访问其他文件方案中的内容，默认值true
            setting.setAllowFileAccessFromFileURLs(true);
            //设置是否开启数据库存储API权限，默认false，未开启
            setting.setDatabaseEnabled(true);
            //设置是否开启DOM存储API权限，默认false，未开启，设置为true，WebView能够使用DOM storage API
            setting.setDomStorageEnabled(true);
            //设置是否开启定位功能，默认true，开启定位
            setting.setGeolocationEnabled(true);
            //设置脚本是否允许自动打开弹窗，默认false，不允许
            setting.setJavaScriptCanOpenWindowsAutomatically(true);
            //设置WebView是否需要设置一个节点获取焦点当被回调的时候，默认true
            setting.setNeedInitialFocus(true);
            //重写缓存被使用到的方法，该方法基于Navigation Type，
            // 加载普通的页面，将会检查缓存同时重新验证是否需要加载，
            // 如果不需要重新加载，将直接从缓存读取数据，允许客户端通过指定
            // LOAD_DEFAULT、LOAD_CACHE_ELSE_NETWORK、LOAD_NO_CACHE、LOAD_CACHE_ONLY
            // 其中之一重写该行为方法，默认值LOAD_DEFAULT
            setting.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            //设置当一个安全站点企图加载来自一个不安全站点资源时WebView的行为，
            // android.os.Build.VERSION_CODES.KITKAT默认为
            // MIXED_CONTENT_ALWAYS_ALLOW，
            // android.os.Build.VERSION_CODES#LOLLIPOP默认为MIXED_CONTENT_NEVER_ALLOW，
            // 取值其中之一：MIXED_CONTENT_NEVER_ALLOW、MIXED_CONTENT_ALWAYS_ALLOW、MIXED_CONTENT_COMPATIBILITY_MODE.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                setting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            }
        }
        return webView;
    }

    /**
     * webView其它相关设置方法
     */
    private static void settingOtherMethods(WebSettings setting) {
        //设置WebView中加载页面字体变焦百分比，默认100，整型数
        setting.setTextZoom(100);
        //设置WebView标准字体库字体，默认字体“sans-serif”
        setting.setStandardFontFamily("sans-serif");
        //设置WebView固定的字体库字体，默认“monospace”。
        setting.setFixedFontFamily("monospace");
        //设置WebView Sans SeriFontFamily字体库字体，默认“sans-serif”。
        setting.setSansSerifFontFamily("sans-serif");
        //设置WebView seri FontFamily字体库字体，默认“sans-serif”。
        setting.setSerifFontFamily("sans-serif");
        //设置WebView字体库字体，默认“cursive”
        setting.setCursiveFontFamily("cursive");
        //设置WebView字体库字体，默认“fantasy”。
        setting.setFantasyFontFamily("fantasy");
        //设置WebView字体最小值，默认值8，取值1到72
        setting.setMinimumFontSize(8);
        //设置WebView逻辑上最小字体值，默认值8，取值1到72
        setting.setMinimumLogicalFontSize(8);
        //设置WebView默认值字体值，默认值16，取值1到72
        setting.setDefaultFontSize(16);
        //设置WebView默认固定的字体值，默认值16，取值1到72
        setting.setDefaultFixedFontSize(16);
        //设置WebView加载页面文本内容的编码，默认“UTF-8”。
        setting.setDefaultTextEncodingName("UTF-8");
        //设置WebView代理字符串，如果String为null或为空，将使用系统默认值
        setting.setUserAgentString("");
        //设置WebView是否通过手势触发播放媒体，默认是true，需要手势触发
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            setting.setMediaPlaybackRequiresUserGesture(true);
        }
        //设置WebView视图是否应该保存表单数据,在Android 8.0,平台已经实现了一个功能齐全的自动填充功能存储表单数据
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            setting.setSaveFormData(true);
        }
        //设置WebView保存地理位置信息数据路径，指定的路径Application具备写入权限
        setting.setGeolocationDatabasePath("");
        //设置Application缓存API是否开启，默认false，设置有效的缓存路径参考setAppCachePath(String path)方法
        setting.setAppCacheEnabled(true);
        //设置当前Application缓存文件路径，Application Cache API能够开启需要指定Application具备写入权限的路径
        setting.setAppCachePath("");
    }
}
