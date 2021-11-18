package com.jietu.admin.controller.system;

import com.jietu.admin.service.system.ISystemParameterService;
import com.jietu.common.entity.system.SystemParameter;
import com.jietu.admin.entity.system.UserEntity;
import com.jietu.admin.utils.SessionUtils;
import com.jietu.admin.model.AjaxResult;
import com.jietu.admin.utils.Assert;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 统参数控制器类
 * @author 印修河
 * @date 2017年10月16日 下午4:40:26
 */
@RestController
@RequestMapping("systemParameter")
public class SystemParameterController extends BaseController {
	
	@Autowired
	private ISystemParameterService systemParameterService;

	/**
	 * 获取systemParameter列表数据
	 * @author 印修河
	 * @date 2017年9月26日 上午11:35:09Rest
	 * @param systemParameter
	 * @return
	 */
	@PostMapping("list")
	@RequiresPermissions("systemParameter:list")
	public AjaxResult list(SystemParameter systemParameter) {
		return new AjaxResult(HttpStatus.OK,"查询成功",systemParameterService.findByQuery(systemParameter));
	}
	
	/**
	 * 系统参数保存
	 * @author 印修河
	 * @date 2017年9月14日 下午6:27:28
	 * @param systemParameter
	 * @return
	 */
	@PostMapping("save")
	@RequiresPermissions("systemParameter:save")
	public AjaxResult save(SystemParameter systemParameter){
		UserEntity user = SessionUtils.getCurrentUser();

		Assert.notNull(systemParameter.getId(), "请上送系统参数编号");
		Assert.notNull(systemParameter.getValue(), "请上送系统参数值");
		//判断系统参数编号是否存在
		SystemParameter entity = systemParameterService.getById(systemParameter.getId());
		Assert.isNull(entity, "系统参数编号已存在");
		systemParameter.setCreateDate(new Date());
		systemParameter.setCreateName(user.getUsername());
		systemParameterService.insert(systemParameter);
		return new AjaxResult(HttpStatus.OK,"添加系统参数成功");
	}
	
	/**
	 * 更新系统参数
	 * @author 印修河
	 * @date 2017年9月14日 下午7:17:18
	 * @param systemParameter
	 * @return
	 */
	@PostMapping("update")
	@RequiresPermissions("systemParameter:update")
	public AjaxResult update(SystemParameter systemParameter){
		UserEntity user = SessionUtils.getCurrentUser();

		Assert.notNull(systemParameter.getId(), "请上送系统参数编号");
		Assert.notNull(systemParameter.getValue(), "请上送系统参数值");

		systemParameter.setUpdateDate(new Date());
		systemParameter.setUpdateName(user.getUsername());
		systemParameterService.update(systemParameter);
		return new AjaxResult(HttpStatus.OK,"更新系统参数成功");
	}
	
	/**
	 * 删除系统参数
	 * @author 印修河
	 * @date 2017年9月14日 下午7:37:14
	 * @param id
	 * @return
	 */
	@PostMapping("delete")
	@RequiresPermissions("systemParameter:delete")
	public AjaxResult delete(String id){
		Assert.notNull(id, "请上送系统参数编号");
		SystemParameter systemParameter = systemParameterService.getById(id);
		Assert.notNull(systemParameter, "上送系统参数id错误");

		systemParameterService.delete(id);
		return new AjaxResult(HttpStatus.OK,"删除系统参数成功");
	}

	/**
	 * 根据参数类型返回参数列表
	 * @param typeId
	 * @return
	 */
	@PostMapping("findByType")
	public AjaxResult findByType(String typeId) {
		return new AjaxResult(HttpStatus.OK, "查询成功", systemParameterService.findByType(typeId));
	}

	/**
	 * 返回省份列表
	 * @return
	 */
	@PostMapping("getProvince")
	public AjaxResult getProvince() {
		return new AjaxResult(HttpStatus.OK, "查询成功", systemParameterService.getProvince());
	}

	/**
	 * 返回市列表
	 * @param province 省份
	 * @return
	 */
	@PostMapping("getCity")
	public AjaxResult getCity(String province) {
		Assert.notNull(province, "请上送省份代码");
		return new AjaxResult(HttpStatus.OK, "查询成功", systemParameterService.getCity(province.substring(0,2)));
	}

	/**
	 * 返回区/县列表
	 * @param city 城市代码
	 * @return
	 */
	@PostMapping("getArea")
	public AjaxResult getArea(String city) {
		Assert.notNull(city, "请上送市代码");
		return new AjaxResult(HttpStatus.OK, "查询成功", systemParameterService.getArea(city.substring(0,4)));
	}

	/**
	 * 返回地区数据
	 * @return
	 */
	@PostMapping("getAreaData")
	public AjaxResult getAreaData() {
		return AjaxResult.getSuccess(systemParameterService.getAreaData());
	}
}
