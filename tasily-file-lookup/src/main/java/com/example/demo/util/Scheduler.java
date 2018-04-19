package com.example.demo.util;

import com.example.demo.dao.FileMessageDao;
import com.example.demo.dao.RootDirDao;
import com.example.demo.domain.FileMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenqian
 * @date 2018-04-04 12:53
 * 定时器
 */

@Component
public class Scheduler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RootDirDao rootDirDao;

    @Autowired
    private FileMessageDao fileMessageDao;

    @Scheduled(cron="0 0/6 * * * ?") //每xx分钟执行一次
    public void statusCheck() {
        logger.info("once every six minutes , start……");
        Long start = System.currentTimeMillis();
        String rootPath = rootDirDao.findRootDirectoryByMaxId();
        if (rootPath != null){
            String capitalPath = rootPath.toUpperCase();
            //本机文件 [0] 文件名 [1] 绝对路径 [2] 文件最后修改时间
            List<Object[]> messageList = new ArrayList<>();
            messageList = this.getFilesNameByLocalMC(messageList, capitalPath);
            for (Object[] message : messageList){ // 取本机文件绝对路径
                FileMessage file = fileMessageDao.findFileMessageByAbsolutePath((String) message[1]);
                if (file == null) {
                    FileMessage fileMessage = new FileMessage();
                    fileMessage.setAbsolutePath((String) message[1]);
                    fileMessage.setFileName((String) message[0]);
                    fileMessage.setLastModifyTime((Long) message[2]);
                    fileMessageDao.save(fileMessage);
                }
            }
            Long end2 = System.currentTimeMillis();
            logger.info("end , " + "by time: " + (end2-start)/1000 + " second");
        }
    }

    public List<Object[]> getFilesNameByLocalMC(List<Object[]> list, String rootPath) {
        File[] files = new File(rootPath).listFiles();
        for (File file : files) {
            if (file.isDirectory() && !file.isHidden()){ //是非隐藏文件
                getFilesNameByLocalMC(list, file.getAbsolutePath());
            }else if (file.isFile()){ //是文件
                list.add(new Object[]{file.getName(), file.getAbsolutePath(), file.lastModified()});
            }
        }
        return list;
    }
}
