package com.concurrency.practice.basic.thread;

/**
 * @description:
 * @create: 2020/8/15
 * @author: altenchen
 */
public class StopThread implements Runnable{

    @Override
    public void run() {
        int count = 0;
        while (!Thread.currentThread().isInterrupted() && count < 1000) {
            System.out.println("count = " + count++);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new StopThread());
        thread.start();
        thread.sleep(5);
        thread.interrupt();
    }
}
