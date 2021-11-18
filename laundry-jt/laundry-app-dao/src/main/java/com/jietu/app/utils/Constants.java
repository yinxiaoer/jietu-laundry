package com.jietu.app.utils;

/**
 * 常量类
 * @author: 印修河
 * @date: 2019/2/28 17:48
 */
public class Constants {

    /** 微信accesstoken redis key 值 */
    public static final String WEIXIN_ACCESSTOKEN_CACHE_KEY = "WEIXIN_ACCESSTOKEN";

    /** 微信accesstoken redis key 值 */
    public static final String WEIXIN_SESSION_KEY_PRE = "WEIXIN_SESSION_KEY_";
    /** 客户默认头像路径 */
    public static final String CUSTOMER_DEFAULT_PORTRAIT_PATH = "/static/customer/defaultPortrait.jpg";
    /** 充值规则 */
    public static final String TOP_UP_RULE = "TOP_UP_RULE";
    /** 充值列表typeId */
    public static final String TOP_UP_LIST = "TOP_UP_LIST";

    public static final String ORDER_SN_KEY = "ORDER_SN";

    /** 手机号正则表达式 */
    public static final String MOBILE_NO_REG = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";


}
