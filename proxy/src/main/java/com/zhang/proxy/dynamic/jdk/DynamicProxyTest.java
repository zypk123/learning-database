package com.zhang.proxy.dynamic.jdk;

import com.zhang.proxy.pattern.Business;
import com.zhang.proxy.pattern.RealDeal;

import java.lang.reflect.Proxy;

/**
 * JDK动态代理测试
 *
 * @author zhangyu
 * @create 2018-11-29 15:33
 **/
public class DynamicProxyTest {

    public static void main(String[] args) {

        Business business = (Business) Proxy.newProxyInstance(Business.class.getClassLoader(), new Class[]{Business.class}, new DynamicProxyHandler(new RealDeal()));

        business.buyHouse();
        business.buyCar();
    }
}
