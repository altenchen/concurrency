package com.concurrency.practice.basic.thread;

/**
 * @description:
 * @create: 2020/8/15
 * @author: altenchen
 */
public class StopDuringSleep {

    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = ()-> {
            int num = 0;
            try {
                while (!Thread.currentThread().isInterrupted() && num < 1000) {
                    System.out.println(num);
                    num++;
                    Thread.sleep(1000000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
        thread.sleep(5);
        thread.interrupt();
    }
}
