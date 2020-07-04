package com.concurrency.practice.lock.spinlock;

/**
 * @author altenchen
 * @time 2020/7/4
 * @description 功能
 */
public class SpinlockTest {
    
    public static void main(String[] args) {
        ReentrantSpinLock reentrantSpinLock = new ReentrantSpinLock();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " 开始尝试获取自旋锁");
                reentrantSpinLock.lock();
                try {
                    System.out.println(Thread.currentThread().getName() + " 获取到了自旋锁");
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    reentrantSpinLock.unlock();
                    System.out.println(Thread.currentThread().getName() + " 释放自旋锁");
                }
            }
        };
    
        Thread t1 = new Thread(runnable, "thread-1");
        Thread t2 = new Thread(runnable, "thread-2");
        Thread t3 = new Thread(runnable, "thread-3");
        t1.start();
        t2.start();
        t3.start();
    }
    
    
}
