package com.jietu.common.entity.customer;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户足迹
 * @author: 印修河
 * @date: 2019/2/26 9:32
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerFootprint implements Serializable {

    private static final long serialVersionUID = -109359564413216713L;
    private Long id;
    /** 客户id */
    private Long customerId;
    /** 商户id */
    private Long merchantId;
    /** 创建时间 */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;
    /** 更新时间 */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateDate;
}
