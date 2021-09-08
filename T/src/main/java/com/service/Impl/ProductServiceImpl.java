package com.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mapper.ProductInfoMapper;
import com.pojo.ProductInfo;
import com.pojo.ProductInfoExample;
import com.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductServiceImpl implements ProductInfoService {
    @Autowired
    private ProductInfoMapper mapper;
    /**
     * 查询所有商品信息：不分页显示
     * @return
     */
    @Override
    public List<ProductInfo> selectAll() {
        return mapper.selectByExample(new ProductInfoExample());
    }

    /**
     * 分页显示商品信息
     * @return
     */
    @Override
    public PageInfo splitPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);   //1.分页设置
        ProductInfoExample example=new ProductInfoExample();
        example.setOrderByClause("p_id desc");    //2.设置查询条件，按照id降序排列
        List<ProductInfo> list = mapper.selectByExample(example);  //3.执行sql，获取到查询结果集
        PageInfo<ProductInfo> pageInfo=new PageInfo<>(list);   //4.将查到的集合封装数据到PageInfo中，完成各个属性值的自动赋值
        return pageInfo;  //5.返回pageInfo对象
    }

    @Override
    public int addProduct(ProductInfo productInfo) {
        return mapper.insert(productInfo);
    }

    @Override
    public ProductInfo selectById(Integer pid) {
       return mapper.selectByPrimaryKey(pid);
    }

    @Override
    public int updateProduct(ProductInfo productInfo) {
        return mapper.updateByPrimaryKey(productInfo);
    }

    @Override
    public int deleteOne(Integer pid) {
        return mapper.deleteByPrimaryKey(pid);
    }

    @Override
    public int deleteBatch(String[] ids) {
        return mapper.deleteBatch(ids);
    }

}
