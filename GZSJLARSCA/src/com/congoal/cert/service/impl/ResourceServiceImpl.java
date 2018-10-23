package com.congoal.cert.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.congoal.cert.dao.ResourceDao;
import com.congoal.cert.service.ResourceService;

@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {
	@Autowired
	private ResourceDao resourceDao;

	public void setResourceDao(ResourceDao resourceDao) {
		this.resourceDao = resourceDao;
	}

	public boolean saveOrUpdateObj(Object entity) {
		return resourceDao.saveOrUpdateObj(entity);
	}

	public Object findAll(String hql) {
		return resourceDao.findAll(hql);
	}

	public boolean updateObj(Object entity) {
		return resourceDao.updateObj(entity);
	}

	@Override
	public boolean delObj(Object entity) {
		resourceDao.delObj(entity);
		return true;
	}

	@Override
	public void updateWithHql(String hql) {
		resourceDao.updateByHql(hql);
	}

	@Override
	public void updateWithSql(String sql) {
		resourceDao.updateBySql(sql);
	}
	
}
