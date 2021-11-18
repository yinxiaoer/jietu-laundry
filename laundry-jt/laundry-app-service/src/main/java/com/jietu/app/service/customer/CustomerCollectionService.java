package com.jietu.app.service.customer;

import com.jietu.app.dao.customer.CustomerCollectionDao;
import com.jietu.common.entity.customer.CustomerCollection;
import com.jietu.common.entity.merchant.Merchant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 客户收藏服务类
 * @author: 印修河
 * @date: 2019/2/5 10:31
 */
@Service
public class CustomerCollectionService {

    @Autowired
    private CustomerCollectionDao customerCollectionDao;

    /**
     * 添加用户收藏
     * @param customerCollection
     */
    public void add(CustomerCollection customerCollection){
        customerCollectionDao.save(customerCollection);
    }

    /**
     * 删除收藏
     * @param customerId 客户id
     * @param merchantId 商户id
     */
    public void delete(Long customerId, Long merchantId){
        customerCollectionDao.delete(customerId, merchantId);
    }

    /**
     * 获取用户收藏列表
     * @param customerId 客户id
     * @param page 页数
     * @param limit 条数
     * @return
     */
    public List<Merchant> getCollectionList(Long customerId, Integer page, Integer limit){
        return customerCollectionDao.getCollectionList(customerId, page, limit);
    }

    /**
     * 通过用户id和商户id查询收藏
     * @param customerId 用户id
     * @param merchantId 商户id
     * @return
     */
    public CustomerCollection findByCustomerIdAndMerchantId(Long customerId, Long merchantId){
        return customerCollectionDao.findByCustomerIdAndMerchantId(customerId, merchantId);
    }
}
