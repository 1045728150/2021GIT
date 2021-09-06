package com.service;

import com.pojo.Admin;

public interface AdminService {
    //登录判断,返回查询到的admin类对象
    Admin login(String name, String password);
}
