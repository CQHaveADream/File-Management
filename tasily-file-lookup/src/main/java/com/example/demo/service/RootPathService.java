package com.example.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.dao.FileMessageDao;
import com.example.demo.dao.RootDirDao;
import com.example.demo.domain.FileMessage;
import com.example.demo.domain.RootDirectory;
import com.example.demo.util.TasilyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenqian
 * @date 2018-04-03 14:57
 * 用于操作根目录
 */

@Service
public class RootPathService {

    @Autowired
    private RootDirDao rootDirDao;

    @Autowired
    private TasilyUtil tasilyUtil;

    @Autowired
    private FileMessageDao fileMessageDao;

    public JSONObject checkRootDirectory(){
        RootDirectory directory = rootDirDao.findRootDirectoryById(1);
        if (directory == null){ return tasilyUtil.info("RootPathNotExist"); }
        String rootPath = directory.getRootPath();
        JSONObject object = tasilyUtil.getFilesMsg(rootPath);
        object.put("rootPath", rootPath);
        return object;
    }

    @Transactional
    public JSONObject saveOrModifyRootPath(String rootPath, boolean addFlag, boolean updateFlag){
        if (addFlag == false && updateFlag == false) return tasilyUtil.info("NoStatus");
        if (addFlag) rootDirDao.addRootDirectory(1, rootPath);
        if (updateFlag) {
            String dataPath = rootDirDao.findRootPathById(1);
            if (rootPath.equals(dataPath))return tasilyUtil.info("ThePathAlreadyExist");
            rootDirDao.updateRootPath(rootPath);
        }
        this.HandelFiles(rootPath);
        return tasilyUtil.info(null);
    }

    //递归
    public JSONObject HandelFiles(String filePath) {
        File rootFile = new File(filePath);
        if (rootFile.lastModified() == 0) return tasilyUtil.info("filePathNotExist");
        File[] files = rootFile.listFiles();
        if (files.length == 0 ) return tasilyUtil.info("FileContentIsEmpty");
        List<FileMessage> messageList = new ArrayList<>();
        for (File file : files) {
            if (file.isDirectory()){ //是文件夹
                HandelFiles(file.getAbsolutePath());
            }else if (file.isFile()){ //是文件
                FileMessage fileMessage = new FileMessage();
                fileMessage.setFileName(file.getName());
                fileMessage.setAbsolutePath(file.getAbsolutePath());
                fileMessage.setLastModifyTime(file.lastModified());
                messageList.add(fileMessage);
            }
        }
        fileMessageDao.saveAll(messageList);
        return tasilyUtil.info("success");
    }

}
