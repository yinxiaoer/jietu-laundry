package com.jietu.admin.service.goods;

import com.jietu.common.entity.goods.GoodsCategory;
import com.jietu.admin.service.system.IBaseService;

import java.util.List;

/**
 * 商品类型服务接口类
 * @author: 印修河
 * @date: 2018/12/18 22:28
 */
public interface IGoodsCategoryService extends IBaseService<GoodsCategory, Long> {

    /**
     * 查询商户下的所有类型
     * @param merchantId
     * @return
     */
    List<GoodsCategory> findByMerchantId(Long merchantId);

}
