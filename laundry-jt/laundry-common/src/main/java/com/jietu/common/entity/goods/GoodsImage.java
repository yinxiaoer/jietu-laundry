package com.jietu.common.entity.goods;

import com.jietu.common.entity.BaseEntity;
import lombok.*;

/**
 * 商品图片
 * @author: 印修河
 * @date: 2018/12/23 21:23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GoodsImage extends BaseEntity {

    private static final long serialVersionUID = 1661718995767727287L;
    private Long id;
    /** 商品id */
    private Long goodsId;
    /** 图片类型：0=banner图，1=商品详情图 */
    private Integer types;
    /** 排序 */
    private Integer sortOrder;
    /** 图片url */
    private String url;

}
