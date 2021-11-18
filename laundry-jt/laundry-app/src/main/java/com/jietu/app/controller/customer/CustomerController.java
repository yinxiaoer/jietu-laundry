package com.jietu.app.controller.customer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jietu.app.controller.BaseController;
import com.jietu.app.service.customer.CustomerService;
import com.jietu.app.utils.Assert;
import com.jietu.app.utils.CustomerUtils;
import com.jietu.app.utils.Result;
import com.jietu.common.entity.customer.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 客户授权控制器
 * @author: 印修河
 * @date: 2018/12/25 13:05
 */
@RestController
@RequestMapping("/customer")
public class CustomerController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    @Autowired
    private CustomerService customerService;

    /**
     * 用户授权
     * @param request
     * @return
     */
    @PostMapping("/authorization")
    public Result authorization(@RequestBody JSONObject request){
        logger.info("微信授权请求参数：{}", request);
        String token = customerService.authorization(request.getString("code"));
        JSONObject data = new JSONObject();
        data.put("token", token);
        return successResponse(data);
    }

    /**
     * 获取用户个人信息
     * @return
     */
    @PostMapping("/getUserInfo")
    public Result getUserInfo(){
        return successResponse(customerService.getUserInfo());
    }

    /**
     * 更新客户信息
     * @param customer
     * @return
     */
    @PostMapping("/updateCustomerInfo")
    public Result updateCustomerInfo(@RequestBody Customer customer){
        logger.info("更新客户信息请求参数：{}", JSON.toJSONString(customer));
        return successResponse(customerService.updateCustomerInfo(customer));
    }

    /**
     * 保存客户微信手机号
     * @param request
     * @return
     */
    @PostMapping("/saveWeixinPhone")
    public Result saveWeixinPhone(@RequestBody JSONObject request){

        String encryptedData = request.getString("encryptedData");
        String iv = request.getString("iv");

        Assert.notNull(encryptedData, "请上送加密信息字段");
        Assert.notNull(iv, "请上送偏移量");

        customerService.saveWeixinPhone(encryptedData, iv);
        return successResponse();
    }

}