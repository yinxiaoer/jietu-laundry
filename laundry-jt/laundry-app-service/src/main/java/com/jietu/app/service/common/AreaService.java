package com.jietu.app.service.common;

import com.jietu.app.dao.common.AreaDao;
import com.jietu.app.dao.common.AreaDao;
import com.jietu.app.entity.common.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 区域服务类
 * @author: 印修河
 * @date: 2018/12/28 21:41
 */
@Service
public class AreaService {

    @Autowired
    private AreaDao areaDao;

    /**
     * 获取省份列表
     * @return
     */
    @Cacheable(value = "provinceList", key = "'provinceList'")
    public List<Area> getProvince(){
        return areaDao.findByParent("0");
    }

    /**
     * 通过父地区查询子地区列表
     * @param parent
     * @return
     */
    @Cacheable(value = "provinceList", key = "'areaList_' + #parent")
    public List<Area> findByParent(String parent){
        return areaDao.findByParent(parent);
    }
}
