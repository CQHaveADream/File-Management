package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.domain.LabelType;
import com.example.demo.service.FileService;
import com.example.demo.service.LabelTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author chenqian
 * @date 2018-04-14 15:25
 */

@RestController
@RequestMapping("/label")
public class LabelTypeController {

    @Autowired
    private LabelTypeService labelTypeService;

    @Autowired
    private FileService fileService;

    /**
     * 查询所有标签类型
     * @return
     */
    @RequestMapping(value = "/findAllLabelType.form", method = RequestMethod.POST)
    public List<LabelType> findAllLabelType(){
        return labelTypeService.findAllLabelType();
    }

    /**
     * 添加新标签类型
     * @param code
     * @return
     */
    @RequestMapping(value = "/addLabelType.form", method = RequestMethod.POST)
    public JSONObject addLabelType(@RequestBody JSONObject code){
        String labelName = code.getString("labelName");
        return labelTypeService.addLabel(labelName);
    }

    /**
     * 删除标签类型
     * @param object
     */
    @RequestMapping(value = "/deleteLabelType.form", method = RequestMethod.POST)
    public JSONObject deleteLabelType(@RequestBody JSONObject object){
        String labelName = object.getString("labelName");
        fileService.deleteLabelType("", labelName);
        return labelTypeService.deleteLabelType(labelName);
    }

}
