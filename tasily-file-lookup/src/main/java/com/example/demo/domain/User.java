package com.example.demo.domain;

import javax.persistence.*;

/**
 * @author chenqian
 * @date 2018-04-03 15:14
 */

@Entity
@Table(name = "user")
public class User {

    private Integer id;
    private String password;
    private String name;

    public User() {
    }

    public User(Integer id, String password, String name) {
        this.id = id;
        this.password = password;
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
