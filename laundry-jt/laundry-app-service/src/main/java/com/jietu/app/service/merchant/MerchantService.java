package com.jietu.app.service.merchant;

import com.alibaba.fastjson.JSONObject;
import com.jietu.app.dao.merchant.MerchantDao;
import com.jietu.app.dao.merchant.MerchantImageDao;
import com.jietu.app.dao.order.OrderCommentDao;
import com.jietu.app.dto.merchant.DistributionTimeResponse;
import com.jietu.app.entity.merchant.MerchantDistance;
import com.jietu.app.service.customer.CustomerFootprintService;
import com.jietu.app.utils.Assert;
import com.jietu.app.utils.CustomerUtils;
import com.jietu.common.entity.merchant.Merchant;
import com.jietu.common.entity.merchant.MerchantImage;
import com.jietu.common.enums.MerchantImageTypes;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 商户服务类
 * @author: 印修河
 * @date: 2019/1/6 10:27
 */
@Slf4j
@Service
public class MerchantService {

    @Autowired
    private MerchantDao merchantDao;
    @Autowired
    private MerchantImageDao merchantImageDao;
    @Autowired
    private CustomerFootprintService customerFootprintService;
    @Autowired
    private OrderCommentDao orderCommentDao;

    /**
     * 查询周围商户列表
     * @param merchant
     * @return
     */
    public List<MerchantDistance> findCircumMerchant(Merchant merchant){
        return merchantDao.findCircumMerchant(merchant);
    }

    /**
     * 获取商户详情
     * @param merchantId
     * @return
     */
    public MerchantDistance getMerchantDetails(Long merchantId, BigDecimal longitude, BigDecimal latitude){
        MerchantDistance merchantDetails = merchantDao.getMerchantDetails(merchantId, longitude, latitude);
        Assert.notNull(merchantDetails, "请选择正确的商户");
        //异步保存客户足迹
        customerFootprintService.save(CustomerUtils.getCurrentCustomer().getId(), merchantId);
        return merchantDetails;
    }

    /**
     * 获取商户图片列表
     * @param merchantId 商户id
     * @return
     */
    public List<MerchantImage> getMerchantInfoImages(Long merchantId){
        return merchantImageDao.getMerchantInfoImages(
                MerchantImage.builder()
                        .merchantId(merchantId)
                        .types(MerchantImageTypes.MERCHANT_INFO.getCode())
                        .build());
    }

    /**
     * 获取配送时间列表
     * @param merchantId
     * @return
     */
    public List<DistributionTimeResponse> getDistributionTimeList(Long merchantId) {
        Merchant merchant = merchantDao.getById(merchantId);
        Assert.notNull(merchant, "请选择正确的商户");
        List<DistributionTimeResponse> responseList = new ArrayList<>();

        String businessHoursStart = merchant.getBusinessHoursStart();
        String businessHoursEnd = merchant.getBusinessHoursEnd();
        Date startTime = null;
        Date endTime = null;
        try {
            startTime = DateUtils.parseDate(businessHoursStart, "HH:mm");
            endTime = DateUtils.parseDate(businessHoursEnd, "HH:mm");
        } catch (Exception e) {
            log.error("解析商户营业时间失败" + e.getMessage(), e);
            try {
                //解析失败设置默认时间
                startTime = DateUtils.parseDate("08:00", "HH:mm");
                endTime = DateUtils.parseDate("18:00", "HH:mm");
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        }

        Date tempDate = startTime;
        while (true) {
            if(tempDate.after(endTime)){
                String start = DateFormatUtils.format(tempDate, "HH:mm");
                String end = DateFormatUtils.format(endTime, "HH:mm");
                DistributionTimeResponse response = DistributionTimeResponse.builder()
                        .label(start + "-" + end)
                        .value(start + "-" + end)
                        .start(start)
                        .end(end)
                        .money("0元配送费")
                        .build();
                responseList.add(response);
                break;
            }
            Date nextDate = DateUtils.addHours(tempDate, 2);
            String start = DateFormatUtils.format(tempDate, "HH:mm");
            String end = DateFormatUtils.format(nextDate, "HH:mm");
            DistributionTimeResponse response = DistributionTimeResponse.builder()
                    .label(start + "-" + end)
                    .value(start + "-" + end)
                    .start(start)
                    .end(end)
                    .money("0元配送费")
                    .build();
            responseList.add(response);
            tempDate = nextDate;

            if(tempDate.equals(endTime)){
                break;
            }
        }
        return responseList;
    }

    /**
     * 获取评价统计
     * @param merchantId
     * @return
     */
    public Map<String, Object> getCommentCount(Long merchantId){
        return orderCommentDao.getMerchantCommentCount(merchantId);
    }
}
