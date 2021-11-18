package com.jietu.common.entity.pay;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 支付流水
 * @author: 印修河
 * @date: 2019/3/3 18:22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayFlow implements Serializable {

    private static final long serialVersionUID = 9051188767727795829L;
    private Long id;
    /** 支付流水状态，0=待支付，1=支付完成 */
    private Integer status;
    /** 支付流水类型，0=订单支付，1=充值 */
    private Integer types;
    /** 客户id */
    private Long customerId;
    /** 商户id */
    private Long merchantId;
    /** 订单id */
    private Long orderId;
    /** 支付金额 */
    private BigDecimal payAmount;
    /** 描述 */
    private String desc;
    /** 支付方式 */
    private Integer payType;
    /** 失败原因 */
    private String errorMsg;
    /** 创建时间 */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;
    /** 更新时间 */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateDate;


}
