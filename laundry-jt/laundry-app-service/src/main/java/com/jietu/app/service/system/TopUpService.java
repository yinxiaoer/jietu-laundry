package com.jietu.app.service.system;

import com.jietu.app.dao.merchant.MerchantDao;
import com.jietu.app.dao.merchant.MerchantTopUpRulesDao;
import com.jietu.app.service.pay.PayFlowService;
import com.jietu.app.service.wx.WXPayService;
import com.jietu.app.utils.Assert;
import com.jietu.app.utils.CustomerUtils;
import com.jietu.common.entity.customer.Customer;
import com.jietu.common.entity.merchant.Merchant;
import com.jietu.common.entity.merchant.MerchantTopUpRules;
import com.jietu.common.enums.PayFlowType;
import com.jietu.common.enums.PayType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 充值服务类
 * @author: 印修河
 * @date: 2019/3/4 22:24
 */
@Service
public class TopUpService {

    @Autowired
    private MerchantDao merchantDao;
    @Autowired
    private WXPayService wxPayService;
    @Autowired
    private PayFlowService payFlowService;
    @Autowired
    private MerchantTopUpRulesDao merchantTopUpRulesDao;

    /**
     * 获取充值列表
     */
    public List<MerchantTopUpRules> list(Long merchantId){
        return merchantTopUpRulesDao.findByMerchantId(merchantId);
    }

    /**
     * 充值支付
     * @param amount
     * @return
     */
    public String pay(Long merchantId, BigDecimal amount) throws Exception {
        Customer currentCustomer = CustomerUtils.getCurrentCustomer();
        Merchant merchant = merchantDao.getById(merchantId);
        Assert.notNull(merchant, "请选择正确的商户");
        //保存支付流水
        String payDesc = "洁兔洗衣余额充值";
        Long transactionId = payFlowService.save(PayFlowType.TOP_UP, currentCustomer.getId(), merchantId, null,
                PayType.WX_PAY.getCode(), amount, payDesc);

        return wxPayService.unifiedorder(transactionId, amount, payDesc);
    }

}
