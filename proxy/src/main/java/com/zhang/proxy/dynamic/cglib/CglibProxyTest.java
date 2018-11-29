package com.zhang.proxy.dynamic.cglib;

import com.zhang.proxy.pattern.RealDeal;

/**
 * @author zhangyu
 * @create 2018-11-29 17:00
 **/
public class CglibProxyTest {
    public static void main(String[] args) {

        RealDeal realDeal = (RealDeal) new CglibProxy().getInstance(new RealDeal());

        realDeal.buyHouse();

    }
}
