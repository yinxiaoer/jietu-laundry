package com.jietu.common.entity.merchant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 商户图片类
 * @author: 印修河
 * @date: 2019/1/7 23:03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MerchantImage implements Serializable {

    private static final long serialVersionUID = 5131682471800714687L;
    private Long id;
    /** 商户id */
    private Long merchantId;
    /** 订单id */
    private Long orderId;
    /** 图片类型：0=商家简介图，1=评价图 */
    private Integer types;
    /** 排序 */
    private Integer sortOrder;
    /** 文件路径 */
    private String url;

}
