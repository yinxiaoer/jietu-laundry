package com.jietu.admin.model;

import java.io.Serializable;
import java.util.List;

/**
 * 列表页面数据包装类
 * @author 印修河
 * @date 2017年9月12日 下午6:25:41
 */
public class PageModel<T> implements Serializable{

	private Long count = 0L;
	private List<T> data;

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

}
