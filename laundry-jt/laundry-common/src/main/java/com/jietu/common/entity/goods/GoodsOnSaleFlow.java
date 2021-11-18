package com.jietu.common.entity.goods;

import lombok.Data;

import java.io.Serializable;

/**
 * 商品上架流水
 * @author: 印修河
 * @date: 2019/1/1 18:21
 */
@Data
public class GoodsOnSaleFlow implements Serializable {

    private static final long serialVersionUID = -6645654407852276877L;
    private Long id;
    private Long goodsId;
    /** 流程状态 0=审核中，1=审核成功，2=审核失败 */
    private Integer status;
    /** 检查结果 */
    private String checkResult;


}
