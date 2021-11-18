package com.jietu.app.dao.shoppingcart;

import com.jietu.app.entity.shoppingcart.ShoppingCart;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 购物车信息dao
 * @author: lzm
 * @date: 2018/1/10 21:35
 */
public interface ShoppingCartDao {

    /**
     * 基于某一用户id,商户id前提，新插入购物车项目
     * @param shoppingCart
     * @return
     */
    Integer insertItem(ShoppingCart shoppingCart);

    /**
     * 基于某一用户id,商户id前提，更新购物车某一商品id的相关信息
     * @param shoppingCart
     * @return
     */
    Integer updateItemByGoodsId(ShoppingCart shoppingCart);

    /**
     * 基于某一用户id,商户id前提，删除全部商品
     * @param shoppingCart
     * @return
     */
    Integer deleteItem(ShoppingCart shoppingCart);


    /**
     * 基于某一用户id,商户id前提，删除某一商品id的相关信息
     * @param shoppingCart
     * @return
     */
    Integer deleteSingleItem(ShoppingCart shoppingCart);


    /**
     * 基于某一用户id,商户id前提，显示其商品列表
     * @param shoppingCart
     * @return
     */
    List<ShoppingCart> showList(ShoppingCart shoppingCart);

    /**
     * 基于某一用户id,商户id,商品id前提，查询某商品
     * @param shoppingCart
     * @return
     */
    ShoppingCart getItemByGoodsId(ShoppingCart shoppingCart);

    /**
     * 统计购物车金额
     * @param customerId 客户id
     * @param merchantId 商户id
     * @return
     */
    BigDecimal countShoppingCartAmount(@Param("customerId") Long customerId, @Param("merchantId") Long merchantId);
}
