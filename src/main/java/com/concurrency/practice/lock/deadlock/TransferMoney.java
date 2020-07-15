package com.concurrency.practice.lock.deadlock;

/**
 * @description:
 * @create: 2020/7/15
 * @author: altenchen
 */
public class TransferMoney implements Runnable {

    private int flag;
    private static Account a = new Account(500);
    private static Account b = new Account(500);

    private static class Account{
        private int balance;
        public Account(int balance) {
            this.balance = balance;
        }
    }

    @Override
    public void run() {
        if (flag == 1) {
            transferMoney(a, b, 200);
        }
        if (flag == 0) {
            transferMoney(b, a, 200);
        }
    }

    private static void transferMoney(Account from, Account to, int amount) {
        int fromHash = System.identityHashCode(from);
        int toHash = System.identityHashCode(to);
        if (fromHash < toHash) {
            //先获取两把锁，然后开始转账
            synchronized (from) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (to) {
                    if (from.balance - amount < 0) {
                        System.out.println("余额不足，转账失败。");
                        return;
                    }
                    from.balance -= amount;
                    to.balance += amount;
                    System.out.println("成功转账" + amount + "元");
                }
            }
        } else if (fromHash > toHash) {
            //先获取两把锁，然后开始转账
            synchronized (to) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (from) {
                    if (from.balance - amount < 0) {
                        System.out.println("余额不足，转账失败。");
                        return;
                    }
                    from.balance -= amount;
                    to.balance += amount;
                    System.out.println("成功转账" + amount + "元");
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        TransferMoney r1 = new TransferMoney();
        TransferMoney r2 = new TransferMoney();
        r1.flag = 1;
        r2.flag = 0;

        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("a的余额" + a.balance);
        System.out.println("b的余额" + b.balance);
    }


}
