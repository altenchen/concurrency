package com.concurrency.practice.queue;

import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author altenchen
 * @time 2020/7/4
 * @description 功能
 */
public class BlockingQueueTest {
    
    public static void main(String[] args) throws InterruptedException {
        //add, remove element
        ArrayBlockingQueue<Integer> queue1 = new ArrayBlockingQueue<>(2);
//
////        queue1.add(1);
////        queue1.add(1);
////        queue1.add(1);
//
//        System.out.println(queue1.peek());
//        queue1.offer(1);
//        System.out.println(queue1.peek());
//        System.out.println(queue1.poll());
//        System.out.println(queue1.poll());
//        System.out.println(queue1.element());
//        try {
//            System.out.println(queue1.poll(100, TimeUnit.SECONDS));
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        
        priorityQueueTest();
        
    }
    
    private static void priorityQueueTest() throws InterruptedException {
        PriorityBlockingQueue<Person> priorityQueue = new PriorityBlockingQueue();
        priorityQueue.add(new Person(5, "KOBE"));
        System.out.println("容器为：" +priorityQueue);
        priorityQueue.add(new Person(4, "JORDAN"));
        System.out.println("容器为：" + priorityQueue);
        priorityQueue.add(new Person(6, "Mike"));
        System.out.println("容器为：" + priorityQueue);
        priorityQueue.add(new Person(1, "ALLEN"));
        System.out.println("容器为：" + priorityQueue);
    
        System.out.println("获取元素：" + priorityQueue.take().getId());
        System.out.println("容器为：" + priorityQueue);
        System.out.println("----------------");
    
        System.out.println("获取元素：" + priorityQueue.take().getId());
        System.out.println("容器为：" + priorityQueue);
        System.out.println("----------------");
    
        System.out.println("获取元素：" + priorityQueue.take().getId());
        System.out.println("容器为：" + priorityQueue);
        System.out.println("----------------");
        
    }
   
}
