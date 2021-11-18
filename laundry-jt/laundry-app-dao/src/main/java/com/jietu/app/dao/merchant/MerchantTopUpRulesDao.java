package com.jietu.app.dao.merchant;

import com.jietu.common.entity.merchant.MerchantTopUpRules;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商户充值规则
 * @author: 印修河
 * @date: 2019/3/30 22:43
 */
public interface MerchantTopUpRulesDao {

    /**
     * 通过商户id查询商户充值规则
     * @param merchantId
     * @return
     */
    List<MerchantTopUpRules> findByMerchantId(@Param("merchantId") Long merchantId);

    /**
     * 获取充值规则
     * @param merchantId
     * @param topUpAmount
     * @return
     */
    MerchantTopUpRules getTopUpRules(@Param("merchantId") Long merchantId, @Param("topUpAmount") BigDecimal topUpAmount);

}
