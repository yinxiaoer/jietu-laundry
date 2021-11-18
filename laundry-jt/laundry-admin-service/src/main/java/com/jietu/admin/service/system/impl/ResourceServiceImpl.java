package com.jietu.admin.service.system.impl;

import com.jietu.admin.entity.system.ResourceEntity;
import com.jietu.admin.entity.system.ResourceEntity;
import com.jietu.admin.dao.system.ResourceDao;
import com.jietu.admin.service.system.IResourceService;
import com.jietu.admin.model.RoleResourceModel;
import com.jietu.admin.model.TreeModel;
import com.jietu.admin.utils.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 资源服务实现类
 * @author: 印修河
 * @date: 2017/12/7 9:55
 */
@Service("resourceService")
public class ResourceServiceImpl extends BaseServiceImpl<ResourceEntity, String> implements IResourceService {

    @Autowired
    private ResourceDao resourceDao;

    @Override
    public List<ResourceEntity> findByRoleId(String roleId) {
        return resourceDao.findByRoleId(roleId);
    }

    @Override
    public List<ResourceEntity> findByRoleMenu(String roleId) {
        Assert.notNull(roleId, "请上送角色id");
        return resourceDao.findByRoleMenu(roleId);
    }

    @Override
    public List<TreeModel> findByRoleMenuTree(String roleId) {
        Assert.notNull(roleId, "请上送角色id");
        return resourceDao.findByRoleMenuTree(roleId);
    }

    @Override
    public List<ResourceEntity> findMenu() {
        return resourceDao.findMenu();
    }

    @Override
    public List<ResourceEntity> findButtonByMenuId(String menuId) {
        Assert.notNull(menuId, "请上送菜单id");
        return resourceDao.findButtonByMenuId(menuId);
    }

    @Override
    public List<TreeModel> findByRoleButtonTree(RoleResourceModel roleResourceModel) {
        Assert.notNull(roleResourceModel.getResourceId(), "请上送资源id");
        Assert.notNull(roleResourceModel.getRoleId(), "请上送权限id");
        return resourceDao.findByRoleButtonTree(roleResourceModel);
    }
}
