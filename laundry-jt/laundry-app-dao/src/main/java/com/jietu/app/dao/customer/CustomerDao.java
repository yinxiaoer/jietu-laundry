package com.jietu.app.dao.customer;

import com.jietu.common.entity.customer.Customer;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * 客户信息dao
 * @author: 印修河
 * @date: 2018/12/25 21:35
 */
public interface CustomerDao {

    /**
     * 保存客户信息
     * @param customer
     * @return
     */
    Integer save(Customer customer);

    /**
     * 更新客户信息
     * @param customer
     * @return
     */
    Integer update(Customer customer);

    /**
     * 通过id查询客户信息
     * @param id
     * @return
     */
    Customer getById(@Param("id") Long id);

    /**
     * 通过openid获取用户信息
     * @param openid
     * @return
     */
    Customer findByOpenid(@Param("openid") String openid);

}
