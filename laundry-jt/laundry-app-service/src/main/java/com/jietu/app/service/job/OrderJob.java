package com.jietu.app.service.job;

import com.jietu.app.dao.order.OrderDao;
import com.jietu.app.utils.Constants;
import com.jietu.common.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 订单任务
 * @author: 印修河
 * @date: 2019/3/5 22:59
 */
@Slf4j
@Component
public class OrderJob {

    @Autowired
    private RedisService redisService;
    @Autowired
    private OrderDao orderDao;

    /**
     * 日终刷新编号
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void dayEndRestSnJob(){
        log.info("开始日终刷新编号任务");
        //每天零点将订单编号设置为0
        redisService.set(Constants.ORDER_SN_KEY, 0);
        log.info("结束日终刷新编号任务");
    }

    /**
     * 更新订单超时任务
     */
    @Scheduled(cron = "0 * * * * ?")
    public void orderTimeOutJob(){
        log.info("更新订单超时任务");
        Integer num = orderDao.updateTimeOutOrder();
        log.info("结束更新订单超时任务,更新失效订单数：{}", num);
    }
}
