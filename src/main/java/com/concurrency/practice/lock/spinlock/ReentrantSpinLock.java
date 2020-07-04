package com.concurrency.practice.lock.spinlock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author altenchen
 * @time 2020/7/4
 * @description 功能
 */
public class ReentrantSpinLock {
    
    private AtomicReference<Thread> owner = new AtomicReference<>();
    
    //重入次数
    private int count = 0;
    
    public void lock() {
        Thread t = Thread.currentThread();
        if (t == owner.get()) {
            ++count;
            System.out.println(t.getName() + " 自旋锁重入，重入次数累加1，count = " + count);
        }
        //自旋获取锁
        while (!owner.compareAndSet(null, t)) {
            System.out.println(t.getName() + " 自旋了");
        }
        System.out.println(Thread.currentThread().getName() + " 第一次获取自旋锁");
    }
    
    public void unlock() {
        Thread t = Thread.currentThread();
        //只有持有锁才能解锁
        if (t == owner.get()) {
            System.out.println(t.getName() + " 尝试解锁，获取到自旋锁，开始减少重入次数或者解锁，重入次数 = " + count);
            if (count > 0) {
                --count;
                System.out.println(t.getName() + " 自旋锁重入退出，递减后重入次数 = " + count);
            } else {
                //此处无需CAS操作，因为没有竞争，因为只有线程持有者才能解锁
                owner.set(null);
            }
        }
    }
    
}
