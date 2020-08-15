package com.concurrency.practice.basic.thread.wrongvolatile;

import com.concurrency.practice.queue.BlockingQueueTest;

import java.util.concurrent.BlockingQueue;

/**
 * @description:
 * @create: 2020/8/15
 * @author: altenchen
 */
public class Producer implements Runnable {

    public volatile boolean canceled = false;
    BlockingQueue storage;

    public Producer(BlockingQueue storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        int num = 0;
        try {
            while (num < 100000 && !Thread.currentThread().isInterrupted()) {
                if (num % 50 == 0) {
                    storage.put(num);
                    System.out.println(num + "是50的倍数，被放到仓库中了。");
                }
                num++;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("生产者结束运行");
        }
    }
}
