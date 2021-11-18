package com.jietu.app.entity.goods;

import lombok.Data;

import java.io.Serializable;

/**
 * 商品销量实体类
 * @author: 印修河
 * @date: 2019/1/7 22:32
 */
@Data
public class goodsSales implements Serializable {

    private static final long serialVersionUID = 1876975665989256873L;
    private Long id;
    /** 商户id */
    private Long goodsId;
    /** 销量日期：yyyyMMdd */
    private String date;
    /** 销量 */
    private Long sales;

}
