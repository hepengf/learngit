package com.congoal.cert.dao;

public interface ResourceDao {
	public boolean saveOrUpdateObj(Object entity);

	public Object findAll(String hql);

	public boolean updateObj(Object entity);
	
	public boolean delObj(Object entity);
	
	 public void updateByHql(String hql) ;
	 
	 public void updateBySql(String sql);
}
