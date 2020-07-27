package com.concurrency.practice.future.demo;

import com.concurrency.practice.threadfatory.CustomizeThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.*;

/**
 * @description:
 * @create: 2020/7/26
 * @author: altenchen
 */
@Slf4j
public class ConcurrentProcessor {
    
    private static Logger logger = LoggerFactory.getLogger(ConcurrentProcessor.class);
    
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

    final static ThreadPoolExecutor writeHbaseExecutor = new ThreadPoolExecutor(
            5,
            10,
            3,
            TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<Runnable>(10),
            new CustomizeThreadFactory.NameThreadFactory("writeHbaseExecutor")
    );

    final static ThreadPoolExecutor writeHdfsExecutor = new ThreadPoolExecutor(
            5,
            10,
            3,
            TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<Runnable>(10),
            new CustomizeThreadFactory.NameThreadFactory("writeHdfsExecutor")
    );

    final static ThreadPoolExecutor mainExecutor = new ThreadPoolExecutor(
            1,
            1,
            3,
            TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<Runnable>(10),
            new CustomizeThreadFactory.NameThreadFactory("mainExecutor")
    );




    public static void main(String[] args) {
        List<TaskModel> paths = new ArrayList<>();
    
        ConcurrentHashMap<Integer, TaskModel> taskModelMap = new ConcurrentHashMap<>();
        paths.add(new TaskModel(1, "remoteZipPath1", "", "", "", Status.DOWNLOAD));
        paths.add(new TaskModel(2, "remoteZipPath2", "", "", "", Status.DOWNLOAD));
        paths.add(new TaskModel(3, "remoteZipPath3", "", "", "", Status.DOWNLOAD));
        paths.add(new TaskModel(4, "remoteZipPath4", "", "", "", Status.DOWNLOAD));
        paths.add(new TaskModel(5, "remoteZipPath5", "", "", "", Status.DOWNLOAD));

        for (TaskModel taskModel : paths) {
            Set<Status> statuses = Collections.synchronizedSet(new HashSet<>());
            Task task = new Task(taskModel, taskModelMap, statuses);
            mainExecutor.execute(task);
        }
        
        mainExecutor.shutdown();
        System.out.println("主流程执行完成...");
    }

    static class Task implements Runnable {

        private TaskModel taskModel;
        private ConcurrentHashMap<Integer, TaskModel> taskModelMap;
        private Set<Status> states;

        public Task(TaskModel zipFilePath, ConcurrentHashMap<Integer, TaskModel> taskModelMap, Set<Status> states) {
            this.taskModel = zipFilePath;
            this.taskModelMap = taskModelMap;
            this.states = states;
        }

        private void executeTask() {
            CompletableFuture<TaskModel> downloadZipTask = CompletableFuture
                    .supplyAsync(() -> this.downloadZipFile(taskModel, taskModelMap), downloadExecutor);
    
            CompletableFuture<TaskModel> parseAndWriteHbaseTask = downloadZipTask
                    .thenApplyAsync(r -> this.parseText(r), parseExecutor)
                    .thenApplyAsync(r -> this.writeHbase(r), writeHbaseExecutor)
                    .exceptionally(ex -> {
                        try {
                            logger.error("下载，解析，写入Hbase作业执行异常: " + ex.getMessage());
                        } finally {
                            taskModel.setJobInfo("执行失败，下载，解析，写入Hbase作业执行异常：" + taskModel.toString());
                            taskModelMap.put(taskModel.getTaskId(), taskModel);
                            return taskModel;
                        }
                    });
    
            CompletableFuture<TaskModel> writeHdfsTask = downloadZipTask
                    .thenApplyAsync(r -> this.writeHdfs(r), writeHdfsExecutor)
                    .exceptionally(ex-> {
                        try {
                            logger.info("写入Hdfs作业执行异常: " + ex.getMessage());
                        } finally {
                            taskModel.setJobInfo("执行失败，写入Hdfs异常：" + taskModel.toString());
                            taskModelMap.put(taskModel.getTaskId(), taskModel);
                            return taskModel;
                        }
                    });
    
            
            CompletableFuture
                    .allOf(parseAndWriteHbaseTask, writeHdfsTask)
                    .thenAcceptAsync(t -> this.freeThreadPools())
                    .exceptionally(ex -> {
                        try {
                            logger.info("执行失败，关闭线程池资源异常: " + ex.getMessage());
                        } finally {
                            return null;
                        }
                    });
        }

