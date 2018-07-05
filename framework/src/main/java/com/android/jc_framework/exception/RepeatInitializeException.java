package com.android.jc_framework.exception;

/**
 * @author Mr.Hu(Jc)
 * @create 2018/3/16 15:09
 * @describe
 * @update
 */

public class RepeatInitializeException extends RuntimeException{
    public RepeatInitializeException(Class tClass){
        super(tClass.getSimpleName()+"has initialize");
    }
}
