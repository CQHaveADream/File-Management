package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.service.UserService;
import com.example.demo.util.TasilyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenqian
 * @date 2018-04-03 15:18
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TasilyUtil tasilyUtil;

    @RequestMapping(value = "/userRegister.form", method = RequestMethod.POST)
    private JSONObject userRegister(@RequestBody JSONObject code){
        String name = code.getString("name");
        String password = code.getString("password");
        if ("".equals(name) || name == null) return tasilyUtil.info("UserNameIsEmpty");
        if ("".equals(password) || password == null) return tasilyUtil.info("PasswordIsEmpty");
        return userService.userRegister(name, password);
    }

    @RequestMapping(value = "/userLogin.form", method = RequestMethod.POST)
    private JSONObject login(@RequestBody JSONObject code){
        String name = code.getString("name");
        String password = code.getString("password");
        return userService.login(name, password);
    }

    @RequestMapping(value = "/modifyPassword.form", method = RequestMethod.POST)
    private JSONObject modifyPassword(@RequestBody JSONObject code){
        String password = code.getString("password");
        String name = code.getString("name");
        if ("".equals(name) || name == null) return tasilyUtil.info("CheckUserName");
        if (password.equals("") || password == null) return tasilyUtil.info("CheckPassword");
        return userService.updatePassword(password, name);
    }
}
