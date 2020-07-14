package com.concurrency.practice.visibility;

/**
 * @description:
 * @create: 2020/7/11
 * @author: altenchen
 */
public class VisibilityProblem2 {

    int x = 0;

    public void write() {
        x = 1;
    }

    public void read() {
        int y = x;
    }

    public static void main(String[] args) {
        VisibilityProblem2 problem2 = new VisibilityProblem2();
        while (true) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    problem2.write();
                }
            }).start();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    problem2.read();
                }
            }).start();
        }
    }

}
