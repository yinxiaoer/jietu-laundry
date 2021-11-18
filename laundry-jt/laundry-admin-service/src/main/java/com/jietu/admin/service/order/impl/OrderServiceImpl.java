package com.jietu.admin.service.order.impl;

import com.jietu.admin.dao.merchant.MerchantDao;
import com.jietu.admin.dao.order.OrderDao;
import com.jietu.admin.entity.system.UserEntity;
import com.jietu.admin.model.PageModel;
import com.jietu.admin.service.order.IOrderSerivce;
import com.jietu.admin.service.system.impl.BaseServiceImpl;
import com.jietu.admin.utils.Assert;
import com.jietu.admin.utils.Constants;
import com.jietu.admin.utils.MyException;
import com.jietu.admin.utils.SessionUtils;
import com.jietu.common.entity.merchant.Merchant;
import com.jietu.common.entity.order.Order;
import com.jietu.common.entity.order.OrderComment;
import com.jietu.common.enums.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 订单服务类
 *
 * @author: 印修河
 * @date: 2019/1/18 19:28
 */
@Service
public class OrderServiceImpl extends BaseServiceImpl<Order, Long> implements IOrderSerivce {

    @Autowired
    private MerchantDao merchantDao;
    @Autowired
    private OrderDao orderDao;

    @Override
    public PageModel<Order> findByQuery(Order order) {

        order.setMerchantId(getUserMerchantId());

        //默认通过时间倒序排序
        order.setSort("createDate");
        order.setOrder("desc");
        return super.findByQuery(order);
    }

    @Override
    public void startSendMerchant(Long id) {
        Order order = orderDao.getById(id);
        Assert.notNull(order, "订单号不存在");
        if (!OrderStatus.WAIT_CLAIM_GOODS.getStatus().equals(order.getStatus())) {
            throw new MyException("此订单未处于【" + OrderStatus.WAIT_CLAIM_GOODS.getDesc() + "】状态");
        }
        orderDao.updateStatus(id, OrderStatus.SEND_MERCHANTING.getStatus());
    }

    @Override
    public void washingStart(Long id) {
        Order order = orderDao.getById(id);
        Assert.notNull(order, "订单号不存在");
        if (!OrderStatus.SEND_MERCHANTING.getStatus().equals(order.getStatus())) {
            throw new MyException("此订单未处于【" + OrderStatus.SEND_MERCHANTING.getDesc() + "】状态");
        }
        orderDao.updateStatus(id, OrderStatus.WASHING.getStatus());
    }

    @Override
    public void washingEnd(Long id) {
        Order order = orderDao.getById(id);
        Assert.notNull(order, "订单号不存在");
        if (!OrderStatus.WASHING.getStatus().equals(order.getStatus())) {
            throw new MyException("此订单未处于【" + OrderStatus.WASHING.getDesc() + "】状态");
        }
        orderDao.updateStatus(id, OrderStatus.WAIT_SEND_CUSTOMER.getStatus());
    }

    @Override
    public void sendCustomerStart(Long id) {
        Order order = orderDao.getById(id);
        Assert.notNull(order, "订单号不存在");
        if (!OrderStatus.WAIT_SEND_CUSTOMER.getStatus().equals(order.getStatus())) {
            throw new MyException("此订单未处于【" + OrderStatus.WAIT_SEND_CUSTOMER.getDesc() + "】状态");
        }
        orderDao.updateStatus(id, OrderStatus.SEND_CUSTOMERING.getStatus());
    }

    @Override
    public void sendCustomerEnd(Long id) {
        Order order = orderDao.getById(id);
        Assert.notNull(order, "订单号不存在");
        if (!OrderStatus.SEND_CUSTOMERING.getStatus().equals(order.getStatus())) {
            throw new MyException("此订单未处于【" + OrderStatus.SEND_CUSTOMERING.getDesc() + "】状态");
        }
        orderDao.updateStatus(id, OrderStatus.WAIT_EVALUATE.getStatus());
    }

    /**
     * 获取订单评价
     * @param orderId
     * @return
     */
    @Override
    public OrderComment getOrderComment(Long orderId){
        Long merchantId = getUserMerchantId();
        return orderDao.getOrderComment(orderId, merchantId);
    }

    @Override
    public void commentReply(Long orderCommentId, String replyContent) {
        orderDao.commentReply(orderCommentId, replyContent);
    }

    /**
     * 返回用户商户id
     * @return
     */
    private Long getUserMerchantId() {
        //权限查询过滤
        UserEntity currentUser = SessionUtils.getCurrentUser();
        if (Constants.ROLE_ID_MERCHANT.equals(currentUser.getRoleId())) {
            Merchant merchant = merchantDao.findByUserId(currentUser.getId());
            return merchant.getId();
        }
        return null;
    }
}
