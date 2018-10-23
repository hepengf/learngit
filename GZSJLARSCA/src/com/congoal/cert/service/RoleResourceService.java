package com.congoal.cert.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.congoal.cert.dao.RoleResourceDao;
import com.congoal.cert.pojo.RoleResource;

@Controller
@Scope("prototype")
public class RoleResourceService extends BaseService<RoleResource> {
	
	@Resource
	public void setDao(RoleResourceDao dao) {
		super.setDao(dao);
	}
	/**
	 * 
	 * @param sql
	 * @return
	 */
	public List<RoleResource> findAll(String sql) {
		return (List<RoleResource>) this.getDao().findBySql(sql);
	}
}
