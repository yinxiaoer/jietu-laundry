package com.jietu.common.entity.merchant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 商户月销量实体类
 * @author: 印修河
 * @date: 2019/2/8 13:16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MerchantMonthSales implements Serializable {

    private static final long serialVersionUID = -3380379628921574480L;
    private Long id;
    /** 商户id */
    private Long merchantId;
    /** 月份 */
    private String date;
    /** 销量 */
    private Long sales;

}
