package com.congoal.cert.service;

import java.util.List;

import javax.annotation.Resource;



import org.springframework.stereotype.Service;

import com.congoal.cert.dao.UnCertBulkDao;
import com.congoal.cert.pojo.UnCertAward;
import com.congoal.cert.pojo.UnCertBulk;
import com.congoal.cert.pojo.UnCertBulk;
import com.congoal.cert.utils.Pager;

@Service
public class UnCertBulkService extends BaseService<UnCertBulk> {
	
	@Resource
	public void setDao(UnCertBulkDao dao) {
		super.setDao(dao);
	}
	public List<UnCertBulk> findAllUnCertBulks(String hql, Pager pager) {
		List<UnCertBulk> unCerts = (List<UnCertBulk>) this.findByPager(hql, pager);
		return unCerts;
	}
	
}
