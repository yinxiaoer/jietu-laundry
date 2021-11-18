package com.jietu.admin.controller.merchant;

import com.jietu.admin.controller.system.BaseController;
import com.jietu.admin.service.merchant.IMerchantService;
import com.jietu.admin.controller.system.BaseController;
import com.jietu.admin.service.merchant.IMerchantService;
import com.jietu.admin.utils.SessionUtils;
import com.jietu.common.entity.merchant.Merchant;
import com.jietu.admin.model.AjaxResult;
import com.jietu.admin.utils.Assert;
import com.jietu.common.entity.merchant.MerchantImage;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商户控制器
 * @author: 印修河
 * @date: 2018/12/20 22:46
 */
@RestController
@RequestMapping("/merchant")
public class MerchantController extends BaseController {

    @Autowired
    private IMerchantService merchantService;

    /**
     * 获取商户列表
     * @param merchant
     * @return
     */
    @RequestMapping("list")
    @RequiresPermissions("merchant:list")
    public AjaxResult list(Merchant merchant){
        return AjaxResult.getSuccess(merchantService.findByQuery(merchant));
    }

    /**
     * 保存商户
     * @param merchant
     * @return
     */
    @PostMapping("save")
    @RequiresPermissions("merchant:save")
    public AjaxResult save(Merchant merchant){

        Assert.notNull(merchant.getName(), "请上送商户名称");
        Assert.notNull(merchant.getAddress(), "请上送商户地址");
        Assert.notNull(merchant.getBusinessLicenseCode(), "请上送商户营业执照编号");
        Assert.notNull(merchant.getContactName(), "请上送联系人姓名");
        Assert.notNull(merchant.getContactMobile(), "请上送联系人手机号");
        Assert.notNull(merchant.getLongitude(), "请上送坐标经度");
        Assert.notNull(merchant.getLatitude(), "请上送坐标纬度");

        merchantService.insert(merchant);
        return AjaxResult.getSuccess();
    }

    /**
     * 更新商户
     * @param merchant
     * @return
     */
    @PostMapping("update")
    @RequiresPermissions("merchant:update")
    public AjaxResult update(Merchant merchant){

        Assert.notNull(merchant.getId(), "请上送商户id");
        Assert.notNull(merchant.getName(), "请上送商户名称");
        Assert.notNull(merchant.getAddress(), "请上送商户地址");
        Assert.notNull(merchant.getBusinessLicenseCode(), "请上送商户营业执照编号");
        Assert.notNull(merchant.getContactName(), "请上送联系人姓名");
        Assert.notNull(merchant.getContactMobile(), "请上送联系人手机号");
        Assert.notNull(merchant.getLongitude(), "请上送坐标经度");
        Assert.notNull(merchant.getLatitude(), "请上送坐标纬度");

        merchant.setUpdateName(SessionUtils.getCurrentUser().getRealname());
        merchantService.update(merchant);
        return AjaxResult.getSuccess();
    }

    /**
     * 删除商户
     * @param id
     * @return
     */
    @PostMapping("delete")
    @RequiresPermissions("merchant:delete")
    public AjaxResult delete(Long id){
        merchantService.delete(id);
        return AjaxResult.getSuccess();
    }

    /**
     * 获取商户列表
     * @return
     */
    @PostMapping("getAll")
    public AjaxResult getAll(){
        return AjaxResult.getSuccess(merchantService.getAll());
    }

    /**
     * 返回单个商户信息
     * @param id
     * @return
     */
    @PostMapping("/getById")
    public AjaxResult getById(Long id){
        Assert.notNull(id, "请上送商户id");
        return AjaxResult.getSuccess(merchantService.getById(id));
    }

    /**
     * 获取商户图片列表
     * @param merchantId
     * @return
     */
    @PostMapping("/getMerchantImageList")
    public AjaxResult getMerchantImageList(Long merchantId){
        Assert.notNull(merchantId, "请上送商户id");
        return AjaxResult.getSuccess(merchantService.getMerchantImageList(merchantId));
    }

    /**
     * 保存商户图片
     * @param merchantImage
     * @return
     */
    @PostMapping("/saveMerchantImage")
    public AjaxResult saveMerchantImage(MerchantImage merchantImage){

        Assert.notNull(merchantImage.getMerchantId(), "请上送商户id");
        Assert.notNull(merchantImage.getUrl(), "请上送图片路径");
        merchantService.saveMerchantImage(merchantImage);
        return AjaxResult.getSuccess();
    }

    /**
     * 删除商户图片
     * @param id 商户图片id
     * @return
     */
    @PostMapping("/deleteMerchantImage")
    public AjaxResult deleteMerchantImage(Long id){

        Assert.notNull(id, "请上送商户图片id");

        merchantService.deleteMerchantImage(id);
        return AjaxResult.getSuccess();
    }

    /**
     * 更新商户图片顺序
     * @param srcId 源id
     * @param tarId 目标id
     * @param oldIndex 源顺序
     * @param newIndex 目标顺序
     */
    @PostMapping("/updateMerchantImageSeq")
    public AjaxResult updateMerchantImageSeq(Long srcId, Long tarId, Integer oldIndex, Integer newIndex){

        Assert.notNull(srcId, "请上送源图片id");
        Assert.notNull(tarId, "请上送目标图片id");
        Assert.notNull(oldIndex, "请上送源图片顺序");
        Assert.notNull(newIndex, "请上送目标图片顺序");

        merchantService.updateMerchantImageSeq(srcId, tarId, oldIndex, newIndex);
        return AjaxResult.getSuccess();
    }
}
