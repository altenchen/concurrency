package com.concurrency.practice.basic.thread;


/**
 * @description:
 * @create: 2021/1/11
 * @author: altenchen
 */
public class ThreadLocalDemo {

    static ThreadLocal<Integer> arg = new ThreadLocal();

    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                //初始化参数
                arg.set(0);
                task1();
            }
        });
        t1.start();

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                //初始化另外一个参数
                arg.set(1);
                task1();
            }
        });
        t2.start();

    }

    public static void task1() {
        task2();
    }

    private static void task2() {
        //ThreadLocal获取到的值，对每一个线程是唯一的
        System.out.println(arg.get());
    }

}
