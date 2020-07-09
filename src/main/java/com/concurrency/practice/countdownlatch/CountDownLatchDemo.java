package com.concurrency.practice.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author altenchen
 * @time 2020/7/9
 * @description 功能: 一个线程等待其他多个线程都执行完毕，再继续自己的工作
 */
public class CountDownLatchDemo {
    
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        ExecutorService service = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            int no = i + 1;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        long runTime = (long) (Math.random() * 10000);
                        Thread.sleep(runTime);
                        System.out.println(no + "号运动员已完成比赛，耗时" + runTime + "毫秒");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        countDownLatch.countDown();
                    }
                }
            };
            service.submit(runnable);
        }
        System.out.println("等待所有运动员完成比赛。");
        countDownLatch.await();
        System.out.println("所有人都完成比赛。");
        service.shutdown();
    }
    
}
