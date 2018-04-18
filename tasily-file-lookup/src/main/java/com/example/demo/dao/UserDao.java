package com.example.demo.dao;

import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

public interface UserDao extends JpaRepository<User, Serializable>{

    @Query("select u from User u where u.name = ?1")
    User findUserByName(String name);

    @Transactional
    @Modifying
    @Query(value = "update user u set u.password = ?1 where u.name = ?2", nativeQuery = true)
    void updatePasswordByName(String password, String name);

}
