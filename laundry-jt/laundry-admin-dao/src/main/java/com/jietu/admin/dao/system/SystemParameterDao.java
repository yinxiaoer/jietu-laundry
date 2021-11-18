package com.jietu.admin.dao.system;


import com.jietu.common.entity.system.SystemParameter;
import com.jietu.admin.model.ElCascaderModel;
import com.jietu.admin.model.AreaModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *  系统参数dao
 * @author 印修河
 * @date 2017年10月12日 下午7:31:39
 */
public interface SystemParameterDao extends BaseDao<SystemParameter, String> {
	
	/**
	 * 通过参数类型查找参数list
	 * @author 印修河
	 * @date 2017年10月31日 下午3:20:05
	 * @param type
	 * @return
	 */
	List<SystemParameter> findByType(String type);

	/**
	 * 根据编号查找地区
	 * @param code 地区编号
	 * @return
	 */
	AreaModel findAreaByCode(@Param("code") String code);

	/**
	 * 返回省列表
	 * @return
	 */
	List<AreaModel> getProvince();

	/**
	 * 返回市列表
	 * @param province 省份代码
	 * @return
	 */
	List<AreaModel> getCity(@Param("province") String province);

	/**
	 * 返回区/县列表
	 * @param city 市代码
	 * @return
	 */
	List<AreaModel> getArea(@Param("city") String city);

	/**
	 * 地区所有数据
	 * @return
	 */
	List<ElCascaderModel> getAreaData();
}
