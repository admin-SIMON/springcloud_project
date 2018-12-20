package com.order_service.service;

import com.order_service.fallback.ProductClientFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 商品服务客户端
 */
@FeignClient(name = "product-service", fallback = ProductClientFallBack.class)
public interface ProductClient {

    @RequestMapping("/info/{id}")
    String getProById(@PathVariable("id") Integer id);
}
