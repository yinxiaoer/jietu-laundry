package com.jietu.common.enums;

/**
 * 支付流水类型
 * @author: 印修河
 * @date: 2019/3/9 11:24
 */
public enum PayFlowType {

    /** 订单支付 */
    ORDER_PAY(0, "订单支付"),
    /** 充值 */
    TOP_UP(1, "充值");

    private Integer code;
    private String desc;

    PayFlowType(Integer code, String desc) {
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
