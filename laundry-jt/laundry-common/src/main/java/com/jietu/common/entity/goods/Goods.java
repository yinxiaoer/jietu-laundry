package com.jietu.common.entity.goods;

import com.jietu.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品类
 * @author: 印修河
 * @date: 2018/12/18 21:13
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Goods extends BaseEntity {

    private static final long serialVersionUID = 3857731862817559278L;
    private Long id;
    /** 商户id */
    private Long merchantId;
    /** 商户名称 */
    private String merchantName;
    /** 商品名称 */
    private String name;
    /** 商品类型Id */
    private Long categoryId;
    /** 商品类型名称 */
    private String categoryName;
    /** 简介*/
    private String info;
    /** 服务说明 */
    private String serviceDesc;
     /** 上架 */
    private Boolean onSale;
    /** 排序 */
    private Integer sortOrder;
    /** 删除状态 */
    private Boolean deleted;
    /**商品主图*/
    private String picUrl;
    /**商品banner图列表*/
    private List<String> picUrlList;
    /**商品详情图列表*/
    private List<String> detailsUrlList;
    /**单位价格，单价*/
    private BigDecimal price;
    /**零售价格*/
    private BigDecimal retailPrice;
    /**市场价*/
    private BigDecimal marketPrice;
    /** 月销量 */
    private Long monthSales;

}
