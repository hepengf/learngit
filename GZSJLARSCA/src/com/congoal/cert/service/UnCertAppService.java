package com.congoal.cert.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;






import com.congoal.cert.dao.BaseDao;
import com.congoal.cert.dao.UnCertAppDao;
import com.congoal.cert.pojo.UnCertApp;

@Service
public class UnCertAppService extends BaseService<UnCertApp> {
	
	@Resource
	public void setDao(UnCertAppDao dao) {
		super.setDao(dao);
	}
}
