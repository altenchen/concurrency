package com.concurrency.practice.lock.rrwlock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author altenchen
 * @time 2020/7/2
 * @description 功能
 */
public class RWLockDowngradeSupport {
    
    private int data = 0;
    private volatile boolean validCache;
    private final ReentrantReadWriteLock rrwl = new ReentrantReadWriteLock();
    
    public void processValidData() {
        rrwl.readLock().lock();
        if (!validCache) {
            //在获取写锁之前，必须首先释放读锁。
            rrwl.readLock().unlock();
            rrwl.writeLock().lock();
            try {
                //这里需要再次判断数据的有效性,因为在我们释放读锁和获取写锁的空隙之内，可能有其他线程修改了数据。
                if (!validCache) {
                    System.out.println("缓存失效，线程" + Thread.currentThread().getName() + "得到写锁，更新数据");
                    data++;
                    validCache = true;
                }
                //在不释放写锁的情况下，直接获取读锁，这就是读写锁的降级。
                System.out.println("线程" + Thread.currentThread().getName() + "降级，获取读锁");
                rrwl.readLock().lock();
            } finally {
                //释放了写锁，但是依然有读锁
                System.out.println("线程" + Thread.currentThread().getName() + "释放写锁");
                rrwl.writeLock().unlock();
            }
        }
        
        try {
            System.out.println("线程" + Thread.currentThread().getName() + "读取数据：" + data);
        } finally {
            System.out.println("线程" + Thread.currentThread().getName() + "释放读锁");
            rrwl.readLock().unlock();
        }
    }
    
}
