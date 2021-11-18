package com.jietu.admin.model;

import java.io.Serializable;

/**
 * 权限资源包装类
 * @author: 印修河
 * @date: 2018/1/10 9:59
 */
public class RoleResourceModel  implements Serializable {

    private Integer id;
    private String roleId;
    private String resourceId;
    private String resourceType;

    public RoleResourceModel() {
    }

    public RoleResourceModel(String roleId, String resourceId, String resourceType) {
        this.roleId = roleId;
        this.resourceId = resourceId;
        this.resourceType = resourceType;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }
}
