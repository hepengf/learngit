package com.congoal.cert.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.congoal.cert.dao.ModuleDao;
import com.congoal.cert.pojo.Resource;
import com.congoal.cert.utils.Pager;

@Service
public class ModuleService extends BaseService<Resource> {
	
	@javax.annotation.Resource
	public void setDao(ModuleDao dao) {
		super.setDao(dao);
	}
	/**
	 * 
	 * @param hql
	 * @return
	 */
	public List<Resource> findAll(String hql) {
		return (List<Resource>) this.getDao().findByHql(hql);
	}
	
	public List<Resource> findAllResources(String hql, Pager pager) {
		int totalSize = this.getDao().executeCountQuery(hql);
		pager.setTotalSize(totalSize);
		List<Resource> res = (List<Resource>) this.findByPager(hql, pager);
		return res;
	}
}
