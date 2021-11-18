package com.jietu.app.controller.goods;

import com.alibaba.fastjson.JSONObject;
import com.jietu.app.controller.BaseController;
import com.jietu.app.service.goods.GoodsService;
import com.jietu.app.utils.Assert;
import com.jietu.app.utils.Result;
import com.jietu.app.controller.BaseController;
import com.jietu.app.service.goods.GoodsService;
import com.jietu.app.utils.Assert;
import com.jietu.app.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品控制器
 * @author: 印修河
 * @date: 2019/1/6 18:22
 */
@RestController
@RequestMapping("/goods")
public class GoodsController extends BaseController {

    @Autowired
    private GoodsService goodsService;

    @PostMapping("/getGoodsCategory")
    public Result getGoodsCategory(@RequestBody JSONObject request){

        Long merchantId = request.getLong("merchantId");
        Assert.notNull(merchantId, "请上送商户ID");
        JSONObject result = new JSONObject();
        result.put("categoryList", goodsService.getGoodsCategory(merchantId));
        return successResponse(result);
    }

}
