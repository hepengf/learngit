package com.congoal.cert.dao;

public interface UserDao {
	public boolean saveOrUpdateObj(Object entity);

	public Object findAll(String hql);

	public boolean updateObj(Object entity);
	
	public void deleteObj(Object entity);
	
}
