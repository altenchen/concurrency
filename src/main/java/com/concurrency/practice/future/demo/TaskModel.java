package com.concurrency.practice.future.demo;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author altenchen
 * @time 2020/7/27
 * @description 功能
 */
public class TaskModel{
    
    private int taskId;
    
    private String remoteZipFilePath;
    
    private String localZipFilePath;
    
    private String localTextFilePath;
    
    private String hdfsFilePath;
    
    private Status status;
    
    private Set<Status> states;
    
    private String jobInfo;
    
    private int taskStatus;
    
    public int getTaskId() {
        return taskId;
    }
    
    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
    
    public String getRemoteZipFilePath() {
        return remoteZipFilePath;
    }
    
    public void setRemoteZipFilePath(String remoteZipFilePath) {
        this.remoteZipFilePath = remoteZipFilePath;
    }
    
    public String getLocalZipFilePath() {
        return localZipFilePath;
    }
    
    public void setLocalZipFilePath(String localZipFilePath) {
        this.localZipFilePath = localZipFilePath;
    }
    
    public String getLocalTextFilePath() {
        return localTextFilePath;
    }
    
    public void setLocalTextFilePath(String localTextFilePath) {
        this.localTextFilePath = localTextFilePath;
    }
    
    public String getHdfsFilePath() {
        return hdfsFilePath;
    }
    
    public void setHdfsFilePath(String hdfsFilePath) {
        this.hdfsFilePath = hdfsFilePath;
    }
    
    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
    
    public Set<Status> getStates() {
        return states;
    }
    
    public void setStates(Set<Status> states) {
        this.states = states;
    }
    
    public String getJobInfo() {
        return jobInfo;
    }
    
    public void setJobInfo(String jobInfo) {
        this.jobInfo = jobInfo;
    }
    
    public int getTaskStatus() {
        return taskStatus;
    }
    
    public void setTaskStatus(int taskStatus) {
        this.taskStatus = taskStatus;
    }
    
    public TaskModel(int taskId, String remoteZipFilePath, String localZipFilePath, String localTextFilePath, String hdfsFilePath, Status status) {
        this.taskId = taskId;
        this.remoteZipFilePath = remoteZipFilePath;
        this.localZipFilePath = localZipFilePath;
        this.localTextFilePath = localTextFilePath;
        this.hdfsFilePath = hdfsFilePath;
        this.status = status;
    }
    
    @Override
    public String toString() {
        return modelToString(this);
    }
    
    public static <T> String modelToString(T t) {
        StringBuilder result = new StringBuilder("[");
        
        for (Field declaredField : t.getClass().getDeclaredFields()) {
            try {
                result
                        .append(declaredField.getName())
                        .append("=")
                        .append(declaredField.get(t))
                        .append(",");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        
        return result.substring(0, result.length() - 1) + "]";
    }
}
