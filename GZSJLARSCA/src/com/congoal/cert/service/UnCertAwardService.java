package com.congoal.cert.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.congoal.cert.dao.UnCertAwardDao;
import com.congoal.cert.pojo.UnCertAward;
import com.congoal.cert.utils.Pager;
@Service
public class UnCertAwardService extends BaseService<UnCertAward> {
	
	@Resource
	public void setDao(UnCertAwardDao dao) {
		super.setDao(dao);
	}
	/**
	 * 
	 * @param hql
	 * @param pager
	 * @return
	 */
	public List<UnCertAward> findAllCerts(String hql, Pager pager) {
		List<UnCertAward> certs = (List<UnCertAward>) this.findByPager(hql, pager);
		return certs;
	}
	
}
