package com.congoal.cert.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.congoal.cert.dao.UnCertRevokeAppDao;
import com.congoal.cert.pojo.UnCertRevokeApp;
import com.congoal.cert.pojo.UnCertRevokeApp;

@Service
public class UnCertRevokeAppService extends BaseService<UnCertRevokeApp> {

	public void setDao(UnCertRevokeAppDao dao) {
		super.setDao(dao);
	}
	
	
}
