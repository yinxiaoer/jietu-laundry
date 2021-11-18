package com.jietu.common.enums;

/**
 * 支付流水状态
 * @author: 印修河
 * @date: 2019/3/10 21:05
 */
public enum PayFlowStatus {

    /** 待支付 */
    WAIT_PAY(0, "待支付"),
    /** 支付完成 */
    PAY_SUCCESS(1, "支付成功"),
    /** 支付失败 */
    PAY_FAIL(2, "支付失败"),
    /** 支付超时失效 */
    PAY_TIME_OUT(3, "支付超时失效");

    private Integer code;
    private String desc;

    PayFlowStatus(Integer code, String desc) {
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
