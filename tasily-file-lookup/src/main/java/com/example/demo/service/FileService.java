package com.example.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.dao.FileMessageDao;
import com.example.demo.domain.FileMessage;
import com.example.demo.util.TasilyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author chenqian
 * @date 2018-04-03 22:13
 */
@Service
public class FileService {

    @Autowired
    private TasilyUtil tasilyUtil;

    @Autowired
    private FileMessageDao fileMessageDao;

    @Transactional
    public JSONObject open(String absolutePath) throws IOException{
        File file = new File(absolutePath);
        if (!file.exists()) {
            fileMessageDao.deleteFileMessageByAbsolutePath(absolutePath);
            return tasilyUtil.info("TheFileDeleteSuccess");
        }
        Runtime.getRuntime().exec("cmd /c " + absolutePath);
        return tasilyUtil.info("OpenTargetFileSuccess");
    }

    @Transactional
    public void addLabel(String label, String path){
        fileMessageDao.addLabelByPath(label, path);
    }

    public List<FileMessage> findFilesByType(String path, String type){
        return fileMessageDao.findFilesLikeLabel(tasilyUtil.HandelPath(path), type);
    }

    public List<FileMessage> findFilesByFileName(String path, String fileName){
        return fileMessageDao.findFilesLikeFileName(tasilyUtil.HandelPath(path), fileName);
    }

    public List<String> findAllFileAbsolute(String path){
       return fileMessageDao.findAllAbsolute("%" + tasilyUtil.HandelPath(path) + "%");
    }

}
