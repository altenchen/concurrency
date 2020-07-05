package com.concurrency.practice.atmoic;


import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @description:
 * @create: 2020/7/5
 * @author: altenchen
 */
public class AtomicIntegerFieldUpdaterDemo implements Runnable{

    private static Score math;
    private static Score computer;

    private static AtomicIntegerFieldUpdater<Score> scoreUpdater = AtomicIntegerFieldUpdater.newUpdater(Score.class, "score");

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            math.score++;
            scoreUpdater.getAndIncrement(computer);
        }
    }


    public static class Score{
        volatile int score;
    }

    public static void main(String[] args) throws InterruptedException {
        math = new Score();
        computer = new Score();
        AtomicIntegerFieldUpdaterDemo r = new AtomicIntegerFieldUpdaterDemo();
        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("normal var result: " + math.score);
        System.out.println("updated var result: " + computer.score);
    }
}
