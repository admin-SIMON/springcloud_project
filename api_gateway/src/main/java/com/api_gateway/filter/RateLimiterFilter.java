package com.api_gateway.filter;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SERVLET_DETECTION_FILTER_ORDER;

/**
 * 速度限制器过滤器(限流)
 */
@Component
public class RateLimiterFilter extends ZuulFilter {
    /**
     * 每秒产生40个令牌
     */
    private static final RateLimiter RATE_LIMITER = RateLimiter.create(40);

    /**
     * 过滤器类型
     *
     * @return 前置过滤器
     */
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    /**
     * 过滤器顺序，限流过滤器在所有过滤器前执行
     *
     * @return
     */
    @Override
    public int filterOrder() {
        //SERVLET_DETECTION_FILTER_ORDER(-3)过滤常数为过滤器配置最先执行的(越小越先执行)
        return SERVLET_DETECTION_FILTER_ORDER - 1;
    }

    /**
     * 过滤器是否生效
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        //对订单接口限流
        if ("/apigateway/order/info".equalsIgnoreCase(request.getRequestURI()))
            return true;
        return false;
    }

    /**
     * 过滤逻辑
     *
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = new RequestContext();

        //阻塞方式，无参默认等待1秒(获取不到令牌阻塞1秒)
//        RATE_LIMITER.acquire();
        //
        if (!RATE_LIMITER.tryAcquire()) {//非阻塞方式，无参默认等待0秒(获取不到立马返回)
            requestContext.setSendZuulResponse(false);
            //TOO_MANY_REQUESTS 请求过多(429)
            requestContext.setResponseStatusCode(HttpStatus.TOO_MANY_REQUESTS.value());
        }

        return null;
    }
}
