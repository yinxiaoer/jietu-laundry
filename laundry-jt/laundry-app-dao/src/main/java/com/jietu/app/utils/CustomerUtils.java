package com.jietu.app.utils;

import com.jietu.common.entity.customer.Customer;
import com.jietu.common.service.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

/**
 * 客户工具类
 * @author: 印修河
 * @date: 2019/2/23 14:56
 */
public class CustomerUtils {


    /**
     * 获取当前客户
     * @return
     */
    public static Customer getCurrentCustomer(){
        RedisService redisService = SpringUtils.getBean(RedisService.class);
        String token = MDC.get("token");
        if(StringUtils.isBlank(token)){
            throw new MyException(ErrorCode.NOT_AUTH);
        }
        return (Customer) redisService.get(token);
    }

}
