package com.jietu.common.entity.system;

import com.jietu.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *  系统参数类型实体类
 * @author 印修河
 * @date 2017年10月12日 下午7:17:33
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SystemParameterType extends BaseEntity {

	private static final long serialVersionUID = 5211129425642581539L;
	/** 参数类型编号 */
	private String id;
	/** 参数类型名称 */
	private String name;
	/** 参数类型描述 */
	private String description;
	/** 参数类型排序 */
	private Integer seq;

}
