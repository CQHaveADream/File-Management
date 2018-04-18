package com.example.demo.dao;

import com.example.demo.domain.FileMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

public interface FileMessageDao extends JpaRepository<FileMessage, Serializable> {

    @Transactional
    @Modifying
    @Query("update FileMessage f set f.label = ?1 , f.labelType = ?2 where f.absolutePath = ?3")
    void addLabelByPath(String label, String labelType, String absolutePath);

    @Query("select f from FileMessage f where f.absolutePath like concat('%',?1,'%') and (f.label like concat('%',?2,'%') or f.fileName like concat('%',?2,'%'))")
    List<FileMessage> findFilesLikeLabel(String absolutePath, String type);

    @Query("select f from FileMessage f where f.absolutePath like concat('%',?1,'%') and (f.label like concat('%',?2,'%') or f.fileName like concat('%',?2,'%')) and f.labelType = ?3")
    List<FileMessage> findFilesLikeLabelAndLabelType(String absolutePath, String type, String labelType);

    @Query("select f from FileMessage f where f.labelType like ?1")
    List<FileMessage> findFileMessagesLikeLabelType(String labelType);

    @Query("select f from FileMessage f where f.absolutePath = ?1")
    FileMessage findFileMessageByAbsolutePath(String path);

    @Transactional
    @Modifying
    @Query("delete from FileMessage f where f.absolutePath = ?1")
    void deleteFileMessageByAbsolutePath(String name);

    @Query("select f.absolutePath from FileMessage f where f.absolutePath like ?1")
    List<String> findAllAbsolute(String path);

    @Transactional
    @Modifying
    @Query("update FileMessage f set f.labelType = ?1 where f.labelType = ?2")
    void updateLabelType(String newType, String labelType);

}
