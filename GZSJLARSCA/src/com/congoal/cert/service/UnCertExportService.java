package com.congoal.cert.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.congoal.cert.dao.UnCertExportDao;
import com.congoal.cert.pojo.UnCertExport;
import com.congoal.cert.pojo.UnCertExport;

@Service
public class UnCertExportService extends BaseService<UnCertExport> {
	
	@Resource
	public void setDao(UnCertExportDao dao) {
		super.setDao(dao);
	}
	
}
