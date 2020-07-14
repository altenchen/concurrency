package com.concurrency.practice.visibility;

/**
 * @description:
 * @create: 2020/7/11
 * @author: altenchen
 */
public class VisibilityProblem1 {

    int a = 10;
    int b = 20;

    private void change() {
        a = 30;
        b = a;
    }

    private void print() {
        System.out.println("b=" + b + "; a=" + a);
    }

    public static void main(String[] args) {
        while (true) {
            VisibilityProblem1 problem = new VisibilityProblem1();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    problem.change();
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
                    problem.print();
                }
            }).start();

        }

    }

}
