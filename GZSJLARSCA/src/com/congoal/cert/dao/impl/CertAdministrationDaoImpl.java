package com.congoal.cert.dao.impl;

import java.io.Serializable;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.congoal.cert.dao.CertAdministrationDao;

@Repository("certAdministrationDao")
public class CertAdministrationDaoImpl extends HibernateDaoSupport implements
		CertAdministrationDao {

	@Autowired
	public void setMySessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	public Serializable  saveObj(Object entity) {
		return this.getHibernateTemplate().save(entity);
	}
	
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

	public Session getCurrentSession()
	{
		return this.getSessionFactory().getCurrentSession();
	}
	

	@Override
	public int updateWithHql(String hql) {
		return this.getCurrentSession().createQuery(hql).executeUpdate();
	}

	@Override
	public int updateWithSql(String sql) {
		return this.getCurrentSession().createSQLQuery(sql).executeUpdate();
	}

	@Override
	public Object findAllBySql(String sql) {
//		return this.getCurrentSession().createQuery(sql).
		return this.getCurrentSession().createSQLQuery(sql).list();
	}
}
