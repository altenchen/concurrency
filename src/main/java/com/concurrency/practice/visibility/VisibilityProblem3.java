package com.concurrency.practice.visibility;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author altenchen
 * @time 2020/7/18
 * @description 功能
 */
public class VisibilityProblem3 implements Runnable {
    
    private volatile int tickets = 200;
    
    private static ReentrantLock rrl = new ReentrantLock();
    
    @Override
    public void run() {
        while (true) {
            rrl.lock();
            System.out.println(Thread.currentThread().getName() + "获取到锁，开始消费车票");
            try {
                if (tickets > 0) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "消费车票，剩余车票数为：" + tickets--);
                } else {
                    break;
                }
            } finally {
                System.out.println(Thread.currentThread().getName() + "消费完成，释放锁");
                rrl.unlock();
            }
        }
    }
    
    public static void main(String[] args) {
        VisibilityProblem3 task = new VisibilityProblem3();
    
        Thread t1 = new Thread(task, "t1");
        Thread t2 = new Thread(task, "t2");
        Thread t3 = new Thread(task, "t3");
        
        t1.start();
        t2.start();
        t3.start();
    }
}
