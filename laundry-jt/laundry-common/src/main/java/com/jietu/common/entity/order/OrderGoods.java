package com.jietu.common.entity.order;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单商品类
 * @author: 印修河
 * @date: 2019/1/10 22:28
 */
@Data
public class OrderGoods implements Serializable {

    private static final long serialVersionUID = 3295046546985985533L;
    private Long id;
    /** 订单id */
    private Long orderId;
    /** 商品id */
    private Long goodsId;
    /** 商品主图 */
    private String picUrl;
    /** 商品名称 */
    private String name;
    /** 商品数量 */
    private Integer number;
    /**单位价格，单价*/
    private BigDecimal price;
    /**零售价格*/
    private BigDecimal retailPrice;
    /**市场价*/
    private BigDecimal marketPrice;

}
