package com.product_service.service;

import com.product_service.pojo.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProAll();

    Product getProById(Integer id);

}
