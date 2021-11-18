package com.jietu.common.enums;

/**
 * 支付类型
 * @author: 印修河
 * @date: 2019/1/29 5:52
 */
public enum PayType {

    /** 余额支付 */
    BALANCE_PAY(0, "余额支付"),
    /** 微信支付 */
    WX_PAY(1, "微信支付");

    private Integer code;
    private String desc;

    PayType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static PayType valueOfCode(Integer code){
        PayType[] values = PayType.values();
        for(PayType payType : values){
            if(payType.getCode().equals(code)){
                return payType;
            }
        }
        return null;
    }
}
