package com.jietu.app.dao.system;

import com.jietu.common.entity.system.SystemFeedback;
import com.jietu.common.entity.system.SystemFeedbackImage;

/**
 * 意见反馈dao
 * @author: 印修河
 * @date: 2019/2/17 20:57
 */
public interface SystemFeedbackDao {

    /**
     * 保存意见反馈
     * @param feedback
     * @return
     */
    Integer save(SystemFeedback feedback);

    /**
     * 保存意见反馈图片
     * @param systemFeedbackImage
     * @return
     */
    Integer saveFeedbackImage(SystemFeedbackImage systemFeedbackImage);

}
