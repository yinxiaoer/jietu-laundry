package com.jietu.app.controller.customer;

import com.alibaba.fastjson.JSONObject;
import com.jietu.app.controller.BaseController;
import com.jietu.app.service.customer.CustomerFootprintService;
import com.jietu.app.utils.Result;
import com.jietu.common.entity.merchant.Merchant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 客户足迹控制器类
 * @author: 印修河
 * @date: 2019/2/26 10:39
 */
@RestController
@RequestMapping("/customer/footprint")
public class CustomerFootprintController extends BaseController {

    @Autowired
    private CustomerFootprintService customerFootprintService;

    /**
     * 获取用户足迹列表
     * @param request
     * @return
     */
    @PostMapping("/getFootprintList")
    public Result getFootprintList(@RequestBody JSONObject request){

        Integer page = request.getInteger("page");
        Integer limit = request.getInteger("limit");

        List<Merchant> footprintList = customerFootprintService.getFootprintList(page, limit);
        JSONObject result = new JSONObject();
        result.put("footprintList", footprintList);
        return successResponse(result);
    }

}
