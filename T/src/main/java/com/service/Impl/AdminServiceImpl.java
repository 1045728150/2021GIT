package com.service.Impl;

import com.mapper.AdminMapper;
import com.pojo.Admin;
import com.pojo.AdminExample;
import com.service.AdminService;
import com.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin selectAdminLogin(String name, String password) {
        Admin admin=new Admin();
        AdminExample example=new AdminExample();
        example.createCriteria().andANameEqualTo(name);
        List<Admin> admins = adminMapper.selectByExample(example);
        if(admins.size()>0){
            admin=admins.get(0);
            String Pwd= MD5Util.getMD5(password);
            if(Pwd.equals(admin.getaPass()))
                return admin;
        }
        return null;
    }
}
