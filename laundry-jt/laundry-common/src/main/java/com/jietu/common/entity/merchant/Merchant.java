package com.jietu.common.entity.merchant;

import com.jietu.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 商户实体类
 * @author: 印修河
 * @date: 2018/12/20 20:52
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Merchant extends BaseEntity {

    private static final long serialVersionUID = 3510843653338431704L;
    private Long id;
    /** 商户名称 */
    private String name;
    /** 商户状态，0=禁用，1=营业中，2=打烊 */
    private Integer status;
    /** 商户简介 */
    private String info;
    /** 商户地址 */
    private String address;
    /** 商户营业执照编号 */
    private String businessLicenseCode;
    /** 联系人名称 */
    private String contactName;
    /** 联系人手机号 */
    private String contactMobile;
    /** 联系人邮箱 */
    private String contactMail;
    /** 用户id */
    private Long userId;
    /** 经度 */
    private BigDecimal longitude;
    /** 纬度 */
    private BigDecimal latitude;
    /** logo */
    private String logo;
    /** 月销量 */
    private Long monthSales;
    /** 总销量 */
    private Long totalSales;
    /** 评价分数 */
    private Double grade;
    /** 服务范围，单位米 */
    private Long scopeOfServices;
    /** 营业开始时间 格式HH:mm*/
    private String businessHoursStart;
    /** 营业结束时间 格式HH:mm*/
    private String businessHoursEnd;

}
