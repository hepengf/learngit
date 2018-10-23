package com.congoal.cert.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.congoal.cert.dao.UnCertCRLDao;
import com.congoal.cert.pojo.UnCertCRL;
import com.congoal.cert.pojo.UnCertCRL;
import com.congoal.cert.utils.Pager;

@Service
public class UnCertCRLService extends BaseService<UnCertCRL> {
	
	@Resource
	public void setDao(UnCertCRLDao dao) {
		super.setDao(dao);
	}
	/**
	 * 
	 * @param hql
	 * @param pager
	 * @return
	 */
	public List<UnCertCRL> findAllCRL(String hql, Pager pager) {
		List<UnCertCRL> crls = (List<UnCertCRL>) this.findByPager(hql, pager);
		return crls;
	}
}
