package com.concurrency.practice.basic.thread.wrongvolatile;

import java.util.concurrent.BlockingQueue;

/**
 * @description:
 * @create: 2020/8/15
 * @author: altenchen
 */
public class Consumer {

    BlockingQueue storage;

    public Consumer(BlockingQueue storage) {
        this.storage = storage;
    }

    public boolean needMoreNums() {
        if (Math.random() > 0.97) {
            return false;
        }
        return true;
    }
}
