package com.product_service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.product_service")       //加载@Service @Control注解类
@MapperScan(value = "com.product_service.mapper")  //mybatis 需要扫描mapper接口
public class ProductServiceApplication {
    /**
     * 商品服务
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }
}
