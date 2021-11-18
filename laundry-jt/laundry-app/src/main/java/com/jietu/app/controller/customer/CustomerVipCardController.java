package com.jietu.app.controller.customer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jietu.app.controller.BaseController;
import com.jietu.app.service.customer.CustomerVipCardService;
import com.jietu.app.utils.Assert;
import com.jietu.app.utils.CustomerUtils;
import com.jietu.app.utils.Result;
import com.jietu.common.entity.customer.Customer;
import com.jietu.common.entity.customer.CustomerVipCard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 客户会员卡控制器
 * @author: 印修河
 * @date: 2019/3/30 18:19
 */
@Slf4j
@RestController
@RequestMapping("/customer/vipCard")
public class CustomerVipCardController extends BaseController {

    @Autowired
    private CustomerVipCardService customerVipCardService;

    /**
     * 获取会员卡列表
     * @param customerVipCard
     * @return
     */
    @PostMapping("/getVipCardList")
    public Result getVipCardList(@RequestBody CustomerVipCard customerVipCard){
        log.info("获取会员卡请求参数：{}", JSON.toJSONString(customerVipCard));
        JSONObject result = new JSONObject();
        result.put("vipCardList", customerVipCardService.getVipCardList(customerVipCard));
        return successResponse(result);
    }

    /**
     * 返回客户余额
     * @return
     */
    @PostMapping("/getBalance")
    public Result getBalance(@RequestBody JSONObject request){
        Long merchantId = request.getLong("merchantId");
        Assert.notNull(merchantId, "请选择商户");
        log.info("获取客户余额请求参数：{}", request.toString());
        Customer currentCustomer = CustomerUtils.getCurrentCustomer();
        CustomerVipCard customerVipCard = customerVipCardService.findByCustomerIdAndMerchantId(currentCustomer.getId(), merchantId);
        JSONObject result = new JSONObject();
        if(customerVipCard != null){
            result.put("balance", customerVipCard.getBalance());
        } else {
            result.put("balance", 0);
        }

        return successResponse(result);
    }

}
