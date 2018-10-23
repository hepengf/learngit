package com.congoal.cert.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.congoal.cert.dao.UserDao;
import com.congoal.cert.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public boolean saveOrUpdateObj(Object entity) {
		return userDao.saveOrUpdateObj(entity);
	}

	public Object findAll(String hql) {
		return userDao.findAll(hql);
	}

	public boolean updateObj(Object entity) {
		return userDao.updateObj(entity);
	}
	
	public void deleteObj(Object entity) {
		userDao.deleteObj(entity);
	}
}
