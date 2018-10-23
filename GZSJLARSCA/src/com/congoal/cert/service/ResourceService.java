package com.congoal.cert.service;


public interface ResourceService {

	public boolean saveOrUpdateObj(Object entity);

	public Object findAll(String hql);

	public boolean updateObj(Object entity);
	
	public boolean delObj(Object entity);
	
	 public void updateWithHql(String hql);
	 
	 public void updateWithSql(String sql);
}
