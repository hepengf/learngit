package com.congoal.cert.service;

public interface RoleService {
	public boolean saveOrUpdateObj(Object entity);

	public Object findAll(String hql);

	public boolean updateObj(Object entity);
}
