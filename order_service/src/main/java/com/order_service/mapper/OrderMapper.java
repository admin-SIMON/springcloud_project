package com.order_service.mapper;

import com.order_service.pojo.Productorder;

import java.util.List;

public interface OrderMapper {
    List<Productorder> getOrderAll();

    Productorder getOrderById(Integer id);
}
