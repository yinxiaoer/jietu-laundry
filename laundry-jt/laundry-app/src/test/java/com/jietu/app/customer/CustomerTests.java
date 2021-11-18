package com.jietu.app.customer;

import com.jietu.app.utils.SpringUtils;
import com.jietu.common.service.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerTests {

    @Test
    public void getCurrentCustomerTest(){
        RedisService redisService = SpringUtils.getBean(RedisService.class);
    }

}
