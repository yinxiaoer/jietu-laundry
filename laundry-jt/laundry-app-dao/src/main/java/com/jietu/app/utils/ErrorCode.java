package com.jietu.app.utils;

/**
 * 错误码枚举类
 * @author: 印修河
 * @date: 2018/12/25 19:49
 */
public enum ErrorCode {
    /** 成功 */
    SUCCESS("0000","成功"),
    /** 系统错误 */
    FAIL("1000","系统错误"),
    /** 未授权 */
    NOT_AUTH("2000","未授权"),
    /** 位置信息未授权 */
    LOCATION_NOT_AUTH("2001","位置信息未授权");

    private String code;
    private String msg;

    ErrorCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
