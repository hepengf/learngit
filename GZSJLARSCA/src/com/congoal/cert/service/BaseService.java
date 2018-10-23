package com.congoal.cert.service;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.congoal.cert.dao.BaseDao;
import com.congoal.cert.pojo.UnCertRevokeApp;
import com.congoal.cert.utils.Order;
import com.congoal.cert.utils.Pager;
import com.congoal.cert.utils.Property;

public class BaseService<T extends Serializable> {

    private BaseDao<T> dao;

    public void save(T entity) {
        dao.save(entity);
    }

    public void saveOrUpdate(T entity) {
        dao.saveOrUpdate(entity);
    }

    public void update(Object entity) {
        dao.update(entity);
    }

    public void delete(Object entity) {
        dao.delete(entity);
    }

    public void deleteById(Serializable id) {
        dao.delete(find(id));
    }

    public T find(Serializable id) {
        T entity = (T) dao.find(id);
        return entity;
    }

    public List<T> findAll() {
        return dao.findAll(Order.asc("id"));
    }

    public List<T> findByPager(Pager pager) {
        return getDao().findByPager(pager, Order.desc("id"));
    }

    public List<T> findByPager(Pager pager, Order order) {
        return getDao().findByPager(pager, order);
    }

    public List<T> findByPager(Pager pager, Order order, Property... propertys) {
        return getDao().findByPager(pager, new Order[]{order}, propertys);
    }

    public List<?> findByPager(String hql, Pager pager) {
        return dao.findByHql(hql, pager);
    }

    public BaseDao<T> getDao() {
        return dao;
    }

    public void setDao(BaseDao<T> dao) {
        this.dao = dao;
    }

    @SuppressWarnings({"rawtypes"})
    public List query(Pager pager, Object object, Map dateMap) {
        return getDao().query(pager, object, dateMap);
    }

    public void updateByHql(String hql) {
        getDao().updateByHql(hql);
    }
    
    public void UpdateWithSql(String sql) {
    	this.getDao().updateWithSql(sql);
    }
    
    /**
	 * 
	 * @param hql
	 * @return
	 */
	public List<T> findAll(String hql) {
		return (List<T>) this.getDao().findByHql(hql);
	}
	/**
	 * 
	 * @param sql
	 * @return
	 */
	public List<T> findAllBySql(String sql) {
		return (List<T>) this.getDao().findBySql(sql);
	}
	/**
	 * 
	 * @param sql
	 * @return
	 */
	public List<Object[]> findAllWithSql(String sql) {
		return this.getDao().queryBySQL(sql, null);
	}
	/**
	 * 
	 * @param sql
	 * @return
	 */
	public List<Object> findBySql(String sql) {
		return this.getDao().queryBySQL(sql, null);
	}
	
	public List<T> findWithHql(String hql) {
		return this.getDao().findWithHql(hql);
	}
	
	public List<T> findWithSql(String sql) {
		return this.getDao().findWithSql(sql);
	}
	
	/**
	 * 查询表数据总条数
	 * @param hql
	 * @return
	 */
	public int getTotalSize(String hql)
	{
		return this.getDao().executeCountQuery(hql);
	}
	
}
