package com.zhang.proxy.dynamic.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * JDK动态处理类
 * 实现InvocationHandler接口
 *
 * @author zhangyu
 * @create 2018-11-29 15:04
 **/
public class DynamicProxyHandler implements InvocationHandler {

    private Object object;

    public DynamicProxyHandler(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before");
        Object result = method.invoke(object, args);
        System.out.println("after");
        return result;
    }

}
