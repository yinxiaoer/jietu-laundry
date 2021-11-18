package com.jietu.app.service.system;

import com.jietu.app.dao.system.SystemFeedbackDao;
import com.jietu.app.dao.system.SystemParameterDao;
import com.jietu.app.utils.CustomerUtils;
import com.jietu.common.entity.customer.Customer;
import com.jietu.common.entity.system.SystemFeedback;
import com.jietu.common.entity.system.SystemParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 意见反馈服务类
 * @author: 印修河
 * @date: 2019/2/17 20:37
 */
@Service
public class SystemFeedbackService {

    @Autowired
    private SystemParameterDao systemParameterDao;
    @Autowired
    private SystemFeedbackDao systemFeedbackDao;
    /** 意见反馈类型参数key */
    private static final String FEEDBACK_TYPE_KEY = "FEEDBACK_TYPE";

    /**
     * 获取意见反馈类型
     * @return
     */
    public List<SystemParameter> getFeedbackType(){
        return systemParameterDao.findByType(FEEDBACK_TYPE_KEY);
    }

    /**
     * 保存意见反馈
     * @param feedback
     */
    @Transactional(rollbackFor = Exception.class)
    public void save(SystemFeedback feedback){
        Customer customer = CustomerUtils.getCurrentCustomer();
        feedback.setCustomerId(customer.getId());
        systemFeedbackDao.save(feedback);
        if(feedback.getImageList() != null){
            feedback.getImageList().forEach(img -> {
                img.setFeedbackId(feedback.getId());
                systemFeedbackDao.saveFeedbackImage(img);
            });
        }

    }
}
