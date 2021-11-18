package com.jietu.common.entity.system;

import com.jietu.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *  系统参数实体类
 * @author 印修河
 * @date 2017年10月12日 下午7:22:37
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SystemParameter extends BaseEntity {

	private static final long serialVersionUID = -6791823912662414222L;
	/** 参数编号 */
	private String id;
	/** 参数值 */
	private String value;
	/** 参数描述 */
	private String description;
	/** 参数排序 */
	private Integer seq;
	/** 参数类型id */
	private String typeId;

}
