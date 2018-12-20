package com.order_service.fallback;

import com.order_service.service.ProductClient;
import org.springframework.stereotype.Component;

/**
 * 针对商品服务，降级处理
 */
@Component  //将被转换成Spring bean
public class ProductClientFallBack implements ProductClient {
    @Override
    public String getProById(Integer id) {
        System.out.println("feign 调用 product-service 服务 getProById 方法 异常");
        return null;
    }
}
