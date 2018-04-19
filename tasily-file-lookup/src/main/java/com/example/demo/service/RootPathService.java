package com.example.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.dao.FileMessageDao;
import com.example.demo.dao.RootDirDao;
import com.example.demo.domain.FileMessage;
import com.example.demo.domain.LabelType;
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

    @Autowired
    private LabelTypeService labelTypeService;

    public JSONObject checkRootDirectory(){
        String rootPath = rootDirDao.findRootDirectoryByMaxId();
        if (rootPath == null){ return tasilyUtil.info("RootPathNotExist"); }
        List<LabelType> labelTypes = labelTypeService.findAllLabelType();
        JSONObject object = tasilyUtil.getFilesMsg(rootPath);
        object.put("labelTypes",labelTypes);
        object.put("rootPath", rootPath);
        return object;
    }

    @Transactional
    public JSONObject saveOrModifyRootPath(String rootPath, boolean addFlag, boolean updateFlag){
        String lowerPath = rootPath.toLowerCase();
        File rootFile = new File(rootPath);
        if (rootFile.lastModified() == 0) return tasilyUtil.info("filePathNotExist");
        if (addFlag == false && updateFlag == false) return tasilyUtil.info("NoStatus");
        if (addFlag) {
            SaveObject(lowerPath);
            HandelAddFiles(rootPath);
        }
        if (updateFlag) {
            String dbRoot = rootDirDao.findRootDirectoryByMaxId();
            if (lowerPath.equals(dbRoot)) return tasilyUtil.info("ThePathAlreadyExist");
            List<String> dataPath = rootDirDao.findAllRootPath();
            if (dataPath.contains(lowerPath)){
                SaveObject(lowerPath);
                return new JSONObject();
            }else {
                SaveObject(lowerPath);
                HandelAddFiles(rootPath);
            }
        }
        return tasilyUtil.info("success");
    }

    public JSONObject HandelAddFiles(String filePath) {
        File rootFile = new File(filePath);
        if (rootFile.lastModified() == 0) return tasilyUtil.info("filePathNotExist");
        File[] files = rootFile.listFiles();
        if (files.length == 0 ) return tasilyUtil.info("FileContentIsEmpty");
        List<FileMessage> messageList = new ArrayList<>();
        for (File file : files) {
            if (file.isDirectory()){ //是文件夹
                HandelAddFiles(file.getAbsolutePath());
            }else if (file.isFile()){ //是文件
                FileMessage Message = new FileMessage();
                Message.setFileName(file.getName());
                Message.setAbsolutePath(file.getAbsolutePath());
                Message.setLastModifyTime(file.lastModified());
                messageList.add(Message);
            }
        }
        fileMessageDao.saveAll(messageList);
        return tasilyUtil.info("success");
    }

    @Transactional
    public void SaveObject(String path){
        RootDirectory rootDirectory = new RootDirectory();
        rootDirectory.setRootPath(path);
        rootDirDao.save(rootDirectory);
    }

}
