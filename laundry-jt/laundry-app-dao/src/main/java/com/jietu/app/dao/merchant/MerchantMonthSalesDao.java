package com.jietu.app.dao.merchant;

import com.jietu.common.entity.merchant.MerchantMonthSales;
import org.apache.ibatis.annotations.Param;

/**
 * 商户月销量dao
 * @author: 印修河
 * @date: 2019/2/8 20:21
 */
public interface MerchantMonthSalesDao {

    /**
     * 插入商户月销量记录
     * @param merchantMonthSales
     * @return
     */
    Integer insert(MerchantMonthSales merchantMonthSales);

    /**
     * 添加商户销量记录
     * @param merchantId 商户id
     * @param date 时间
     * @return
     */
    Integer addSales(@Param("merchantId") Long merchantId, @Param("date") String date);

    /**
     * 通过商户和时间查询销量
     * @param merchantId 商户id
     * @param date 时间
     * @return
     */
    MerchantMonthSales findByMerchantIdAndDate(@Param("merchantId") Long merchantId, @Param("date") String date);
}
