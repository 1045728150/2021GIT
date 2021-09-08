package com.service;

import com.github.pagehelper.PageInfo;
import com.pojo.ProductInfo;

import java.util.List;

public interface ProductInfoService{
    public List<ProductInfo> selectAll();
    public PageInfo splitPage(Integer pageNum, Integer pageSize);
    public int addProduct(ProductInfo productInfo);
    public ProductInfo selectById(Integer pid);
    public int updateProduct(ProductInfo productInfo);
    public int deleteOne(Integer pid);
    public int deleteBatch(String [] ids);
}
