package com.service;

import com.github.pagehelper.PageInfo;
import com.pojo.ProductInfo;
import com.pojo.vo.ProductInfoVo;

import java.util.List;

public interface ProductInfoService {
    //显示全部商品（不分页）
    List<ProductInfo> getAll();

    //商品显示（分页）
    //sql语句：select * from limit 当前页起始记录数（（当前页-1）*每页条数），每页取几条

    /**
     * @param pageNum 当前页
     * @param pageSize 每页有几条
     * @return PageInfo对象，封装了分页相关的若干信息
     */
    PageInfo splitPage(int pageNum,int pageSize);

    /**
     * 保存商品信息
     * @param productInfo
     * @return
     */
    int save(ProductInfo productInfo);

    /**
     * 按商品主键id查询商品
     */
    ProductInfo getByID(Integer pid);

    /**
     * 根据主键id更新商品
     */
    int updateProductInfo(ProductInfo productInfo);

    /**
     * 单个商品删除
     */
    int deleteOne(Integer pid);

    /**
     * 批量删除商品
     */
    int deleteBatch(String [] ids);

    /**
     * 多条件查询商品
     */
    List<ProductInfo> selectCondition(ProductInfoVo vo);

    /**
     * 多条件查询-分页版
     */
    PageInfo splitPageVo(ProductInfoVo vo,int pagesize);
}
