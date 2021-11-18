package com.jietu.app.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.jietu.app.controller.BaseController;
import com.jietu.app.service.system.SystemFeedbackService;
import com.jietu.app.utils.Assert;
import com.jietu.app.utils.Result;
import com.jietu.common.entity.system.SystemFeedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 意见反馈控制器
 * @author: 印修河
 * @date: 2019/2/17 20:46
 */
@RestController
@RequestMapping("/feedback")
public class SystemFeedbackController extends BaseController {

    @Autowired
    private SystemFeedbackService systemFeedbackService;

    /**
     * 获取意见反馈类型
     * @return
     */
    @PostMapping("/getFeedbackType")
    public Result getFeedbackType(){
        JSONObject result = new JSONObject();
        result.put("FeedbackTypeList", systemFeedbackService.getFeedbackType());
        return successResponse(result);
    }

    /**
     * 保存意见反馈
     * @param feedback
     */
    @PostMapping("/save")
    public Result save(@RequestBody SystemFeedback feedback){

        Assert.notNull(feedback.getTypeId(), "请选择意见反馈类型");
        Assert.notNull(feedback.getContent(), "请输入意见反馈内容");
        systemFeedbackService.save(feedback);

        return successResponse();
    }
}
