package com.jietu.app.entity.merchant;

import com.jietu.common.entity.merchant.Merchant;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商户距离实体类
 * @author: 印修河
 * @date: 2019/1/6 10:17
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MerchantDistance extends Merchant {

    private static final long serialVersionUID = 2789410164625412588L;
    /** 距离 */
    private Long distance;

}
