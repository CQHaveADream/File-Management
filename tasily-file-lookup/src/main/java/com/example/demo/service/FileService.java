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
        Process process = Runtime.getRuntime().exec("cmd /c " + absolutePath);
        if (process != null)  return tasilyUtil.info("OpenTargetFileSuccess");
        return tasilyUtil.info("OpenTargetFileFail");
    }

    @Transactional
    public void addLabel(String label, String path, String labelType){
        fileMessageDao.addLabelByPath(label, labelType, path);
    }

    public List<FileMessage> findFilesByType(String path, String type, String labelType){
        if (labelType != null){
            return fileMessageDao.findFilesLikeLabelAndLabelType(tasilyUtil.HandelPath(path), type, labelType);
        }else {
            return fileMessageDao.findFilesLikeLabel(tasilyUtil.HandelPath(path), type);
        }

    }

    public List<String> findAllFileAbsolute(String path){
       return fileMessageDao.findAllAbsolute("%" + tasilyUtil.HandelPath(path) + "%");
    }

    public List<FileMessage> findByLabelType(String labelType){
        return fileMessageDao.findFileMessagesLikeLabelType("%" + labelType + "%");
    }

    public void deleteLabelType(String newType, String labelType){
        fileMessageDao.updateLabelType(newType, labelType);
    }

}
