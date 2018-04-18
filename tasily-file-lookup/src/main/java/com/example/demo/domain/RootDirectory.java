package com.example.demo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 根目录实体
 * @author chenqian
 * @date 2018-04-03 14:14
 */

@Entity
@Table(name = "root_directory")
public class RootDirectory {
    private Integer id;
    private String rootPath; //根目录名称

    public RootDirectory() {
    }

    public RootDirectory(Integer id, String rootPath) {
        this.id = id;
        this.rootPath = rootPath;
    }

    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }
}
