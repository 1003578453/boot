package com.li.web.boot.btuser.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class PageController {

    //主页面
    @GetMapping({"/indexPage"})
    public String indexPage (){
        return "index";
    }

    //登录页面
    @GetMapping({"/","/loginPage"})
    public String loginPage (){
        return "login";
    }

    //404页面
    @GetMapping({"/Page404","/Page405","/Page406"})
    public String Page404 (){
        return "error/404";
    }
}
