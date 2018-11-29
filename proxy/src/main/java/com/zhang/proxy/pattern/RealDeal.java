package com.zhang.proxy.pattern;

/**
 * 实际处理类
 *
 * @author zhangyu
 * @create 2018-11-29 10:54
 **/
public class RealDeal implements Business {

    @Override
    public void buyHouse() {
        System.out.println("买房子");
    }

    @Override
    public void buyCar() {
        System.out.println("买车");
    }

}
