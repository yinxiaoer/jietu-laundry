package com.jietu.app.service.customer;

import com.jietu.app.dao.customer.CustomerFootprintDao;
import com.jietu.app.utils.CustomerUtils;
import com.jietu.common.entity.customer.Customer;
import com.jietu.common.entity.customer.CustomerFootprint;
import com.jietu.common.entity.merchant.Merchant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 客户足迹服务类
 * @author: 印修河
 * @date: 2019/2/26 9:52
 */
@Slf4j
@Service
public class CustomerFootprintService {

    @Autowired
    private CustomerFootprintDao customerFootprintDao;

    /**
     * 异步保存客户足迹
     * @param customerId
     * @param merchantId
     */
    @Async
    public void save(Long customerId, Long merchantId){
        CustomerFootprint customerFootprint = customerFootprintDao.findByCustomerIdAndMerchantId(customerId, merchantId);
        if(customerFootprint == null){
            customerFootprintDao.save(
                    CustomerFootprint.builder()
                            .customerId(customerId)
                            .merchantId(merchantId)
                            .build());
        } else {
            customerFootprintDao.updateDate(customerFootprint.getId(), new Date());
        }
    }

    /**
     * 获取客户足迹列表
     * @param page
     * @param limit
     * @return
     */
    public List<Merchant> getFootprintList(Integer page, Integer limit){
        Customer currentCustomer = CustomerUtils.getCurrentCustomer();
        if(page == null){
            page = 1;
        }
        if(limit == null){
            limit = 30;
        }
        return customerFootprintDao.getFootprintList(currentCustomer.getId(), page, limit);
    }
}
