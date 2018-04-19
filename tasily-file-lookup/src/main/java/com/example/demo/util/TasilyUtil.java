package com.example.demo.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.example.demo.dao.FileMessageDao;
import com.example.demo.domain.FileMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenqian
 * @date 2018-04-03 14:24
 */

@Component
public class TasilyUtil {

    @Autowired
    private FileMessageDao fileMessageDao;

    public JSONArray ObjectToJSONArray(Object obj, String ...filters) {
        SimplePropertyPreFilter spp = new SimplePropertyPreFilter();
        for (String filter : filters) {
            spp.getExcludes().add(filter);
        }
        String jsonString = JSONObject.toJSONString(obj, spp, SerializerFeature.DisableCircularReferenceDetect);
        return JSONArray.parseArray(jsonString);
    }

    public JSONObject ObjectToJSONObject(Object obj, String ...filters) {
        SimplePropertyPreFilter spp = new SimplePropertyPreFilter();
        for (String filter : filters) {
            spp.getExcludes().add(filter);
        }
        String jsonString = JSONObject.toJSONString(obj, spp, SerializerFeature.DisableCircularReferenceDetect);
        return JSONObject.parseObject(jsonString);
    }

    public JSONObject JsonArrayToJsonObject(JSONArray jo) throws Exception{
        JSONObject ret = new JSONObject();
        ret.put("data", jo == null ? "[]".getBytes() : jo.toJSONString().getBytes("UTF-8"));
        return ret;
    }

    public JSONArray JSONObjectToJSONArray(JSONObject object){
        JSONArray array = new JSONArray();
        String strJson = object.toJSONString();
        array.add(strJson);
        return array;
    }

    public List JSONArrayToList(JSONArray jsonArray){
        List list = new ArrayList();
        jsonArray.forEach(array->{
            list.add(array);
        });
        return list;
    }

    public JSONObject info(String info) {
        JSONObject retObj = new JSONObject();
        if(info != null) retObj.put("info", info);
        return retObj;
    }

    public JSONObject getFilesMsg(String Path){
        File rootFile = new File(Path);
        if (rootFile.lastModified() == 0) return this.info("filePathNotExist");
        File[] files = rootFile.listFiles();
        List<FileMessage> list = new ArrayList<>();
        for (File file : files){
            FileMessage fileMessage = new FileMessage();
            this.addByDbFileMessage(file, fileMessage);
            fileMessage.setFileName(file.getName());
            fileMessage.setAbsolutePath(file.getAbsolutePath());
            fileMessage.setLastModifyTime(file.lastModified());
            list.add(fileMessage);
        }
        JSONObject object = new JSONObject();
        object.put("subFileName", list);
        return object;
    }

    public String HandelPath(String path){
        String newPath = path.replace("\\","\\\\");
        return newPath;
    }

    public void addByDbFileMessage(File file, FileMessage fileMessage){
        FileMessage dbFile = fileMessageDao.findFileMessageByAbsolutePath(file.getAbsolutePath());
        if (dbFile !=null){
            if (dbFile.getLabel() != null ){ fileMessage.setLabel(dbFile.getLabel()); }
            if (dbFile.getLabelType()!= null){ fileMessage.setLabelType(dbFile.getLabelType()); }
        }
    }

}
