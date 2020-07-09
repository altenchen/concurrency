package com.concurrency.practice.future;

import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author altenchen
 * @time 2020/7/9
 * @description 功能
 */
@Slf4j
public class TimeoutNoMatchFutureDemo {
    
    public static void main(String[] args) {
        TimeoutNoMatchFutureDemo timeoutNoMatchFutureDemo = new TimeoutNoMatchFutureDemo();
        System.out.println("开始准备比赛，准备时间5秒钟");
        Set<String> preparedPlayers = timeoutNoMatchFutureDemo.startCompute();
        System.out.println("准备时间已到，比赛开始，参赛运动员为：" + preparedPlayers);
    }
    
    private Set<String> startCompute() {
        Set<String> players = Collections.synchronizedSet(new HashSet<String>());
        CompletableFuture<Void> player1 = CompletableFuture.runAsync(new Task(1, players));
        CompletableFuture<Void> player2 = CompletableFuture.runAsync(new Task(2, players));
        CompletableFuture<Void> player3 = CompletableFuture.runAsync(new Task(3, players));
    
        CompletableFuture<Void> allPreparedPlayers = CompletableFuture.allOf(player1, player2, player3);
        try {
            allPreparedPlayers.get(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
            System.out.println("运动员准备超时，准备时间为5秒，运动员实际准备时间为：" + e.getMessage());
        }
        return players;
    }
    
    private class Task implements Runnable {
        
        int no;
        Set<String> preparedPlays;
    
        public Task(int no, Set<String> preparedPlays) {
            this.no = no;
            this.preparedPlays = preparedPlays;
        }
    
        @Override
        public void run() {
            long prepareTime = (long)(Math.random()*8000);
            try {
                Thread.sleep(prepareTime);
                System.out.println(no + "号运动员准备了" + prepareTime + "ms");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            preparedPlays.add(no + "");
        }
    }
    
}
