package com.jietu.app.entity.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 地址类
 * @author: 印修河
 * @date: 2018/12/28 21:19
 */
@Data
public class Area implements Serializable {

    private static final long serialVersionUID = -3539145690091449747L;
    private String code;
    private String name;
    private String parent;

}
