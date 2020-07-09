package com.concurrency.practice.future;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.concurrent.Executors.*;

/**
 * @author altenchen
 * @time 2020/7/9
 * @description 功能
 * 旅游问题描述：什么是旅游平台问题呢？如果想要搭建一个旅游平台，
 * 经常会有这样的需求，那就是用户想同时获取多家航空公司的航班信息。
 * 比如，从北京到上海的机票钱是多少？有很多家航空公司都有这样的航
 * 班信息，所以应该把所有航空公司的航班、票价等信息都获取到，然后
 * 再聚合。由于每个航空公司都有自己的服务器，所以分别去请求它们的
 * 服务器就可以了。
 */
public class ThreadPoolDemo {
    
    ExecutorService threadPool = Executors.newFixedThreadPool(3);
    
    public static void main(String[] args) throws InterruptedException {
        ThreadPoolDemo threadPoolDemo = new ThreadPoolDemo();
        System.out.println(threadPoolDemo.getPrices());
    }
    
    public Set<Integer> getPrices() throws InterruptedException {
        Set<Integer> prices = Collections.synchronizedSet(new HashSet<Integer>());
        threadPool.submit(new Task(123, prices));
        threadPool.submit(new Task(456, prices));
        threadPool.submit(new Task(789, prices));
        Thread.sleep(3000);
        return prices;
    }
    
    private class Task implements Runnable{
    
        Integer productId;
        Set<Integer> prices;
    
        public Task(Integer productId, Set<Integer> prices) {
            this.productId = productId;
            this.prices = prices;
        }
    
        @Override
        public void run() {
            int price = 0;
            try {
                Thread.sleep((long)(Math.random()*4000));
                price = (int)(Math.random()*4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            prices.add(price);
        }
    }
    
}
