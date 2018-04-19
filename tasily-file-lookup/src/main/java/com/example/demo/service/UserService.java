package com.example.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.dao.UserDao;
import com.example.demo.domain.User;
import com.example.demo.util.TasilyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chenqian
 * @date 2018-04-03 15:25
 */

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private TasilyUtil tasilyUtil;

    public JSONObject userRegister(String name, String password){
        User user = userDao.findUserByName(name);
        if (user != null){ return tasilyUtil.info("UserNameAlreadyExist"); }
        if (!password.matches("[a-zA-Z0-9]{6}")){ return tasilyUtil.info("密码长度为6位，由数字和字母(不区分大小写)组成"); }
        User u = new User();
        u.setName(name);
        u.setPassword(password);
        userDao.save(u);
        return tasilyUtil.info("UserRegisterSuccess");
    }

    public JSONObject login(String name, String password){
        User user = userDao.findUserByName(name);
        if (user == null){ return tasilyUtil.info("UserNotRegister");}
        if (!password.equalsIgnoreCase(user.getPassword()) || "".equals(password) || password == null){
            return tasilyUtil.info("PasswordNotCorrect");
        }
        return tasilyUtil.info("LoginSuccess");
    }

    @Transactional
    public JSONObject updatePassword(String password, String name){
        if (!password.matches("[a-zA-Z0-9]{6}")){ return tasilyUtil.info("密码长度为6位，由数字和字母(不区分大小写)组成"); }
        User user = userDao.findUserByName(name);
        if (user == null) return tasilyUtil.info("UserNotExist");
        userDao.updatePasswordByName(password, name);
        return tasilyUtil.info("ModifyPasswordSuccess");
    }



}
