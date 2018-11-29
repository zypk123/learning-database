package com.zhang.proxy.pattern;

/**
 * 代理类处理
 *
 * @author zhangyu
 * @create 2018-11-29 10:56
 **/
public class ProxyDeal implements Business {

    // 依赖实际处理类
    private RealDeal realDeal;

    public ProxyDeal(RealDeal realDeal) {
        this.realDeal = realDeal;
    }

    @Override
    public void buyHouse() {
        System.out.println("before");
        realDeal.buyHouse();
        System.out.println("after");
    }

    @Override
    public void buyCar() {
        System.out.println("before");
        realDeal.buyCar();
        System.out.println("after");
    }

}
