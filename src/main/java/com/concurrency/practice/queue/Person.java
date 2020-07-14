package com.concurrency.practice.queue;

import lombok.Data;

import java.util.Comparator;

/**
 * @author altenchen
 * @time 2020/7/4
 * @description 功能
 */
@Data
public class Person implements Comparable<Person> {
    
    private int id;
    
    private long timestamp;
    
    private String name;
    
    public Person(int id, long timestamp) {
        this.id = id;
        this.timestamp = timestamp;
    }
    
    public static final Comparator<Person> COMPARATOR = Comparator.comparingLong(Person::getTimestamp)
            .thenComparingInt(Person::getId);
    
    @Override
    public int compareTo(Person o) {
        return COMPARATOR.compare(this, o);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (null == o || getClass() != o.getClass()) {
            return false;
        }
        return super.equals(o);
    }
}
