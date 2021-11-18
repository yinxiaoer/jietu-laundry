package com.jietu.admin.controller.system;


import com.jietu.admin.service.system.IUserService;
import com.jietu.admin.entity.system.UserEntity;
import com.jietu.admin.service.system.IUserService;
import com.jietu.admin.utils.PasswordUtil;
import com.jietu.admin.utils.SessionUtils;
import com.jietu.admin.model.AjaxResult;
import com.jietu.admin.utils.Assert;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 用户控制器
 * @author: 印修河
 * @date: 2017/12/6 18:41
 */
@RestController
@RequestMapping("user")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    /**
     * 返回用户列表
     * @param user
     * @return
     */
    @PostMapping("list")
    @RequiresPermissions("user:list")
    public AjaxResult list(UserEntity user){
        return new AjaxResult(HttpStatus.OK, "查询成功", userService.findByQuery(user));
    }

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    @PostMapping("findByUsername")
    public AjaxResult findByUsername(String username){
        return new AjaxResult(HttpStatus.OK, "查询成功", userService.findByUsername(username));
    }

    /**
     * 保存用户
     * @param user
     * @return
     */
    @PostMapping("save")
    @RequiresPermissions("user:save")
    public AjaxResult save(UserEntity user){

        Assert.notNull(user.getUsername(), "请上送用户名");
        Assert.notNull(user.getPassword(), "请上送密码");
        Assert.notNull(user.getRealname(), "请上送真实名称");
        Assert.notNull(user.getRoleId(), "请上送角色id");
        //判断用户名是否存在
        UserEntity oldUser = userService.findByUsername(user.getUsername());
        Assert.isNull(oldUser, "用户名已存在");

        user.setCreateName(SessionUtils.getCurrentUser().getRealname());
        user.setCreateDate(new Date());
        //对密码加密
        user.setPassword(PasswordUtil.encrypting(user.getPassword(), user.getUsername()));
        user.setAvailable(true);
        userService.insert(user);
        return new AjaxResult(HttpStatus.OK,"添加用户成功");
    }

    /**
     * 更新用户
     * @param user
     * @return
     */
    @PostMapping("update")
    @RequiresPermissions("user:update")
    public AjaxResult update(UserEntity user){

        Assert.notNull(user.getId(), "请上送用户id");
        Assert.notNull(user.getRealname(), "请上送真实名称");
        Assert.notNull(user.getRoleId(), "请上送角色id");

        user.setUpdateName(SessionUtils.getCurrentUser().getRealname());
        user.setUpdateDate(new Date());
        userService.update(user);
        return new AjaxResult(HttpStatus.OK,"更新用户成功");
    }

    /**
     * 删除单个用户
     * @param id
     * @return
     */
    @PostMapping("delete")
    @RequiresPermissions("user:delete")
    public AjaxResult delete(Long id){
        userService.delete(id);
        return new AjaxResult(HttpStatus.OK,"删除用户成功");
    }


    /**
     * 修改密码
     * @param userId  用户id
     * @param password 新密码
     * @param oldPassword 原密码
     * @return
     */
    @PostMapping("updatePassword")
    public AjaxResult updatePassword(Long userId, String password, String oldPassword){
        userService.updatePassword(userId, password, oldPassword);
        return new AjaxResult(HttpStatus.OK,"修改密码成功");
    }

    /**
     * 重置密码
     * @param userId  用户id
     * @param password 新密码
     * @return
     */
    @PostMapping("resetPassword")
    @RequiresPermissions("user:resetPassword")
    public AjaxResult resetPassword(Long userId, String password){
        userService.resetPassword(userId, password);
        return new AjaxResult(HttpStatus.OK,"修改密码成功");
    }

    /**
     * 改变是否可用状态
     * @param userId
     * @param available
     * @return
     */
    @PostMapping("updateAvailable")
    @RequiresPermissions("user:updateAvailable")
    public AjaxResult updateAvailable(Long userId, Boolean available){
        userService.updateStatus(userId, available);
        return new AjaxResult(HttpStatus.OK,"更新状态成功");
    }

    /**
     * 更新个人资料
     * @param user
     * @return
     */
    @PostMapping("updateCurrentUser")
    public AjaxResult updateCurrentUser(UserEntity user){
        Assert.notNull(user.getId(), "请上送用户id");
        userService.updateCurrentUser(user);
        return new AjaxResult(HttpStatus.OK,"更新成功");
    }
}
