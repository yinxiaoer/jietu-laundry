package com.jietu.app.service.wx;

import com.alibaba.fastjson.JSON;
import com.github.wxpay.sdk.WXPay;
import com.jietu.app.utils.CustomerUtils;
import com.jietu.app.utils.MyException;
import com.jietu.common.entity.customer.Customer;
import com.jietu.common.entity.merchant.Merchant;
import com.jietu.common.entity.order.Order;
import com.jietu.common.enums.CurrencyCode;
import com.jietu.common.enums.WXResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付服务类
 * @author: 印修河
 * @date: 2019/1/16 15:05
 */
@Slf4j
@Service
public class WXPayService {

    @Autowired
    private WXPay wxPay;

    @Value("${wxpay.notify.url}")
    private String wxpayNotifyUrl;
    @Value("${spring.profiles.active}")
    private String profile;

    /**
     * 统一下单
     * @param transactionId 交易id
     * @param payAmount 支付金额
     * @param desc 描述
     */
    public String unifiedorder(Long transactionId, BigDecimal payAmount, String desc) throws Exception {
        Map<String, String> request = new HashMap<>(32);
        request.put("body", desc);
        request.put("out_trade_no", profile.toUpperCase() + transactionId.toString());
        //货币类型
        request.put("fee_type", CurrencyCode.CNY.getCode());
        //标价金额,单位分
        request.put("total_fee", payAmount.multiply(new BigDecimal(100)).toBigInteger().toString());
        //终端IP
        String requestIp = MDC.get("REQUEST_IP");
        request.put("spbill_create_ip", requestIp);
        //交易起始时间
        Date startDate = new Date();
        request.put("time_start", DateFormatUtils.format(startDate, "yyyyMMddHHmmss"));
        Date endDate = DateUtils.addMinutes(startDate, 30);
        //交易结束时间
        request.put("time_expire", DateFormatUtils.format(endDate, "yyyyMMddHHmmss"));
        //通知地址
        request.put("notify_url", wxpayNotifyUrl);
        //交易类型
        request.put("trade_type", "JSAPI");
        Customer customer = CustomerUtils.getCurrentCustomer();
        request.put("openid", customer.getOpenid());
        //电子发票入口开放标识
        // request.put("receipt", "");
        log.info("调用微信统一下单请求参数：{}", JSON.toJSONString(request));
        Map<String, String> response = wxPay.unifiedOrder(request);
        log.info("调用微信统一下单相应参数：{}", JSON.toJSONString(response));
        String returnCode = response.get("return_code");
        //如果响应码错误则直接抛错
        if(!WXResponseCode.SUCCESS.getCode().equals(returnCode)){
            String returnMsg = response.get("return_msg");
            if(StringUtils.isBlank(returnMsg)){
                returnMsg= "调用微信支付失败";
            }
            throw new MyException(returnMsg);
        }
        //如果业务结果错误则直接抛错
        String resultCode = response.get("result_code");
        if(!WXResponseCode.SUCCESS.getCode().equals(resultCode)){
            String errCodeDes = response.get("err_code_des");
            if(StringUtils.isBlank(errCodeDes)){
                errCodeDes= "调用微信支付失败";
            }
            throw new MyException(errCodeDes);
        }
        return response.get("prepay_id");
    }

}
