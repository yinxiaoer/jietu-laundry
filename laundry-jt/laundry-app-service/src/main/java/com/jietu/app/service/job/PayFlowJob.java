package com.jietu.app.service.job;

import com.jietu.app.dao.pay.PayFlowDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 支付流水任务
 * @author: 印修河
 * @date: 2019/3/17 17:43
 */
@Slf4j
@Component
public class PayFlowJob {

    @Autowired
    private PayFlowDao payFlowDao;

    /**
     * 更新支付流水超时任务
     */
    @Scheduled(cron = "0 * * * * ?")
    public void orderTimeOutJob(){
        log.info("更新支付流水超时任务");
        Integer num = payFlowDao.updateTimeOutFlow();
        log.info("结束更新支付流水超时任务，更新失效支付流水数:{}", num);
    }

}
