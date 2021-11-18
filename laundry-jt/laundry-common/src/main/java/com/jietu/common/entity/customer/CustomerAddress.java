package com.jietu.common.entity.customer;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 客户地址实体类
 * @author: 印修河
 * @date: 2018/12/26 23:52
 */
@Data
public class CustomerAddress implements Serializable {

    private static final long serialVersionUID = -911684660749380547L;
    private Long id;
    /** 客户id */
    private Long customerId;
    /** 经度 */
    private BigDecimal longitude;
    /** 纬度 */
    private BigDecimal latitude;
    /** 地址名称 */
    private String locationName;
    /** 地址 */
    private String locationAddress;
    /** 地址 */
    private String detailsAddress;
    /** 收货人姓名 */
    private String receiverName;
    /** 收货人手机号 */
    private String receiverMobile;
    /** 收货人固话 */
    private String receiverTelephone;
    /** 收货人邮箱 */
    private String receiverEmail;
    /** 是否默认地址 */
    private Boolean defaulted;

}
