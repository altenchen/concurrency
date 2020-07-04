package com.concurrency.practice.queue;

/**
 * @author altenchen
 * @time 2020/7/4
 * @description 功能
 */
public class Person implements Comparable<Person> {
    
    private int id;
    
    private String name;
    
    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return this.id + ":" + this.name;
    }
    
    @Override
    public int compareTo(Person o) {
        return this.id < o.id ? 1 : (this.id > o.id ? -1 : 0);
    }
}
