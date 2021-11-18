package com.jietu.app.controller.customer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jietu.app.controller.BaseController;
import com.jietu.app.service.customer.CustomerAddressService;
import com.jietu.app.utils.*;
import com.jietu.app.controller.BaseController;
import com.jietu.app.service.customer.CustomerAddressService;
import com.jietu.app.utils.Assert;
import com.jietu.app.utils.Result;
import com.jietu.common.entity.customer.CustomerAddress;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 客户地址控制器
 * @author: 印修河
 * @date: 2018/12/27 22:27
 */
@Slf4j
@RestController
@RequestMapping("/customer")
public class CustomerAddressController extends BaseController {

    @Autowired
    private CustomerAddressService customerAddressService;

    /**
     * 获取用户地址
     * @return
     */
    @PostMapping("/getAddresses")
    public Result getAddresses(){
        List<CustomerAddress> customerAddressList = customerAddressService.getCustomerAddress();
        JSONObject resultJson = new JSONObject();
        resultJson.put("addresses", customerAddressList);
        return successResponse(resultJson);
    }

    /**
     * 保存或更新客户信息
     * @param customerAddress
     * @return
     */
    @PostMapping("/saveOrUpdateAddress")
    public Result saveOrUpdateAddress(@RequestBody CustomerAddress customerAddress){
        log.info("客户保存或更新地址信息请求参数：{}", JSON.toJSONString(customerAddress));

        if(customerAddress.getLatitude() == null || customerAddress.getLatitude() == null){
            throw new MyException(ErrorCode.LOCATION_NOT_AUTH);
        }
        Assert.notNull(customerAddress.getLocationName(), "请选择定位位置");
        Assert.notNull(customerAddress.getLocationAddress(), "请选择定位位置");
        Assert.notNull(customerAddress.getDetailsAddress(), "请输入详细地址");
        Assert.notNull(customerAddress.getReceiverName(), "请输入名称");
        Assert.notNull(customerAddress.getReceiverMobile(), "请输入联系电话");
        //校验手机号格式
        Assert.isTrue(customerAddress.getReceiverMobile().matches(Constants.MOBILE_NO_REG), "联系电话格式错误");

        List<CustomerAddress> customerAddressList = customerAddressService.saveOrUpdate(customerAddress);
        JSONObject resultJson = new JSONObject();
        resultJson.put("addresses", customerAddressList);
        return successResponse(resultJson);
    }

    /**
     * 设置为默认地址
     * @param id
     * @return
     */
    @PostMapping("/setDefaultedAddress/{id}")
    public Result setDefaultedAddress(@PathVariable("id") Long id){
        log.info("客户保存或更新地址信息请求参数：{}", id);
        Assert.notNull(id, "请上送id");
        List<CustomerAddress> customerAddressList = customerAddressService.setDefaulted(id);
        JSONObject resultJson = new JSONObject();
        resultJson.put("addresses", customerAddressList);
        return successResponse(resultJson);
    }

    /**
     * 删除客户地址
     * @param id
     * @return
     */
    @PostMapping("/deleteAddress/{id}")
    public Result deleteAddress(@PathVariable("id") Long id){
        log.info("删除客户地址请求参数：{}", id);

        Assert.notNull(id, "请上送id");

        JSONObject resultJson = new JSONObject();
        resultJson.put("addresses", customerAddressService.delete(id));
        return successResponse(resultJson);
    }
}
