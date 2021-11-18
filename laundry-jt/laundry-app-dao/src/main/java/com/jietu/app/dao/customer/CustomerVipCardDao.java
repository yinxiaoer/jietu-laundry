package com.jietu.app.dao.customer;

import com.jietu.common.entity.customer.CustomerVipCard;
import com.jietu.common.entity.merchant.MerchantBalance;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface CustomerVipCardDao {

    /**
     * 保存会员卡
     * @param customerVipCard
     * @return
     */
    Integer save(CustomerVipCard customerVipCard);

    /**
     * 查询会员卡列表
     *
     * @param customerVipCard
     * @return
     */
    List<MerchantBalance> getVipCardList(CustomerVipCard customerVipCard);

    /**
     * 通过客户id和商户id查询会员卡
     * @param customerId
     * @param merchantId
     * @return
     */
    CustomerVipCard findByCustomerIdAndMerchantId(@Param("customerId") Long customerId, @Param("merchantId") Long merchantId);

    /**
     * 账户支付
     * @param id id
     * @param payAmount 支付金额
     */
    void balancePay(@Param("id") Long id, @Param("payAmount") BigDecimal payAmount);

    /**
     * 账户充值
     * @param id id
     * @param topUpAmount 充值金额
     */
    void balanceTopUp(@Param("id") Long id, @Param("topUpAmount") BigDecimal topUpAmount);

    /**
     * 统计客户总余额
     * @param customerId 客户id
     * @return
     */
    BigDecimal countTotalBalance(Long customerId);

}
