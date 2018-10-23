package com.congoal.cert.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.congoal.cert.dao.RoleDao;
import com.congoal.cert.service.RoleService;

@Service("roleService")
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleDao roleDao;


	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public boolean saveOrUpdateObj(Object entity) {
		return roleDao.saveOrUpdateObj(entity);
	}

	public Object findAll(String hql) {
		return roleDao.findAll(hql);
	}

	public boolean updateObj(Object entity) {
		return roleDao.updateObj(entity);
	}
}
