package com.example.demo.dao;

import com.example.demo.domain.RootDirectory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

public interface RootDirDao extends JpaRepository<RootDirectory, Serializable> {

    @Query(value = "select rootPath from root_directory  where id = (select max(id) from root_directory)", nativeQuery = true)
    String findRootDirectoryByMaxId();

    @Transactional
    @Modifying
    @Query(value = "insert into root_directory (id, RootPath) values (1, ?1) ", nativeQuery = true)
    void addRootPath(String rootPath);

    @Transactional
    @Modifying
    @Query(value = "update root_directory r set r.rootPath = ?1 where r.id = 1", nativeQuery = true)
    void updateRootPath(String rootPath);

    @Query("select r.rootPath from RootDirectory r ")
    List<String> findAllRootPath();


}
