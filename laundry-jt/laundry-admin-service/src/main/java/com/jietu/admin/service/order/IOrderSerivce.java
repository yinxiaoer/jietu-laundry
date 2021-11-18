package com.jietu.admin.service.order;

import com.jietu.admin.service.system.IBaseService;
import com.jietu.common.entity.order.Order;
import com.jietu.common.entity.order.OrderComment;

public interface IOrderSerivce extends IBaseService<Order, Long> {

    /**
     * 开始配送到商家
     *
     * @param id
     */
    void startSendMerchant(Long id);

    /**
     * 商家确认收货洗涤开始
     *
     * @param id
     */
    void washingStart(Long id);

    /**
     * 商家确认洗涤完毕
     *
     * @param id
     */
    void washingEnd(Long id);

    /**
     * 配送到用户开始
     *
     * @param id
     */
    void sendCustomerStart(Long id);

    /**
     * 配送到用户结束
     *
     * @param id
     */
    void sendCustomerEnd(Long id);

    /**
     * 获取订单评价
     * @param orderId
     * @return
     */
    OrderComment getOrderComment(Long orderId);

    /**
     * 评价回复
     * @param orderCommentId 订单评价id
     * @param replyContent 回复内容
     */
    void commentReply(Long orderCommentId, String replyContent);
}
