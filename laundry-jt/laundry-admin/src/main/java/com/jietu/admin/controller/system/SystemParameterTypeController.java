package com.jietu.admin.controller.system;

import com.jietu.common.entity.system.SystemParameterType;
import com.jietu.admin.service.system.ISystemParameterTypeService;
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
 *  系统参数类型控制器类
 * @author 印修河
 * @date 2017年10月16日 下午4:40:26
 */
@RestController
@RequestMapping("systemParameterType")
public class SystemParameterTypeController extends BaseController {

	@Autowired
	private ISystemParameterTypeService systemParameterTypeService;
	
	/**
	 *  获取systemParameterType列表数据
	 * @author 印修河
	 * @date 2017年9月26日 上35:09
	 * @param systemParameterType
	 * @return
	 */
	@PostMapping("list")
	@RequiresPermissions("systemParameterType:list")
	public AjaxResult list(SystemParameterType systemParameterType) {
		return new AjaxResult(HttpStatus.OK,"查询成功",systemParameterTypeService.findByQuery(systemParameterType));
	}
	
	/**
	 * 系统参数类型保存
	 * @author 印修河
	 * @date 2017年9月14日 下午6:27:28
	 * @param systemParameterType
	 * @return
	 */
	@PostMapping("save")
	@RequiresPermissions("systemParameterType:save")
	public AjaxResult save(SystemParameterType systemParameterType){
		UserEntity user = SessionUtils.getCurrentUser();
		Assert.notNull(systemParameterType.getId(), "请上送系统参数类型编号");
		Assert.notNull(systemParameterType.getName(), "请上送系统参数类型名称");
		//判断编号是否存在
		SystemParameterType entity = systemParameterTypeService.getById(systemParameterType.getId());
		Assert.isNull(entity, "系统参数类型编号已存在");
		systemParameterType.setCreateDate(new Date());
		systemParameterType.setCreateName(user.getUsername());
		systemParameterTypeService.insert(systemParameterType);
		return new AjaxResult(HttpStatus.OK,"添加系统参数类型成功");
	}

	/**
	 *  更新系统参数类型
	 * @author 印修河
	 * @date 2017年9月14日 下午7:17:18
	 * @param systemParameterType
	 * @return
	 */
	@PostMapping("update")
	@RequiresPermissions("systemParameterType:update")
	public AjaxResult update(SystemParameterType systemParameterType){
		UserEntity user = SessionUtils.getCurrentUser();

		Assert.notNull(systemParameterType.getId(), "请上送系统参数类型编号");
		Assert.notNull(systemParameterType.getName(), "请上送系统参数类型名称");

		systemParameterType.setUpdateDate(new Date());
		systemParameterType.setUpdateName(user.getUsername());
		systemParameterTypeService.update(systemParameterType);
		return new AjaxResult(HttpStatus.OK,"更新系统参数类型成功");
	}
	
	/**
	 *  删除系统参数类型
	 * @author 印修河
	 * @date 2017年9月14日 下午7:37:14
	 * @param id
	 * @return
	 */
	@PostMapping("delete")
	@RequiresPermissions("systemParameterType:delete")
	public AjaxResult delete(String id){
		Assert.notNull(id, "请上送系统参数类型");
		SystemParameterType systemParameterType = systemParameterTypeService.getById(id);
		Assert.notNull(systemParameterType, "请上送系统参数类型");
		systemParameterTypeService.delete(id);
		return new AjaxResult(HttpStatus.OK,"删除系统参数类型成功");
	}

	/**
	 * 返回所有参数类型
	 * @return
	 */
	@PostMapping("getAll")
	public AjaxResult getAll(){
		return new AjaxResult(HttpStatus.OK,"查询成功", systemParameterTypeService.getAll());
	}
}
