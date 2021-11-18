package com.jietu.common.entity.customer;

import com.jietu.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 客户会员卡
 * @author: 印修河
 * @date: 2019/3/30 13:05
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerVipCard extends BaseEntity {

    private static final long serialVersionUID = 1522123903418096145L;
    private Long id;
    /** 客户id */
    private Long customerId;
    /** 商户id */
    private Long merchantId;
    /** 余额 */
    private BigDecimal balance;
    /** 创建时间 */
    private Date createDate;
    /** 更新时间 */
    private Date updateDate;

}
