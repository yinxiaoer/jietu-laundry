package com.jietu.app.utils;

import lombok.Builder;
import lombok.Data;

/**
 * 响应包装类
 *
 * @author: 印修河
 * @date: 2018/12/25 16:00
 */
@Data
@Builder
public class Result {

    private String code;
    private String msg;
    private Object data;

    public static Result getSuccess() {
        return Result.builder()
                .code(ErrorCode.SUCCESS.getCode())
                .msg(ErrorCode.SUCCESS.getMsg())
                .build();
    }

    public static Result getSuccess(Object data) {
        return Result.builder()
                .code(ErrorCode.SUCCESS.getCode())
                .msg(ErrorCode.SUCCESS.getMsg())
                .data(data)
                .build();
    }

    public static Result getFail(ErrorCode errorCode) {
        return Result.builder()
                .code(errorCode.getCode())
                .msg(errorCode.getMsg())
                .build();
    }
}
