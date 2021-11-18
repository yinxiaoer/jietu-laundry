package com.jietu.app.config;

import com.github.wxpay.sdk.WXPayConfig;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * 微信支付配置
 * @author: 印修河
 * @date: 2019/1/16 11:25
 */
@Configuration
public class MyWXPayConfig implements WXPayConfig {

    /** appid */
    @Value("${weixin.appid}")
    private String appID;
    /** 商户id */
    @Value("${weixin.merchantId}")
    private String mchID;
    /** 秘钥 */
    @Value("${weixin.payKey}")
    private String key;


    /** 加载证书  这里证书需要到微信商户平台进行下载*/
    private byte [] certData;

    public MyWXPayConfig() throws  Exception{
        InputStream certStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("cert/wxpay/apiclient_cert.p12");
        this.certData = IOUtils.toByteArray(certStream);
        certStream.close();
    }

    @Override
    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    @Override
    public String getMchID() {
        return mchID;
    }

    public void setMchID(String mchID) {
        this.mchID = mchID;
    }

    @Override
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public InputStream getCertStream() {
        return new ByteArrayInputStream(this.certData);
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 60000;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 60000;
    }

}
