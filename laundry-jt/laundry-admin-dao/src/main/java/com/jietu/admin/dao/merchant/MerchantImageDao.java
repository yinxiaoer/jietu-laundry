package com.jietu.admin.dao.merchant;

import com.jietu.common.entity.merchant.MerchantImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商户图片dao
 * @author: 印修河
 * @date: 2019/2/6 16:08
 */
public interface MerchantImageDao {

    /**
     * 插入商户图片
     * @param merchantImage
     * @return
     */
    Integer insert(MerchantImage merchantImage);

    /**
     * 删除商户图片
     * @param id
     * @return
     */
    Integer delete(Long id);

    /**
     * 获取商户图片列表
     * @param merchantImage
     * @return
     */
    List<MerchantImage> getMerchantImageList(MerchantImage merchantImage);

    /**
     * 耿勋商户图片排序
     * @param id 图片id
     * @param sortOrder 排序
     * @return
     */
    Integer updateMerchantImageSeq(@Param("id") Long id, @Param("sortOrder") Integer sortOrder);
}
