package com.jietu.admin.entity.system;


import com.jietu.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户实体类
 * @author: 印修河
 * @date: 2017/12/5 19:24
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserEntity extends BaseEntity {

    private Long id;
    /** 用户名 */
    private String username;
    /** 真实名 */
    private String realname;
    /** 密码 */
    private String password;
    /** 联系电话 */
    private String phone;
    /** 邮箱 */
    private String email;
    /** 是否可用 */
    private Boolean available;
    /** 角色id */
    private String roleId;

    private String openid;
}
