package com.jietu.admin.dao.system;

import com.jietu.admin.entity.system.UserEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 *  用户Dao操作类
 * @author 印修河
 * @date 2017年9月12日 下午5:24:43
 */
public interface UserDao extends BaseDao<UserEntity, Long> {

	/**
	 * 根据用户名查询用户
	 * @param username
	 * @return
	 */
	UserEntity findByUsername(@Param("username") String username);

	/**
	 *  重置用户密码
	 * @param user
	 */
	void updatePassword(UserEntity user);

	/**
	 *  切换可用用户状态
	 * @param user
	 */
	void updateStatus(UserEntity user);

	/**
	 * 查询角色用户列表
	 * @param roleId 角色id
	 * @return
	 */
	List<UserEntity> findByRoleId(@Param("roleId") String roleId);

	/**
	 * 更新openid
	 * @param id
	 * @param openid
	 */
	void updateOpenid(@Param("id") Long id, @Param("openid") String openid);

}
