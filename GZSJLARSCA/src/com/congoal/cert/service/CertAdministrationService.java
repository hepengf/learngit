package com.congoal.cert.service;

import com.congoal.cert.pojo.UnCert;
import com.congoal.cert.pojo.UnCertApp;
import com.congoal.cert.pojo.UnCertAward;
import com.congoal.cert.pojo.UnCertRevokeApp;
import com.congoal.cert.pojo.User;

public interface CertAdministrationService {
	public boolean saveOrUpdateObj(Object entity);

	public Object findAll(String hql);

	public boolean updateObj(Object entity);
	
	public int updateWithHql(String hql);
	
	public int updateWithSql(String sql);
	/**
	 * 作废证书
	 * @param uaid
	 * @param cid
	 * @param userid
	 * @return
	 */
	public boolean disabledCert(String uaid,String cid,String userid) ;
	/**
	 * 吊销证书
	 * @param uid
	 * @param user
	 * @return
	 */
	public boolean disabledCert(String uid,User user);
	/**
	 * 作废证书
	 * @param cid
	 * @param userid
	 * @return
	 */
	public boolean disabledCert(String ... params) ;
	/**
	 * 作废证书申请
	 * @return
	 */
	public boolean disabledCertApp(UnCertRevokeApp  revokeApp);
	/**
	 * 签发证书
	 */
	public boolean signCert(UnCertApp app,User user,String ca, String uniq_batch,boolean isSignSevCert)  throws Exception;
	
	/**
	 * 导出证书 
	 * @param params
	 */
	public boolean exportCert(String ... params);
	
	public Object findAllBySql(String sql);
	public boolean createCertCrl(Object ...objects);
}
