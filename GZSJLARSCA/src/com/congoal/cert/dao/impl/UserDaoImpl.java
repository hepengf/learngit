package com.congoal.cert.dao.impl;


import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.congoal.cert.dao.UserDao;

@Repository("userDao")
public class UserDaoImpl extends HibernateDaoSupport implements UserDao {

	public boolean saveOrUpdateObj(Object entity) {
		this.getHibernateTemplate().saveOrUpdate(entity);
		return true;
	}

	public Object findAll(String hql) {
		return this.getHibernateTemplate().find(hql);
	}

	public boolean updateObj(Object entity) {
		this.getHibernateTemplate().update(entity);
		return true;
	}

	@Autowired
	public void setMySessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	@Override
	public void deleteObj(Object entity) {
		this.getHibernateTemplate().delete(entity);;;
	}

}
