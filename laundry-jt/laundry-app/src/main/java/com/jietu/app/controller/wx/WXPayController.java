package com.jietu.app.controller.wx;

import com.alibaba.fastjson.JSON;
import com.github.wxpay.sdk.WXPayUtil;
import com.jietu.app.controller.BaseController;
import com.jietu.app.service.order.OrderService;
import com.jietu.app.service.pay.PayFlowService;
import com.jietu.common.enums.WXResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付控制器
 * @author: 印修河
 * @date: 2019/2/28 11:09
 */
@Slf4j
@RestController
@RequestMapping("/wxpay")
public class WXPayController extends BaseController {

    @Autowired
    private PayFlowService payFlowService;
    @Value("${spring.profiles.active}")
    private String profile;

    /**
     * 支付结果通知
     * @param request
     */
    @PostMapping("/payResultNotice")
    public String payResultNotice(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, String> resultMap = new HashMap<>(4);
        String resultxml = IOUtils.toString(request.getInputStream(), "utf-8");
        Map<String, String> params = WXPayUtil.xmlToMap(resultxml);
        log.info("微信支付回调通知请求参数：{}", JSON.toJSONString(params));
        //接口状态不等于成功返回失败
        if(!WXResponseCode.SUCCESS.getCode().equals(params.get("return_code"))){
            resultMap.put("return_code", WXResponseCode.FAIL.getCode());
            resultMap.put("return_msg", WXResponseCode.FAIL.getDesc());
            String resultStr = WXPayUtil.mapToXml(resultMap);
            log.info("微信支付回调通知响应参数：{}", resultStr);
            return resultStr;
        }

        WXResponseCode wxResponseCode = WXResponseCode.FAIL;
        if(WXResponseCode.SUCCESS.getCode().equals(params.get("result_code"))){
            wxResponseCode = WXResponseCode.SUCCESS;
        }
        try {
            String outTradeNo = params.get("out_trade_no");
            String transactionId = outTradeNo.substring(profile.length());
            payFlowService.payResultNotice(Long.valueOf(transactionId), wxResponseCode, params.get("err_code_des"));
            resultMap.put("return_code", WXResponseCode.SUCCESS.getCode());
            resultMap.put("return_msg", WXResponseCode.SUCCESS.getDesc());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultMap.put("return_code", WXResponseCode.FAIL.getCode());
            resultMap.put("return_msg", WXResponseCode.FAIL.getDesc());
        }
        String resultStr = WXPayUtil.mapToXml(resultMap);
        log.info("微信支付回调通知响应参数：{}", resultStr);
        return resultStr;
    }


}
