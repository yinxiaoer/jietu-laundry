package com.jietu.app.order;

import com.alibaba.fastjson.JSONObject;
import com.jietu.app.service.order.OrderService;
import com.jietu.common.entity.order.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderTests {

    @Autowired
    private OrderService orderService;

    @Test
    public void createOrder() {
        JSONObject request = JSONObject.parseObject("{\"currencyCode\":\"CNY\",\"customerAddressId\":12,\"deliverEndDate\":1548072000000,\"deliverStartDate\":1548064800000,\"discountAmount\":0,\"fetchEndDate\":1547784000000,\"fetchStartDate\":1547776800000,\"freightFee\":0,\"goodsTailAmount\":80,\"limit\":30,\"merchantId\":6,\"order\":\"desc\",\"orderGoodsList\":[{\"goodsId\":13,\"number\":2,\"price\":30},{\"goodsId\":14,\"number\":2,\"price\":50}],\"page\":1,\"payAmount\":80,\"sort\":\"createDate\"}");
        Order order = request.toJavaObject(Order.class);
        orderService.createOrder(order);
    }

}
