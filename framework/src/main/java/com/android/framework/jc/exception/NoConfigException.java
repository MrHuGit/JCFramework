package com.android.framework.jc.exception;

/**
 * @author Mr.Hu(Jc) JCFramework
 * @create 2018/7/21 17:03
 * @describe
 * @update
 */
public class NoConfigException extends IllegalArgumentException {
    public NoConfigException(String key) {
        super("You don't add the " + key + " in framework_configuration");
    }


}
