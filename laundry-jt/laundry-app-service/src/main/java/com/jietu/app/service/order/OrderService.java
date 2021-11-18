package com.jietu.app.service.order;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jietu.app.dao.customer.CustomerAddressDao;
import com.jietu.app.dao.customer.CustomerVipCardDao;
import com.jietu.app.dao.goods.GoodsDao;
import com.jietu.app.dao.merchant.MerchantDao;
import com.jietu.app.dao.order.OrderDao;
import com.jietu.app.dao.shoppingcart.ShoppingCartDao;
import com.jietu.app.dto.order.CartItemCalculateRequest;
import com.jietu.app.dto.order.CartItemCalculateResponse;
import com.jietu.app.entity.shoppingcart.ShoppingCart;
import com.jietu.app.service.customer.CustomerService;
import com.jietu.app.service.customer.CustomerVipCardService;
import com.jietu.app.service.merchant.MerchantMonthSalesService;
import com.jietu.app.service.pay.PayFlowService;
import com.jietu.app.service.wx.WXPayService;
import com.jietu.app.utils.*;
import com.jietu.common.entity.customer.Customer;
import com.jietu.common.entity.customer.CustomerAddress;
import com.jietu.common.entity.goods.Goods;
import com.jietu.common.entity.merchant.Merchant;
import com.jietu.common.entity.order.Order;
import com.jietu.common.entity.order.OrderGoods;
import com.jietu.common.enums.CurrencyCode;
import com.jietu.common.enums.OrderStatus;
import com.jietu.common.enums.PayFlowType;
import com.jietu.common.enums.PayType;
import com.jietu.common.service.RedisService;
import com.jietu.common.utils.CalulateDistanceUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单服务类
 *
 * @author: 印修河
 * @date: 2019/1/12 17:02
 */
@Service
public class OrderService {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(OrderService.class);
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private CustomerAddressDao customerAddressDao;
    @Autowired
    private ShoppingCartDao shoppingCartDao;
    @Autowired
    private MerchantDao merchantDao;
    @Autowired
    private MerchantMonthSalesService merchantMonthSalesService;
    @Autowired
    private WXPayService wxPayService;
    @Autowired
    private PayFlowService payFlowService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private CustomerVipCardService customerVipCardService;

    /**
     * 创建订单
     *
     * @param order
     */
    @Transactional(rollbackFor = Exception.class)
    public JSONObject createOrder(Order order) {
        Customer customer = CustomerUtils.getCurrentCustomer();
        Merchant merchant = merchantDao.getById(order.getMerchantId());
        //校验收货地址是否在商户服务范围内,设置收货信息
        getCustomerAddress(order, merchant);

        //校验金额是否一致
        saveOrder(customer, order);

        //循环保存订单商品
        saveOrderGoods(order);

        //清空用户该商户下购物车
        clearShoppingCart(order);

        //增加商户销量
        merchantMonthSalesService.addSales(order.getMerchantId());
        JSONObject result = new JSONObject(4);
        result.put("orderId", order.getId());
        result.put("payAmount", order.getPayAmount());
        return result;
    }

    /**
     * 保存订单
     * @param customer 客户
     * @param order 订单
     */
    private void saveOrder(Customer customer, Order order) {
        //促销优惠价格
        BigDecimal discountAmount = new BigDecimal(0);
        //邮费 暂时写0，后面通过距离计算出配送费用
        BigDecimal freightFee = new BigDecimal(0);
        //商品总金额
        BigDecimal goodsTailAmount = countGoodsAmount(order.getOrderGoodsList());
        logger.info("后台计算商品总金额为：{},优惠价格为:{},邮费为：{}", goodsTailAmount, discountAmount, freightFee);
        //订单总金额=商品金额-优惠价格+邮费
        BigDecimal payAmount = goodsTailAmount.subtract(discountAmount).add(freightFee);
        logger.info("后台计算订单支付金额为：{}", payAmount);
        //如果支付金额不相等则报错
        payAmount = payAmount.setScale(2, BigDecimal.ROUND_UP);
        order.setPayAmount(order.getPayAmount().setScale(2, BigDecimal.ROUND_UP));
        if (!payAmount.equals(order.getPayAmount())) {
            throw new MyException(ErrorCode.FAIL, "支付金额与后台计算支付金额不一致");
        }
        //创建初始化订单
        //设置订单编号
        order.setSn(getOrderSn());
        order.setCustomerId(customer.getId());
        //如果没有传类型则默认设置为人民币
        if (StringUtils.isNotBlank(order.getCurrencyCode())) {
            order.setCurrencyCode(CurrencyCode.CNY.getCode());
        }
        order.setPayAmount(payAmount);
        order.setGoodsTailAmount(goodsTailAmount);
        order.setFreightFee(freightFee);
        order.setDiscountAmount(discountAmount);
        //由于没有调用微信支付，所直接将状态设置为带待取货
        order.setStatus(OrderStatus.WAIT_PAY.getStatus());
        orderDao.save(order);
    }

