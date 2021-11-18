package com.jietu.app.service.customer;

import com.jietu.app.dao.customer.CustomerAddressDao;
import com.jietu.app.utils.CustomerUtils;
import com.jietu.common.entity.customer.Customer;
import com.jietu.common.entity.customer.CustomerAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 客户地址服务类
 * @author: 印修河
 * @date: 2018/12/27 22:17
 */
@Service
public class CustomerAddressService {

    @Autowired
    private CustomerAddressDao customerAddressDao;

    /**
     * 获取当前用户地址列表
     * @return
     */
    public List<CustomerAddress> getCustomerAddress(){
        Customer customer = CustomerUtils.getCurrentCustomer();
        return customerAddressDao.findByCustomerId(customer.getId());
    }

    /**
     * 保存或更新地址
     * @param customerAddress
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public List<CustomerAddress> saveOrUpdate(CustomerAddress customerAddress){
        Customer customer = CustomerUtils.getCurrentCustomer();
        //如果id为空，或者id无记录则新增，否则更新记录
        if(customerAddress.getId() == null
                || customerAddressDao.getById(customerAddress.getId()) == null){
            customerAddress.setCustomerId(customer.getId());
            customerAddressDao.save(customerAddress);
        } else {
            customerAddressDao.update(customerAddress);
        }
        //如果是默认地址则将其他的地址改成非默认地址
        if(customerAddress.getDefaulted()){
            customerAddressDao.updateNotDefault(customerAddress.getId(), customer.getId());
        }
        return customerAddressDao.findByCustomerId(customer.getId());
    }

    /**
     * 设置默认地址
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public List<CustomerAddress> setDefaulted(Long id){
        Customer customer = CustomerUtils.getCurrentCustomer();
        //更新当前地址为默认地址
        customerAddressDao.updateDefault(id, customer.getId());
        //更新其他地址为非默认地址
        customerAddressDao.updateNotDefault(id, customer.getId());
        return customerAddressDao.findByCustomerId(customer.getId());
    }

    /**
     * 删除收获地址
     * @param id 收获地址id
     * @return
     */
    public List<CustomerAddress> delete(Long id) {
        Customer customer = CustomerUtils.getCurrentCustomer();
        customerAddressDao.delete(id, customer.getId());
        return customerAddressDao.findByCustomerId(customer.getId());
    }
}
