package com.jietu.admin.controller.system;

import com.jietu.admin.entity.system.UserEntity;
import com.jietu.admin.utils.SessionUtils;
import com.jietu.admin.model.AjaxResult;
import com.jietu.admin.utils.MyException;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 公有控制器
 * @author: 印修河
 * @date: 2017/12/7 10:58
 */
@RestController
public class LoginController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/isLogin")
    public AjaxResult isLogin(){
        UserEntity currentUser = SessionUtils.getCurrentUser();
        if(currentUser == null){
            return new AjaxResult(HttpStatus.UNAUTHORIZED, "用户未登录");
        }
        return new AjaxResult(HttpStatus.OK, "已登录", currentUser);
    }

    @RequestMapping("/login")
    public AjaxResult login(String username, String password){
        //已经登录直接返回
        if(SessionUtils.getCurrentUser() != null){
            return new AjaxResult(HttpStatus.OK, "登录成功", SessionUtils.getCurrentUser());
        }
        if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
            throw new MyException("用户名或密码不能为空");
        }
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            SecurityUtils.getSubject().login(token);
        } catch (AuthenticationException e) {
            throw new MyException("用户名或密码错误");
        }
        UserEntity currentUser = SessionUtils.getCurrentUser();
        logger.info("用户{}_{}登录成功", currentUser.getUsername(),currentUser.getRealname());
        return new AjaxResult(HttpStatus.OK, "登录成功", currentUser);
    }

    @RequestMapping("logout")
    public AjaxResult logout(){
        UserEntity currentUser = SessionUtils.getCurrentUser();
        SecurityUtils.getSubject().logout();
        if(currentUser != null){
            logger.info("用户{}_{}退出登录成功", currentUser.getUsername(),currentUser.getRealname());
        }
        return new AjaxResult(HttpStatus.OK, "退出登录成功");
    }

    /**
     * 返回当前登录用户
     * @return
     */
    @PostMapping("getCurrentUser")
    public AjaxResult getCurrentUser(){
        return AjaxResult.getSuccess(SessionUtils.getCurrentUser());
    }

    @RequestMapping("403")
    public AjaxResult unauthorizedRole(){
        return new AjaxResult(HttpStatus.FORBIDDEN, "您还没有权限哦，请及时联系管理员处理！");
    }
}
