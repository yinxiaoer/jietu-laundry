package com.jietu.admin.service.system.impl;

import com.jietu.admin.service.system.IRoleService;
import com.jietu.admin.entity.system.RoleEntity;
import com.jietu.admin.service.system.IRoleService;
import com.jietu.admin.dao.system.RoleDao;
import com.jietu.admin.utils.Constants;
import com.jietu.admin.model.RoleResourceModel;
import com.jietu.admin.utils.Assert;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色服务实现类
 * @author: 印修河
 * @date: 2017/12/6 18:52
 */
@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<RoleEntity, String> implements IRoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRoleResource(String parentId, String roleId, String resourceType, String[] resourceIds) {

        Assert.notNull(roleId, "请上送角色id");
        Assert.notNull(resourceType, "请上送资源类型");
        Assert.isFalse(Constants.RESOURCE_TYPE_BUTTON.equals(resourceType) && StringUtils.isBlank(parentId), "请上送父节点id");
        //删除原来映射关系
        Map<String, String> map = new HashMap<>(4);
        map.put("roleId", roleId);
        map.put("resourceType", resourceType);
        map.put("parentId", parentId);
        roleDao.deleteRoleResource(map);

        if(resourceIds != null && resourceIds.length > 0){
            List<RoleResourceModel> list = new ArrayList<RoleResourceModel>();
            for (String resourceId : resourceIds) {
                if(StringUtils.isBlank(resourceId)){
                    continue;
                }
                RoleResourceModel model = new RoleResourceModel(roleId, resourceId, resourceType);
                list.add(model);
            }
            if(list.size() > 0){
                roleDao.saveRoleResource(list);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(String id, Boolean available) {
        Assert.notNull(id, "请上送id");
        Assert.notNull(available, "请上送可用状态");
        RoleEntity role = roleDao.getById(id);
        Assert.notNull(role, "角色不存在");
        if(role.getAvailable().equals(available)){
            return;
        }
        role.setAvailable(available);
        roleDao.updateStatus(role);
    }
}
