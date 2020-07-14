package com.concurrency.practice.queue;

import java.util.PriorityQueue;

/**
 * @author altenchen
 * @time 2020/7/14
 * @description 功能
 */
public class PriorityBlockingQueueDemo {
    
    public static void main(String[] args) {
    
        PriorityQueue<Person> queue = new PriorityQueue<>();
    
        Person p1 = new Person(1, 3);
        Person p2 = new Person(3, 3);
        Person p3 = new Person(3, 2);
        Person p4 = new Person(2, 1);
        Person p5 = new Person(2, 2);
    
        queue.add(p1);
        queue.add(p2);
        queue.add(p3);
        queue.add(p4);
        queue.add(p5);
        
        queue.forEach(x-> System.out.println(x));
        
        
    
    }


}
