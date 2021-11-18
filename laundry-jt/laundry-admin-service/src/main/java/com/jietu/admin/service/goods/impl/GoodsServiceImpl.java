package com.jietu.admin.service.goods.impl;

import com.jietu.admin.dao.goods.GoodsDao;
import com.jietu.admin.dao.goods.GoodsImageDao;
import com.jietu.admin.service.goods.IGoodsService;
import com.jietu.admin.utils.SessionUtils;
import com.jietu.common.entity.goods.Goods;
import com.jietu.common.entity.goods.GoodsImage;
import com.jietu.admin.service.system.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品服务实现类
 * @author: 印修河
 * @date: 2018/12/18 22:30
 */
@Service
public class GoodsServiceImpl extends BaseServiceImpl<Goods, Long> implements IGoodsService {

    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private GoodsImageDao goodsImageDao;

    @Override
    public void insert(Goods goods) {
        goods.setDeleted(false);
        goods.setOnSale(false);
        goods.setCreateName(SessionUtils.getCurrentUser().getRealname());
        goodsDao.insert(goods);
    }

    @Override
    public List<GoodsImage> getGoodsImages(Long id) {
        return goodsImageDao.findByGoodsId(id);
    }

    @Override
    public List<GoodsImage> saveGoodsImage(Long id, String url) {
        GoodsImage goodsImage = new GoodsImage();
        goodsImage.setGoodsId(id);
        goodsImage.setUrl(url);
        goodsImageDao.insert(goodsImage);
        return goodsImageDao.findByGoodsId(id);
    }

    @Override
    public Integer deleteGoodsImageBy(Long id) {
        return goodsImageDao.delete(id);
    }
}
