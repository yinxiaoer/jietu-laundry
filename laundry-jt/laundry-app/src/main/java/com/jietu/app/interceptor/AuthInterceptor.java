package com.jietu.app.interceptor;

import com.jietu.app.utils.ErrorCode;
import com.jietu.app.utils.MyException;
import com.jietu.common.service.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限拦截器
 * @author: 印修河
 * @date: 2018/12/27 8:54
 */
@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);
    @Autowired
    private RedisService redisService;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        logger.debug("请求token:{}", token);
        //未上送token返回未鉴权
        if(StringUtils.isBlank(token)){
            throw new MyException(ErrorCode.NOT_AUTH);
        }
        //token不存在返回未鉴权
        Object value = redisService.get(token);
        if(value == null){
            throw new MyException(ErrorCode.NOT_AUTH);
        }
        //将token放入线程变量和session
        MDC.put("token", token);
        request.getSession().setAttribute("token", token);
        return true;
    }

}
