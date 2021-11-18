package com.jietu.common.entity.goods;

import com.jietu.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 商品类型
 * @author: 印修河
 * @date: 2018/12/18 21:12
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GoodsCategory extends BaseEntity {

    private static final long serialVersionUID = 2753367533014261930L;
    private Long id;
    /** 类型名称 */
    private String name;
    /** 类型描述 */
    private String desc;
    /** 商户id */
    private Long merchantId;
    /** 商户名称 */
    private String merchantName;
    /** 排序 */
    private Integer sortOrder;
    /** 分类下的商品列表 */
    private List<Goods> goodsList;
}
