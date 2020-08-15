package com.concurrency.practice.future.demo;

import java.util.List;

/**
 * @author altenchen
 * @time 2020/7/27
 * @description 功能
 */
public enum Status {
    
    DOWNLOAD("下载Zip文件", 1, 0),
    UNZIP_AND_PARSE("解压并解析", 2, 0),
    WRITE_TO_HBASE("写入Hbase", 3, 0),
    WRITE_TO_HDFS("写入HDFS", 4, 0);
    
    private String desc;
    private int code;
    private int isFinished;
    
    private Status(String desc, int code, int isFinished) {
        this.desc = desc;
        this.code = code;
        this.isFinished = isFinished;
    }
    
    public String getDesc() {
        return desc;
    }
    
    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    public int getCode() {
        return code;
    }
    
    public void setCode(int code) {
        this.code = code;
    }
    
    public int getIsFinished() {
        return isFinished;
    }
    
    public void setIsFinished(int isFinished) {
        this.isFinished = isFinished;
    }
    
    @Override
    public String toString() {
        return "[desc=" + getDesc() + "], " + "[code=" + getCode() + "], " + "[isFinished=" + getIsFinished() + "]-end" ;
    }

}


