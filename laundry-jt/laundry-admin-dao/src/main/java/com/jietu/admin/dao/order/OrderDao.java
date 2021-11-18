package com.jietu.admin.dao.order;

import com.jietu.admin.dao.system.BaseDao;
import com.jietu.common.entity.order.Order;
import com.jietu.common.entity.order.OrderComment;
import org.apache.ibatis.annotations.Param;

/**
 * 订单dao
 * @author: 印修河
 * @date: 2019/1/18 17:47
 */
public interface OrderDao extends BaseDao<Order, Long> {

    /**
     * 更新订单状态
     * @param id 订单id
     * @param status 订单状态
     * @return
     */
    Integer updateStatus(@Param("id") Long id, @Param("status") Integer status);

    /**
     * 获取订单评价
     * @param orderId
     * @param merchantId
     * @return
     */
    OrderComment getOrderComment(@Param("orderId") Long orderId, @Param("merchantId") Long merchantId);

    /**
     * 评价回复
     * @param orderCommentId 订单评价id
     * @param replyContent 回复内容
     * @return
     */
    Integer commentReply(@Param("orderCommentId") Long orderCommentId, @Param("replyContent") String replyContent);
}
