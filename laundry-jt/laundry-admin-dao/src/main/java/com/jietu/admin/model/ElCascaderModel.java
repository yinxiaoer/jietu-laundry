package com.jietu.admin.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * element ui cascader 组件包装类
 * @author: 印修河
 * @date: 2018/12/22 10:24
 */
@Data
public class ElCascaderModel implements Serializable {

    private String label;
    private String value;
    private List<ElCascaderModel> children;

}
