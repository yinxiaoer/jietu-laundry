package com.jietu.app.entity.merchant;

import lombok.Data;

import java.io.Serializable;

/**
 * 商户销量实体类
 * @author: 印修河
 * @date: 2019/1/7 22:28
 */
@Data
public class MerchantSales implements Serializable {

    private static final long serialVersionUID = 1663804284252461268L;
    private Long id;
    /** 商户id */
    private Long merchantId;
    /** 销量日期：yyyyMMdd */
    private String date;
    /** 销量 */
    private Long sales;

}
