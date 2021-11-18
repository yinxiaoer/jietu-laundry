package com.jietu.app.controller.order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jietu.app.controller.BaseController;
import com.jietu.app.dto.order.CartItemCalculateRequest;
import com.jietu.app.service.order.OrderCommentService;
import com.jietu.app.service.order.OrderService;
import com.jietu.app.utils.Assert;
import com.jietu.app.utils.CustomerUtils;
import com.jietu.app.utils.Result;
import com.jietu.common.entity.customer.Customer;
import com.jietu.common.entity.order.Order;
import com.jietu.common.entity.order.OrderComment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订单控制器
 * @author: 印修河
 * @date: 2019/1/12 16:54
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderCommentService orderCommentService;

    /**
     * 创建订单接口
     * @param order
     * @return
     */
    @PostMapping("/createOrder")
    public Result createOrder(@RequestBody Order order) throws Exception {
        log.info("创建订单请求参数：{}", JSON.toJSONString(order));
        Assert.notNull(order.getMerchantId(), "请上送商户号");
        Assert.notNull(order.getFetchStartDate(), "请选择取衣开始时间");
        Assert.notNull(order.getFetchEndDate(), "请选择取衣结束时间");
        Assert.notNull(order.getDeliverStartDate(), "请选择送衣开始时间");
        Assert.notNull(order.getDeliverEndDate(), "请选择送衣结束时间");
        Assert.notNull(order.getCustomerAddressId(), "请选择收货地址");
        Assert.notNull(order.getPayAmount(), "请上送支付金额");
        Assert.notNull(order.getFreightFee(), "请上送配送费用");
        Assert.notNull(order.getGoodsTailAmount(), "请上送商品总价");
        Assert.notNull(order.getDiscountAmount(), "请上送优惠金额");
        Assert.notNull(order.getOrderGoodsList(), "请上送商品列表");

        return  successResponse(orderService.createOrder(order));
    }

    /**
     * 购物车金额计算接口
     * @param request
     * @return
     */
    @PostMapping("/cartItemCalculate")
    public Result cartItemCalculate(@RequestBody CartItemCalculateRequest request){
        log.info("购物车金额计算请求参数：{}", JSON.toJSONString(request));

        Assert.notNull(request.getGoodsList(), "请上送商品列表");

        return successResponse(orderService.cartItemCalculate(request));
    }

    /**
     * 获取客户订单列表
     * @param request
     * @return
     */
    @PostMapping("/getCustomerOrderList")
    public Result getCustomerOrderList(@RequestBody JSONObject request){
        log.info("获取客户订单列表请求参数:{}", request);
        Customer customer = CustomerUtils.getCurrentCustomer();
        List<Order> orderList = orderService.getCustomerOrderList(customer, request);
        JSONObject result = new JSONObject();
        result.put("orderList", orderList);
        //设置页数
        Integer page = request.getInteger("page");
        if(page == null){
            page = 1;
        }
        result.put("page", page);
        //设置条数
        Integer limit = request.getInteger("limit");
        if(limit == null){
            limit = 30;
        }
        result.put("limit", limit);
        return successResponse(result);
    }

    /**
     * 获取订单详情
     * @param request
     * @return
     */
    @PostMapping("/getOrderDetails")
    public Result getOrderDetails(@RequestBody JSONObject request){
        log.info("获取订单详情请求参数:{}", request);
        Long orderId = request.getLong("orderId");
        Assert.notNull(orderId, "请选择订单");
        Customer customer = CustomerUtils.getCurrentCustomer();
        Order order = orderService.getOrderDetails(orderId, customer.getId());
        JSONObject result = new JSONObject();
        result.put("order", order);
        return successResponse(result);
    }

    /**
     * 订单评价
     * @param orderComment
     * @return
     */
    @PostMapping("/comment")
    public Result comment(@RequestBody OrderComment orderComment){
        log.info("订单评价请求参数： {}", orderComment);

        Assert.notNull(orderComment.getOrderId(), "请选择订单后评价");
        Assert.notNull(orderComment.getGrade(), "请选择评价等级");

        Customer customer = CustomerUtils.getCurrentCustomer();
        orderCommentService.comment(orderComment, customer);
        return successResponse();
    }

    /**
     * 支付
     * @param request
     * @return
     */
    @PostMapping("/pay")
    public Result pay(@RequestBody JSONObject request) throws Exception {
        log.info("订单支付请求参数：{}", request.toJSONString());
        Long orderId = request.getLong("orderId");
        Integer payType = request.getInteger("payType");

        Assert.notNull(orderId, "请上送订单id");
        Assert.notNull(payType, "请支付方式");

        return successResponse(orderService.pay(orderId, payType));
    }

    /**
     * 取消订单
     * @param orderId
     * @return
     */
    @PostMapping("/cancel/{orderId}")
    public Result cancel(@PathVariable("orderId") Long orderId){
        log.info("取消订单请求参数:{}", orderId);
        Assert.notNull(orderId, "请选择订单");

        orderService.cancel(orderId);

        return successResponse();
    }
}
