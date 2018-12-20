package com.order_service.control;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.order_service.pojo.Productorder;
import com.order_service.service.OrderService;
import com.order_service.service.ProductClient;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
public class OrderControl {
    @Resource
    private OrderService orderService;
    @Resource
    private ProductClient productClient;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Value("${server.port}")
    String port;

    @RequestMapping("/info")
    @ResponseBody
    public Object getOrder() {
        List<Productorder> orderAll = orderService.getOrderAll();
        return orderAll;
    }

    @RequestMapping("/info/{id}")
    @HystrixCommand(fallbackMethod = "fail")
    @ResponseBody
    public Object getOrderById(@PathVariable("id") Integer id, HttpServletRequest request) {

        //测试zuul网关敏感拦截cookie
        //Arrays.asList("Cookie", "Set-Cookie", "Authorization"));
        String token = request.getHeader("token");
        String cookie = request.getHeader("cookie");
        System.out.println("token : " + token);
        System.out.println("cookie : " + cookie);

        HashMap<String, Object> data = new HashMap<>();
        Productorder orderById = orderService.getOrderById(id);
        data.put("code", "0");
        data.put("msg", "success");
        data.put("data", orderById);
        return data;
    }

    @RequestMapping("/infoPro/{id}")
    @HystrixCommand(fallbackMethod = "fail")
    @ResponseBody
    public Object getProById(@PathVariable("id") Integer id, HttpServletRequest request) {

        //测试zuul网关敏感拦截cookie
        //Arrays.asList("Cookie", "Set-Cookie", "Authorization"));
        String token = request.getHeader("token");
        String cookie = request.getHeader("cookie");
        System.out.println("token : " + token);
        System.out.println("cookie : " + cookie);

        HashMap<String, Object> data = new HashMap<>();
        String proById = productClient.getProById(id);
        data.put("code", "0");
        data.put("msg", "success");
        data.put("data", proById);
        return data;
    }

    /**
     * 针对当前服务，降级处理 @HystrixCommand(fallbackMethod = "fail")
     */
    private Object fail(Integer id, HttpServletRequest request) {

        //开启新的线程执行短信发送，避免线程阻塞
        new Thread(() -> {
            //nosql数据库 readis 实现监控报警
            String failOrderKey = "fail-order-key";
            String failOrderValue = redisTemplate.opsForValue().get(failOrderKey);
            if (StringUtils.isBlank(failOrderValue)) {  //true 二十秒内第一次警告
                System.out.println(String.format("feign 调用 product-service 服务异常 当前的IP地址是:%s:%s", request.getRemoteAddr(), port));
                //调用HTTP请求，调用短信服务 TODO
                System.out.println("短信发送成功，监控报警");
                //记录短信已发送，缓存20秒
                redisTemplate.opsForValue().set(failOrderKey, "order-server-fail", 20, TimeUnit.SECONDS);
            } else {
                System.out.println("短信20秒内已发送，不重复发送");
            }
        }).start();

        HashMap<String, Object> msg = new HashMap<>();
        msg.put("code", "-1");
        msg.put("msg", "error");
        msg.put("data", "系统繁忙");
        return msg;
    }

}
