package com.api_gateway.filter;

import com.api_gateway.config.AccessControlList;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * 登录过滤器
 */
@Component
public class LoginFilter extends ZuulFilter {
    /**
     * 获取bean访问控制表
     */
    @Autowired
    AccessControlList accessControlList;

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
     * 过滤器顺序，越小越先执行
     *
     * @return 前置过滤器顺序(5)前执行
     */
    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER - 1;
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

        //getRequestURI输出:  /apigateway/order/info
        System.out.println(request.getRequestURI());
        //getRequestURL输出:  http://localhost:9000/apigateway/order/info
        System.out.println(request.getRequestURL());

        if (accessControlList.equalsIgnoreCase(request.getRequestURI())) {
            return true;
        }
        //RESTful 拦截
        if (accessControlList.matches(request.getRequestURI())) {
            return true;
        }

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

        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        //从头部获取token对象
        String token = request.getHeader("token");
        //从请求参数获取token对象
        if (StringUtils.isBlank(token)) {
            token = request.getParameter("token");
        }

        //校验逻辑 可以使用JWT(通过数字签名进行验证和信任)
        if (StringUtils.isBlank(token)) {
            //设置是否往下走
            requestContext.setSendZuulResponse(false);
            //设置状态编码 如: 404 (UNAUTHORIZED 未授权 401)
            requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        }

        return null;
    }
}
