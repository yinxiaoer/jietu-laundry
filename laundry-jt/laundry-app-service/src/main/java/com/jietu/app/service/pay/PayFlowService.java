package com.jietu.app.service.pay;

import com.jietu.app.dao.merchant.MerchantTopUpRulesDao;
import com.jietu.app.dao.order.OrderDao;
import com.jietu.app.dao.pay.PayFlowDao;
import com.jietu.app.dao.system.SystemParameterDao;
import com.jietu.app.service.customer.CustomerVipCardService;
import com.jietu.app.utils.Assert;
import com.jietu.common.entity.merchant.MerchantTopUpRules;
import com.jietu.common.entity.order.Order;
import com.jietu.common.entity.pay.PayFlow;
import com.jietu.common.enums.OrderStatus;
import com.jietu.common.enums.PayFlowStatus;
import com.jietu.common.enums.PayFlowType;
import com.jietu.common.enums.WXResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * 支付流水服务类
 * @author: 印修河
 * @date: 2019/3/3 18:33
 */
@Slf4j
@Service
public class PayFlowService {

    @Autowired
    private PayFlowDao payFlowDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private CustomerVipCardService customerVipCardService;
    @Autowired
    private SystemParameterDao systemParameterDao;
    @Autowired
    private MerchantTopUpRulesDao merchantTopUpRulesDao;

    /**
     * 异步保存支付流水
     * @param customerId 客户id
     * @param orderId 订单id
     * @param payAmount 支付金额
     * @param desc 描述
     * @return
     */
    public Long save(PayFlowType types, Long customerId, Long merchantId, Long orderId,
                     Integer payType, BigDecimal payAmount, String desc) {
        PayFlow payFlow = PayFlow
                .builder()
                .status(PayFlowStatus.WAIT_PAY.getCode())
                .types(types.getCode())
                .customerId(customerId)
                .merchantId(merchantId)
                .orderId(orderId)
                .payType(payType)
                .payAmount(payAmount)
                .desc(desc)
                .build();
        payFlowDao.save(payFlow);
        return payFlow.getId();
    }

    /**
     * 微信结果通知
     * @param transactionId
     * @param wxResponseCode
     */
    @Transactional(rollbackFor = Exception.class)
    public void payResultNotice(Long transactionId, WXResponseCode wxResponseCode, String errorMsg) {
        PayFlow payFlow = payFlowDao.getById(transactionId);
        Assert.notNull(payFlow, "订单号不存在");
        //如果支付状态不等于待支付则忽略
        if(!PayFlowStatus.WAIT_PAY.getCode().equals(payFlow.getStatus())){
            log.info("支付流水状态未在待付款状态，结束支付通知");
            return ;
        }
        PayFlowStatus payFlowStatus = PayFlowStatus.PAY_FAIL;
        if(WXResponseCode.SUCCESS.equals(wxResponseCode)){
            payFlowStatus = PayFlowStatus.PAY_SUCCESS;
        }
        //更新支付流水
        payFlowDao.update(PayFlow
                .builder()
                .id(transactionId)
                .status(payFlowStatus.getCode())
                .errorMsg(errorMsg).build());
        if(PayFlowType.ORDER_PAY.getCode().equals(payFlow.getTypes())){
            orderPayResultNotice(wxResponseCode, payFlow);
        } else if(PayFlowType.TOP_UP.getCode().equals(payFlow.getTypes())){
            topUpPayResultNotice(wxResponseCode, payFlow);
        }
    }

    /**
     * 订单支付结果通知
     * @param wxResponseCode
     * @param payFlow
     */
    private void orderPayResultNotice(WXResponseCode wxResponseCode, PayFlow payFlow) {
        if(WXResponseCode.SUCCESS.equals(wxResponseCode)){
            Order updateOrder = new Order();
            updateOrder.setId(payFlow.getOrderId());
            //这支付成功将订单状态设置为待取货
            updateOrder.setStatus(OrderStatus.WAIT_CLAIM_GOODS.getStatus());
            orderDao.update(updateOrder);
        }
    }

    /**
     * 充值支付结果通知
     * @param wxResponseCode
     * @param payFlow
     */
    private void topUpPayResultNotice(WXResponseCode wxResponseCode, PayFlow payFlow){
        if(WXResponseCode.SUCCESS.equals(wxResponseCode)){
            //获取充值规则
            MerchantTopUpRules topUpRules = merchantTopUpRulesDao.getTopUpRules(payFlow.getMerchantId(), payFlow.getPayAmount());
            BigDecimal topUpAmount = payFlow.getPayAmount();
            if(topUpRules != null){
                topUpAmount = topUpAmount.add(topUpRules.getPresenterAmount());
            }
            customerVipCardService.balanceTopUp(payFlow.getCustomerId(), payFlow.getMerchantId(), topUpAmount);
        }
    }

}
