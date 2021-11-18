package com.jietu.admin.dao.goods;


import com.jietu.admin.dao.system.BaseDao;
import com.jietu.common.entity.goods.GoodsCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品类型dao
 * @author: 印修河
 * @date: 2018/12/18 22:04
 */
public interface GoodsCategoryDao extends BaseDao<GoodsCategory, Long> {

    /**
     * 通过商户id查询类型
     * @param merchantId
     * @return
     */
    List<GoodsCategory> findByMerchantId(@Param("merchantId") Long merchantId);

}
