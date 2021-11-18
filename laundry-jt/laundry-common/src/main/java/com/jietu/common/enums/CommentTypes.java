package com.jietu.common.enums;

/**
 * 评价类型
 * @author: 印修河
 * @date: 2019/3/5 14:33
 */
public enum CommentTypes {

    /** 好评 */
    GOOD_COMMENT(0, "好评"),
    /** 好评 */
    NEGATIVE_COMMENT(1, "差评"),
    /** 好评 */
    IMAGE_COMMENT(2, "有图评价"),
    ;

    private Integer code;
    private String desc;

    CommentTypes(Integer code, String desc) {
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
