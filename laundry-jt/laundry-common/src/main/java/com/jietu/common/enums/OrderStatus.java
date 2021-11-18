package com.jietu.common.enums;

/**
 * 订单枚举类
 * @author: 印修河
 * @date: 2019/1/10 22:50
 */
public enum OrderStatus {

    //=================订单状态================
    /** 待付款 */
    WAIT_PAY(0, "待付款"),
    /** 订单已完成 */
    SUCCESS(1, "订单已完成"),
    /** 待评价 */
    WAIT_EVALUATE(2, "待评价"),
    /** 订单取消 */
    ORDER_CANCEL(3, "订单已取消"),
    /** 订单已失效 */
    ORDER_TIME_OUT(4, "订单已失效"),
    //=================配送及洗涤状态================
    /** 待取货 */
    WAIT_CLAIM_GOODS(101, "待取货"),
    /** 待取货 */
    SEND_MERCHANTING(102, "配送到商家中"),
    /** 洗涤中 */
    WASHING(103, "洗涤中"),
    /** 等待配送到用户 */
    WAIT_SEND_CUSTOMER(104, "等待配送到用户"),
    /** 配送到用户中 */
    SEND_CUSTOMERING(105, "配送到用户中"),
    //=================退款状态================
    /** 退款中 */
    REFUNDING(201, "退款中"),
    /** 退款完成 */
    REFUND_END(202, "退款完成"),
    ;


    OrderStatus(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    private Integer status;
    private String desc;

    public Integer getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
}
