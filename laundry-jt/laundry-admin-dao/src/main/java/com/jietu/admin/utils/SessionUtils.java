package com.jietu.admin.utils;

import com.jietu.admin.entity.system.UserEntity;
import com.jietu.admin.entity.system.UserEntity;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * session 操作工具类
 *
 * @author 印修河
 * @date 2017年9月26日 上午11:09:03
 */
public class SessionUtils {


    /**
     * 返回当前登录的user对象
     *
     * @return
     * @author 印修河
     * @date 2017年9月26日 上午11:08:21
     */
    public static UserEntity getCurrentUser() {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if (principal instanceof UserEntity) {
            return (UserEntity) principal;
        }
        return null;
    }

    /**
     * 返回访问IP
     *
     * @param request
     * @return
     * @author 印修河
     * @date 2017年9月26日 上午11:08:41
     */
    public static String getUserIp(HttpServletRequest request) {
        return request.getRemoteAddr();
    }

    /**
     * 获得请求路径
     *
     * @return
     */
    public static String getRequestPath() {
        String requestPath = getRequest().getRequestURI();
        // 去掉其他参数
        if (requestPath.indexOf("?") > -1) {
            requestPath = requestPath.substring(0, requestPath.indexOf("?"));
        }
        // 去掉项目路径
        requestPath = requestPath.substring(getRequest().getContextPath().length());
        return requestPath;
    }

    /**
     * SpringMvc下获取request
     *
     * @return
     */
    public static HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;

    }
}
