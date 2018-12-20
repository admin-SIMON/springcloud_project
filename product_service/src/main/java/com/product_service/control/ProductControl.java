package com.product_service.control;

import com.product_service.pojo.Product;
import com.product_service.service.ProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProductControl {
    @Value("${server.port}")
    private String port;
    @Resource
    private ProductService productService;

    @RequestMapping("/info")
    @ResponseBody
    public Object getProAll() {
        List<Product> proAll = productService.getProAll();
        return proAll;
    }

    @RequestMapping("/info/{id}")
    @ResponseBody
    public Object getProById(@PathVariable("id") Integer id) {
        Map<String, Object> map = new HashMap<>();
        Product proById = productService.getProById(id);
        proById.setName(String.format("%s data from port=%s", proById.getName(), port));
        return proById;
    }
}
