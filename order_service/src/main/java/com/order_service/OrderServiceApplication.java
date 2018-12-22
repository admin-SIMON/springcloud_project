package com.order_service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.order_service")      //加载@Service @Control注解类
@MapperScan(value = "com.order_service.mapper")     //mybatis 需要扫描mapper接口
@EnableFeignClients     //feign服务调用
@EnableCircuitBreaker   //Hystrix断路器
@EnableHystrixDashboard //Dashboard仪表盘监控
public class OrderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

}
