package com.concurrency.practice.lock.rrwlock;

import com.concurrency.practice.lock.rrwlock.RWLockDowngradeSupport;

/**
 * @author altenchen
 * @time 2020/7/4
 * @description 功能
 */
public class DowngradeLockTest {
    
    public static void main(String[] args) {
        RWLockDowngradeSupport lockDowngrade = new RWLockDowngradeSupport();
        
        new Thread(()-> lockDowngrade.processValidData(), "thread-2").start();
        new Thread(()-> lockDowngrade.processValidData(), "thread-1").start();
        new Thread(()-> lockDowngrade.processValidData(), "thread-3").start();
        new Thread(()-> lockDowngrade.processValidData(), "thread-5").start();
    }
    
    
}
