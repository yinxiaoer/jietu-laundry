package com.jietu.app.service.customer;

import com.jietu.app.dao.customer.CustomerDao;
import com.jietu.app.dao.customer.CustomerVipCardDao;
import com.jietu.app.utils.CustomerUtils;
import com.jietu.app.utils.MyException;
import com.jietu.common.entity.customer.Customer;
import com.jietu.common.entity.customer.CustomerVipCard;
import com.jietu.common.entity.merchant.MerchantBalance;
import com.jietu.common.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 客户会员卡类
 * @author: 印修河
 * @date: 2019/3/30 14:14
 */
@Slf4j
@Service
public class CustomerVipCardService {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerVipCardDao customerVipCardDao;

    /**
     * 获取会员卡列表
     * @param customerVipCard
     * @return
     */
    public List<MerchantBalance> getVipCardList(CustomerVipCard customerVipCard){
        Customer currentCustomer = CustomerUtils.getCurrentCustomer();
        customerVipCard.setCustomerId(currentCustomer.getId());
        return customerVipCardDao.getVipCardList(customerVipCard);
    }

    /**
     * 通过客户id和商户id查询会员卡
     * @param customerId
     * @param merchantId
     * @return
     */
    public CustomerVipCard findByCustomerIdAndMerchantId(Long customerId, Long merchantId){
        return customerVipCardDao.findByCustomerIdAndMerchantId(customerId, merchantId);
    }

    /**
     * 余额支付
     * @param customerId 客户id
     * @param payAmount 支付金额
     */
    public void balancePay(Long customerId, Long merchantId, BigDecimal payAmount){
        CustomerVipCard customerVipCard = customerVipCardDao.findByCustomerIdAndMerchantId(customerId, merchantId);
        if(customerVipCard.getBalance().compareTo(payAmount) < 0){
            throw new MyException("账户余额不足");
        }
        customerVipCardDao.balancePay(customerVipCard.getId(), payAmount);
        //更新客户信息
        customerService.refreshCustomerInfoCache(customerId);
    }

    /**
     * 余额充值
     * @param customerId 客户id
     * @param payAmount 支付金额
     */
    public void balanceTopUp(Long customerId, Long merchantId, BigDecimal payAmount){
        CustomerVipCard customerVipCard = customerVipCardDao.findByCustomerIdAndMerchantId(customerId, merchantId);
        if(customerVipCard == null){
            customerVipCardDao.save(CustomerVipCard.builder().customerId(customerId).merchantId(merchantId).balance(payAmount).build());
        } else {
            customerVipCardDao.balanceTopUp(customerVipCard.getId(), payAmount);
        }
        //更新客户信息
        customerService.refreshCustomerInfoCache(customerId);
    }

}
