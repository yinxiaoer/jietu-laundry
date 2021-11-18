package com.jietu.app.dao.order;

import com.jietu.common.entity.order.OrderComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 订单评论dao
 * @author: 印修河
 * @date: 2019/2/14 6:04
 */
public interface OrderCommentDao {

    /**
     * 保存订单评价
     * @param orderComment
     * @return
     */
    Integer save(OrderComment orderComment);

    /**
     * 获取评价列表
     * @param merchantId
     * @param startGrade
     * @param endGrade
     * @param page
     * @param limit
     * @param imageComment
     * @return
     */
    List<OrderComment> getMerchantComment(@Param("merchantId") Long merchantId, @Param("startGrade") Double startGrade, @Param("endGrade") Double endGrade,
                                          @Param("page") Integer page, @Param("limit") Integer limit, @Param("imageComment") Boolean imageComment);

    /**
     * 获取商户评价统计
     * @param merchantId 商户id
     * @return
     */
    Map<String, Object> getMerchantCommentCount(@Param("merchantId") Long merchantId);
}
