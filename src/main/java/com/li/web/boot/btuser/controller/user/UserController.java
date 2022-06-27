package com.li.web.boot.btuser.controller.user;

import com.li.web.boot.btuser.entity.Tb_user;
import com.li.web.boot.btuser.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    @Autowired
    IUserService iUserService;

    /*//登录
    @PostMapping({"/user/login"})
    public String login (){
        return "login";
    }*/

    //通过用户名获取用户信息
    @PreAuthorize("hasAnyRole('admin')")
    @GetMapping ({"/getUser"})
    @ResponseBody
    public Object getUser (String username){
        SecurityContext context = SecurityContextHolder.getContext();
        return iUserService.getUserByUsername(username);
    }

    @PostMapping({"/insertUser"})
    @ResponseBody
    public String insertUser (Tb_user tb_user){
        return iUserService.insertUser(tb_user);
    }



}