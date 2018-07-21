# JCFramework
简单功能封装
<br/>
Step 1. Add the JitPack repository to your build file
<br/>
Add it in your root build.gradle at the end of repositories:
```aidl
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Step 2. Add the dependency
```aidl
dependencies {
	        implementation 'com.github.MrHuGit:JCFramework:版本号'
	}
```
[版本号]（https://github.com/MrHuGit/JCFramework/releases）
###初始化

```java
public class MineApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JcFramework.init(this);
    }
}
```

###配置文件
```html
<?xml version="1.0" encoding="utf-8"?>
<framework_configuration xmlns:android="http://schemas.android.com/apk/res/android">
    <item
        description="Log是否显示（true,false）"
        key="logDebug"
        value="true"/>
    <item
        description="OkHttp连接超时时间（MILLISECONDS）"
        key="okHttpConnectTimeout"
        value="20000"/>
    <item
        description="OkHttp读取超时时间（MILLISECONDS）"
        key="okHttpReadTimeout"
        value="20000"/>
    <item
        description="OkHttp写入超时时间（MILLISECONDS）"
        key="okHttpWriteTimeout"
        value="20000"/>
</framework_configuration>
``` 
可以按照这种模式添加自己的配置，再通过
```java
ConfigManager.getInstance().getValue("logDebug");
```
方式获取
###下载文件
调用此方法下载文件
```java
 /**
     * 下载文件
     *
     * @param url
     *         下载文件的url
     * @param savePath
     *         保存文件的路径
     * @param listener
     *         监听
     */
    public void downloadFile(String url, String savePath, Listener listener) {
        Activity activity = JcFramework.getInstance().getTopActivity();
        FkPermission.with(activity)
                .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .listener(new FkPermission.IPermissionListener() {
                    @Override
                    public void onGrant() {
                        NetworkManager.getInstance().addDispose(url, download(url, savePath, listener));
                    }

                    @Override
                    public void onRationale(String[] permissions) {
                        listener.onError(new MessageException("授权拒绝"));
                    }

                    @Override
                    public void onDenied(String[] permissions) {
                        listener.onError(new MessageException("授权拒绝"));
                    }
                }).request();

    }
```