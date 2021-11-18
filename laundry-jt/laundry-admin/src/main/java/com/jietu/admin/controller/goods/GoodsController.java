package com.jietu.admin.controller.goods;

import com.jietu.admin.service.goods.IGoodsService;
import com.jietu.admin.service.goods.IGoodsService;
import com.jietu.admin.controller.system.BaseController;
import com.jietu.admin.service.merchant.IMerchantService;
import com.jietu.admin.utils.Constants;
import com.jietu.admin.utils.SessionUtils;
import com.jietu.common.entity.goods.Goods;
import com.jietu.admin.model.AjaxResult;
import com.jietu.admin.utils.Assert;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品控制器类
 * @author: 印修河
 * @date: 2018/12/18 22:31
 */
@RestController
@RequestMapping("goods")
public class GoodsController extends BaseController {

    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private IMerchantService merchantService;

    /**
     * 商品列表
     * @param goods
     * @return
     */
    @RequestMapping("list")
    public AjaxResult list(Goods goods){
        return AjaxResult.getSuccess(goodsService.findByQuery(goods));
    }

    /**
     * 删除商品
     * @param id
     * @return
     */
    @PostMapping("getById")
    public AjaxResult getById(Long id){
        Assert.notNull(id, "请上送商品id");
        return AjaxResult.getSuccess(goodsService.getById(id));
    }

    /**
     * 保存商品
     * @param goods
     * @return
     */
    @PostMapping("save")
    @RequiresPermissions("goods:save")
    public AjaxResult save(Goods goods){

        Assert.notNull(goods.getName(), "请上送商品名");
        Assert.notNull(goods.getMerchantId(), "请上送商户id");
        Assert.notNull(goods.getCategoryId(), "请上送类型id");
        Assert.notNull(goods.getPrice(), "请上送单价");

        goodsService.insert(goods);
        return AjaxResult.getSuccess();
    }

    /**
     * 更新商品
     * @param goods
     * @return
     */
    @PostMapping("update")
    @RequiresPermissions("goods:update")
    public AjaxResult update(Goods goods){

        Assert.notNull(goods.getName(), "请上送商品名");
        Assert.notNull(goods.getMerchantId(), "请上送商户id");
        Assert.notNull(goods.getCategoryId(), "请上送类型id");
        Assert.notNull(goods.getPrice(), "请上送单价");

        goods.setUpdateName(SessionUtils.getCurrentUser().getRealname());
        goodsService.update(goods);
        return AjaxResult.getSuccess();
    }

    /**
     * 删除商品
     * @param id
     * @return
     */
    @PostMapping("delete")
    @RequiresPermissions("goods:delete")
    public AjaxResult delete(Long id){
        Assert.notNull(id, "请上送商品id");
        goodsService.delete(id);
        return AjaxResult.getSuccess();
    }

    /**
     * 是否显示商户
     * @return
     */
    @RequestMapping("isShowMerchant")
    public AjaxResult isShowMerchant(){
        //如果是管理员则显示商户
        if(Constants.ROLE_ID_ADMIN.equals(SessionUtils.getCurrentUser().getRoleId())){
            return AjaxResult.getSuccess(merchantService.getAll());
        }
        return AjaxResult.getSuccess();
    }

    /**
     * 保存商品图片
     * @param id 商品id
     * @param url 图片id
     * @return 返回所有商品图片列表
     */
    @RequestMapping("saveGoodsImage")
    @RequiresPermissions("goods:save")
    public AjaxResult saveGoodsImage(Long id, String url){
        return AjaxResult.getSuccess(goodsService.saveGoodsImage(id, url));
    }

    /**
     * 删除商品图片
     * @param id 图片id
     * @return
     */
    @RequestMapping("deleteGoodsImage")
    @RequiresPermissions("goods:save")
    public AjaxResult deleteGoodsImage(Long id){
        goodsService.deleteGoodsImageBy(id);
        return AjaxResult.getSuccess();
    }

    /**
     * 返回商品图片列表
     * @param id 商品id
     * @return
     */
    @RequestMapping("getGoodsImages")
    public AjaxResult getGoodsImages(Long id){
        return AjaxResult.getSuccess(goodsService.getGoodsImages(id));
    }
}
