package com.order_service.service;

import com.order_service.pojo.Productorder;

import java.util.List;

public interface OrderService {
    List<Productorder> getOrderAll();

    Productorder getOrderById(Integer id);
}
