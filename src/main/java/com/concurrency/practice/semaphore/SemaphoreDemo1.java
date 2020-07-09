package com.concurrency.practice.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author altenchen
 * @time 2020/7/9
 * @description 功能
 */
public class SemaphoreDemo1 {
    
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(50);
        for (int i = 0; i < 1000; i++) {
            service.submit(new Task());
        }
        service.shutdown();
    }
    
    private static class Task implements Runnable {
    
        @Override
        public void run() {
            System.out.println("调用了慢服务：" + Thread.currentThread().getName());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
}
