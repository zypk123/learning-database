package com.zhang.proxy.dynamic.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Cglib动态代理处理器
 *
 * @author zhangyu
 * @create 2018-11-29 16:49
 **/
public class CglibProxy implements MethodInterceptor {

    private Object target;

    public Object getInstance(final Object target) {
        this.target = target;

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.target.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("before");
        Object result = methodProxy.invokeSuper(object, args);
        System.out.println("after");
        return result;
    }

}
