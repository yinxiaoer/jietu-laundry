package com.jietu.common.entity.order;

import com.jietu.common.entity.BaseEntity;
import com.jietu.common.entity.merchant.MerchantImage;
import lombok.*;

import java.util.List;

/**
 * 订单评价实体类
 * @author: 印修河
 * @date: 2019/2/14 5:39
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrderComment extends BaseEntity {

    private static final long serialVersionUID = 4118852994141718702L;
    private Long id;
    /** 订单id */
    private Long orderId;
    /** 商户id */
    private Long merchantId;
    /** 评价分数 */
    private Double grade;
    /** 评价内容 */
    private String comment;
    /** 评价图片 */
    private List<MerchantImage> imageList;
    /** 商户回复 */
    private String merchantReply;
    /** 客户姓名 */
    private Long customerId;
    /** 客户姓名 */
    private String customerName;
    /** 客户头像 */
    private String customerPortrait;
    /** 评价类型 */
    private Integer types;

}
