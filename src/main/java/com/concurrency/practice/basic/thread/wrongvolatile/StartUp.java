package com.concurrency.practice.basic.thread.wrongvolatile;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @description:
 * @create: 2020/8/15
 * @author: altenchen
 */
public class StartUp {

    public static void main(String[] args) throws InterruptedException {
        ArrayBlockingQueue<Object> storage = new ArrayBlockingQueue<>(8);

        Producer producer = new Producer(storage);
        Thread producerThread = new Thread(producer);
        producerThread.start();
        Thread.sleep(500);

        Consumer consumer = new Consumer(storage);
        while (consumer.needMoreNums()) {
            System.out.println(consumer.storage.take() + "被消费了");
            Thread.sleep(100);
        }
        System.out.println("消费者不需要更多数据了");

        //一旦消费者不需要更多数据，应该让生产者也停止下来，但实际情况却停不下来
        producer.canceled = true;
        System.out.println(producer.canceled);
    }

}
