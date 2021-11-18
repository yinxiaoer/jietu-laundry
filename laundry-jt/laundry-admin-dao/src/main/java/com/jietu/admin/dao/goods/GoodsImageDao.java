package com.jietu.admin.dao.goods;

import com.jietu.admin.dao.system.BaseDao;
import com.jietu.common.entity.goods.GoodsImage;

import java.util.List;

/**
 * 商品图片
 * @author: 印修河
 * @date: 2018/12/23 21:26
 */
public interface GoodsImageDao extends BaseDao<GoodsImage, Long>{

    /**
     * 通过商品id查询图片列表
     * @param goodsId
     * @return
     */
    List<GoodsImage> findByGoodsId(Long goodsId);

}
