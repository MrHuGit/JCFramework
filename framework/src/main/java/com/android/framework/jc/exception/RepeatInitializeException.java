package com.android.framework.jc.exception;

/**
 * @author Mr.Hu(Jc)
 * @create 2018/3/16 15:09
 * @describe 重复初始化异常
 * @update
 */

public class RepeatInitializeException extends RuntimeException{
    public RepeatInitializeException(Class tClass){
        super(tClass.getSimpleName()+"has initialize");
    }
}
