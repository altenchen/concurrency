package com.concurrency.practice.lock.deadlock;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * @description:
 * @create: 2020/7/15
 * @author: altenchen
 */
public class DiningPhilosophers {

    public static class Philosopher implements Runnable{

        private Object leftChopstick;
        private Object rightChopstick;

        public Philosopher(Object leftChopstick, Object rightChopstick) {
            this.leftChopstick = leftChopstick;
            this.rightChopstick = rightChopstick;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    doAction("思考人生，宇宙，万物，灵魂...");
                    synchronized (leftChopstick) {
                        doAction("拿起左边筷子");
                        synchronized (rightChopstick) {
                            doAction("拿起右边筷子");
                            doAction("吃饭");
                            doAction("放下右边的筷子");
                        }
                        doAction("放下左边的筷子");
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void doAction(String action) throws InterruptedException {
            System.out.println(Thread.currentThread().getName() + " " + action);
            Thread.sleep((long)(Math.random()*10));
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Philosopher[] philosophers = new Philosopher[5];
        Object[] chopsticks = new Object[philosophers.length];
        for (int i = 0; i < chopsticks.length; i++) {
            chopsticks[i] = new Object();
        }
        for (int i = 0; i < philosophers.length; i++) {
            Object leftChopstick = chopsticks[i];
            Object rightChopstick;
            if (i == philosophers.length - 1) {
                rightChopstick = chopsticks[0];
            } else {
                rightChopstick = chopsticks[i + 1];
            }

            if (i == philosophers.length - 1) {
                philosophers[i] = new Philosopher(rightChopstick, leftChopstick);
            } else {
                philosophers[i] = new Philosopher(leftChopstick, rightChopstick);
            }

            new Thread(philosophers[i], "哲学家" + (i + 1) + "号").start();
        }

        new Thread(new DeadLockDetector(), "DeadLockDetectorThread").start();
    }


    private static class DeadLockDetector implements Runnable {

        private boolean isStuck = false;

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + " 线程开始死锁监测...");
                while (!isStuck) {
                    Thread.sleep(1000);
                    ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
                    long[] deadlockedThreads = threadMXBean.findDeadlockedThreads();
                    if (deadlockedThreads != null && deadlockedThreads.length > 0) {
                        isStuck = true;
                        for (int i = 0; i < deadlockedThreads.length; i++) {
                            ThreadInfo threadInfo = threadMXBean.getThreadInfo(deadlockedThreads[i]);
                            System.out.println("线程id为[" + threadInfo.getThreadId() + "], 线程名为[" + threadInfo.getThreadName() + "]的线程已经发生死锁，需要的锁正在被线程[" + threadInfo.getLockOwnerName() +"]持有。");
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
