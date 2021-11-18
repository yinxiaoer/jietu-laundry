package com.jietu.admin.controller.order;

import com.jietu.admin.model.AjaxResult;
import com.jietu.admin.service.merchant.IMerchantService;
import com.jietu.admin.service.order.IOrderSerivce;
import com.jietu.admin.utils.Assert;
import com.jietu.admin.utils.Constants;
import com.jietu.admin.utils.SessionUtils;
import com.jietu.common.entity.order.Order;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单控制器
 * @author: 印修河
 * @date: 2019/1/18 19:29
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IOrderSerivce orderSerivce;
    @Autowired
    private IMerchantService merchantService;

    /**
     * 查询订单列表
     * @param order
     * @return
     */
    @RequestMapping("list")
    @RequiresPermissions("order:list")
    public AjaxResult list(Order order){
        return AjaxResult.getSuccess(orderSerivce.findByQuery(order));
    }

    /**
     * 通过id查询订单详情
     * @param id
     * @return
     */
    @RequestMapping("getById")
    public AjaxResult getById(Long id){
        return AjaxResult.getSuccess(orderSerivce.getById(id));
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
     * 开始配送到商家
     * @param id
     * @return
     */
    @PostMapping("/startSendMerchant")
    @RequiresPermissions("order:startSendMerchant")
    public AjaxResult startSendMerchant(Long id){
        Assert.notNull(id, "请选择订单");
        orderSerivce.startSendMerchant(id);
        return AjaxResult.getSuccess();
    }

    /**
     * 商家确认收货洗涤开始
     * @param id
     * @return
     */
    @PostMapping("/washingStart")
    @RequiresPermissions("order:washingStart")
    public AjaxResult washingStart(Long id){
        Assert.notNull(id, "请选择订单");
        orderSerivce.washingStart(id);
        return AjaxResult.getSuccess();
    }
    /**
     * 商家确认洗涤完毕
     * @param id
     * @return
     */
    @PostMapping("/washingEnd")
    @RequiresPermissions("order:washingEnd")
    public AjaxResult washingEnd(Long id){
        Assert.notNull(id, "请选择订单");
        orderSerivce.washingEnd(id);
        return AjaxResult.getSuccess();
    }
    /**
     * 配送到用户开始
     * @param id
     * @return
     */
    @PostMapping("/sendCustomerStart")
    @RequiresPermissions("order:sendCustomerStart")
    public AjaxResult sendCustomerStart(Long id){
        Assert.notNull(id, "请选择订单");
        orderSerivce.sendCustomerStart(id);
        return AjaxResult.getSuccess();
    }
    /**
     * 配送到用户结束
     * @param id
     * @return
     */
    @PostMapping("/sendCustomerEnd")
    @RequiresPermissions("order:sendCustomerEnd")
    public AjaxResult sendCustomerEnd(Long id){
        Assert.notNull(id, "请选择订单");
        orderSerivce.sendCustomerEnd(id);
        return AjaxResult.getSuccess();
    }

    /**
     * 获取订单评价
     * @param orderId
     * @return
     */
    @PostMapping("/getOrderComment")
    public AjaxResult getOrderComment(Long orderId){
        Assert.notNull(orderId, "请选择订单");
        return AjaxResult.getSuccess(orderSerivce.getOrderComment(orderId));
    }

    /**
     * 评价回复
     * @param orderCommentId 订单评价id
     * @param replyContent 回复内容
     * @return
     */
    @PostMapping("/commentReply")
    public AjaxResult commentReply(Long orderCommentId, String replyContent){
        Assert.notNull(orderCommentId, "请上送评价id");
        Assert.notNull(replyContent, "请输入回复内容");

        orderSerivce.commentReply(orderCommentId, replyContent);

        return AjaxResult.getSuccess();
    }
}
