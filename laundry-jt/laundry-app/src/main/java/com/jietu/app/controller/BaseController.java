package com.jietu.app.controller;

import com.alibaba.fastjson.JSON;
import com.jietu.app.utils.ErrorCode;
import com.jietu.app.utils.MyException;
import com.jietu.app.utils.Result;
import com.jietu.app.utils.ErrorCode;
import com.jietu.app.utils.MyException;
import com.jietu.app.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 控制器基类
 * @author: 印修河
 * @date: 2018/12/27 8:35
 */
public class BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    /**
     * 自定义异常处理类
     * @param e
     * @return
     */
    @ExceptionHandler(MyException.class)
    public Result myExceptionHandler(MyException e) {
        logger.error(e.getMsg());
        return Result.builder().code(e.getCode()).msg(e.getMsg()).build();
    }

    /**
     * controller全局异常处理类
     * @param e
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public Result runtimeExceptionHandler(RuntimeException e) {
        logger.error(e.getMessage(), e);
        return Result.getFail(ErrorCode.FAIL);
    }

    /**
     * 成功响应方法
     * @param data 响应数据
     * @return
     */
    protected Result successResponse(Object data){
        logger.info("接口响应参数：{}", JSON.toJSONString(data));
        return Result.getSuccess(data);
    }

    /**
     * 成功响应方法
     * @return
     */
    protected Result successResponse(){
        return Result.getSuccess();
    }
}
