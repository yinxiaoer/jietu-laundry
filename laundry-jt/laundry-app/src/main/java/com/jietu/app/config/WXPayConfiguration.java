package com.jietu.app.config;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class WXPayConfiguration {

    @Value("${spring.profiles.active}")
    private String profile;

    @Bean
    public WXPay wxPay(MyWXPayConfig myWXPayConfig) {
        return new WXPay(myWXPayConfig, WXPayConstants.SignType.MD5);
    }

}
