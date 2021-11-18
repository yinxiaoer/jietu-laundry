package com.jietu.app.dao.pay;

import com.jietu.common.entity.pay.PayFlow;
import org.apache.ibatis.annotations.Param;

/**
 * 支付流水dao
 * @author: 印修河
 * @date: 2019/3/3 18:27
 */
public interface PayFlowDao {

    /**
     * 保存支付流水
     * @param payFlow
     * @return
     */
    Integer save(PayFlow payFlow);

    /**
     * 更新支付流水
     * @param payFlow
     * @return
     */
    Integer update(PayFlow payFlow);

    /**
     * 通过id获取支付流水
     * @param id
     * @return
     */
    PayFlow getById(@Param("id") Long id);

    /**
     * 更新超时失效的支付流水
     * @return
     */
    Integer updateTimeOutFlow();
}
