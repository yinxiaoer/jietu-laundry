package com.jietu.app.dao.system;


import com.jietu.common.entity.system.SystemParameter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统参数dao
 * @author: 印修河
 * @date: 2019/2/17 20:33
 */
public interface SystemParameterDao {
	
	/**
	 * 通过参数类型查找参数list
	 * @param typeId 类型id
	 * @return
	 */
	List<SystemParameter> findByType(@Param("typeId") String typeId);

	/**
	 * 通过id查询参数配置
	 * @param id
	 * @return
	 */
	SystemParameter getById(@Param("id") String id);


}
