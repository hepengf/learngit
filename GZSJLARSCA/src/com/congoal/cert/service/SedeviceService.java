package com.congoal.cert.service;

import javax.annotation.Resource;



import org.springframework.stereotype.Service;

import com.congoal.cert.dao.SedeviceDao;
import com.congoal.cert.pojo.Sedevice;
@Service
public class SedeviceService extends BaseService<Sedevice> {
	
	@Resource
	public void setDao(SedeviceDao dao) {
		super.setDao(dao);
	}
}
