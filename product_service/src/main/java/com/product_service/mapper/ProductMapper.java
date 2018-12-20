package com.product_service.mapper;

import com.product_service.pojo.Product;

import java.util.List;

public interface ProductMapper {
    List<Product> getProAll();

    Product getProById(Integer id);
}
