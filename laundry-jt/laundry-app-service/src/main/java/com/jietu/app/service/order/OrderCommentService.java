package com.jietu.app.service.order;

import com.jietu.app.dao.merchant.MerchantImageDao;
import com.jietu.app.dao.order.OrderCommentDao;
import com.jietu.app.dao.order.OrderDao;
import com.jietu.app.utils.Assert;
import com.jietu.common.entity.customer.Customer;
import com.jietu.common.entity.order.Order;
import com.jietu.common.entity.order.OrderComment;
import com.jietu.common.enums.CommentTypes;
import com.jietu.common.enums.MerchantImageTypes;
import com.jietu.common.enums.OrderStatus;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 订单评价等级服务类
 * @author: 印修河
 * @date: 2019/2/14 6:22
 */
@Service
public class OrderCommentService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderCommentDao orderCommentDao;
    @Autowired
    private MerchantImageDao merchantImageDao;

    /**
     * 用户评价
     * @param orderComment 评价
     * @param customer 客户
     */
    @Transactional(rollbackFor = Exception.class)
    public void comment(OrderComment orderComment, Customer customer){
        Order order = orderDao.getOrderDetails(orderComment.getOrderId(), customer.getId());
        Assert.notNull(order, "请选择正确的订单");
        Assert.isEquals(order.getStatus(), OrderStatus.WAIT_EVALUATE.getStatus(), "该订单未在待评价状态，请刷新后重试");
        orderComment.setMerchantId(order.getMerchantId());
        orderComment.setCustomerId(customer.getId());
        //如果评价等级大于5则设为最大等级
        if(orderComment.getGrade() > 5D){
            orderComment.setGrade(5D);
        }
        orderCommentDao.save(orderComment);
        //循环保存订单图片列表
        if(orderComment.getImageList() != null){
            orderComment.getImageList().forEach(merchantImage -> {
                merchantImage.setTypes(MerchantImageTypes.COMMENT.getCode());
                merchantImage.setOrderId(order.getId());
                merchantImage.setMerchantId(order.getMerchantId());
                merchantImageDao.save(merchantImage);
            });
        }
        //修改订单状态为已完成
        orderDao.updateStatus(order.getId(), OrderStatus.SUCCESS.getStatus());
    }

    /**
     * 获取商户评论列表
     * @param orderComment
     * @return
     */
    public List<OrderComment> getMerchantComment(OrderComment orderComment){

        if(orderComment.getPage() == null){
            orderComment.setPage(1);
        }
        if(orderComment.getLimit() == null){
            orderComment.setLimit(30);
        }
        Double startGrade = 0D;
        Double endGrade = 5D;
        Boolean imageComment = false;
        if(orderComment.getTypes() != null){
            if(CommentTypes.GOOD_COMMENT.getCode().equals(orderComment.getTypes())){
                startGrade = 3D;
                endGrade = 5D;
            }
            if(CommentTypes.NEGATIVE_COMMENT.getCode().equals(orderComment.getTypes())){
                startGrade = 0D;
                endGrade = 2D;
            }
            if(CommentTypes.IMAGE_COMMENT.getCode().equals(orderComment.getTypes())){
                imageComment = true;
            }
        }

        return orderCommentDao.getMerchantComment(orderComment.getMerchantId(), startGrade, endGrade, orderComment.getPage(), orderComment.getLimit(), imageComment);
    }
}
