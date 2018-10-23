package com.congoal.cert.service;


public interface UserService {

	public boolean saveOrUpdateObj(Object entity);

	public Object findAll(String hql);

	public boolean updateObj(Object entity);
	
	public void deleteObj(Object entity);
}
