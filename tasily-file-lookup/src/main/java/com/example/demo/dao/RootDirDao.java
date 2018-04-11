package com.example.demo.dao;

import com.example.demo.domain.RootDirectory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

public interface RootDirDao extends JpaRepository<RootDirectory,Serializable> {

    @Query("select r from RootDirectory r where r.id = ?1")
    RootDirectory findRootDirectoryById(Integer id);

    @Transactional
    @Modifying
    @Query(value = "insert into root_directory (id, rootPath) values (?1,?2)", nativeQuery = true)
    void addRootDirectory(Integer id, String rootPath);

    @Transactional
    @Modifying
    @Query(value = "update root_directory r set r.rootPath = ?1 where r.id = 1", nativeQuery = true)
    void updateRootPath(String rootPath);

    @Query("select r.rootPath from RootDirectory r where r.id = ?1")
    String findRootPathById(Integer id);


}
