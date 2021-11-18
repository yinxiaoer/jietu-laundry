package com.jietu.app.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.jietu.app.controller.BaseController;
import com.jietu.app.service.system.TopUpService;
import com.jietu.app.utils.Assert;
import com.jietu.app.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * 充值控制器
 *
 * @author: 印修河
 * @date: 2019/3/4 22:10
 */
@RestController
@RequestMapping("/topUp")
public class TopUpController extends BaseController {

    @Autowired
    private TopUpService topUpService;

    /**
     * 获取充值列表
     * @return
     */
    @PostMapping("/list")
    public Result list(@RequestBody JSONObject request) {
        Long merchantId = request.getLong("merchantId");
        Assert.notNull(merchantId, "请选择商户");
        JSONObject result = new JSONObject();
        result.put("topUpList", topUpService.list(merchantId));
        return successResponse(result);
    }

    /**
     * 充值支付
     * @param request
     * @return
     */
    @PostMapping("/pay")
    public Result pay(@RequestBody JSONObject request) throws Exception {
        Long merchantId = request.getLong("merchantId");
        BigDecimal amount = request.getBigDecimal("amount");

        Assert.notNull(amount, "请选择或输入金额");
        Assert.notNull(merchantId, "请选择商户");

        JSONObject result = new JSONObject();
        result.put("prepayId", topUpService.pay(merchantId, amount));
        return successResponse(result);
    }
}
