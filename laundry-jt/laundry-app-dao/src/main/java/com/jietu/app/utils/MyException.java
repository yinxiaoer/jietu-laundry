package com.jietu.app.utils;


/**
 * 自定义异常类
 * @author: 印修河
 * @date: 2018/1/9 11:17
 */
public class MyException extends RuntimeException {

    private String code;
    private String msg;

    public MyException(String msg) {
        super(msg);
        this.code=ErrorCode.FAIL.getCode();
        this.msg=msg;
    }

    public MyException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.code=errorCode.getCode();
        this.msg=errorCode.getMsg();
    }

    public MyException(ErrorCode errorCode, String msg) {
        super(msg);
        this.code=errorCode.getCode();
        this.msg=msg;
    }

    public MyException(String code, String msg) {
        super(msg);
        this.code=code;
        this.msg=msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
