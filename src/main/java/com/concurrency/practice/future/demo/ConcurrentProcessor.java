package com.concurrency.practice.future.demo;

import com.concurrency.practice.threadfatory.CustomizeThreadFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @description:
 * @create: 2020/7/26
 * @author: altenchen
 */
@Slf4j
public class ConcurrentProcessor {

    final static ThreadPoolExecutor downloadExecutor = new ThreadPoolExecutor(
            5,
            10,
            3,
            TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<Runnable>(10),
            new CustomizeThreadFactory.NameThreadFactory("downloadExecutor")
    );

    final static ThreadPoolExecutor parseExecutor = new ThreadPoolExecutor(
            5,
            10,
            3,
            TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<Runnable>(10),
            new CustomizeThreadFactory.NameThreadFactory("parseExecutor")
    );

    final static ThreadPoolExecutor sendExecutor = new ThreadPoolExecutor(
            5,
            10,
            3,
            TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<Runnable>(10),
            new CustomizeThreadFactory.NameThreadFactory("sendExecutor")
    );

    final static ThreadPoolExecutor ftpExecutor = new ThreadPoolExecutor(
            5,
            10,
            3,
            TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<Runnable>(10),
            new CustomizeThreadFactory.NameThreadFactory("ftpExecutor")
    );

    final static ThreadPoolExecutor mainExecutor = new ThreadPoolExecutor(
            5,
            10,
            3,
            TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<Runnable>(10),
            new CustomizeThreadFactory.NameThreadFactory("mainExecutor")
    );




    public static void main(String[] args) throws InterruptedException {

        List<String> paths = new ArrayList<>();

        paths.add("path1");
        paths.add("path2");
        paths.add("path3");
        paths.add("path4");
        paths.add("path5");

        for (String file : paths) {
            Task task = new Task(file);
            mainExecutor.execute(task);
        }

        while (!downloadExecutor.isTerminated()) {
            Thread.sleep(200L);
        }
        downloadExecutor.shutdown();

        while (!parseExecutor.isTerminated()) {
            Thread.sleep(200L);
        }
        parseExecutor.shutdown();

        while (!sendExecutor.isTerminated()) {
            Thread.sleep(200L);
        }
        sendExecutor.shutdown();

        while (!ftpExecutor.isTerminated()) {
            Thread.sleep(200L);
        }
        ftpExecutor.shutdown();

        while (!mainExecutor.isTerminated()) {
            Thread.sleep(200L);
        }
        mainExecutor.shutdown();

    }

    static class Task implements Runnable {

        private String zipFilePath;

        public Task(String zipFilePath) {
            this.zipFilePath = zipFilePath;
        }

        private void executeTask() {
            CompletableFuture
                    .supplyAsync(()-> this.downloadZipFile(zipFilePath), downloadExecutor)
                    .thenAcceptAsync(e-> this.parseText(e), parseExecutor)
                    .thenAcceptAsync(e-> this.sendKafka(), sendExecutor)
                    .thenAccept(v-> ftpExecutor.shutdown())
                    .exceptionally(e-> {
                        try {
                            System.out.println("执行异常");
                            return null;
                        } finally {
                            return null;
                        }
                    });
        }

        private String downloadZipFile(String filePath){
            String localFilePath = "";
            System.out.println(Thread.currentThread().getName() + " Start downloading file，filePath: " + filePath);
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " Downloading file finished...");
            return filePath;
        }

        private void parseText(String filePath) {
            System.out.println(Thread.currentThread().getName() + " Start parsing text, filePath=> " + filePath);
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " Parsing text finished...");
        }

        private void sendKafka() {
            System.out.println(Thread.currentThread().getName() + " Start sending kafka...");
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " Sending kafka finished...");
        }

        @Override
        public void run() {
            this.executeTask();
        }
    }
}
