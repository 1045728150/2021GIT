package com;

import com.mapper.ProductInfoMapper;
import com.pojo.ProductInfo;
import com.pojo.vo.ProductInfoVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * 使用@RunWith注解，让测试类被spring框架支接管
 * 使用@@ContextConfiguration，导入spring框架的配置文件
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext_dao.xml","classpath:applicationContext_service.xml"})
public class SpringTest {
    @Autowired
    ProductInfoMapper mapper ;
    @Test
    public void SelectConditionTest(){
        ProductInfoVo vo=new ProductInfoVo();
        //vo.setPname("1");
        //vo.setTypeid();
        //vo.setLprice(2000);
        //vo.setHprice(4000);

        List<ProductInfo> list = mapper.selectCondition(vo);
        for (ProductInfo info:list) {
            System.out.println(info);
        }
    }
}
