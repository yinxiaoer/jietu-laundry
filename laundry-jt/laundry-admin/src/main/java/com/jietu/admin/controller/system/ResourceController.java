package com.jietu.admin.controller.system;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jietu.admin.entity.system.ResourceEntity;
import com.jietu.admin.service.system.IResourceService;
import com.jietu.admin.entity.system.ResourceEntity;
import com.jietu.admin.service.system.IResourceService;
import com.jietu.admin.utils.SessionUtils;
import com.jietu.admin.model.AjaxResult;
import com.jietu.admin.model.RoleResourceModel;
import com.jietu.admin.utils.Assert;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * 用户资源控制器类
 * @author: 印修河
 * @date: 2017/12/14 15:53
 */
@RestController
@RequestMapping("resource")
public class ResourceController extends BaseController {

    @Autowired
    private IResourceService resourceService;

    /**
     * 获取菜单列表
     * @return
     */
    @PostMapping("menuList")
    @RequiresPermissions("resource:list")
    public AjaxResult menuList() {
        List<ResourceEntity> menuList = resourceService.findMenu();
        return new AjaxResult(HttpStatus.OK,"查询成功", menuList);
    }

    /**
     * 用户资源列表
     * @param menuId
     * @return
     */
    @PostMapping("buttonList")
    @RequiresPermissions("resource:list")
    public AjaxResult buttonList(String menuId) {
        List<ResourceEntity> menuList = resourceService.findButtonByMenuId(menuId);
        return new AjaxResult(HttpStatus.OK,"查询成功", menuList);
    }

    @PostMapping("save")
    @RequiresPermissions("resource:save")
    public AjaxResult save(ResourceEntity resource){

        Assert.notNull(resource.getId(), "请上送资源Id");
        Assert.notNull(resource.getName(), "请上送资源名称");
        Assert.notNull(resource.getResourceType(), "请上送资源类型");
        Assert.notNull(resource.getPermission(), "请上送权限字符");
        ResourceEntity resourceEntity = resourceService.getById(resource.getId());
        Assert.isNull(resourceEntity, "资源id已存在");

        resource.setCreateName(SessionUtils.getCurrentUser().getRealname());
        resource.setCreateDate(new Date());
        resourceService.insert(resource);
        return new AjaxResult(HttpStatus.OK,"添加资源成功");
    }

    @PostMapping("update")
    @RequiresPermissions("resource:update")
    public AjaxResult update(ResourceEntity resource){

        Assert.notNull(resource.getId(), "请上送资源id");
        Assert.notNull(resource.getName(), "请上送资源名称");
        Assert.notNull(resource.getResourceType(), "请上送资源类型");
        Assert.notNull(resource.getPermission(), "请上送权限字符");

        resource.setUpdateName(SessionUtils.getCurrentUser().getRealname());
        resource.setUpdateDate(new Date());
        resourceService.update(resource);
        return new AjaxResult(HttpStatus.OK,"更新资源成功");
    }

    /**
     * 删除资源
     * @param id
     * @return
     */
    @PostMapping("delete")
    @RequiresPermissions("resource:delete")
    public AjaxResult delete(String id){
        resourceService.delete(id);
        return new AjaxResult(HttpStatus.OK,"删除资源成功");
    }

    /**
     * 返回用户菜单
     * @return
     */
    @PostMapping("getUserRoleMenu")
    public AjaxResult getUserRoleMenu(){
        String roleId = SessionUtils.getCurrentUser().getRoleId();
        List<ResourceEntity> resourceList = resourceService.findByRoleMenu(roleId);
        JSONArray jsonArray = new JSONArray();
        for(ResourceEntity resource:resourceList){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("path", resource.getPath());
            jsonObject.put("name", resource.getId());
            JSONObject meta = new JSONObject();
            meta.put("title", resource.getName());
            meta.put("icon", resource.getIcon());
            jsonObject.put("meta", meta);
            JSONArray childrenArray = new JSONArray();
            List<ResourceEntity> childrenList = resource.getChildren();
            for(ResourceEntity children : childrenList){
                JSONObject childrenObject = new JSONObject();
                childrenObject.put("path", children.getPath());
                childrenObject.put("name", children.getId());
                JSONObject childrenMeta = new JSONObject();
                childrenMeta.put("title", children.getName());
                childrenMeta.put("icon", children.getIcon());
                childrenObject.put("meta", childrenMeta);
                childrenArray.add(childrenObject);
            }
            jsonObject.put("children", childrenArray);
            jsonArray.add(jsonObject);
        }
        return AjaxResult.getSuccess(jsonArray);
    }

    /**
     * 根据权限id查询对应菜单
     * @param roleId
     * @return
     */
    @PostMapping("findByRoleMenu")
    @RequiresPermissions("role:configRole")
    public AjaxResult findByRoleMenu(String roleId){
        return AjaxResult.getSuccess(resourceService.findByRoleMenu(roleId));
    }

    /**
     * 根据权限id查询对应按钮列表
     * @param roleResourceModel
     * @return
     */
    @PostMapping("findByRoleButton")
    public AjaxResult findByRoleButton(RoleResourceModel roleResourceModel){
        roleResourceModel.setRoleId(SessionUtils.getCurrentUser().getRoleId());
        return AjaxResult.getSuccess(resourceService.findByRoleButtonTree(roleResourceModel));
    }

    /**
     * 根据权限id查询对应菜单树
     * @param roleId
     * @return
     */
    @PostMapping("findByRoleMenuTree")
    @RequiresPermissions("role:configRole")
    public AjaxResult findByRoleMenuTree(String roleId){
        Assert.notNull(roleId, "请上送roleId");
        return AjaxResult.getSuccess(resourceService.findByRoleMenuTree(roleId));
    }

    /**
     * 根据权限id查询对应按钮树
     * @param roleResourceModel
     * @return
     */
    @PostMapping("findByRoleButtonTree")
    @RequiresPermissions("role:configRole")
    public AjaxResult findByRoleButtonTree(RoleResourceModel roleResourceModel){
        return AjaxResult.getSuccess(resourceService.findByRoleButtonTree(roleResourceModel));
    }
}