    /**
     * 获取订单编号
     * @return
     */
    private String getOrderSn(){
        String sn = DateFormatUtils.format(new Date(), "yyyyMMdd");
        Long orderSn = redisService.incr(Constants.ORDER_SN_KEY, 1);
        return sn + String.format("%010d", orderSn);
    }

    /**
     * 校验收货地址是否在商户服务范围内,设置收货信息
     * @param order 订单
     */
    private void getCustomerAddress(Order order, Merchant merchant) {
        Assert.notNull(merchant, "请选择正确的商家");
        CustomerAddress customerAddress = customerAddressDao.getById(order.getCustomerAddressId());
        Assert.notNull(customerAddress, "请选择正确的收货地址");
        Long scopeOfServices = CalulateDistanceUtils.CalulateTwoLanLonDistance(merchant.getLatitude(), merchant.getLongitude(), customerAddress.getLatitude(), customerAddress.getLongitude());
        if(scopeOfServices > merchant.getScopeOfServices()){
            throw new MyException(ErrorCode.FAIL, "收获地址不在商家服务范围内");
        }
        //设置收获地址
        String address = customerAddress.getLocationAddress() + customerAddress.getDetailsAddress();
        order.setReceiverAddress(address);
        order.setReceiverName(customerAddress.getReceiverName());
        order.setReceiverMobile(customerAddress.getReceiverMobile());
    }

