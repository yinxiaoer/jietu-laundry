package com.jietu.app.controller.customer;

import com.alibaba.fastjson.JSONObject;
import com.jietu.app.controller.BaseController;
import com.jietu.app.service.customer.CustomerCollectionService;
import com.jietu.app.utils.Assert;
import com.jietu.app.utils.CustomerUtils;
import com.jietu.app.utils.Result;
import com.jietu.common.entity.customer.Customer;
import com.jietu.common.entity.customer.CustomerCollection;
import com.jietu.common.entity.merchant.Merchant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 客户收藏控制器
 * @author: 印修河
 * @date: 2019/2/5 10:33
 */
@Slf4j
@RestController
@RequestMapping("/customer/collection")
public class CustomerCollectionController extends BaseController {

    @Autowired
    private CustomerCollectionService customerCollectionService;

    /**
     * 添加客户收藏
     * @param merchantId 商户id
     * @return
     */
    @PostMapping("/add/{merchantId}")
    public Result add(@PathVariable("merchantId") Long merchantId){
        log.info("添加收藏请求参数：{}", merchantId);

        Assert.notNull(merchantId, "请选择商户");
        Customer customer = CustomerUtils.getCurrentCustomer();

        customerCollectionService.add(CustomerCollection
                .builder()
                .customerId(customer.getId())
                .merchantId(merchantId)
                .build());
        return successResponse();
    }

    /**
     * 删除客户收藏
     * @param merchantId 商户id
     * @return
     */
    @PostMapping("/delete/{merchantId}")
    public Result delete(@PathVariable("merchantId") Long merchantId){
        log.info("删除收藏请求参数：{}", merchantId);

        Assert.notNull(merchantId, "请选择商户");
        Customer customer = CustomerUtils.getCurrentCustomer();
        customerCollectionService.delete(customer.getId(), merchantId);
        return successResponse();
    }

    /**
     * 获取用户收藏列表
     * @param request
     * @return
     */
    @PostMapping("/getCollectionList")
    public Result getCollectionList(@RequestBody JSONObject request){

        Integer page = request.getInteger("page");
        Integer limit = request.getInteger("limit");
        if(page == null){
            page = 1;
        }
        if(limit == null){
            limit = 30;
        }
        Customer customer = CustomerUtils.getCurrentCustomer();
        List<Merchant> collectionList = customerCollectionService.getCollectionList(customer.getId(), page, limit);
        JSONObject result = new JSONObject();
        result.put("collectionList", collectionList);
        return successResponse(result);
    }
}
