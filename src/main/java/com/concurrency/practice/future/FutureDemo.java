//package com.concurrency.practice.future;
//
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ThreadFactory;
//import java.util.function.Supplier;
//
///**
// * @author altenchen
// * @time 2020/6/29
// * @description 功能
// */
//public class FutureDemo {
//
//    public static void main(String[] args) {
//
//
//
//        ExecutorService firstExecutors = Executors.newFixedThreadPool(10);
////        ExecutorService secondExecutors = Executors.newFixedThreadPool(10);
////        ExecutorService senderExecutors = Executors.newFixedThreadPool(10);
//
//        CompletableFuture
//                .supplyAsync(()-> new Sender().run(), firstExecutors)
//                .exceptionally(e -> {
//                    System.out.println("处理异常...");
//                });
//
//    }
//
//    private static String sendStartMethod() {
//        int i = 0;
//        String res = "";
//        for (int j = 0; j < 10; j++) {
//            res = "first method" + i++;
//            System.out.println(res + " --> " + Thread.currentThread().getName());
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        return res;
//    }
//
//    private static String sendSecondMethod() {
//        System.out.println("second method called");
//        return "second method";
//    }
//
//    private static String senderMethod() {
//        System.out.println("sender method called");
//        return "sender method";
//    }
//
//
//}
