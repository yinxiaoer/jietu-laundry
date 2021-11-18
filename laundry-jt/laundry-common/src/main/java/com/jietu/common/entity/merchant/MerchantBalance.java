package com.jietu.common.entity.merchant;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 商户余额
 * @author: 印修河
 * @date: 2019/3/30 21:49
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MerchantBalance extends Merchant {

    /** 余额 */
    private BigDecimal balance;

}
