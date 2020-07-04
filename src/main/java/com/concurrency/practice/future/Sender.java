package com.concurrency.practice.future;

/**
 * @author altenchen
 * @time 2020/6/29
 * @description 功能
 */
public class Sender implements Runnable {
    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        System.out.println("sender...");
        System.out.println(Thread.currentThread().getName());
    }
}
