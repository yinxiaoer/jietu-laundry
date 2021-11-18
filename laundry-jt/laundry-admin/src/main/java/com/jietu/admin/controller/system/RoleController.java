package com.jietu.admin.controller.system;


import com.jietu.admin.service.system.IRoleService;
import com.jietu.admin.entity.system.RoleEntity;
import com.jietu.admin.service.system.IRoleService;
import com.jietu.admin.utils.SessionUtils;
import com.jietu.admin.model.AjaxResult;
import com.jietu.admin.utils.Assert;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 角色控制器类
 * @author: 印修河
 * @date: 2017/12/12 11:27
 */
@RestController
@RequestMapping("role")
public class RoleController  extends BaseController {

    @Autowired
    private IRoleService roleService;

    /**
     * 获取角色列表
     * @param role
     * @return
     */
    @PostMapping("list")
    @RequiresPermissions("role:list")
    public AjaxResult list(RoleEntity role){
        role.setPage(null);
        return new AjaxResult(HttpStatus.OK, "查询成功", roleService.findByQuery(role));
    }

    /**
     * 添加角色
     * @param role
     * @return
     */
    @PostMapping("save")
    @RequiresPermissions("role:save")
    public AjaxResult save(RoleEntity role){
        Assert.notNull(role.getId(), "请上送角色Id");
        Assert.notNull(role.getName(), "请上送角色名称");

        role.setAvailable(true);
        role.setCreateName(SessionUtils.getCurrentUser().getRealname());
        role.setCreateDate(new Date());
        roleService.insert(role);
        return new AjaxResult(HttpStatus.OK,"添加角色成功");
    }

    /**
     * 更新角色
     * @param role
     * @return
     */
    @PostMapping("update")
    @RequiresPermissions("role:update")
    public AjaxResult update(RoleEntity role){
        Assert.notNull(role.getId(), "请上送角色Id");
        Assert.notNull(role.getName(), "请上送角色名称");

        role.setUpdateName(SessionUtils.getCurrentUser().getRealname());
        role.setUpdateDate(new Date());
        roleService.update(role);
        return new AjaxResult(HttpStatus.OK,"更新角色成功");
    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    @PostMapping("delete")
    @RequiresRoles("ADMIN")
    @RequiresPermissions("role:delete")
    public AjaxResult delete(String id){
        roleService.delete(id);
        return new AjaxResult(HttpStatus.OK,"删除角色成功");
    }

    /**
     * 改变是否可用状态
     * @param id
     * @param available
     * @return
     */
    @PostMapping("updateAvailable")
    @RequiresPermissions("role:updateAvailable")
    public AjaxResult updateAvailable(String id, Boolean available){
        roleService.updateStatus(id, available);
        return new AjaxResult(HttpStatus.OK,"更新状态成功");
    }

    /**
     * 保存用户资源
     * @param roleId
     * @param resourceType
     * @param resourceIds
     * @return
     */
    @PostMapping("saveRoleResource")
    @RequiresPermissions("role:configRole")
    public AjaxResult saveRoleResource(String parentId, String roleId, String resourceType, String resourceIds){
        String[] resourceIdArr = null;
        if(resourceIds != null){
            resourceIdArr = resourceIds.split(",");
        }
        roleService.saveRoleResource(parentId, roleId, resourceType, resourceIdArr);
        return new AjaxResult(HttpStatus.OK,"保存用户资源成功");
    }

}
