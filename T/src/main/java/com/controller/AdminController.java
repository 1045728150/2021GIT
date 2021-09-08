package com.controller;

import com.pojo.Admin;
import com.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    /**
     * 用来验证用户登录是否成功
     */
    @RequestMapping("/login")
    public String Login(String name, String pwd, HttpServletRequest req){
        Admin admin = adminService.selectAdminLogin(name, pwd);
        if(admin!=null){
            req.setAttribute("admin",admin);  //把admin传给main.jsp
            System.out.println("成功！！！");
            return "main";
        }else{
            req.setAttribute("errmsg","用户名或密码错误");
            System.out.println("失败！！！");
            return "login";
        }
    }

}
