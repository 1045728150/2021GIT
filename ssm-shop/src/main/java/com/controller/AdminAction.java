package com.controller;

import com.pojo.Admin;
import com.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
class AdminAction {
    //在所有界面层，一定有service对象
    @Autowired
    private AdminService adminService;

    //实现登录判断，并进行相应跳转
    @RequestMapping("/login")
    public String login(String name, String pwd, HttpServletRequest req){
        Admin admin = adminService.login(name, pwd);
        if(admin!=null){
            req.setAttribute("admin",admin);  //把admin传给main.jsp
            return "main"; //登录成功，跳转到main.jsp
        }
        else {
            req.setAttribute("errmsg","用户名或密码不正确!");
            return "login";  //登陆失败，回到login.jsp
        }
    }
}
