package com.jietu.app.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 日志拦截器
 * @author: 印修河
 * @date: 2018/12/27 8:54
 */
@Component
public class LogInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        MDC.put("LOG_ID", UUID.randomUUID().toString().replace("-", ""));
        String requestIp = request.getRemoteAddr();
        MDC.put("REQUEST_IP", requestIp);
        logger.info("请求ip：{}, 请求地址：{}, 请求方式：{}", requestIp, request.getRequestURI(), request.getMethod());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //清空线程
        MDC.clear();
    }

}
