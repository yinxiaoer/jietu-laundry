package com.jietu.app.dto.order;

import com.jietu.common.entity.order.OrderGoods;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 购物车计算价格包装类
 * @author: 印修河
 * @date: 2019/1/14 22:27
 */
@Data
public class CartItemCalculateRequest implements Serializable {

    /** 商品列表 */
    private List<OrderGoods> goodsList;

}
