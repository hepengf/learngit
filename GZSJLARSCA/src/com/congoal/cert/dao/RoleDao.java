package com.congoal.cert.dao;

public interface RoleDao {
	public boolean saveOrUpdateObj(Object entity);

	public Object findAll(String hql);

	public boolean updateObj(Object entity);
}
