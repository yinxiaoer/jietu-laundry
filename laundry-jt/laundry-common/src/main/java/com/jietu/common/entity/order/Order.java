package com.jietu.common.entity.order;

import com.alibaba.fastjson.annotation.JSONField;
import com.jietu.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单实体类
 * @author: 印修河
 * @date: 2019/1/10 22:31
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Order extends BaseEntity {

    private static final long serialVersionUID = 1240281166575860004L;
    private Long id;
    /** 订单编号 */
    private String sn;
    /** 客户id */
    private Long customerId;
    /** 客户地址id */
    private Long customerAddressId;
    /** 商户id */
    private Long merchantId;
    /** 商户名称 */
    private String merchantName;
    /** 商户logo */
    private String merchantLogo;
    /** 订单状态 */
    private Integer status;
    /** 订单状态 */
    private String statusList;
    /** 收货人名字 */
    private String receiverName;
    /** 收货人手机号 */
    private String receiverMobile;
    /** 收货人地址 */
    private String receiverAddress;
    /** 买家备注 */
    private String buyerRemark;
    /** 商品总价 */
    private BigDecimal goodsTailAmount;
    /** 配送费 */
    private BigDecimal freightFee;
    /** 优惠金额 */
    private BigDecimal discountAmount;
    /** 支付金额 */
    private BigDecimal payAmount;
    /** 货币类型 */
    private String currencyCode;
    /** 取衣开始时间 */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date fetchStartDate;
    /** 取衣结束时间 */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date fetchEndDate;
    /** 送衣开始时间 */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date deliverStartDate;
    /** 送衣结束时间 */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date deliverEndDate;
    /** 支付方式 */
    private Integer payType;

    private List<OrderGoods> orderGoodsList;

}
