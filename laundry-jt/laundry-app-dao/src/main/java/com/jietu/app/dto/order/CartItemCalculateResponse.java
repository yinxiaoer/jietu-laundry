package com.jietu.app.dto.order;

import com.jietu.common.enums.CurrencyCode;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 购物车计算价格返回包装类
 * @author: 印修河
 * @date: 2019/1/14 22:20
 */
@Data
public class CartItemCalculateResponse implements Serializable {

    /** 支付总金额 */
    private BigDecimal payAmount;
    /** 折扣金额 */
    private BigDecimal discountAmount;
    /** 商品总金额 */
    private BigDecimal goodsTailAmount;
    /** 邮费 */
    private BigDecimal freightFee;
    /** 货币类型,默认人民币 */
    private String currencyCode = CurrencyCode.CNY.getCode();
    /** 活动列表 */
    private List activityList = new ArrayList();
    /** 优惠券列表 */
    private List couponsList = new ArrayList();

}
