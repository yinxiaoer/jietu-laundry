package com.jietu.app.dao.customer;

import com.jietu.common.entity.customer.CustomerFootprint;
import com.jietu.common.entity.merchant.Merchant;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 客户足迹dao
 * @author: 印修河
 * @date: 2019/2/26 9:48
 */
public interface CustomerFootprintDao {

    /**
     * 保存客户足迹
     * @param customerFootprint
     * @return
     */
    Integer save(CustomerFootprint customerFootprint);

    /**
     * 更新时间
     * @param id
     * @param updateDate
     * @return
     */
    Integer updateDate(Long id, Date updateDate);

    /**
     * 通过客户id和商户id查询客户足迹
     * @param customerId 客户id
     * @param merchantId 商户id
     * @return
     */
    CustomerFootprint findByCustomerIdAndMerchantId(@Param("customerId") Long customerId, @Param("merchantId") Long merchantId);

    /**
     * 获取客户足迹列表
     * @param customerId 客户id
     * @param page 页数
     * @param limit 每页多少条
     * @return
     */
    List<Merchant> getFootprintList(@Param("customerId") Long customerId, @Param("page") Integer page, @Param("limit") Integer limit);
}
