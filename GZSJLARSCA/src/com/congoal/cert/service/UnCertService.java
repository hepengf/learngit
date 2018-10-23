package com.congoal.cert.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.congoal.cert.dao.UnCertDao;
import com.congoal.cert.pojo.UnCert;
import com.congoal.cert.pojo.UnCert;
import com.congoal.cert.utils.Pager;

@Service
public class UnCertService extends BaseService<UnCert> {
	
	@Resource
	public void setDao(UnCertDao dao) {
		super.setDao(dao);
	}
	/**
	 * 
	 * @param hql
	 * @param pager
	 * @return
	 */
	public List<UnCert> findAllCerts(String hql, Pager pager) {
		List<UnCert> certs = (List<UnCert>) this.findByPager(hql, pager);
		return certs;
	}
}
