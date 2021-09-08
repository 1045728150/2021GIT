import com.mapper.ProductInfoMapper;
import com.pojo.ProductInfo;
import com.vo.ProductInfoVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext_dao.xml","classpath:applicationContext_service.xml"})
public class MapperTest {
    @Autowired
    ProductInfoMapper productInfoMapper;

    @Test
    public void Test1(){
        ProductInfoVo vo=new ProductInfoVo();
        vo.setVoName("Pro");
        //vo.setVoTypeId(4);
        //vo.setLprice(2500);
        vo.setHprice(5000);
        List<ProductInfo> list = productInfoMapper.selectCondition(vo);
        for(ProductInfo info:list){
            System.out.println(info.getpId()+"---"+info.getpName()+"---"+info.getTypeId()+"---"+info.getpPrice());
        }
    }
}
