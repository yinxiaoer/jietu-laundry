package com.jietu.common.entity.customer;

import com.jietu.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 客户实体类
 * @author: 印修河
 * @date: 2018/12/25 21:47
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Customer extends BaseEntity {

    private static final long serialVersionUID = -5224905207419368791L;
    private Long id;
    /** 用户名 */
    private String username;
    /** 昵称 */
    private String nickname;
    /** 头像 */
    private String portrait;
    /** 身份证号 */
    private String idCard;
    /** 手机号 */
    private String mobile;
    /** 年龄 */
    private Integer age;
    /** 性别 */
    private String sex;
    /** 邮箱 */
    private String email;
    /** 是否为会员 */
    private Boolean vip;
    /** 用户唯一标识 */
    private String openid;
    private String unionid;
    /** 客户余额 */
    private BigDecimal balance;

}
