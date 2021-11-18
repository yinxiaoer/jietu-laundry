package com.jietu.admin.model;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 *  ajax返回封装类
 * @author 印修河
 * @date 2017年9月12日 上午10:03:56
 */
public class AjaxResult implements Serializable {

	/**
	 *  200 成功
	 *  400 参数错误或操作有误
	 *  401 用户未登录
	 *  403 权限不足
	 *  404 url没找到
	 *  500 服务器错误（代码异常）
	 */
	private Integer status = HttpStatus.OK.value();
	private String message = "操作成功";
	private Object obj;

	public AjaxResult() {

	}

	public AjaxResult(Integer status, String message) {
		this.status=status;
		this.message=message;
	}

	public AjaxResult(HttpStatus httpStatus, String message) {
		this.status=httpStatus.value();
		this.message=message;
	}

	public AjaxResult(HttpStatus httpStatus, String message, Object obj) {
		this.status = httpStatus.value();
		this.message = message;
		this.obj = obj;
	}

	public static AjaxResult getSuccess(Object obj){
		AjaxResult result = new AjaxResult();
		result.setObj(obj);
		return result;
	}

	public static AjaxResult getSuccess(){
		return new AjaxResult();
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

}
