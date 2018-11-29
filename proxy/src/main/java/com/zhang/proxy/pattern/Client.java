package com.zhang.proxy.pattern;

/**
 * 客户端测试类
 *
 * @author zhangyu
 * @create 2018-11-29 10:59
 **/
public class Client {
    public static void main(String[] args) {
        Business business = new ProxyDeal(new RealDeal());
        business.buyHouse();
        business.buyCar();
    }
}
