package com.example.demo.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 文件信息实体
 * @author chenqian
 * @date 2018-04-03 20:18
 */

@Entity
@Table(name = "file_message")
public class FileMessage {

    private String fileName;
    private String label;
    private String absolutePath; //文件的绝对路径
    private Long lastModifyTime;

    public FileMessage() {
    }

    public FileMessage(String fileName, String label, String absolutePath) {
        this.fileName = fileName;
        this.label = label;
        this.absolutePath = absolutePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Id
    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public Long getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Long lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }
}
