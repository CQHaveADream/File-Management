package com.example.demo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 标签类型
 * @author chenqian
 * @date 2018-04-14 15:13
 */

@Entity
@Table(name = "label_type")
public class LabelType {

    private Integer id;
    private String name;

    public LabelType() {
    }

    public LabelType(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Id
    @GeneratedValue
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