    /**
     * 清空用户该商户下购物车
     * @param order 订单
     */
    private void clearShoppingCart(Order order) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCustomerId(order.getCustomerId());
        shoppingCart.setMerchantId(order.getMerchantId());
        shoppingCartDao.deleteItem(shoppingCart);
    }

    /**
     * 循环保存订单商品
     * @param order
     */
    private void saveOrderGoods(Order order) {
        for (OrderGoods orderGoods : order.getOrderGoodsList()) {
            Goods goods = goodsDao.getById(orderGoods.getGoodsId());
            orderGoods.setOrderId(order.getId());
            orderGoods.setName(goods.getName());
            orderGoods.setPicUrl(goods.getPicUrl());
            orderGoods.setMarketPrice(goods.getMarketPrice());
            orderGoods.setRetailPrice(goods.getRetailPrice());
            orderDao.saveOrderGoods(orderGoods);
        }
    }

    /**
     * 购物车金额计算
     *
     * @return
     */
    public CartItemCalculateResponse cartItemCalculate(CartItemCalculateRequest request) {
        CartItemCalculateResponse response = new CartItemCalculateResponse();

        //促销优惠价格
        BigDecimal discountAmount = new BigDecimal(0);
        //邮费 暂时写0，后面通过距离计算出配送费用
        BigDecimal freightFee = new BigDecimal(0);
        //商品总金额
        BigDecimal goodsTailAmount = countGoodsAmount(request.getGoodsList());

        //订单总金额=商品金额-优惠价格+邮费
        BigDecimal payAmount = goodsTailAmount.subtract(discountAmount).add(freightFee);
        //设置返回信息
        response.setPayAmount(payAmount.setScale(2, BigDecimal.ROUND_UP));
        response.setDiscountAmount(discountAmount.setScale(2, BigDecimal.ROUND_UP));
        response.setGoodsTailAmount(goodsTailAmount.setScale(2, BigDecimal.ROUND_UP));
        response.setFreightFee(freightFee.setScale(2, BigDecimal.ROUND_UP));
        return response;
    }

    /**
     * 计算金额
     *
     * @param orderGoodsList
     * @return
     */
    private BigDecimal countGoodsAmount(List<OrderGoods> orderGoodsList) {
        BigDecimal goodsTailAmount = BigDecimal.ZERO;
        //循环计算商品单价*数量
        for (OrderGoods orderGoods : orderGoodsList) {
            Goods goods = goodsDao.getById(orderGoods.getGoodsId());
            goodsTailAmount = goodsTailAmount.add(goods.getPrice().multiply(new BigDecimal(orderGoods.getNumber())));
        }
        return goodsTailAmount;
    }

    /**
     * 获取客户订单列表
     *
     * @param customer
     * @param request
     * @return
     */
    public List<Order> getCustomerOrderList(Customer customer, JSONObject request) {
        Order order = new Order();
        order.setCustomerId(customer.getId());
        //设置页数
        Integer page = request.getInteger("page");
        if (page == null) {
            page = 1;
        }
        order.setPage(page);
        //设置条数
        Integer limit = request.getInteger("limit");
        if (limit == null) {
            limit = 30;
        }
        order.setLimit(limit);
        //设置状态status
        JSONArray statusList = request.getJSONArray("statusList");
        if (statusList != null) {
            StringBuilder statusStr = new StringBuilder();
            for (int i = 0; i < statusList.size(); i++) {
                if (i == statusList.size() - 1) {
                    statusStr.append(statusList.getInteger(i));
                } else {
                    statusStr.append(statusList.getInteger(i)).append(",");
                }
            }
            order.setStatusList(statusStr.toString());
        }
        return orderDao.getCustomerOrderList(order);
    }

    /**
     * 获取订单详情
     *
     * @param orderId    订单id
     * @param customerId 客户id
     * @return
     */
    public Order getOrderDetails(Long orderId, Long customerId) {
        return orderDao.getOrderDetails(orderId, customerId);
    }

    /**
     * 支付
     * @param orderId 订单id
     * @param payTypeCode 支付方式
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public JSONObject pay(Long orderId, Integer payTypeCode) throws Exception {
        Customer currentCustomer = CustomerUtils.getCurrentCustomer();

        PayType payType = PayType.valueOfCode(payTypeCode);
        Assert.notNull(payType, "请选择正确的支付方式");

        Order order = orderDao.getOrderDetails(orderId, currentCustomer.getId());
        if(order == null || !OrderStatus.WAIT_PAY.getStatus().equals(order.getStatus())){
            throw new MyException("请选择正确有效的订单");
        }

        JSONObject result = new JSONObject(4);
        Merchant merchant = merchantDao.getById(order.getMerchantId());
        Order updateOrder = new Order();
        updateOrder.setId(orderId);
        //保存支付流水
        String payDesc = "购买 " + merchant.getName() + " 洗衣服务";
        Long transactionId = payFlowService.save(PayFlowType.ORDER_PAY, currentCustomer.getId(), merchant.getId(), order.getId(),
                payType.getCode(), order.getPayAmount(), payDesc);
        //余额支付
        if(PayType.BALANCE_PAY.equals(payType)){
            customerVipCardService.balancePay(currentCustomer.getId(), order.getMerchantId(), order.getPayAmount());
            //设置状态为待收货
            updateOrder.setStatus(OrderStatus.WAIT_CLAIM_GOODS.getStatus());
        } else if(PayType.WX_PAY.equals(payType)){
            //调用微信统一下单
            String prepayId = wxPayService.unifiedorder(transactionId, order.getPayAmount(), payDesc);
            result.put("prepayId", prepayId);
        }

        //更新订单信息
        updateOrder.setPayType(payType.getCode());
        orderDao.update(updateOrder);

        result.put("orderSn", order.getSn());
        result.put("payAmount", order.getPayAmount());
        return result;
    }

    /**
     * 取消订单
     * @param orderId
     */
    public void cancel(Long orderId){
        Customer currentCustomer = CustomerUtils.getCurrentCustomer();
        Order orderDetails = orderDao.getOrderDetails(orderId, currentCustomer.getId());
        Assert.notNull(orderDetails, "请选择正确的订单号");
        if(!OrderStatus.WAIT_PAY.getStatus().equals(orderDetails.getStatus())){
            throw new MyException("该订单状态未在待付款，不支持取消订单");
        }
        orderDao.updateStatus(orderDetails.getId(), OrderStatus.ORDER_CANCEL.getStatus());
    }

}
