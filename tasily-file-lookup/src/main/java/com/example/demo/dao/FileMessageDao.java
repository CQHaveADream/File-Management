package com.example.demo.dao;

import com.example.demo.domain.FileMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

public interface FileMessageDao extends JpaRepository<FileMessage,Serializable> {

    @Transactional
    @Modifying
    @Query("update FileMessage f set f.label = ?1 where f.absolutePath = ?2")
    void addLabelByPath(String label, String path);

    @Query("select f from FileMessage f where f.absolutePath like concat('%',?1,'%') and (f.label like concat('%',?2,'%') or f.fileName like concat('%',?2,'%'))")
    List<FileMessage> findFilesLikeLabel(String absolutePath, String type);

    @Query("select f from FileMessage f where f.absolutePath like concat('%',?1,'%') and f.fileName like concat('%',?2,'%')")
    List<FileMessage> findFilesLikeFileName(String path, String fileName);

    @Query("select f from FileMessage f where f.absolutePath = ?1")
    FileMessage findFileMessageByAbsolutePath(String path);

    @Query("select f.label from FileMessage f where f.absolutePath = ?1")
    String findLabelByAbsolutePath(String name);

    @Transactional
    @Modifying
    @Query("delete from FileMessage f where f.absolutePath = ?1")
    void deleteFileMessageByAbsolutePath(String name);

    @Query("select f.absolutePath from FileMessage f where f.absolutePath like ?1")
    List<String> findAllAbsolute(String path);

}
