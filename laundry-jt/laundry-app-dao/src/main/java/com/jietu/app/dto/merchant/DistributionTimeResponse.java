package com.jietu.app.dto.merchant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 商户配送时间相应包装类
 * @author: 印修河
 * @date: 2019/2/23 17:15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributionTimeResponse implements Serializable {

    private static final long serialVersionUID = 1635358040216563196L;
    public String label;
    public String value;
    public String start;
    public String end;
    public String money;

}
