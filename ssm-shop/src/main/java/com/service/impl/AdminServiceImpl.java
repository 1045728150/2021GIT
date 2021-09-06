package com.service.impl;

import com.mapper.AdminMapper;
import com.pojo.Admin;
import com.pojo.AdminExample;
import com.service.AdminService;
import com.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    //数据访问层对象(自动注入)
    @Autowired
    private AdminMapper adminMapper;

    //根据传入的用户名到数据库中查找到相应的用户对象，如果查到用户再进行对象比对
    public Admin login(String name, String pwd) {
        Admin admin=new Admin();
        //创建AdminExample对象，用来封装条件,追加sql语句where后面的操作
        AdminExample example=new AdminExample();
        //为AdminExample对象添加条件，成select * from admin where a_name='admin'
        example.createCriteria().andANameEqualTo(name);  //追加 a_name=‘name值’
        //执行条件查询语句
        List<Admin> adminList=adminMapper.selectByExample(example);
        if(adminList.size()>0){
            admin=adminList.get(0);
            /**
             * admin.getApass拿到的时数据库中的密文
             * 页面传来的pwd是123456
             * 在进行密码比对时，要将传入的password进行MD5加密，再与数据库中密码的比对
             */
            String miPwd= MD5Util.getMD5(pwd);
            if(miPwd.equals(admin.getaPass())){
                return admin;   //登录成功，返回对象
            }
        }
        return null;  //失败，返回null
    }
}
