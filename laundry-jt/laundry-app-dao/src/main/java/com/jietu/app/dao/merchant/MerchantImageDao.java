package com.jietu.app.dao.merchant;

import com.jietu.common.entity.merchant.MerchantImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商户图片到dao
 * @author: 印修河
 * @date: 2019/2/14 6:31
 */
public interface MerchantImageDao {

    /**
     * 保存商户图片
     * @param merchantImage
     * @return
     */
    Integer save(MerchantImage merchantImage);

    /**
     * 获取商户图片列表
     * @param merchantImage
     * @return
     */
    List<MerchantImage> getMerchantInfoImages(MerchantImage merchantImage);

}
