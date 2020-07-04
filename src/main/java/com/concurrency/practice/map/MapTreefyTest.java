package com.concurrency.practice.map;

import java.util.HashMap;

/**
 * @author altenchen
 * @time 2020/7/4
 * @description 功能
 */
public class MapTreefyTest {
    
    public static void main(String[] args) {
        HashMap<MapTreefyTest, Integer> map = new HashMap<>(1);
        for (int i = 0; i < 1000; i++) {
            MapTreefyTest mapTreefyTest = new MapTreefyTest();
            map.put(mapTreefyTest, null);
        }
        System.out.println("运行结束");
    }
    
    @Override
    public int hashCode() {
        return 1;
    }
}
