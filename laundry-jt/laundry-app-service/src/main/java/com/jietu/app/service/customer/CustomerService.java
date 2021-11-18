package com.jietu.app.service.customer;

import com.alibaba.fastjson.JSONObject;
import com.jietu.app.dao.customer.CustomerDao;
import com.jietu.app.dao.customer.CustomerVipCardDao;
import com.jietu.app.utils.Constants;
import com.jietu.app.utils.CustomerUtils;
import com.jietu.app.utils.MyException;
import com.jietu.app.utils.WeixinUtil;
import com.jietu.common.entity.customer.Customer;
import com.jietu.common.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * 客户service
 *
 * @author: 印修河
 * @date: 2018/12/25 21:27
 */
@Slf4j
@Service
public class CustomerService {

    @Autowired
    private RedisService redisService;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private CustomerVipCardDao customerVipCardDao;
    @Value("${weixin.appid}")
    private String appid;
    @Value("${weixin.appsecret}")
    private String appsecret;

    /**
     * 客户小程序授权
     *
     * @param code 登陆code
     * @return token
     */
    public String authorization(String code) {
        JSONObject authJson = WeixinUtil.authorization(appid, appsecret, code);
        //用户唯一标识
        String openid = authJson.getString("openid");
        String unionid = authJson.getString("unionid");
        //会话密钥
        String sessionKey = authJson.getString("session_key");

        Customer customer = customerDao.findByOpenid(openid);
        if (customer == null) {
            //如果客户为空则获取客户信息插入
            customer = new Customer();
            customer.setOpenid(openid);
            customer.setUnionid(unionid);
            customer.setNickname("洁兔会员_" + RandomStringUtils.randomNumeric(5));
            customer.setPortrait(Constants.CUSTOMER_DEFAULT_PORTRAIT_PATH);
            customerDao.save(customer);
        }
        String oldToken = (String) redisService.get(openid);
        //如果用户在缓存中存在，则获取原来的token直接返回
        if(StringUtils.isNotBlank(oldToken)){
            redisService.set(Constants.WEIXIN_SESSION_KEY_PRE + oldToken, sessionKey);
            return oldToken;
        }
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        //将对应信息放入缓存
        redisService.set(token, customer);
        redisService.set(openid, token);
        redisService.set(Constants.WEIXIN_SESSION_KEY_PRE + token, sessionKey);
        return token;
    }

    /**
     * 获取用户信息
     * @return
     */
    public Customer getUserInfo(){
        return CustomerUtils.getCurrentCustomer();
    }

    /**
     * 更新客户信息
     * @param customer
     * @return
     */
    public Customer updateCustomerInfo(Customer customer){
        String token = MDC.get("token");

        Customer oldCustomer = CustomerUtils.getCurrentCustomer();
        customer.setId(oldCustomer.getId());
        customerDao.update(customer);

        Customer cst = customerDao.getById(oldCustomer.getId());
        redisService.set(token, cst);
        return cst;
    }

    /**
     * 保存微信手机号
     * @param encryptedData
     * @param iv
     */
    public void saveWeixinPhone(String encryptedData, String iv){
        String token = MDC.get("token");
        Customer oldCustomer = CustomerUtils.getCurrentCustomer();
        if(StringUtils.isNotBlank(oldCustomer.getUsername())){
            return ;
        }
        String sessionKey = (String) redisService.get(Constants.WEIXIN_SESSION_KEY_PRE + token);
        String decrypt = WeixinUtil.decrypt(appid, encryptedData, sessionKey, iv);
        log.info("用户微信手机绑定数据解密报文：{}", decrypt);
        if(StringUtils.isBlank(decrypt)){
            throw new MyException("微信解密失败");
        }
        JSONObject userPhone = JSONObject.parseObject(decrypt);
        String phoneNumber = userPhone.getString("phoneNumber");

        Customer customer = new Customer();
        customer.setId(oldCustomer.getId());
        customer.setMobile(phoneNumber);
        customer.setUsername(phoneNumber);
        customerDao.update(customer);

        customer = customerDao.getById(oldCustomer.getId());
        redisService.set(token, customer);
    }

    /**
     * 刷新客户信息缓存
     * @param customerId
     */
    public void refreshCustomerInfoCache(Long customerId){
        //更新客户信息
        try {
            Customer customer = customerDao.getById(customerId);
            BigDecimal totalBalance = customerVipCardDao.countTotalBalance(customerId);
            customer.setBalance(totalBalance);
            String token = (String) redisService.get(customer.getOpenid());
            redisService.set(token, customer);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
