package com.jietu.app.dao.customer;

import com.jietu.common.entity.customer.CustomerAddress;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 客户地址dao
 * @author: 印修河
 * @date: 2018/12/27 17:54
 */
public interface CustomerAddressDao {


    /**
     * 保存
     * @param customerAddress
     * @return
     */
    Integer save(CustomerAddress customerAddress);

    /**
     * 更新
     * @param customerAddress
     * @return
     */
    Integer update(CustomerAddress customerAddress);

    /**
     * 将此客户下的除此id都更新为非默认地址
     * @param customerId
     * @param id
     * @return
     */
    Integer updateNotDefault(@Param("id") Long id, @Param("customerId") Long customerId);

    /**
     * 将此客户下的此id地址更新为默认地址
     * @param customerId
     * @param id
     * @return
     */
    Integer updateDefault(@Param("id") Long id, @Param("customerId") Long customerId);

    /**
     * 通过id查询
     * @param id
     * @return
     */
    CustomerAddress getById(@Param("id") Long id);

    /**
     * 通过客户id查询客户地址
     * @param customerId
     * @return
     */
    List<CustomerAddress> findByCustomerId(@Param("customerId") Long customerId);

    /**
     * 删除客户地址
     * @param id
     * @param customerId
     * @return
     */
    Integer delete(@Param("id") Long id, @Param("customerId") Long customerId);
}
