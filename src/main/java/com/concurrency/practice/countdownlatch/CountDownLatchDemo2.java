package com.concurrency.practice.countdownlatch;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author altenchen
 * @time 2020/7/9
 * @description 功能：多个线程等待某一个线程的信号，同时开始执行
 */
public class CountDownLatchDemo2 {
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("运动员有5秒准备时间");
        CountDownLatch latch = new CountDownLatch(1);
        ExecutorService service = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            int no = i + 1;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        long prepareTime = (long)(Math.random()* 8000);
                        Thread.sleep(prepareTime);
                        System.out.println(no + "号运动员准备完毕了，耗时" + prepareTime + "ms, 等待裁判员的发令枪");
                        latch.await();
                        System.out.println(no + "号运动员开始跑步了");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            service.submit(runnable);
        }
        Thread.sleep(5000);
        System.out.println("5秒准备时间已过，发令枪响，开始比赛！");
        latch.countDown();
        service.shutdown();
    }
    
    
}
