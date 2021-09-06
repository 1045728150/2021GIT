package com;

import com.utils.MD5Util;
import org.junit.Test;


 class MyTest {
    /**
     * 测试MD5加密方法
     */
    @Test
    public void TsetMD5(){
        String mi= MD5Util.getMD5("123456");   //测试对123456进行加密
        System.out.println(mi);
    }
}
