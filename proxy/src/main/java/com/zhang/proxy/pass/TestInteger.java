package com.zhang.proxy.pass;

import java.util.concurrent.TimeUnit;

/**
 * 包装类的传递问题
 *
 * @author zhangyu
 * @create 2018-12-26 16:55
 **/
public class TestInteger {

    public static void main(String[] args) throws InterruptedException {
        Integer index = 0;
        TestObject runable = new TestObject(index);

        synchronized (index) {
            new Thread(runable).start();
            index.wait();
        }
        System.out.println("end");

        /**
         *
         * 多线程锁中，synchronized后面必须是同一个对象才可以解锁成功
         * 以上代码 证实了，Integer中传入的是同一个对象
         * 即，包装类传递的也是引用，但是由于包装类以及String中，value是没有setter的，是用final修饰的，所以无法改变值，不要错以为传递的是值
         *
         *
         */
    }


    static class TestObject implements Runnable {

        private Integer index;

        public TestObject(Integer index) {
            this.index = index;
        }

        @Override
        public void run() {

            try {
                // sleep个5秒
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (index) {
                // 唤醒
                index.notify();
            }
        }
    }
}
