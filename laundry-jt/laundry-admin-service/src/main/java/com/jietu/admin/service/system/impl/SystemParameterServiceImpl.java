package com.jietu.admin.service.system.impl;

import com.jietu.common.entity.system.SystemParameter;
import com.jietu.admin.dao.system.SystemParameterDao;
import com.jietu.admin.model.ElCascaderModel;
import com.jietu.admin.service.system.ISystemParameterService;
import com.jietu.admin.model.AreaModel;
import com.jietu.admin.utils.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  系统参数服务接口实现类
 * @author 印修河
 * @date 2017年10月16日 下午4:33:46
 */
@Service("systemParameterService")
public class SystemParameterServiceImpl extends BaseServiceImpl<SystemParameter, String> implements ISystemParameterService {

	@Autowired
	private SystemParameterDao systemParameterDao;

	@Override
	@CacheEvict(value = "systemParameter", key = "'systemParameter_' + #systemParameter.typeId")
	public void insert(SystemParameter systemParameter) {
		super.insert(systemParameter);
	}

	@Override
	@CacheEvict(value = "systemParameter", key = "'systemParameter_' + #systemParameter.typeId")
	public void update(SystemParameter systemParameter) {
		super.update(systemParameter);
	}

	@Override
	@CacheEvict(value = "systemParameter", key = "'systemParameter_' + #systemParameter.typeId")
	public void delete(SystemParameter systemParameter) {
		super.delete(systemParameter.getId());
	}

	@Override
	@Cacheable(value = "systemParameter", key = "'systemParameter_' + #type")
	public List<SystemParameter> findByType(String type) {
		Assert.notNull(type, "请上送系统参数类型");
		return systemParameterDao.findByType(type);
	}

	//==============================地区参数==================================


	@Override
	public AreaModel findAreaByCode(String code) {
		return systemParameterDao.findAreaByCode(code);
	}

	@Override
	@Cacheable(value = "provinceList",key = "'provinceList'")
	public List<AreaModel> getProvince() {
		return systemParameterDao.getProvince();
	}

	@Override
	@Cacheable(value = "cityList", key = "'cityList_' + #province")
	public List<AreaModel> getCity(String province) {
		return systemParameterDao.getCity(province);
	}

	@Override
	@Cacheable(value = "areaList", key = "'areaList_' + #city")
	public List<AreaModel> getArea(String city) {
		return systemParameterDao.getArea(city);
	}

	@Override
	@Cacheable(value = "areaData", key = "'areaData'")
	public List<ElCascaderModel> getAreaData() {
		return systemParameterDao.getAreaData();
	}
}
