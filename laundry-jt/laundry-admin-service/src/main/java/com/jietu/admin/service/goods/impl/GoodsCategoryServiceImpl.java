package com.jietu.admin.service.goods.impl;

import com.jietu.admin.dao.goods.GoodsCategoryDao;
import com.jietu.admin.dao.merchant.MerchantDao;
import com.jietu.admin.entity.system.UserEntity;
import com.jietu.admin.model.PageModel;
import com.jietu.admin.service.goods.IGoodsCategoryService;
import com.jietu.admin.utils.Constants;
import com.jietu.admin.utils.SessionUtils;
import com.jietu.common.entity.goods.GoodsCategory;
import com.jietu.admin.service.system.impl.BaseServiceImpl;
import com.jietu.common.entity.merchant.Merchant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品类型服务实现类
 * @author: 印修河
 * @date: 2018/12/18 22:30
 */
@Service
public class GoodsCategoryServiceImpl extends BaseServiceImpl<GoodsCategory, Long> implements IGoodsCategoryService {

    @Autowired
    private GoodsCategoryDao goodsCategoryDao;
    @Autowired
    private MerchantDao merchantDao;

    @Override
    public PageModel<GoodsCategory> findByQuery(GoodsCategory goodsCategory) {
        UserEntity currentUser = SessionUtils.getCurrentUser();
        //如果是权限为商户则只能查询商户自己的数据
        if(Constants.ROLE_ID_MERCHANT.equals(currentUser.getRoleId())){
            Merchant merchant = merchantDao.findByUserId(currentUser.getId());
            goodsCategory.setMerchantId(merchant.getId());
        }
        return super.findByQuery(goodsCategory);
    }

    @Override
    public List<GoodsCategory> findByMerchantId(Long merchantId) {
        UserEntity currentUser = SessionUtils.getCurrentUser();
        if(Constants.ROLE_ID_MERCHANT.equals(currentUser.getRoleId())){
            Merchant merchant = merchantDao.findByUserId(currentUser.getId());
            return goodsCategoryDao.findByMerchantId(merchant.getId());
        }
        return goodsCategoryDao.findByMerchantId(merchantId);
    }
}
