package com.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mapper.ProductInfoMapper;
import com.pojo.ProductInfo;
import com.pojo.ProductInfoExample;
import com.pojo.vo.ProductInfoVo;
import com.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {
    @Autowired
    private ProductInfoMapper productInfoMapper;


    public List<ProductInfo> getAll() {
        return productInfoMapper.selectByExample(new ProductInfoExample());
    }

    @Override
    public PageInfo splitPage(int pageNum, int pageSize) {
        //分页插件使用PageHelper完成分页设置
        PageHelper.startPage(pageNum, pageSize);
        //pageInfo的数据封装：进行有条件的查询，所以要创建ProductInfoExample对象
        ProductInfoExample example=new ProductInfoExample();
        //设置排序，按主键降序（因为新插入的数据在数据库的最后面，应该先显示新数据）
        example.setOrderByClause("p_id desc");
        //取集合（一定要在取集合前设置PageHelper.startPage）
        List<ProductInfo> list=productInfoMapper.selectByExample(example);
        //将查到的集合封装数据到PageInfo中,只需在new集合时将list传入参数即可自动实现所以成员变量的赋值
        PageInfo<ProductInfo> pageInfo=new PageInfo<>(list);
        return pageInfo;

    }

    @Override
    public int save(ProductInfo productInfo) {
        return productInfoMapper.insert(productInfo);

    }

    @Override
    public ProductInfo getByID(Integer pid) {
        return productInfoMapper.selectByPrimaryKey(pid);
    }

    @Override
    public int updateProductInfo(ProductInfo productInfo) {
       return productInfoMapper.updateByPrimaryKey(productInfo);
    }

    @Override
    public int deleteOne(Integer pid) {
        return productInfoMapper.deleteByPrimaryKey(pid);
    }

    @Override
    public int deleteBatch(String[] ids) {
        return productInfoMapper.deleteBatch(ids);
    }

    @Override
    public List<ProductInfo> selectCondition(ProductInfoVo vo) {
        return productInfoMapper.selectCondition(vo);
    }

    @Override
    public PageInfo splitPageVo(ProductInfoVo vo, int pagesize) {
        //取出集合前，先设置PageHelper.startPage()
        PageHelper.startPage(vo.getPage(), pagesize);
        //取出vo对象表示的条件下的集合
        List<ProductInfo> list= productInfoMapper.selectCondition(vo);
        //将查到的集合封装数据到PageInfo中,只需在new集合时将list传入参数即可自动实现所以成员变量的赋值
        return new PageInfo<>(list);
    }

}
