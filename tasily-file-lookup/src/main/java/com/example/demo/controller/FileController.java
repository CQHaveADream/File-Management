package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.domain.FileMessage;
import com.example.demo.service.FileService;
import com.example.demo.service.RootPathService;
import com.example.demo.util.TasilyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @author chenqian
 * @date 2018-04-03 14:01
 */

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private RootPathService rootPathService;

    @Autowired
    private TasilyUtil tasilyUtil;

    @Autowired
    private FileService fileService;

    /**
     * 登录之后，检查根目录是否存在
     * @return object 1.{[fileName,fileName...]}
     *                2.{status:RootPathNotExist}
     */
    @RequestMapping(value = "/checkRootPath.form", method = RequestMethod.POST)
    private JSONObject checkRootDirectory(){
        return rootPathService.checkRootDirectory();
    }

    /**
     *
     * @param code 添加或更改根目录
     * @return
     */
    @RequestMapping(value = "/addAndUpdateRootPath.form", method = RequestMethod.POST)
    private JSONObject addAndUpdateRootPath(@RequestBody JSONObject code){
        String rootPath = code.getString("rootPath");
        boolean addFlag = code.containsKey("add")? code.getBoolean("add") : false;
        boolean updateFlag = code.containsKey("update")? code.getBoolean("update") : false;
        if ("".equals(rootPath) || rootPath == null) return tasilyUtil.info("NoRootPath");
        JSONObject object = rootPathService.saveOrModifyRootPath(rootPath, addFlag, updateFlag);
        object.put("rootPath", rootPath);
        return object;
    }

    /**
     * 根据文件夹绝对路径查找子文件
     * @param object 文件的绝对路径
     * @return
     */
    @RequestMapping(value = "/findSubFolder.form", method = RequestMethod.POST)
    private JSONObject findSubFolder(@RequestBody JSONObject object){
        String absolutePath = object.getString("absolutePath");
        if ("".equals(absolutePath) || absolutePath == null) return tasilyUtil.info("NoAbsolutePath");
        return tasilyUtil.getFilesMsg(absolutePath);
    }

    /**
     * 根据文件绝对路径，打开文件
     * @param object
     * @return {status:OpenTargetFileSuccess/TheFileNotExist}
     * @throws Exception
     */
    @RequestMapping(value = "/openTargetFile.form", method = RequestMethod.POST)
    private JSONObject openTargetFile(@RequestBody JSONObject object)throws IOException{
        String absolutePath = object.getString("absolutePath");
        if ("".equals(absolutePath) || absolutePath == null) return tasilyUtil.info("NoAbsolutePath");
        return fileService.open(absolutePath);
    }

    /**
     * 插入标签
     * @param code {label:标签；absolutePath：路径}
     */
    @RequestMapping(value = "/insertLabel.form", method = RequestMethod.POST)
    private JSONObject insertLabel(@RequestBody JSONObject code){
        String label = code.containsKey("label")? code.getString("label") : null;
        String absolutePath = code.containsKey("absolutePath")? code.getString("absolutePath") : null;
        String labelType = code.containsKey("labelType")? code.getString("labelType") : null;
        fileService.addLabel(label, absolutePath, labelType);
        return tasilyUtil.info("InsertLabelSuccess");
    }

    /**
     * 根据描述或文件名查询
     * @param code
     * @return
     */
    @RequestMapping(value = "/findFilesByLabelOrFileName.form", method = RequestMethod.POST)
    private List<FileMessage> findFilesByLabelOrFileName(@RequestBody JSONObject code){
        String labelType = code.containsKey("labelType")? code.getString("labelType") : null;
        String filePath = code.containsKey("path")? code.getString("path") : null;
        String type = code.containsKey("label")? code.getString("label") : null;
        List<FileMessage> list = fileService.findFilesByType(filePath, type, labelType);
        return list;
    }

    /**
     * 查询所有文件绝对路径
     * @return
     */
    @RequestMapping(value = "/findAbsoluteByPath.form", method = RequestMethod.POST)
    private List<String> findAllAbsolute(@RequestBody JSONObject code){
        String path = code.containsKey("path")? code.getString("path") : null;
        return fileService.findAllFileAbsolute(path);
    }

    /**
     * 根据标签类型查询
     * @param code
     * @return
     */
    @RequestMapping(value = "/findByLabelType.form", method = RequestMethod.POST)
    private List<FileMessage> findByLabelType(@RequestBody JSONObject code){
        String labelType = code.containsKey("labelType")? code.getString("labelType") : null;
        return fileService.findByLabelType(labelType);
    }

}
