package com.zhang.proxy.pass;

/**
 * @author zhangyu
 * @create 2018-12-26 17:13
 **/
public class PassDemo {

    public static void main(String[] args) {

        int a = 10;
        demo1(a);
        // 基本类型是值传递，demo1()的参数传入的是a的值，其实就是10，不会影响a本身
        System.out.println(a);

        System.out.println("================================");

        Person person = new Person();
        person.setName("张三");
        person.setAge(20);
        demo2(person);
        // 复合数据类型，demo2()参数传入的是对象a的地址值，demo2()中对a的修改，会导致值的改变
        System.out.println(person);

        System.out.println("================================");

        Integer integer = 10;
        demo3(integer);
        // Integer是包装类型的，其实也是类，传递的是引用，但是由于包装类的value字段是用final修饰的，所以一旦创建就无法改变，所以在方法外是无法改变包装类的值的
        // String也是一样
        System.out.println(integer);
        
    }

    public static void demo1(int a) {
        a = 20;
    }

    public static void demo2(Person person) {
        person.setName("李四");
        person.setAge(22);
    }

    public static void demo3(Integer integer) {
        integer = 20;
    }


}
