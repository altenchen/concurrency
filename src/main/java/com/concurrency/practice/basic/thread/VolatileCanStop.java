package com.concurrency.practice.basic.thread;

/**
 * @description:
 * @create: 2020/8/15
 * @author: altenchen
 */
public class VolatileCanStop implements Runnable {

    private volatile boolean canceled = false;

    @Override
    public void run() {
        int num = 0;
        try {
            while (!canceled && num <= 10000000) {
                if (num % 10 == 0) {
                    System.out.println(num + "是10的倍数。");
                }
                num++;
                Thread.sleep(1);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        VolatileCanStop volatileCanStop = new VolatileCanStop();
        Thread thread = new Thread(volatileCanStop);
        thread.start();
        Thread.sleep(3000);
        volatileCanStop.canceled = true;
    }
}
