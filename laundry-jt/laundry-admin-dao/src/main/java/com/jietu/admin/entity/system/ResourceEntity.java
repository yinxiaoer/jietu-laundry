package com.jietu.admin.entity.system;

import com.jietu.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 用户资源实体类
 * @author: 印修河
 * @date: 2017/12/5 19:28
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ResourceEntity extends BaseEntity {

    private String id;
    /** 资源图标 */
    private String icon;
    /** 名称 */
    private String name;
    /** 资源类型，[menu|button] */
    private String resourceType;
    /** 资源路径 */
    private String url;
    /** 前台路由路径 */
    private String path;
    /** 权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view */
    private String permission;
    /** 父编号 */
    private String parentId;
    /** 排序编号 */
    private Integer seq;
    /** 子节点列表 */
    private List<ResourceEntity> children;

}
