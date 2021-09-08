package com.service.Impl;

import com.mapper.ProductTypeMapper;
import com.pojo.ProductType;
import com.pojo.ProductTypeExample;
import com.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("productTypeService")
public class ProductTypeServiceImpl implements ProductTypeService {

    @Autowired
    private ProductTypeMapper mapper;

    @Override
    public List<ProductType> selectAllType() {
        return mapper.selectByExample(new ProductTypeExample());
    }
}
