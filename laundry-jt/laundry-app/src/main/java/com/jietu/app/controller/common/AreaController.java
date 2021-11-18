package com.jietu.app.controller.common;

import com.alibaba.fastjson.JSONObject;
import com.jietu.app.controller.BaseController;
import com.jietu.app.service.common.AreaService;
import com.jietu.app.utils.Assert;
import com.jietu.app.utils.Result;
import com.jietu.app.controller.BaseController;
import com.jietu.app.entity.common.Area;
import com.jietu.app.service.common.AreaService;
import com.jietu.app.utils.Assert;
import com.jietu.app.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 地区控制器
 * @author: 印修河
 * @date: 2018/12/28 21:59
 */
@RestController
@RequestMapping("/area")
public class AreaController extends BaseController {

    @Autowired
    private AreaService areaService;

    /**
     * 获取省份列表
     * @return
     */
    @PostMapping("/getProvince")
    public Result getProvince(){
        JSONObject resultJson = new JSONObject();
        resultJson.put("area", areaService.getProvince());
        return successResponse(resultJson);
    }

    /**
     * 通过父地区查询子地区列表
     * @param request
     * @return
     */
    @PostMapping("/getByParent")
    public Result getByParent(@RequestBody JSONObject request){
        String parent = request.getString("parent");
        Assert.notNull(parent, "请上送parent");
        JSONObject resultJson = new JSONObject();
        resultJson.put("area", areaService.findByParent(parent));
        return successResponse(resultJson);
    }
}
