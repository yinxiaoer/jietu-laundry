package com.jietu.common.enums;

/**
 * 商户图片枚举类
 * @author: 印修河
 * @date: 2019/2/14 6:34
 */
public enum MerchantImageTypes {

    /** 商家简介图 */
    MERCHANT_INFO(0, "商家简介图"),
    /** 评价图 */
    COMMENT(1, "评价图"),
    ;

    private Integer code;
    private String desc;

    MerchantImageTypes(Integer code, String desc) {
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
