package com.jietu.admin.service.system;

import com.jietu.common.entity.system.SystemParameter;
import com.jietu.admin.model.ElCascaderModel;
import com.jietu.admin.model.AreaModel;

import java.util.List;

/**
 *  系统参数服务接口类
 * @author 印修河
 * @date 2017年10月16日 下午4:33:22
 */
public interface ISystemParameterService extends IBaseService<SystemParameter, String> {

	/**
	 * 删除系统参数
	 * @param systemParameter
	 */
	void delete(SystemParameter systemParameter);

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
	AreaModel findAreaByCode(String code);

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
	List<AreaModel> getCity(String province);

	/**
	 * 返回区/县列表
	 * @param city 市代码
	 * @return
	 */
	List<AreaModel> getArea(String city);

	/**
	 * 地区所有数据
	 * @return
	 */
	List<ElCascaderModel> getAreaData();
}
