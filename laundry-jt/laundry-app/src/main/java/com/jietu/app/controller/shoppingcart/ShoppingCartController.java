package com.jietu.app.controller.shoppingcart;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jietu.app.controller.BaseController;
import com.jietu.app.entity.shoppingcart.ShoppingCart;
import com.jietu.app.service.shoppingcart.ShoppingCartService;
import com.jietu.app.utils.Assert;
import com.jietu.app.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController extends BaseController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 添加商品到购物车
     * @param
     * @return
     */
    @PostMapping("/addItems")
    public Result addItemsToShoppingCart(@RequestBody ShoppingCart shoppingCart){
        log.info("添加购物车请求参数：{}", JSON.toJSONString(shoppingCart));

        Assert.notNull(shoppingCart.getGoodsId(), "商品ID");
        Assert.notNull(shoppingCart.getMerchantId(), "商户ID");

        List<ShoppingCart> shoppingCartList = shoppingCartService.addItemsInShoppingCart(shoppingCart);
        BigDecimal shoppingCartAmount = shoppingCartService.countShoppingCartAmount(shoppingCart.getMerchantId());
        JSONObject resultJson = new JSONObject();
        resultJson.put("goodsList", shoppingCartList);
        resultJson.put("shoppingCartAmount", shoppingCartAmount);
        return successResponse(resultJson);
    }

    /**
     * 显示购物车商品
     * @param
     * @return
     */
    @PostMapping("/showItems")
    public Result showItemsInShoppingCart(@RequestBody ShoppingCart shoppingCart){
        log.info("显示购物车商品请求参数：{}", JSON.toJSONString(shoppingCart));
        Assert.notNull(shoppingCart.getMerchantId(), "商户ID");

        List<ShoppingCart> shoppingCartList = shoppingCartService.showListByMerchantId(shoppingCart);
        BigDecimal shoppingCartAmount = shoppingCartService.countShoppingCartAmount(shoppingCart.getMerchantId());
        JSONObject resultJson = new JSONObject();
        resultJson.put("goodsList", shoppingCartList);
        resultJson.put("shoppingCartAmount", shoppingCartAmount);
        return successResponse(resultJson);

    }



    /**
     * 清空购物车中所有商品
     * @param
     * @return
     */
    @PostMapping("/removeItems")
    public Result removeItemsInShoppingCart(@RequestBody ShoppingCart shoppingCart){
        log.info("清空购物车中所有商品请求参数：{}", JSON.toJSONString(shoppingCart));
        Assert.notNull(shoppingCart.getMerchantId(), "商户ID");

        List<ShoppingCart> shoppingCartList = shoppingCartService.removeItemsInShoppingCart(shoppingCart);
        BigDecimal shoppingCartAmount = shoppingCartService.countShoppingCartAmount(shoppingCart.getMerchantId());
        JSONObject resultJson = new JSONObject();
        resultJson.put("goodsList", shoppingCartList);
        resultJson.put("shoppingCartAmount", shoppingCartAmount);
        return successResponse(resultJson);
    }

    /**
     * 清空购物车中某一项商品
     * @param
     * @return
     */
    @PostMapping("/removeSingleItem")
    public Result removeSingleItemInShoppingCart(@RequestBody ShoppingCart shoppingCart){
        log.info("清空购物车中某一项商品请求参数：{}", JSON.toJSONString(shoppingCart));
        Assert.notNull(shoppingCart.getGoodsId(), "商品ID");
        Assert.notNull(shoppingCart.getMerchantId(), "商户ID");

        List<ShoppingCart> shoppingCartList = shoppingCartService.deleteSingleItemInShoppingCart(shoppingCart);
        BigDecimal shoppingCartAmount = shoppingCartService.countShoppingCartAmount(shoppingCart.getMerchantId());
        JSONObject resultJson = new JSONObject();
        resultJson.put("goodsList", shoppingCartList);
        resultJson.put("shoppingCartAmount", shoppingCartAmount);
        return successResponse(resultJson);
    }



    /**
     * 更新购物车中某商品
     * @param
     * @return
     */
    @PostMapping("/updateItems")
    public Result updateItemsInShoppingCart(@RequestBody ShoppingCart shoppingCart){
        log.info("更新购物车中某商品请求参数：{}", JSON.toJSONString(shoppingCart));
        Assert.notNull(shoppingCart.getGoodsId(), "商品ID");
        Assert.notNull(shoppingCart.getMerchantId(), "商户ID");
        Assert.notNull(shoppingCart.getNumber(), "数量");

        List<ShoppingCart> shoppingCartList = shoppingCartService.updateItemsInShoppingCart(shoppingCart);
        BigDecimal shoppingCartAmount = shoppingCartService.countShoppingCartAmount(shoppingCart.getMerchantId());
        JSONObject resultJson = new JSONObject();
        resultJson.put("goodsList", shoppingCartList);
        resultJson.put("shoppingCartAmount", shoppingCartAmount);
        return successResponse(resultJson);
    }

}
