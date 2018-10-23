package com.congoal.cert.dao.impl;

import org.hibernate.LockMode;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.congoal.cert.dao.ResourceDao;

@Repository("resourceDao")
public class ResourceDaoImpl extends HibernateDaoSupport implements ResourceDao {

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
	public boolean delObj(Object entity) {
		this.getHibernateTemplate().delete(entity);;
		return true;
	}

	@Override
	public void updateByHql(String hql) {
		getSession().createQuery(hql).executeUpdate();
	}

	@Override
	public void updateBySql(String sql) {
		Session session = getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			SQLQuery createSQLQuery = session.createSQLQuery(sql);
			int i = createSQLQuery.executeUpdate();
			tx.commit();
			logger.info("executeUpdate---signal: "+i);
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			session.close();
		}
	}
	
	
}
