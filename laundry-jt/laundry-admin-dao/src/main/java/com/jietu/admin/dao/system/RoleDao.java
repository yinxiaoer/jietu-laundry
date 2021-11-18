package com.jietu.admin.dao.system;

import com.jietu.admin.entity.system.RoleEntity;
import com.jietu.admin.model.RoleResourceModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 角色dao
 * @author: 印修河
 * @date: 2017/12/6 10:07
 */
public interface RoleDao extends BaseDao<RoleEntity, String> {

    /**
     *  保存权限菜单映射关系
     * @author 印修河
     * @date 2017年10月2日 下午11:04:13
     * @param roleResourceList
     */
    void saveRoleResource(@Param("roleResourceList") List<RoleResourceModel> roleResourceList);

    /**
     * 删除权限菜单映射
     * @param map
     */
    void deleteRoleResource(Map map);


    /**
     * 更新角色可用状态
     * @param role
     */
    void updateStatus(RoleEntity role);
}
