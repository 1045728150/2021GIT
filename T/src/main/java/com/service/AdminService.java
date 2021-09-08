package com.service;

import com.pojo.Admin;

import java.util.List;

public interface AdminService {
    public Admin selectAdminLogin(String name,String password);
}
