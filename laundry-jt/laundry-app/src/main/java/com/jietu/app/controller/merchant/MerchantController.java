package com.jietu.app.controller.merchant;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jietu.app.controller.BaseController;
import com.jietu.app.service.customer.CustomerCollectionService;
import com.jietu.app.service.merchant.MerchantService;
import com.jietu.app.service.order.OrderCommentService;
import com.jietu.app.utils.*;
import com.jietu.common.entity.customer.Customer;
import com.jietu.common.entity.customer.CustomerCollection;
import com.jietu.common.entity.merchant.Merchant;
import com.jietu.common.entity.order.OrderComment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 商户控制器
 * @author: 印修河
 * @date: 2019/1/6 14:19
 */
@Slf4j
@RestController
@RequestMapping("/merchant")
public class MerchantController extends BaseController {

    @Autowired
    private MerchantService merchantService;
    @Autowired
    private OrderCommentService orderCommentService;
    @Autowired
    private CustomerCollectionService customerCollectionService;

    /**
     * 查询周边商户
     * @param merchant
     * @return
     */
    @PostMapping("/findCircumMerchant")
    public Result findCircumMerchant(@RequestBody Merchant merchant){
        log.info("查询周边商户请求参数：{}", JSON.toJSONString(merchant));

        if(merchant.getLatitude() == null || merchant.getLatitude() == null){
            throw new MyException(ErrorCode.LOCATION_NOT_AUTH);
        }

        JSONObject result = new JSONObject();
        result.put("merchant", merchantService.findCircumMerchant(merchant));
        return successResponse(result);
    }

    /**
     * 获取商户详情
     * @param request
     * @return
     */
    @PostMapping("/getMerchantDetails")
    public Result getMerchantDetails(@RequestBody JSONObject request){
        log.info("获取商户详情请求参数：{}", request);

        Long merchantId = request.getLong("merchantId");
        BigDecimal longitude = request.getBigDecimal("longitude");
        BigDecimal latitude = request.getBigDecimal("latitude");

        Assert.notNull(merchantId, "请选择商户");
        if(longitude == null || latitude == null){
            throw new MyException(ErrorCode.LOCATION_NOT_AUTH);
        }

        Customer customer = CustomerUtils.getCurrentCustomer();

        JSONObject result = new JSONObject();
        result.put("merchant", merchantService.getMerchantDetails(merchantId, longitude, latitude));
        result.put("merchantInfoImages", merchantService.getMerchantInfoImages(merchantId));
        CustomerCollection customerCollection = customerCollectionService.findByCustomerIdAndMerchantId(customer.getId(), merchantId);
        if(customerCollection != null){
            result.put("isCollection", true);
        } else {
            result.put("isCollection", false);
        }
        return successResponse(result);
    }

    /**
     * 获取商户评价列表
     * @param orderComment
     * @return
     */
    @PostMapping("/getMerchantComment")
    public Result getMerchantComment(@RequestBody OrderComment orderComment){
        log.info("获取商户评价列表请求参数：{}", JSON.toJSONString(orderComment));

        Assert.notNull(orderComment.getMerchantId(), "请选择商户后查询");

        JSONObject result = new JSONObject();
        result.put("commentList", orderCommentService.getMerchantComment(orderComment));
        return successResponse(result);
    }

    /**
     * 获取商户配送时间列表
     * @param request 商户id
     * @return
     */
    @PostMapping("/getDistributionTimeList")
    public Result getDistributionTimeList(@RequestBody JSONObject request){

        Long merchantId = request.getLong("merchantId");
        Assert.notNull(merchantId, "请选择商户后查询");

        JSONObject result = new JSONObject();
        result.put("distributionTimeList", merchantService.getDistributionTimeList(merchantId));
        return successResponse(result);
    }

    /**
     * 获取评价统计
     * @param merchantId
     * @return
     */
    @PostMapping("/getCommentCount/{merchantId}")
    public Result getCommentCount(@PathVariable("merchantId") Long merchantId){

        Assert.notNull(merchantId, "请选择商户");
        Map<String, Object> commentCount = merchantService.getCommentCount(merchantId);
        JSONObject result = new JSONObject();
        result.put("commentCount", commentCount);
        return successResponse(result);
    }
}
