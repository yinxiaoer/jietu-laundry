package com.jietu.app.dao.merchant;

import com.jietu.app.entity.merchant.MerchantDistance;
import com.jietu.common.entity.merchant.Merchant;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商户dao
 * @author: 印修河
 * @date: 2019/1/6 9:48
 */
public interface MerchantDao {

    /**
     * 查询周围商户列表
     * @param merchant
     * @return
     */
    List<MerchantDistance> findCircumMerchant(Merchant merchant);

    /**
     * 获取商户详情
     * @param merchantId 商户id
     * @param longitude 经度
     * @param latitude 纬度
     * @return
     */
    MerchantDistance getMerchantDetails(@Param("merchantId") Long merchantId, @Param("longitude") BigDecimal longitude,
                                        @Param("latitude") BigDecimal latitude);

    /**
     * 通过商户id查询商户
     * @param id 商户id
     * @return
     */
    Merchant getById(@Param("id") Long id);


}
