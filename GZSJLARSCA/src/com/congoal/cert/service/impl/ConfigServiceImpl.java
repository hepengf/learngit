package com.congoal.cert.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.congoal.cert.dao.ConfigDao;
import com.congoal.cert.service.ConfigService;

@Service("configService")
public class ConfigServiceImpl implements ConfigService {
	@Autowired
	private ConfigDao configDao;

	public void setConfigDao(ConfigDao configDao) {
		this.configDao = configDao;
	}

	public Object findAll(String hql) {
		return configDao.findAll(hql);
	}

	public boolean updateObj(Object entity) {
		return configDao.updateObj(entity);
	}

	public boolean saveOrUpdateObj(Object entity) {
		return configDao.saveOrUpdateObj(entity);
	}
}
