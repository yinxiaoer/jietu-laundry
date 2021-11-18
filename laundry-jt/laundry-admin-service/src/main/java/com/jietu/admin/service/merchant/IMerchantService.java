package com.jietu.admin.service.merchant;

import com.jietu.admin.service.system.IBaseService;
import com.jietu.common.entity.merchant.Merchant;
import com.jietu.admin.service.system.IBaseService;
import com.jietu.common.entity.merchant.MerchantImage;

import java.util.List;

/**
 * 商户服务接口类
 * @author: 印修河
 * @date: 2018/12/20 22:45
 */
public interface IMerchantService extends IBaseService<Merchant, Long> {

    /**
     * 保存商户图片
     * @param merchantImage
     */
    void saveMerchantImage(MerchantImage merchantImage);

    /**
     * 删除商户图片
     * @param id
     */
    void deleteMerchantImage(Long id);

    /**
     * 获取商户图片列表
     * @param merchantId
     * @return
     */
    List<MerchantImage> getMerchantImageList(Long merchantId);

    /**
     * 更新商户图片顺序
     * @param srcId 源id
     * @param tarId 目标id
     * @param oldIndex 源顺序
     * @param newIndex 目标顺序
     */
    void updateMerchantImageSeq(Long srcId, Long tarId, Integer oldIndex, Integer newIndex);
}
