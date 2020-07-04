package com.concurrency.practice.lock.rrwlock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author altenchen
 * @time 2020/7/4
 * @description 功能
 */
public class RWLockUpgradeUnsupport {
    
    private static final ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    
    private static void upgrade() {
        reentrantReadWriteLock.readLock().lock();
        System.out.println(Thread.currentThread().getName() + "获取读锁");
//        reentrantReadWriteLock.readLock().unlock();
//        System.out.println(Thread.currentThread().getName() + "释放读锁");
        reentrantReadWriteLock.writeLock().lock();
        System.out.println(Thread.currentThread().getName() + "获取写锁");
//        reentrantReadWriteLock.writeLock().unlock();
//        System.out.println(Thread.currentThread().getName() + "释放写锁");
    }
    
    public static void main(String[] args) {
        new Thread(()-> upgrade(), "thread-4").start();
        new Thread(()-> upgrade(), "thread-2").start();
        new Thread(()-> upgrade(), "thread-1").start();
        new Thread(()-> upgrade(), "thread-3").start();
    }
    
    
}
