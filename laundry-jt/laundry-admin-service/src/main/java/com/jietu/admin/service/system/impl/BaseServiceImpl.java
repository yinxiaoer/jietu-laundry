package com.jietu.admin.service.system.impl;

import com.jietu.admin.service.system.IBaseService;
import com.jietu.admin.model.PageModel;
import com.jietu.admin.service.system.IBaseService;
import com.jietu.admin.dao.system.BaseDao;
import com.jietu.common.entity.BaseEntity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * 
 *  service实现类基类
 * @author 印修河
 * @date 2017年9月12日 下午6:09:52
 */
public abstract class BaseServiceImpl<T extends BaseEntity, Id extends Serializable> implements IBaseService<T, Id> {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	protected BaseDao<T, Id> baseDao;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void insert(T t) {
		baseDao.insert(t);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(T t) {
		baseDao.update(t);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(Id id) {
		baseDao.delete(id);
	}

	@Override
	public T getById(Id id) {
		return baseDao.getById(id);
	}

	@Override
	public List<T> getAll() {
		return baseDao.getAll();
	}

	@Override
	public Long findCountByQuery(T t) {
		return baseDao.findCountByQuery(t);
	}

	@Override
	public List<T> findLimitByQuery(T t) {
		return baseDao.findLimitByQuery(t);
	}

	@Override
	public PageModel<T> findByQuery(T t) {
		//如果排序字段不为空则将对象字段转换为数据库字段
		if(StringUtils.isNotBlank(t.getSort())){
			t.setSort(this.getDBField(t.getSort()));
		}
		PageModel<T> pageModel = new PageModel<T>();
		Long count = baseDao.findCountByQuery(t);
		pageModel.setCount(count);
		if(count > 0){
			pageModel.setData(baseDao.findLimitByQuery(t));
		}
		return pageModel;
	}

	/**
	 * 将对象字段名转换为数据库字段名
	 * @param field 对象字段名称
	 * @return 数据库字段名称
	 */
	protected String getDBField(String field){
		if(StringUtils.isBlank(field)){
			return null;
		}
		StringBuilder sb = new StringBuilder("");
		char[] buffer = field.toCharArray();
		for(int i = 0; i < buffer.length; ++i) {
			char ch = buffer[i];
			if (Character.isUpperCase(ch)) {
				sb.append("_").append(Character.toLowerCase(ch));
			}else{
				sb.append(ch);
			}
		}
		return sb.toString();
	}
}
