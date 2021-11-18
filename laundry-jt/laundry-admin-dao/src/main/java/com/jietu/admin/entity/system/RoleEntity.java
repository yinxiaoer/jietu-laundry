package com.jietu.admin.entity.system;


import com.jietu.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 角色实体类
 * @author: 印修河
 * @date: 2017/12/5 19:22
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleEntity extends BaseEntity {
	
	private String id;
	/** 角色名称 */
	private String name;
	/** 角色描述 */
	private String description;
	/** 是否可用 */
	private Boolean available;
	/** 资源列表 */
	private List<ResourceEntity> resourceList;

}
