package com.concurrency.practice.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author altenchen
 * @time 2020/7/9
 * @description 功能
 */
public class SemaphoreDemo1 {
    
    private static Semaphore semaphore = new Semaphore(3, false);
    
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
            try {
                semaphore.acquire(3);
                System.out.println(Thread.currentThread().getName() + "拿到了许可证，花费2秒执行慢服务");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("慢服务执行完毕，" + Thread.currentThread().getName() + "释放了许可证");
            semaphore.release(3);
        }
    }
    
}
