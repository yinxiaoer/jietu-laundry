package com.jietu.app.entity.shoppingcart;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 购物车实体类
 * @author: lzm
 * @date: 2019/1/9
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCart implements Serializable {

    private static final long serialVersionUID = -4953289359373341983L;
    private Long id;
    /** 商品类型Id */
    private Long goodsId;
    /** 商户id */
    private Long merchantId;
    /** 用户id */
    private Long customerId;
    /** 数量 */
    private Integer number;
    /** 创建时间 */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;
    /** 创更新时间 */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateDate;

    /** 商品名称 */
    private String name;
    /** 商品图片 */
    private String picUrl;
    /**总价=单价*数量*/
    private BigDecimal price;
}
