package com.jietu.common.entity.merchant;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商户充值规则
 * @author: 印修河
 * @date: 2019/3/30 22:22
 */
@Data
public class MerchantTopUpRules implements Serializable {

    private static final long serialVersionUID = -4782500081453935537L;
    private Long id;
    /** 商户id */
    private Long merchantId;
    /** 充值金额 */
    private BigDecimal topUpAmount;
    /** 赠送金额 */
    private BigDecimal presenterAmount;
    /** 描述 */
    private String desc;
    /** 排序 */
    private Integer seq;
    private Date createDate;
    private Date updateDate;

}
