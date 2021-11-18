package com.jietu.common.enums;

/**
 * 微信相应状态code
 * @author: 印修河
 * @date: 2019/2/28 10:41
 */
public enum WXResponseCode {

    /** 成功 */
    SUCCESS("SUCCESS", "成功"),
    /** 失败 */
    FAIL("FAIL", "失败");

    private String code;
    private String desc;

    WXResponseCode(String code, String desc) {
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
