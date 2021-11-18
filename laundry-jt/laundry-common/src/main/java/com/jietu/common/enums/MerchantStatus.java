package com.jietu.common.enums;

/**
 * 商户状态枚举类
 * @author: 印修河
 * @date: 2019/2/11 15:46
 */
public enum MerchantStatus {

    /** 禁用 */
    DISABLE(0, "禁用"),
    /** 营业中 */
    BUSINESSING(1, "营业中"),
    /** 打烊 */
    CLOSED(2, "打烊"),
    ;

    private Integer code;
    private String desc;

    MerchantStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
