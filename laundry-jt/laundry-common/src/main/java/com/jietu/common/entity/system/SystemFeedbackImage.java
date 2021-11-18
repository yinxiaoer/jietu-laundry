package com.jietu.common.entity.system;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 意见反馈图片
 * @author: 印修河
 * @date: 2019/2/17 20:17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemFeedbackImage implements Serializable {

    private static final long serialVersionUID = -3995827674723431950L;
    private Long id;
    /** 意见反馈id */
    private Long feedbackId;
    /** 排序 */
    private Integer sortOrder;
    /** url */
    private String url;

}
