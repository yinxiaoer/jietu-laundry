package com.jietu.app.dao.customer;

import com.jietu.common.entity.customer.CustomerCollection;
import com.jietu.common.entity.merchant.Merchant;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 客户收藏dao
 * @author: 印修河
 * @date: 2019/2/5 10:29
 */
public interface CustomerCollectionDao {

    /**
     * 保存收藏
     * @param customerCollection
     * @return
     */
    Integer save(CustomerCollection customerCollection);

    /**
     * 删除收藏
     * @param customerId 客户id
     * @param merchantId 商户id
     * @return
     */
    Integer delete(@Param("customerId") Long customerId, @Param("merchantId") Long merchantId);

    /**
     * 获取用户收藏列表
     * @param customerId 客户id
     * @param page 页数
     * @param limit 条数
     * @return
     */
    List<Merchant> getCollectionList(@Param("customerId") Long customerId, @Param("page") Integer page, @Param("limit") Integer limit);

    /**
     * 通过用户id和商户id查询收藏
     * @param customerId 用户id
     * @param merchantId 商户id
     * @return
     */
    CustomerCollection findByCustomerIdAndMerchantId(@Param("customerId") Long customerId, @Param("merchantId") Long merchantId);

}
