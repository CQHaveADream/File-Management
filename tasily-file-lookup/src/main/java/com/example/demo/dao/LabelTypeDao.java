package com.example.demo.dao;

import com.example.demo.domain.LabelType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;


public interface LabelTypeDao extends JpaRepository<LabelType, Serializable>{

    @Query("select l from LabelType l where l.name = ?1")
    LabelType findByName(String labelName);

    @Transactional
    @Modifying
    @Query("delete from LabelType l where l.name = ?1")
    void deleteByName(String labelName);

}
