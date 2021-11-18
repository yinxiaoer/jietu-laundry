package com.jietu.app.dao.common;

import com.jietu.app.entity.common.Area;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 区域dao
 * @author: 印修河
 * @date: 2018/12/28 21:24
 */
public interface AreaDao {

    /**
     * 通过上级查询地址列表
     * @param parent
     * @return
     */
    List<Area> findByParent(@Param("parent") String parent);

}
