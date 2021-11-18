package com.jietu.admin.service.system;

import com.jietu.admin.entity.system.UserEntity;

/**
 * 用户服务接口类
 * @author: 印修河
 * @date: 2017/12/6 18:32
 */
public interface IUserService extends IBaseService<UserEntity, Long> {

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    UserEntity findByUsername(String username);

    /**
     * 更新用户密码
     * @param userId
     * @param password
     * @param oldPassword
     */
    void updatePassword(Long userId, String password, String oldPassword);

    /**
     * 重置用户密码
     * @param userId
     * @param password
     */
    void resetPassword(Long userId, String password);

    /**
     * 切换可用用户状态
     * @param userId
     * @param available
     */
    void updateStatus(Long userId, Boolean available);

    /**
     * 更新个人资料
     * @param user
     */
    void updateCurrentUser(UserEntity user);

    void updateOpenid(Long id, String openid);
}
