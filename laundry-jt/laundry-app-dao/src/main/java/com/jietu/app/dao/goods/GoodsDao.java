package com.jietu.app.dao.goods;

import com.jietu.common.entity.goods.Goods;
import com.jietu.common.entity.goods.GoodsCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品dao
 * @author: 印修河
 * @date: 2019/1/6 18:40
 */
public interface GoodsDao {

    /**
     * 查询商品类型列表
     * @param merchantId 商户id
     * @return
     */
    List<GoodsCategory> getGoodsCategory(@Param("merchantId") Long merchantId);

    /**
     * 通过id获取商品
     * @param id
     * @return
     */
    Goods getById(@Param("id") Long id);

}
