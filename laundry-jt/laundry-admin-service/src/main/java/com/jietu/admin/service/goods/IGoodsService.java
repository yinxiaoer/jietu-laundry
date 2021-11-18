package com.jietu.admin.service.goods;

import com.jietu.common.entity.goods.Goods;
import com.jietu.common.entity.goods.GoodsImage;
import com.jietu.admin.service.system.IBaseService;

import java.util.List;

/**
 * 商品服务接口类
 * @author: 印修河
 * @date: 2018/12/18 22:28
 */
public interface IGoodsService extends IBaseService<Goods, Long> {

    /**
     * 获取商品图片列表
     * @param id 商品id
     * @return
     */
    List<GoodsImage> getGoodsImages(Long id);

    /**
     * 保存商品图片
     * @param id 商品id
     * @param url 图片路径
     * @return 保存后返回所有商品图片
     */
    List<GoodsImage> saveGoodsImage(Long id, String url);

    /**
     * 删除商品图片
     * @param id 图片id
     * @return
     */
    Integer deleteGoodsImageBy(Long id);
}
