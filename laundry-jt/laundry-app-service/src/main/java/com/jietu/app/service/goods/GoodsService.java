package com.jietu.app.service.goods;

import com.jietu.app.dao.goods.GoodsDao;
import com.jietu.common.entity.goods.GoodsCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品服务类
 * @author: 印修河
 * @date: 2019/1/6 18:36
 */
@Service
public class GoodsService {

    @Autowired
    private GoodsDao goodsDao;

    /**
     * 查询商品类型列表
     * @param merchantId 商户id
     * @return
     */
    public List<GoodsCategory> getGoodsCategory(Long merchantId){
        return goodsDao.getGoodsCategory(merchantId);
    }

}
