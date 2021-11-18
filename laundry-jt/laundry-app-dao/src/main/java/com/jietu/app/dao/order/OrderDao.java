package com.jietu.app.dao.order;

import com.jietu.common.entity.order.Order;
import com.jietu.common.entity.order.OrderGoods;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单dao
 * @author: 印修河
 * @date: 2019/1/16 21:54
 */
public interface OrderDao {

    /**
     * 保存订单
     * @param order
     * @return
     */
    Integer save(Order order);

    /**
     * 更新订单状态
     * @param order
     * @return
     */
    Integer update(Order order);

    /**
     * 保存订单商品表
     * @param orderGoods
     * @return
     */
    Integer saveOrderGoods(OrderGoods orderGoods);

    /**
     * 更新订单状态
     * @param id 订单id
     * @param status 状态
     * @return
     */
    Integer updateStatus(@Param("id") Long id, @Param("status") Integer status);

    /**
     * 获取订单列表
     * @param order 订单
     * @return
     */
    List<Order> getCustomerOrderList(Order order);

    /**
     * 获取订单列表
     * @param id 订单id
     * @param customerId 客户id
     * @return
     */
    Order getOrderDetails(@Param("id") Long id, @Param("customerId") Long customerId);

    /**
     * 更新超时工单
     * @return
     */
    Integer updateTimeOutOrder();
}
