package com.congoal.cert.service;

import java.io.Serializable;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.congoal.cert.dao.AdminDao;
import com.congoal.cert.pojo.User;
import com.congoal.cert.utils.Pager;

@Service
public class AdminService extends BaseService<User> implements Serializable {
	@Resource
	public void setDao(AdminDao dao) {
		super.setDao(dao);
	}
	/**
	 * 查找所有用户
	 * @param hql
	 * @param pager
	 * @return
	 */
	public List<User> findAllUser(String hql, Pager pager) {
		List<User> users = (List<User>) findByPager(hql, pager);
		return users;
	}	
}
