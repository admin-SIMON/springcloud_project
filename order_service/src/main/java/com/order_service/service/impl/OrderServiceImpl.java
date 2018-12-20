package com.order_service.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.order_service.mapper.OrderMapper;
import com.order_service.pojo.Productorder;
import com.order_service.service.OrderService;
import com.order_service.service.ProductClient;
import com.order_service.utils.JsonUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private ProductClient productClient;

    @Override
    public List<Productorder> getOrderAll() {
        return orderMapper.getOrderAll();
    }

    @Override
    public Productorder getOrderById(Integer id) {
        //模拟接口响应慢，线程睡眠新的方式
//        try {
//            TimeUnit.SECONDS.sleep(2);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        Productorder orderById = orderMapper.getOrderById(id);

        String proById = productClient.getProById(orderById.getProductId());
        JsonNode jsonNode = JsonUtils.str2JsonNode(proById);

        orderById.setName(jsonNode.get("name").toString());
        orderById.setPrice(Double.parseDouble(jsonNode.get("price").toString()));
        orderById.setStore(Integer.parseInt(jsonNode.get("store").toString()));

        return orderById;
    }
}
