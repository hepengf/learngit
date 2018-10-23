package com.congoal.cert.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.congoal.cert.dao.RolesDao;
import com.congoal.cert.pojo.Role;
import com.congoal.cert.utils.Pager;
@Service
public class RolesService extends BaseService<Role> {
	
	@Resource
	public void setDao(RolesDao dao) {
		super.setDao(dao);
	}
	/**
	 * 查找所有用户
	 * @param hql
	 * @param pager
	 * @return
	 */
	public List<Role> findAllRoles(String hql, Pager pager) {
		List<Role> roles = (List<Role>) this.findByPager(hql, pager);
		return roles;
	}
	
	public List<Role> findAll(String hql) {
		return (List<Role>) this.getDao().findByHql(hql);
	}
	
	
}
