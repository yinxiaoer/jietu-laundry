package com.jietu.admin.controller.goods;

import com.jietu.admin.service.goods.IGoodsCategoryService;
import com.jietu.admin.controller.system.BaseController;
import com.jietu.admin.service.goods.IGoodsCategoryService;
import com.jietu.admin.utils.SessionUtils;
import com.jietu.common.entity.goods.GoodsCategory;
import com.jietu.admin.model.AjaxResult;
import com.jietu.admin.utils.Assert;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品类型控制器类
 * @author: 印修河
 * @date: 2018/12/18 22:31
 */
@RestController
@RequestMapping("goodsCategory")
public class GoodsCategoryController extends BaseController {

    @Autowired
    private IGoodsCategoryService goodsCategoryService;

    /**
     * 商品类型列表
     * @param goods
     * @return
     */
    @RequestMapping("list")
    @RequiresPermissions("goodsCategory:list")
    public AjaxResult list(GoodsCategory goods){
        return AjaxResult.getSuccess(goodsCategoryService.findByQuery(goods));
    }

    /**
     * 查询商品类型
     * @param id
     * @return
     */
    @PostMapping("getById")
    public AjaxResult getById(Long id){
        Assert.notNull(id, "请上送商品类型id");
        return AjaxResult.getSuccess(goodsCategoryService.getById(id));
    }

    /**
     * 根据商户查询商品类型
     * @param merchantId
     * @return
     */
    @PostMapping("findByMerchantId")
    public AjaxResult findByMerchantId(Long merchantId){
        return AjaxResult.getSuccess(goodsCategoryService.findByMerchantId(merchantId));
    }

    /**
     * 保存商品类型
     * @param goods
     * @return
     */
    @PostMapping("save")
    @RequiresPermissions("goodsCategory:save")
    public AjaxResult save(GoodsCategory goods){

        Assert.notNull(goods.getName(), "请上送类型名称");
        Assert.notNull(goods.getMerchantId(), "请上送商户id");

        goods.setCreateName(SessionUtils.getCurrentUser().getRealname());
        goodsCategoryService.insert(goods);
        return AjaxResult.getSuccess();
    }

    /**
     * 更新商品类型
     * @param goods
     * @return
     */
    @PostMapping("update")
    @RequiresPermissions("goodsCategory:update")
    public AjaxResult update(GoodsCategory goods){

        Assert.notNull(goods.getName(), "请上送类型名称");
        Assert.notNull(goods.getMerchantId(), "请上送商户id");

        goods.setUpdateName(SessionUtils.getCurrentUser().getRealname());
        goodsCategoryService.update(goods);
        return AjaxResult.getSuccess();
    }

    /**
     * 删除商品类型
     * @param id
     * @return
     */
    @PostMapping("delete")
    @RequiresPermissions("goodsCategory:delete")
    public AjaxResult delete(Long id){
        Assert.notNull(id, "请上送商品id");
        goodsCategoryService.delete(id);
        return AjaxResult.getSuccess();
    }

}
