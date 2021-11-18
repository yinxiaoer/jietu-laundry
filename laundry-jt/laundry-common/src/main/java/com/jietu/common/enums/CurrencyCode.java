package com.jietu.common.enums;

/**
 * 货币类型枚举类
 * @author: 印修河
 * @date: 2019/2/11 20:54
 */
public enum CurrencyCode {

    /** 人民币 */
    CNY("CNY", "人民币");

    /** 码值 */
    private String code;
    /** 描述 */
    private String desc;

    CurrencyCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
