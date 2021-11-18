package com.jietu.admin.controller.system;

import com.jietu.admin.model.AjaxResult;
import com.jietu.admin.utils.MyException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 控制器基类
 *
 * @author: 印修河
 * @date: 2017/12/6 18:41
 */
public class BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    /**
     * 自定义异常处理类
     * @param e
     * @return
     */
    @ExceptionHandler(MyException.class)
    public AjaxResult myExceptionHandler(RuntimeException e) {
        logger.error(e.getMessage(), e);
        return new AjaxResult(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    /**
     * 权限异常
     * @param e
     * @return
     */
    @ExceptionHandler({ UnauthorizedException.class, AuthorizationException.class })
    public AjaxResult authorizationException(Exception e) {
        logger.error(e.getMessage(), e);
        return new AjaxResult(HttpStatus.FORBIDDEN, "您的权限不足，请联系管理员处理");
    }

    /**
     * controller全局异常处理类
     * @param e
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public AjaxResult runtimeExceptionHandler(RuntimeException e) {
        logger.error(e.getMessage(), e);
        return new AjaxResult(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
}
