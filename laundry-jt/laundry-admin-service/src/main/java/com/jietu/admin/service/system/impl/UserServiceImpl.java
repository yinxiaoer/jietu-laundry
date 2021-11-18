package com.jietu.admin.service.system.impl;

import com.alibaba.fastjson.JSONObject;
import com.jietu.admin.service.system.IUserService;
import com.jietu.admin.entity.system.UserEntity;
import com.jietu.admin.dao.system.UserDao;
import com.jietu.admin.service.system.IUserService;
import com.jietu.admin.utils.PasswordUtil;
import com.jietu.admin.utils.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户服务接口实现类
 * @author: 印修河
 * @date: 2017/12/6 18:36
 */
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<UserEntity, Long> implements IUserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserDao userDao;

    @Override
    public UserEntity findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        UserEntity user = userDao.getById(id);
        userDao.delete(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePassword(Long userId, String password, String oldPassword) {
        Assert.notNull(userId, "请上送用户名");
        Assert.notNull(oldPassword, "请上送原密码");
        Assert.notNull(password, "请上送密码");

        UserEntity user = userDao.getById(userId);
        Assert.notNull(user, "用户不存在");
        Assert.isTrue(user.getPassword().equals(PasswordUtil.encrypting(oldPassword, user.getUsername())), "原密码错误");

        user.setPassword(PasswordUtil.encrypting(password, user.getUsername()));
        userDao.updatePassword(user);
    }

    @Override
    public void resetPassword(Long userId, String password) {
        Assert.notNull(userId, "请上送用户名");
        Assert.notNull(password, "请上送密码");
        UserEntity user = userDao.getById(userId);
        Assert.notNull(user, "用户不存在");
        user.setPassword(PasswordUtil.encrypting(password, user.getUsername()));
        userDao.updatePassword(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(Long userId, Boolean available) {
        Assert.notNull(userId, "请上送id");
        Assert.notNull(available, "请上送可用状态");
        UserEntity user = userDao.getById(userId);
        Assert.notNull(user, "用户不存在");
        if(user.getAvailable().equals(available)){
            return;
        }
        user.setAvailable(available);
        userDao.updateStatus(user);
    }

    @Override
    public void updateCurrentUser(UserEntity user) {
        UserEntity oldUser = userDao.getById(user.getId());
        Assert.notNull(oldUser, "请上送正确的userId");
        oldUser.setRealname(user.getRealname());
        oldUser.setPhone(user.getPhone());
        oldUser.setEmail(user.getEmail());
        logger.info("用户更新个人资料为：{}", JSONObject.toJSONString(oldUser));
        userDao.update(oldUser);
    }

    @Override
    public void updateOpenid(Long id, String openid) {
        userDao.updateOpenid(id, openid);
    }
}
