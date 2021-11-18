package com.jietu.admin.dao.system;

import com.jietu.admin.entity.system.ResourceEntity;
import com.jietu.admin.entity.system.ResourceEntity;
import com.jietu.admin.model.RoleResourceModel;
import com.jietu.admin.model.TreeModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 资源dao
 * @author: 印修河
 * @date: 2017/12/6 11:07
 */
public interface ResourceDao extends BaseDao<ResourceEntity, String> {

    /**
     * 通过角色查询资源列表
     * @param roleId
     * @return
     */
    List<ResourceEntity> findByRoleId(@Param("roleId") String roleId);

    /**
     * 查询菜单列表
     * @return
     */
    List<ResourceEntity> findMenu();

    /**
     * 查询菜单下按钮列表
     * @param menuId
     * @return
     */
    List<ResourceEntity> findButtonByMenuId(@Param("menuId") String menuId);

    /**
     * 通过角色id返回菜单
     * @param roleId 角色id
     * @return
     */
    List<ResourceEntity> findByRoleMenu(@Param("roleId") String roleId);

    /**
     * 通过角色id返回菜单树
     * @param roleId 角色id
     * @return
     */
    List<TreeModel> findByRoleMenuTree(@Param("roleId") String roleId);

    /**
     * 查询按钮树列表
     * @param roleResourceModel
     * @return
     */
    List<TreeModel> findByRoleButtonTree(RoleResourceModel roleResourceModel);

}
