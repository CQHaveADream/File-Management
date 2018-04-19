package com.example.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.dao.LabelTypeDao;
import com.example.demo.domain.LabelType;
import com.example.demo.util.TasilyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 标签管理
 * @author chenqian
 * @date 2018-04-14 15:20
 */

@Service
public class LabelTypeService {

    @Autowired
    private LabelTypeDao labelTypeDao;

    @Autowired
    private TasilyUtil tasilyUtil;

    /**
     * @return
     */
    public List<LabelType> findAllLabelType(){
       return labelTypeDao.findAll();
    }

    public JSONObject addLabel(String labelName){
        LabelType labelType = labelTypeDao.findByName(labelName);
        if (labelType != null) return tasilyUtil.info("ThisLabelTypeAlreadyExist");
        LabelType label = new LabelType();
        label.setName(labelName);
        labelTypeDao.save(label);
        return tasilyUtil.info("addLabelSuccess");
    }

    @Transactional
    public JSONObject deleteLabelType(String labelName){
        LabelType labelType = labelTypeDao.findByName(labelName);
        if (labelType == null) return tasilyUtil.info("ThisLabelNotExist");
        labelTypeDao.deleteByName(labelName);
        return tasilyUtil.info("deleteLabelTypeSuccess");
    }

}
