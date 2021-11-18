package com.jietu.app.service.job;

import com.jietu.app.utils.Constants;
import com.jietu.common.service.RedisService;
import com.jietu.app.utils.WeixinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时更新微信token
 * @author: 印修河
 * @date: 2018/4/9 16:41
 */
@Component
public class RefreshTokenJob {

    private static final Logger logger = LoggerFactory.getLogger(RefreshTokenJob.class);
    
    @Value("${weixin.appid}")
    private String appid;
    @Value("${weixin.appsecret}")
    private String secret;
    @Autowired
    private RedisService redisService;

    /**
     * accessToken两个小时更新，则我们必须要在两个小时之类刷新，保证accessToken不失效
     * 心跳更新。启动时执行一次，之后每隔60分钟秒执行一次
     */
    //@Scheduled(fixedRate = 1000 * 60 * 60)
    public void refresh() {
        logger.info("===开始执行刷新微信accessToken任务===");
        String accessToken = WeixinUtil.getAccessToken(appid, secret);
        logger.info("获取微信accessToken={} ", accessToken);
        redisService.set(Constants.WEIXIN_ACCESSTOKEN_CACHE_KEY, accessToken);
        logger.info("===执行刷新微信accessToken任务结束===");
    }
}
