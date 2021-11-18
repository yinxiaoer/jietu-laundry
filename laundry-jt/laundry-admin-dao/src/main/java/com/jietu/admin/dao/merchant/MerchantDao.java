package com.jietu.admin.dao.merchant;

import com.jietu.admin.dao.system.BaseDao;
import com.jietu.common.entity.merchant.Merchant;
import org.apache.ibatis.annotations.Param;

/**
 * 商户dao
 * @author: 印修河
 * @date: 2018/12/20 22:21
 */
public interface MerchantDao extends BaseDao<Merchant, Long> {

    /**
     * 通过userid查询商户
     * @param userId
     * @return
     */
    Merchant findByUserId(Long userId);

    /**
     * 更新状态
     * @param id
     * @param status
     * @return
     */
    Integer updateStatus(@Param("id") Long id, @Param("status") Integer status);
}
