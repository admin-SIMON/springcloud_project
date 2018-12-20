package com.product_service.service.impl;

import com.product_service.mapper.ProductMapper;
import com.product_service.pojo.Product;
import com.product_service.service.ProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Resource
    private ProductMapper productMapper;

    @Override
    public List<Product> getProAll() {
        return productMapper.getProAll();
    }

    @Override
    public Product getProById(Integer id) {
        return productMapper.getProById(id);
    }
}
