package com.jietu.admin.service.system;


import com.jietu.admin.model.PageModel;
import com.jietu.common.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;

/**
 * 
 *  service接口基类
 * @author 印修河
 * @date 2017年9月12日 下午6:09:10
 */
public interface IBaseService<T extends BaseEntity, Id extends Serializable> {

	/**
	 *  保存
	 * @param t
	 * @return
	 */
	void insert(T t);

	/**
	 *  更新
	 * @param t
	 */
	void update(T t);

	/**
	 *  删除
	 * @param id
	 */
	void delete(Id id);

	/**
	 *  通过id查询对象
	 * @param id
	 * @return
	 */
	T getById(Id id);

	/**
	 *  返回所有对象
	 * @return
	 */
	List<T> getAll();

	/**
	 *  页面列表统计
	 * @param t
	 * @return
	 */
	Long findCountByQuery(T t);

	/**
	 *  页面列表分页查询
	 * @param t
	 * @return
	 */
	List<T> findLimitByQuery(T t);

	/**
	 *  页面列表分页查询
	 * @param t
	 * @return
	 */
	PageModel<T> findByQuery(T t);

}
