package com.jietu.common.entity.system;

import com.jietu.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 系统意见反馈实体类
 * @author: 印修河
 * @date: 2019/2/17 20:06
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SystemFeedback extends BaseEntity {

    private static final long serialVersionUID = 9179991585288681238L;
    private Long id;
    /** 用户id */
    private Long customerId;
    /** 类型id */
    private String typeId;
    /** 意见反馈内容 */
    private String content;
    /** 意见反馈图片列表 */
    private List<SystemFeedbackImage> imageList;

}
