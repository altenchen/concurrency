package com.concurrency.practice.lock.deadlock;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * @description:
 * @create: 2020/7/14
 * @author: altenchen
 */
public class MustDeadLock implements Runnable{

    private int flag;

    static Object o1 = new Object();

    static Object o2 = new Object();

    @Override
    public void run() {
        System.out.println("线程" + Thread.currentThread().getName() + "的flag为" + flag);
        if (flag == 1) {
            synchronized (o1) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (o2) {
                    System.out.println("线程1获得了两把锁");
                }
            }
        }

        if (flag == 2) {
            synchronized (o2) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (o1) {
                    System.out.println("线程2获得了两把锁");
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MustDeadLock r1 = new MustDeadLock();
        MustDeadLock r2 = new MustDeadLock();

        r1.flag = 1;
        r2.flag = 2;

        Thread t1 = new Thread(r1, "t1");
        Thread t2 = new Thread(r2, "t2");

        t1.start();
        t2.start();

        Thread.sleep(1000);
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        long[] deadlockedThreads = threadMXBean.findDeadlockedThreads();
        if (deadlockedThreads != null && deadlockedThreads.length > 0) {
            for (int i = 0; i < deadlockedThreads.length; i++) {
                ThreadInfo threadInfo = threadMXBean.getThreadInfo(deadlockedThreads[i]);
                System.out.println("线程id为[" + threadInfo.getThreadId() + "], 线程名为[" + threadInfo.getThreadName() + "]的线程已经发生死锁，需要的锁正在被线程[" + threadInfo.getLockOwnerName() +"]持有。");
            }
        }


    }
}