        private TaskModel downloadZipFile(TaskModel task, Map<Integer, TaskModel> taskModelMap){
            String filePath = task.getRemoteZipFilePath();
            Status download = Status.DOWNLOAD;
            task.setStatus(download);
            states.add(download);
            task.setStates(states);
            String localZipFilePath = "/localZip/" + task.getTaskId();
            task.setLocalZipFilePath(localZipFilePath);
            
            System.out.println(Thread.currentThread().getName() + " Start downloading file，filePath: " + filePath);
            int sleepTime = 0;
            try {
                sleepTime = (new Random().nextInt(5000)) + 1000;
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " Downloading file finished, file saved in path: " + localZipFilePath + ", 耗时：" + sleepTime + "ms");
            
            download.setIsFinished(1);
            task.setStatus(download);
            states.add(download);
            task.setStates(states);
            taskModelMap.put(task.getTaskId(), task);
            
            
            return task;
        }

        private TaskModel parseText(TaskModel task) {
            System.out.println(Thread.currentThread().getName() + " Start parsing text, filePath=> " + task.getLocalZipFilePath());
            Status unzipAndParse = Status.UNZIP_AND_PARSE;
            task.setStatus(unzipAndParse);
            states.add(unzipAndParse);
            task.setStates(states);
            
            String localTextFilePath = "/localText/" + task.getTaskId();
            task.setLocalTextFilePath(localTextFilePath);
    
            int sleepTime = 0;
            try {
                sleepTime = (new Random().nextInt(3000)) + 1000;
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " Parsing text finished, file saved in path: " + task.getLocalTextFilePath() + ", 耗时：" + sleepTime + "ms");
            
            unzipAndParse.setIsFinished(1);
            task.setStatus(unzipAndParse);
            states.add(unzipAndParse);
            task.setStates(states);
            taskModelMap.put(task.getTaskId(), task);
            return task;
        }

        private TaskModel writeHbase(TaskModel task) {
            System.out.println(Thread.currentThread().getName() + " Start writing Hbase, pull target data from: " + task.getLocalTextFilePath());
           
            Status writeToHbase = Status.WRITE_TO_HBASE;
            task.setStatus(writeToHbase);
            states.add(writeToHbase);
            task.setStates(states);
    
            int sleepTime = 0;
            try {
                sleepTime = (new Random().nextInt(10000)) + 1000;
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " Writing hbase finished" + ", 耗时：" + sleepTime + "ms");
    
            writeToHbase.setIsFinished(1);
            task.setStatus(writeToHbase);
            states.add(writeToHbase);
            task.setStates(states);
            taskModelMap.put(task.getTaskId(), task);
            return task;
        }
    
        private TaskModel writeHdfs(TaskModel task) {
            System.out.println(Thread.currentThread().getName() + " Start writing Hdfs, pull target data from: " + task.getLocalTextFilePath());
            
            Status writeToHdfs = Status.WRITE_TO_HDFS;
            writeToHdfs.setIsFinished(0);
            task.setStatus(writeToHdfs);
            states.add(writeToHdfs);
            task.setStates(states);
    
            String hdfsFilePath = "/hdfs/" + task.getTaskId();
            task.setLocalTextFilePath(hdfsFilePath);
    
            int sleepTime = 0;
            try {
                sleepTime = (new Random().nextInt(10000)) + 1000;
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " Writing Hdfs finished, file write to: " + hdfsFilePath + ", 耗时：" + sleepTime + "ms");
    
            writeToHdfs.setIsFinished(1);
            task.setStatus(writeToHdfs);
            states.add(writeToHdfs);
            task.setStates(states);
            taskModelMap.put(task.getTaskId(), task);
            
            return task;
        }
        
        private void freeThreadPools() {
            downloadExecutor.shutdown();
            parseExecutor.shutdown();
            writeHbaseExecutor.shutdown();
            writeHdfsExecutor.shutdown();
            taskModelMap.forEach((key, value)-> System.out.println("任务" + key + "执行完成，任务状态为：" + value.getStates().toString()));
            System.out.println("所有任务线程池资源释放完毕...");
        }

        @Override
        public void run() {
            this.executeTask();
        }
    }
}
