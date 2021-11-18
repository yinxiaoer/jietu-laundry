package com.jietu.admin.model;

import java.io.Serializable;
import java.util.List;

/**
 * 树节点包装类
 * @author: 印修河
 * @date: 2017/12/6 15:43
 */
public class TreeModel implements Serializable {

    private String id;
    /** 节点名称 */
    private String label;
    /** 是否选中 */
    private Boolean hasPermission;
    /** 子节点 */
    private List<TreeModel> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean getHasPermission() {
        return hasPermission;
    }

    public void setHasPermission(Boolean hasPermission) {
        this.hasPermission = hasPermission;
    }

    public List<TreeModel> getChildren() {
        return children;
    }

    public void setChildren(List<TreeModel> children) {
        this.children = children;
    }
}
