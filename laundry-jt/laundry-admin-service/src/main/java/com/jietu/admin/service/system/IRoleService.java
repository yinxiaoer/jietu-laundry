package com.jietu.admin.service.system;

import com.jietu.admin.entity.system.RoleEntity;

/**
 * 角色服务接口类
 * @author: 印修河
 * @date: 2017/12/6 18:49
 */
public interface IRoleService extends IBaseService<RoleEntity, String> {

    /**
     * 保存权限菜单映射关系
     * @param roleId
     * @param resourceType
     * @param resourceIds
     */
    void saveRoleResource(String parentId, String roleId, String resourceType, String[] resourceIds);

    /**
     * 更新角色可用状态
     * @param id
     * @param available
     */
    void updateStatus(String id, Boolean available);
}
