package com.jietu.app.service.merchant;

import com.jietu.app.dao.merchant.MerchantMonthSalesDao;
import com.jietu.common.entity.merchant.MerchantMonthSales;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 商户月销量服务类
 * @author: 印修河
 * @date: 2019/2/8 21:11
 */
@Service
public class MerchantMonthSalesService {

    @Autowired
    private MerchantMonthSalesDao merchantMonthSalesDao;

    /**
     * 增加商户当前月份月销量记录
     * @param merchantId 商户id
     */
    public void save(Long merchantId){
        //得到当前月份
        String date = DateFormatUtils.format(new Date(), "yyyyMM");
        MerchantMonthSales monthSales = MerchantMonthSales
                .builder()
                .merchantId(merchantId)
                .date(date)
                .sales(0L)
                .build();
        merchantMonthSalesDao.insert(monthSales);
    }

    /**
     * 添加商户销量记录
     * @param merchantId 商户id
     */
    @Async
    public void addSales(Long merchantId){
        //得到当前月份
        String date = DateFormatUtils.format(new Date(), "yyyyMM");

        //判断月销量记录是否存在，如果不存在则插入
        MerchantMonthSales merchantMonthSales = merchantMonthSalesDao.findByMerchantIdAndDate(merchantId, date);
        if(merchantMonthSales == null){
            this.save(merchantId);
        } else {
            merchantMonthSalesDao.addSales(merchantId, date);
        }
    }
}
