package com.congoal.cert.dao;

import java.io.Serializable;

public interface CertAdministrationDao {
	public boolean saveOrUpdateObj(Object entity);

	public Object findAll(String hql);
	
	public Object findAllBySql(String sql);

	public boolean updateObj(Object entity);
	
	public int updateWithHql(String hql);
	
	public int updateWithSql(String sql);
	
	public Serializable  saveObj(Object entity);
}
