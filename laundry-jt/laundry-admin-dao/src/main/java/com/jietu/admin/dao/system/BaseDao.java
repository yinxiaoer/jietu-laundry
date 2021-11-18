package com.jietu.admin.dao.system;

import com.jietu.common.entity.BaseEntity;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 *  基类dao
 * @author 印修河
 * @date 2017年10月17日 下午3:16:26
 */
public interface BaseDao<T extends BaseEntity, Id extends Serializable> {

	/**
	 * 保存
	 * @param t
	 * @return
	 */
	Integer insert(T t);

	/**
	 * 更新
	 * @param t
	 */
	Integer update(T t);

	/**
	 * 删除
	 * @param id
	 */
	Integer delete(@Param("id") Id id);

	/**
	 * 通过id查询对象
	 * @param id
	 * @return
	 */
	T getById(@Param("id") Id id);

	/**
	 * 返回所有对象
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

}
