package com.android.jc_framework.exception;

/**
 * @author Mr.Hu(Jc)
 * @create 2018/3/16 15:02
 * @describe 禁止初始化异常
 * @update
 */

public class StopInstantiatedException extends RuntimeException{
    public StopInstantiatedException(){
        super("cannot be instantiated");
    }
}
