package com.api_gateway.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 访问控制表
 */
@Component
@ConfigurationProperties(prefix = "acl")
public class AccessControlList {
    /**
     * 授权列表
     */
    private List<String> auths = new ArrayList<>();
    /**
     * 正则表达式授权列表
     */
    private List<String> regex = new ArrayList<>();

    /**
     * 授权列表匹配
     *
     * @param input
     * @return
     */
    public boolean equalsIgnoreCase(String input) {
        if (0 == auths.size())
            return true;
        for (String auth : auths) {
            if (auth.equalsIgnoreCase(input))
                return true;
        }
        return false;
    }

    /**
     * 正则表达式授权列表匹配
     *
     * @param input
     * @return
     */
    public boolean matches(String input) {
        if (0 == regex.size())
            return true;
        for (String reg : regex) {
            if (Pattern.matches(reg, input))
                return true;
        }
        return false;
    }

    public void setAuths(List<String> auths) {
        this.auths = auths;
    }

    public void setRegex(List<String> regex) {
        this.regex = regex;
    }
}
