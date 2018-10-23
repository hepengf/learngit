package com.congoal.cert.service.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.congoal.cert.action.CertAdministrationAction;
import com.congoal.cert.dao.CertAdministrationDao;
import com.congoal.cert.dao.UserDao;
import com.congoal.cert.pojo.Dn;
import com.congoal.cert.pojo.UnCert;
import com.congoal.cert.pojo.UnCertApp;
import com.congoal.cert.pojo.UnCertAward;
import com.congoal.cert.pojo.UnCertCRL;
import com.congoal.cert.pojo.UnCertExport;
import com.congoal.cert.pojo.UnCertRevokeApp;
import com.congoal.cert.pojo.User;
import com.congoal.cert.pojo.UnCert.UnCertStatus;
import com.congoal.cert.req.CertUtil;
import com.congoal.cert.req.KeyUtil;
import com.congoal.cert.service.CertAdministrationService;
import com.congoal.cert.test.CertUtils;
import com.congoal.cert.utils.BaseUtil;
import com.congoal.cert.utils.CertCommonUtils;
import com.congoal.cert.utils.CreateCertUtil;
import com.congoal.cert.utils.HttpInvoker;
import com.congoal.cert.utils.JsonMapper;
import com.congoal.cert.utils.RSAEncrypt;
import com.congoal.cert.utils.SQL;
import com.congoal.cert.utils.SaveCert;
import com.congoal.cert.utils.SimpleDateFormatUtils;
@Service("certAdministrationService")
@SuppressWarnings("all")
public class CertAdministrationServiceImpl implements CertAdministrationService{
	private Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private CertAdministrationDao certAdministrationDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private HttpInvoker httpInvoker;
	
	@Autowired
	private CertUtil certUtil;
	
	private static final int KEYSIZE = 2048;
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public void setCertAdministrationDao(
			CertAdministrationDao certAdministrationDao) {
		this.certAdministrationDao = certAdministrationDao;
	}

	public void setCertUtil(CertUtil certUtil) {
		this.certUtil = certUtil;
	}
	
	public boolean saveOrUpdateObj(Object entity) {
		return certAdministrationDao.saveOrUpdateObj(entity);
	}

	public Object findAll(String hql) {
		return certAdministrationDao.findAll(hql);
	}

	public boolean updateObj(Object entity) {
		return certAdministrationDao.updateObj(entity);
	}

	@Override
	public int updateWithHql(String hql) {
		return certAdministrationDao.updateWithHql(hql);
	}

	@Override
	public int updateWithSql(String sql) {
		return certAdministrationDao.updateWithSql(sql);
	}
	
	public static void main(String[] args) {
		int len = 1000;
		
		
		int result = len / 1000;
		int mod = len % 1000;
		
		if(mod > 0)
		{
			result = result + 1;
		}
		
		//System.out.println(result+" "+mod);
		
		for(int i = 0;i < result ;i++)
		{
			if(i == result-1)
			{
				if(mod == 0){
					System.out.println("start: "+(i*1000)+",end: "+((i*1000)+999));
				}
				else
				{
					System.out.println("start: "+(i*1000)+",end: "+((i*1000)+(mod-1)));
				}
			}else
			{
				System.out.println("start: "+(i*1000)+",end: "+((i*1000)+999));
			}
		}
	}
	
	/**
	 * 
	 * @param ids
	 * @param caKeyStore
	 * @param alias
	 * @param storepass
	 * @param nextUpdDate
	 * @param olrCrlPath
	 * @param newCrlPath
	 * @return
	 */
	private X509CRL createCertCrlDetail(String ids,KeyStore caKeyStore,String alias, String storepass,int nextUpdDate,String olrCrlPath,String newCrlPath)
	{
		String hql = "from UnCert where id in ("+ids+")";
		List<UnCert> unCerts = (List<UnCert>)this.certAdministrationDao.findAll(hql);
		X509CRL x509CRL = null;
		if(unCerts != null && !unCerts.isEmpty()){
			for(UnCert unCert : unCerts)
			{
				int certType = unCert.getCertType();
				String batchno = unCert.getBatch();
			
				
				//获取证书类型
				String sql = "";
				//证书
				if(certType == 1)
				{
					 sql = "select path from un_cert_award where batch='"+batchno+"'";
				}
				//密钥库
				else if(certType==2)
				{
					 sql = "select path from un_cert_export where batch='"+batchno+"'";
					
				}//私钥
				else if(certType==3)
				{
					return x509CRL;
				}else
				{
					return x509CRL;
				}
				
				List<String> paths = (List<String>)this.certAdministrationDao.findAllBySql(sql);
				
				if(paths != null & !paths.isEmpty())
				{
					String path = paths.get(0);
					
					X509Certificate certificate =  certUtil.getCertFromCertFile(path);
					x509CRL = certUtil.createCertCrl(caKeyStore, alias, storepass,certificate, nextUpdDate, olrCrlPath,newCrlPath);
				}
			}
		}
		
		return x509CRL;
	}
	/**
	 * 创建crl列表
	 */
	public boolean createCertCrl(Object ...objects)
	{
		int nextUpdDate = Integer.parseInt(CertCommonUtils.CRL_NEXT_UPDATE_TIME);//crl文件下次更新时间
		String[] certs = (String[])objects[0];//待登记的证书
		int len = certs.length;
		User user = (User)objects[1];//系统用户
		UnCertAward award = (UnCertAward)objects[2];//根ca证书颁发对象
		UnCert ca = (UnCert)objects[3];//根ca对象
		
		final Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String fileName = "crl_"+sdf.format(now)+"."+CertCommonUtils.CRL_SUFFIX;
//		String fileName = "crl."+CertCommonUtils.CRL_SUFFIX;
		String newCrlPath = CertCommonUtils.CRL_ONLINECERTSPATH+fileName;
				
		String keyStorePath = award.getPath();
		String storeType = award.getFileType();
		String storepass = ca.getSecret();
		String alias = ca.getAlias();
		
		
		KeyStore caKeyStore = null;
		InputStream inStream = null;
		try {
			if ("PFX".equals(storeType.toUpperCase())) {
				caKeyStore = KeyStore.getInstance("PKCS12", "BC");
			} else if ("JKS".equals(storeType.toUpperCase())) {
				caKeyStore = KeyStore.getInstance("JKS");
			}
		    inStream = new FileInputStream(keyStorePath);
		    caKeyStore.load(inStream, storepass.toCharArray());
		}
		catch(Throwable e)
		{
			e.printStackTrace();
		}
		finally{
			if(inStream != null)
			{
				try {
					inStream.close();
				} catch (IOException e) {
				}
			}
		}
		
		//获取最近的crl文件 
		String hql = "from UnCertCRL where status="+UnCertCRL.UnCertCRLStatus.Status.enable+" order by id desc";
		
		List<UnCertCRL> results = (List<UnCertCRL>)findAll(hql);
		String olrCrlPath = null;
		Integer crlId = null;
		UnCertCRL unCertCRL = null;
		
		if(results != null && !results.isEmpty())
		{
			unCertCRL = results.get(0);
			olrCrlPath = unCertCRL.getPath();
			crlId = unCertCRL.getId();
			
		}
		
		X509CRL x509CRL = null;
		List<String> certLists = new ArrayList<String>();
		//数据库 in 关键字只能跟1000个条件
		if(len < 1001)
		{
			String ids = "";
			
			//拼接in字符串
			for(int i = 0;i<len;i++)
			{
				if(i == len-1)
				{
					ids += certs[i];
				}else
				{
					ids += certs[i]+",";
				}
			}
			
		    x509CRL = createCertCrlDetail(ids,caKeyStore,alias,storepass,nextUpdDate,olrCrlPath,newCrlPath);
			
			certLists.add(ids);
		}
		else
		{
			
			int result = len / 1000;
			int mod = len % 1000;
			
			if(mod > 0)
			{
				result = result + 1;
			}
			
			//System.out.println(result+" "+mod);
			
			for(int i = 0;i < result ;i++)
			{
				String ids = "";
				
				int start = (i*1000);
				String[] dest = null;
				if(i == result-1)
				{
					if(mod == 0){
						dest = new String[1000];
						//System.out.println("start: "+(i*1000)+",end: "+((i*1000)+999));
						System.arraycopy(certs, start, dest, 0, 1000);
					}
					else
					{
						dest = new String[mod];
//						System.out.println("start: "+(i*1000)+",end: "+((i*1000)+(mod-1)));
						System.arraycopy(certs, start, dest, 0, mod);
					}
				}
				else
				{
					dest = new String[1000];
//					System.out.println("start: "+(i*1000)+",end: "+((i*1000)+999));
					System.arraycopy(certs, start, dest, 0, 1000);
				}
				
				//拼接in字符串
				for(int j = 0;j<dest.length;j++)
				{
					if(j == dest.length-1)
					{
						ids += dest[j];
					}else
					{
						ids += dest[j]+",";
					}
				}
				
				x509CRL = createCertCrlDetail(ids,caKeyStore,alias,storepass,nextUpdDate,olrCrlPath,newCrlPath);
				
				certLists.add(ids);
			}
			
		}
		//禁用旧的crl列表
//		if(crlId != null)
//		{
//			this.certAdministrationDao.updateWithSql("update un_cert_crl set status="+UnCertCRL.UnCertCRLStatus.Status.disable+" where id="+crlId);
//		}
		//创建crl对象
		if(crlId == null){
			UnCertCRL crl = new UnCertCRL();
			crl.setAwardDate(now);
			crl.setBatch(System.currentTimeMillis()+"");
			crl.setCreateTime(now);
			crl.setCreateOperator(user.getId());
			crl.setCreateOperators(user.getName());
			crl.setFileType(CertCommonUtils.CRL_SUFFIX);
			crl.setFname(fileName);
			crl.setPath(newCrlPath);
			crl.setNextUpdateTime(x509CRL.getNextUpdate());
	//		crl.setSerialnumber(serialnumber);
			crl.setStatus(UnCertCRL.UnCertCRLStatus.Status.enable);
			crl.setLastModifyOperator(user.getId());
			crl.setLastModifyOperators(user.getName());
			crl.setLastModifyTime(now);
			crl.setIssuredn(ca.getIssuredn());
			crlId = (Integer)this.certAdministrationDao.saveObj(crl);
		}
		else
		{
			unCertCRL.setLastModifyOperator(user.getId());
			unCertCRL.setLastModifyTime(now);
			
			this.certAdministrationDao.updateObj(unCertCRL);
		}
		
		
		for(int i = 0; i < certLists.size();i++)
		{
			String ids = certLists.get(i);
			
			String updSql = "update un_cert set createCrl="+UnCert.UnCertStatus.CRL.yes+",createCrlTime='"+sdf1.format(now)+"',crl="+crlId+" where id in ("+ids+")";
			this.certAdministrationDao.updateWithSql(updSql);
		}
		
		
		return true;
	}
	/**
	 * 作废证书申请
	 * @return
	 */
	public boolean disabledCertApp(UnCertRevokeApp  revokeApp)
	{
		String hql = "update un_cert set status = "+UnCertStatus.Status.beforeDisable +" where id="+revokeApp.getCertId();
		
		certAdministrationDao.updateWithSql(hql);
		
		certAdministrationDao.saveOrUpdateObj(revokeApp);
		
		return true;
	}
	/**
	 * 作废证书
	 */
	public boolean disabledCert(String uid, User user) {
		String hql = "from UnCertAward where id="+uid;
		List<UnCertAward> awards = (List<UnCertAward>) this.findAll(hql);
		if (null!=awards && awards.size() > 0) {
			UnCertAward award = awards.get(0);
			hql = "from UnCert where batch='"+award.getBatch()+"'";
			List<UnCert> certs = (List<UnCert>) this.findAll(hql);
			if (null!=certs && certs.size()>0) {
				UnCert cert = certs.get(0);
				
				Date now = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date nextUpdateTime = DateUtils.addMonths(now, 1);//设置下一次更新时间
				
				//获取父节点私钥
				PrivateKey pk = certUtil.getPrivateFromKeyStore(CertCommonUtils.ROOTCA_PATH+CertCommonUtils.CA_NAME, 
						CertCommonUtils.CA_ALIAS, 
						CertCommonUtils.PASSWORD, 
						CertCommonUtils.STORE_TYPE_JKS);
				//取得根证书文件  暂时由秘钥库中取得
				X509Certificate caCrlCert = 
						certUtil.getCertFromKeyStoreFile(
								CertCommonUtils.ROOTCA_PATH+CertCommonUtils.CA_NAME, 
								CertCommonUtils.CA_ALIAS, 
								CertCommonUtils.PASSWORD, 
								CertCommonUtils.STORE_TYPE_JKS);
				//取得要作废的证书
				X509Certificate subCert = certUtil.getCertFromCertFile(award.getPath());
				
				hql = "from UnCertCRL";
				List<UnCertCRL> crls = (List<UnCertCRL>) this.findAll(hql);
				String filePath = "";
				if (null!=crls && crls.size() > 0) {
						UnCertCRL crl = crls.get(0);
						filePath = crl.getPath();
				}else{
					filePath = CertCommonUtils.CRL_ONLINECERTSPATH+"CRL_"+System.nanoTime()+"."+CertCommonUtils.CRL_SUFFIX;
				}
				//生成crl文件
				certUtil.createCertCrl(caCrlCert, pk, subCert, 30, filePath);
				int index = filePath.lastIndexOf("/");
				if (index<=0) {
					index = filePath.lastIndexOf("\\");
				}
				String crlFname = filePath.substring(index+1, filePath.length());
				
				//更新证书吊销文件
				hql = "from UnCertCRL where status="+UnCertStatus.Status.enable;
				List<UnCertCRL> unCRLs = (List<UnCertCRL>) this.findAll(hql);
				UnCertCRL unCRL = null;
				if (null!=unCRLs && unCRLs.size() > 0) {
					unCRL = unCRLs.get(0);
					unCRL.setAwardDate(now);
					unCRL.setBatch(System.nanoTime()+"");
					unCRL.setFname(crlFname);
					unCRL.setPath(filePath);
					unCRL.setFileType("CRL");
					unCRL.setRemark("证书过期或者是私钥泄露了");
					unCRL.setLastModifyOperator(user.getId());//修改人
					unCRL.setLastModifyOperators(user.getUsername());//修改人
					unCRL.setStatus(UnCertRevokeApp.UnCertRevokeAppStatus.Status.approve);//表示已吊销
					unCRL.setSerialnumber(CertCommonUtils.getSerialNumber(caCrlCert));
					unCRL.setNextUpdateTime(nextUpdateTime);//下一次更新时间
					unCRL.setLastModifyTime(now);//最近修改时间
					unCRL.setIssuredn(caCrlCert.getIssuerX500Principal().getName());
				} else {
					unCRL = new UnCertCRL();
					unCRL.setAwardDate(now);//证书签发人
					unCRL.setBatch(System.nanoTime()+"");//吊销的证书批次
					Integer createId = unCRL.getCreateOperator();
					String operator = unCRL.getCreateOperators();
					Date createTime = unCRL.getCreateTime();
					
					//创建人由第一次签发人设置
					if(null==createId || createId.equals("")) {
						unCRL.setCreateOperator(user.getId());
					}
					if (null==operator || operator.equals("")) {
						unCRL.setCreateOperators(user.getUsername());
					}
					if (null==createTime) {
						unCRL.setCreateTime(now);
					}
					unCRL.setFname(crlFname);
					unCRL.setPath(filePath);
					unCRL.setFileType("CRL");
					unCRL.setRemark("证书过期或者是私钥泄露了");
					unCRL.setLastModifyOperator(user.getId());//修改人
					unCRL.setLastModifyOperators(user.getUsername());//修改人
					unCRL.setStatus(UnCertRevokeApp.UnCertRevokeAppStatus.Status.approve);//表示已吊销
					unCRL.setSerialnumber(award.getSerialnumber());
					unCRL.setNextUpdateTime(nextUpdateTime);//下一次更新时间
					unCRL.setLastModifyTime(now);//最近修改时间
					unCRL.setIssuredn(caCrlCert.getIssuerX500Principal().getName());
				}
				
				//更新un_cert_crl实体表
				this.saveOrUpdateObj(unCRL);
				
				UnCertRevokeApp revokeApp = new UnCertRevokeApp();
				revokeApp.setCertId(cert.getId());
				revokeApp.setCreateOperator(user.getId());
				revokeApp.setCreateOperators(user.getName());
				revokeApp.setCreateTime(new Date());
				revokeApp.setSerialnumber(award.getSerialnumber());
				revokeApp
						.setStatus(UnCertRevokeApp.UnCertRevokeAppStatus.Status.approve);
				
				this.saveOrUpdateObj(revokeApp);
				
				//更新证书颁发表
				award.setStatus(2);
				this.saveOrUpdateObj(award);
				
				//更新证书实体表
				cert.setStatus(2);
				cert.setCrl(unCRL.getId());
				cert.setCreateCrl(user.getId());
				cert.setCreateCrlTime(now);
				this.saveOrUpdateObj(cert);
				
				String name = user.getFullname();
				logger.debug("用户[" + name + "]申请吊销证书[" + award.getSerialnumber() + "]");
			}
		}
		return true;
	}
	/**
	 * 作废证书提交
	 */
	public boolean disabledCert(String ... params) {
		CertUtil certUtil = new CertUtil();
		String cid = params[0];//证书实体表id
		String revokeId = params[1];//作废id
		String userId = params[2];//当前用户
		String uid = params[3];//证书颁发表
		String username = params[4];//审核用户名称
		String checkResult = params[5];//审核描述
		String type = params[6];//审核结果  通过或拒绝
		
		User user = null;
		String hql = SQL.User.findUserById(userId);
		List<User> users = (List<User>) this.userDao.findAll(hql);
		if (null != users && users.size()>0) {
			user = users.get(0);
		}

		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//审核成功
		if("approve".equals(type.trim())){
			
			
			hql = "from UnCertAward where id= "+uid;
			UnCertAward ua = null;
			List<UnCertAward> unCertAwards = (List<UnCertAward>) this.findAll(hql);
			if (null != unCertAwards && unCertAwards.size() > 0) {
				ua = unCertAwards.get(0);
			}
			
			
			hql = "from UnCert where id="+cid;
			UnCert uc = null;
			List<UnCert> unCerts = (List<UnCert>) this.findAll(hql);
			if (null!=unCerts && unCerts.size() > 0) {
				uc = unCerts.get(0);
			}
			
			//设置下次更新时间为一个月后
			Date nextUpdateTime = DateUtils.addMonths(now, 1);
			if (ua!=null && uc!=null ) {
			
				PrivateKey pk = certUtil.getPrivateFromKeyStore(CertCommonUtils.ROOTCA_PATH+CertCommonUtils.CA_NAME, 
								CertCommonUtils.CA_ALIAS, 
								CertCommonUtils.PASSWORD, 
								CertCommonUtils.STORE_TYPE_JKS);
				//取得根证书文件  暂时由秘钥库中取得
				X509Certificate caCrlCert = 
						certUtil.getCertFromKeyStoreFile(
								CertCommonUtils.ROOTCA_PATH+CertCommonUtils.CA_NAME, 
								CertCommonUtils.CA_ALIAS, 
								CertCommonUtils.PASSWORD, 
								CertCommonUtils.STORE_TYPE_JKS);
				//取得要作废的证书
				X509Certificate subCert = certUtil.getCertFromCertFile(ua.getPath());
				
				//生成crl文件
				String crlPath = certUtil.createCertCrl(caCrlCert, pk, subCert, 30, CertCommonUtils.CRL_ONLINECERTSPATH);
				int index = crlPath.lastIndexOf("/");
				if (index<=0) {
					index = crlPath.lastIndexOf("\\");
				}
				String crlFname = crlPath.substring(index+1, crlPath.length());
				
				//更新证书吊销文件
				hql = "from UnCertCRL where status="+UnCertStatus.Status.enable;
				List<UnCertCRL> unCRLs = (List<UnCertCRL>) this.findAll(hql);
				UnCertCRL unCRL = null;
				if (null!=unCRLs && unCRLs.size() > 0) {
					unCRL = unCRLs.get(0);
					unCRL.setAwardDate(now);
					unCRL.setBatch(System.nanoTime()+"");
					unCRL.setFname(crlFname);
					unCRL.setPath(crlPath);
					unCRL.setFileType("CRL");
					unCRL.setRemark(checkResult);
					unCRL.setLastModifyOperator(user.getId());//修改人
					unCRL.setLastModifyOperators(user.getUsername());//修改人
					unCRL.setStatus(UnCertRevokeApp.UnCertRevokeAppStatus.Status.approve);//表示已吊销
					unCRL.setSerialnumber(CertCommonUtils.getSerialNumber(caCrlCert));
					unCRL.setNextUpdateTime(nextUpdateTime);//下一次更新时间
					unCRL.setLastModifyTime(now);//最近修改时间
					unCRL.setIssuredn(caCrlCert.getIssuerX500Principal().getName());
					unCRL.setRemark(checkResult);//备注
				} else {
					unCRL = new UnCertCRL();
					unCRL.setAwardDate(now);//证书签发人
					unCRL.setBatch(System.nanoTime()+"");//吊销的证书批次
					Integer createId = unCRL.getCreateOperator();
					String operator = unCRL.getCreateOperators();
					Date createTime = unCRL.getCreateTime();
					
					//创建人由第一次签发人设置
					if(null==createId || createId.equals("")) {
						unCRL.setCreateOperator(user.getId());
					}
					if (null==operator || operator.equals("")) {
						unCRL.setCreateOperators(user.getUsername());
					}
					if (null==createTime) {
						unCRL.setCreateTime(now);
					}
					unCRL.setFname(crlFname);
					unCRL.setPath(crlPath);
					unCRL.setFileType("CRL");
					unCRL.setRemark(checkResult);
					unCRL.setLastModifyOperator(user.getId());//修改人
					unCRL.setLastModifyOperators(user.getUsername());//修改人
					unCRL.setStatus(UnCertRevokeApp.UnCertRevokeAppStatus.Status.approve);//表示已吊销
					unCRL.setSerialnumber(ua.getSerialnumber());
					unCRL.setNextUpdateTime(nextUpdateTime);//下一次更新时间
					unCRL.setLastModifyTime(now);//最近修改时间
					unCRL.setIssuredn(caCrlCert.getIssuerX500Principal().getName());
					unCRL.setRemark(checkResult);//备注
				}
				
				//更新un_cert_crl实体表
				this.saveOrUpdateObj(unCRL);
				//构造更新语句
				hql = "update UnCertAward set status="+UnCertAward.UnCertAwardStatus.Status.disable+",disabledoperator='"+userId+(user==null?"":"disabledoperators='"+user.getName()+"'")+"',disableddate='"+SimpleDateFormatUtils.formatDateReturnYYYMMDD(new Date())+"' where id="+uid;
				this.updateWithSql(hql);
				
				//构造更新语句
			    hql = "update un_cert set status="+UnCert.UnCertStatus.Status.disable+" where id="+cid;
				this.updateWithSql(hql);
				
				
				hql = "update un_cert_revoke_app set status="+UnCertRevokeApp.UnCertRevokeAppStatus.Status.approve+",checkdate='"+sdf.format(now)+"',checkOperator="+userId+(user==null?"":",checkOperators='"+user.getName()+"'")+",checkResult='"+checkResult+"' where id="+revokeId;
				this.updateWithSql(hql);
									
				Integer id = unCRL.getId();
				uc.setCrl(id);
				uc.setCreateCrl(user.getId());
				uc.setCreateCrlTime(now);
									
				//更新证书实体文件
				this.updateObj(uc);
			}
		}//审核失败
		else {
			hql = "update un_cert_revoke_app set status="+UnCertRevokeApp.UnCertRevokeAppStatus.Status.deny+",checkdate='"+sdf.format(now)+"',checkOperator="+userId+(user==null?"":",checkOperators='"+user.getName()+"'")+",checkResult='"+checkResult+"' where id="+revokeId;
			this.updateWithSql(hql);
			return false;
		}
		
		return true;
	}
	
	
	/**
	 * 作废证书提交
	 */
	public boolean disabledCert(String uaid,String cid,String userid) {
		String hql = "from UnCertAward where id = "+uaid;
		
		
		List<UnCertAward> certs = (List<UnCertAward>) findAll(hql);
		
		if(null != certs && 0 < certs.size())
		{
			UnCertAward ua = certs.get(0);
			
//			String path = ua.getPath();//证书下载路径
			
//			System.out.println("path: "+path);
			Date now = new Date();
			
			hql = SQL.User.findUserById(uaid);
			User user = null;
			List<User> users = (List<User>) this.userDao.findAll(hql);
			if (null!=users && users.size()>0) {
				user = users.get(0);
			}
			
			//构造更新语句
			hql = "update UnCertAward set status="+UnCertAward.UnCertAwardStatus.Status.disable+",disabledoperator='"+userid+(user==null?"":"disabledoperators='"+user.getName()+"'")+"',disableddate='"+SimpleDateFormatUtils.formatDateReturnYYYMMDD(new Date())+"' where id="+uaid;
			
			
			this.updateWithSql(hql);
			
			Date expiredDate = DateUtils.addDays(now, -1);
		    hql = "update un_cert set status="+UnCertAward.UnCertAwardStatus.Status.disable+",enddate='"+SimpleDateFormatUtils.formatDateReturnYYYMMDD(expiredDate)+"' where id="+cid;
			
			this.updateWithSql(hql);
			
			//更新证书吊销文件
			
			//更新实体证书文件
		}
		
		return true;
	}
	
	/**
	 * 证书签发
	 * @param app 证书申请实体
	 * @param user 系统用户
	 * @param ca 根证书
	 * @param uniq_batch 同一批次
	 * @return
	 * @throws Exception 
	 */
	public boolean signCert(UnCertApp app,User user,String ca, String uniq_batch,boolean isSignSevCert) throws Exception{

		boolean result = false;
		int isServOrTerm = 0;//服务器证书或者是终端证书
	
		StringBuffer sb = new StringBuffer();
		
		if (StringUtils.isNotBlank(app.getC())) {
			sb.append("C=" + app.getC());//
		}
		if (StringUtils.isNotBlank(app.getSt())) {
			sb.append(",S=" + app.getSt());
		}
		if (StringUtils.isNotBlank(app.getL())) {
			sb.append(",L=" + app.getL());
		}
		if (StringUtils.isNotBlank(app.getOu())) {
			sb.append(",OU="+app.getOu());
		}
		if (StringUtils.isNotBlank(app.getO())) {
			sb.append(",O="+app.getO());//
		}
		if (StringUtils.isNotBlank(app.getCn())) {
			sb.append(",CN="+app.getCn());//名称			
		}	
		String subjectDN=sb.toString();
		//证书类型
		int _certType = app.getCertType();
		int keySize = app.getKeySize() == null ? KEYSIZE : app.getKeySize();
		int validityYears = app.getDueTime() == null ? 1 : app.getDueTime();//证书有效日期
		String password = app.getSecret() == null ? "123456" : app.getSecret();//密钥密码
		String signAlgorithm = app.getSignAlgorithm(); //签名算法
		int newKeyPair = app.getNewKeyPair() == null ? UnCertApp.UnCertAppStatus.NewKeyPair.no : app.getNewKeyPair();  //是否创建秘钥对
		SimpleDateFormat Tsdf=new SimpleDateFormat("yyyyMMdd'T'HHmmss");
		Date start = new Date(System.currentTimeMillis() - 1000L * 60 * 60 * 24* 0);
		Date end = DateUtils.addYears(start, validityYears);
		String endDateStr = null;
		String starDateStr = null;
		try{
			starDateStr=Tsdf.format(start);
			endDateStr=Tsdf.format(end);
		}catch (Exception e) {
			throw new RuntimeException("日期格式转换错误！");
		}
		
		//根证书
		if(_certType == UnCertApp.UnCertAppStatus.CertType.root)
		{
			KeyPair rootkeypair = null;
			PublicKey publickey =  null;
			// 证书名称
			Date date = new Date();
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String certname = CertCommonUtils.ROOTCA_PREFIX;			
			String keyStoreName = CertCommonUtils.ROOT_CERT_NAME;//CertCommonUtils.KEYSTORETYPE_KEYSTORE;//密钥库名称
			X509Certificate rootCert=null;
			// 生成密钥对
			if(newKeyPair == UnCertApp.UnCertAppStatus.NewKeyPair.yes)
			{
				 rootkeypair = KeyUtil.generateKeyPair(keySize);
				 publickey = rootkeypair.getPublic();
				 
				 logger.debug("生成新密钥对,密钥长度为"+keySize);
				 rootCert = certUtil.signRootCertV3(subjectDN, validityYears, rootkeypair,signAlgorithm);
				 // 生成密钥库
				 certUtil.saveCertChainToKeyStore(CertCommonUtils.ROOTCA_ONLINECERTSPATH+ keyStoreName, 
						 new X509Certificate[] { rootCert },rootkeypair, CertCommonUtils.CA_ALIAS, password,CertCommonUtils.STORE_TYPE_JKS);
			}
			else//不生成密钥对，调用加密机接口自签根证书
			{	
			    rootCert = HttpInvoker.signRootCert(starDateStr, endDateStr, subjectDN);
			    if (rootCert == null) {
                    throw new RuntimeException("返回的证书数据为空！");
			    }
			    
			    publickey = rootCert.getPublicKey();
				/*Map<String, String> params=new HashMap<String, String>();
				params.put("msgtype", CertCommonUtils.ROOTMSGTYPE);//消息类型
				params.put("institution", null);//机构编码
				params.put("index", CertCommonUtils.ROOTINDEX);//根密钥索引
				params.put("startdate", starDateStr);//生效时间
				params.put("enddate", endDateStr);//失效时间
				params.put("dn", subjectDN);//证书关键信息
				String response = httpInvoker.post(CertCommonUtils.SJLURL, params);
				JSONObject jsonObject=JSONObject.fromObject(response);
				String code=jsonObject.getString("code");//返回码
				if(StringUtils.isNotBlank(code) && CertCommonUtils.CODE.equals(code))
				{
					String ansdata=jsonObject.getString("ansdata");
					if(StringUtils.isNotBlank(ansdata))
					{						
						SaveCert.SaveCer(BaseUtil.hexStringToBytes(ansdata));//保存根证书到本地
						rootCert=(X509Certificate) CreateCertUtil.generateCertificate(BaseUtil.hexStringToBytes(ansdata));//根据返回的证书数据生成证书
					}else {
						throw new RuntimeException("返回的证书数据为空！");
					}
				}*/
			}						
		
			// 签发成功后，更新原申请状态（证书申请表）
			app.setIsSign(1);
			app.setSigndate(date);

			// 保存证书颁发表
			UnCertAward uncertAward = new UnCertAward();

			uncertAward.setSignOperator(user.getId());
			uncertAward.setSignOperators(user.getName());
			app.setSignOperator(user.getId());
			app.setSignOperators(user.getName());
			app.setNewKeyPair(UnCertApp.UnCertAppStatus.NewKeyPair.no);
			result = updateObj(app);

			uncertAward.setUserid(app.getUserid());// 用户id
			uncertAward.setUsername(app.getUsername());// 用户名
			uncertAward.setFname(keyStoreName);// 密钥库文件名
			uncertAward.setBatch(app.getBatch());// 批次号
			uncertAward.setSerialnumber(CertCommonUtils.getSerialNumber(rootCert));//序列号
			uncertAward.setCtype(2);// 类型
			uncertAward.setStatus(1);// 状态
			uncertAward.setPath(CertCommonUtils.ROOTCA_ONLINECERTSPATH + keyStoreName);
			uncertAward.setAwardDate(date);// 签发日期
			uncertAward.setEffect(app.getEffect());//用途
			uncertAward.setAlias(CertCommonUtils.CA_ALIAS);//条目别名
			uncertAward.setCertType(UnCert.UnCertStatus.CertType.root);
			uncertAward.setIsExportCert(UnCertAward.UnCertAwardStatus.ExportCert.no);
			uncertAward.setFileType(CertCommonUtils.STORE_TYPE_JKS);
			uncertAward.setEnabled(UnCertAward.UnCertAwardStatus.Lock.unlock);
			result = saveOrUpdateObj(uncertAward);

			// 保存证书明细表
			// UnCertAward与实体UnCert 通过用户名 批次号 文件名关联
			UnCert cert = new UnCert();
			cert.setBatch(app.getBatch());// 批次号
			cert.setFname(keyStoreName);// 密钥库文件名
			cert.setUserid(app.getUserid());// 用户名
			cert.setUsername(app.getUsername());
			cert.setBegindate(rootCert.getNotBefore());
			cert.setEnddate(rootCert.getNotAfter());
			cert.setCtype(2);
			cert.setIssuredn(rootCert.getIssuerDN().getName());
			cert.setSubjectdn(rootCert.getSubjectDN().getName());
			/*cert.setPrivatekey(CertUtils.bytesToHexString(rootkeypair.getPrivate().getEncoded()));*/
			cert.setPublickey(CertUtils.bytesToHexString(publickey.getEncoded()));
			cert.setSerialnumber(CertCommonUtils.getSerialNumber(rootCert));
			cert.setSigalgname(rootCert.getSigAlgName());
			cert.setVersion(rootCert.getVersion());
			cert.setAlias(CertCommonUtils.CA_ALIAS);
			cert.setCertType(UnCert.UnCertStatus.CertType.root);
			cert.setSecret(app.getSecret());
			cert.setStatus(1);//证书为可用
			cert.setCreateOperator(user.getId());
			cert.setCreateOperators(user.getName());
			cert.setCreateTime(date);
			cert.setCreateCrl(0);
			cert.setEnabled(UnCertAward.UnCertAwardStatus.Lock.unlock);
			
			
			result = saveOrUpdateObj(cert);
		}
		//子证书签发
		else
		{
					
				String publicKeyStr = app.getPublickeyHex();
				PublicKey publicKey=null;
				if(StringUtils.isNotBlank(publicKeyStr))
				{					
					String publicKeys = publicKeyStr.replaceAll(" ","");
					publicKey = RSAEncrypt.loadPublicKeyByStr(publicKeys);
				}
				// 签发证书
				X509Certificate subCert = null;
				
				if (isSignSevCert) {
				    subCert = HttpInvoker.signInnerServerCert(Integer.parseInt(ca), starDateStr, endDateStr, subjectDN);
				} else {
				    subCert = HttpInvoker.signCert(app.getPublickeyHex(), starDateStr, endDateStr, subjectDN);
				}
				
				/*Map<String, String> params=new HashMap<String, String>();
				
				
				if(isSignSevCert)
				{
					params.put("msgtype", CertCommonUtils.SIGNMSGTYPE);//消息类型
					params.put("signindex", CertCommonUtils.SIGNINDEX);
				}
				if(!isSignSevCert)
				{					
					params.put("msgtype", CertCommonUtils.CERTMSGTYPE);//消息类型
					params.put("key", app.getPublickeyHex());//公钥
				}
				params.put("institution", null);//机构编码
				params.put("index", CertCommonUtils.ROOTINDEX);//根密钥索引
				params.put("startdate", starDateStr);//生效时间
				params.put("enddate", endDateStr);//失效时间
				params.put("dn", subjectDN);//证书关键信息
				String response = httpInvoker.post(CertCommonUtils.SJLURL, params);
				JSONObject jsonObject=JSONObject.fromObject(response);
				String code=jsonObject.getString("code");//返回码
				if(StringUtils.isNotBlank(code) && CertCommonUtils.CODE.equals(code))
				{
					String ansdata=jsonObject.getString("ansdata");
					if(StringUtils.isNotBlank(ansdata))
					{						
						subCert=(X509Certificate) CreateCertUtil.generateCertificate(BaseUtil.hexStringToBytes(ansdata));//根据返回的证书数据生成证书
					}else {
						throw new RuntimeException("返回的证书数据为空！");
					}
				}*/

				// 证书名称
				Date date = new Date();
				final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				String alias = app.getC() + sdf.format(date);
				String deviceId = app.getDeviceId();
				String ip = app.getIp();
				String certname = "";
				
				if (null!=deviceId && !deviceId.equals("")) {
					certname += deviceId;
				}else if (null!=ip && !ip.equals("")) {
					certname += ip;
				}
				certname += "."+CertCommonUtils.EXPORT_CERT_SUFFIX;
				//保存文件到服务器
				certUtil.saveCertToFile(CertCommonUtils.SUBCA_ONLINECERTSPATH+certname, subCert);
				
				app.setIsSign(1);
				app.setSigndate(date);

				// 保存证书颁发表
				UnCertAward uncertAward = new UnCertAward();

				uncertAward.setSignOperator(user.getId());
				uncertAward.setSignOperators(user.getName());
				app.setSignOperator(user.getId());
				app.setSignOperators(user.getName());
				app.setNewKeyPair(newKeyPair);

				System.out.println(com.alibaba.fastjson.JSONObject.toJSONString(app));
				result = updateObj(app);

				uncertAward.setUserid(app.getUserid());// 用户id
				uncertAward.setUsername(app.getUsername()); //用户名
				uncertAward.setFname(certname);// 密钥库文件名
				uncertAward.setBatch(app.getBatch());// 批次号
				uncertAward.setSerialnumber(CertCommonUtils.getSerialNumber(subCert));//序列号
				if (null != app.getDeviceId() && !app.getDeviceId().equals("")) {
					uncertAward.setCtype(4);
				}else if (null != app.getIp() && !app.getIp().equals("")) {
					uncertAward.setCtype(5);
				}
				uncertAward.setStatus(1);// 状态
				uncertAward.setPath(CertCommonUtils.SUBCA_ONLINECERTSPATH +certname);
				uncertAward.setAwardDate(date);// 签发日期
				uncertAward.setEffect(app.getEffect());//用途
				uncertAward.setAlias(alias);//条目别名
				uncertAward.setCertType(UnCert.UnCertStatus.CertType.child);
				uncertAward.setIsExportCert(UnCertAward.UnCertAwardStatus.ExportCert.yes);
				uncertAward.setFileType("CER");
				uncertAward.setEnabled(UnCertAward.UnCertAwardStatus.Lock.unlock);
				uncertAward.setUniqBatch(uniq_batch);//标识同一批公钥文件
				result = saveOrUpdateObj(uncertAward);

				// 保存证书明细表
				// UnCertAward与实体UnCert 通过用户名 批次号 文件名关联
				UnCert cert = new UnCert();
				cert.setBatch(app.getBatch());// 批次号
				cert.setFname(certname);// 密钥库文件名
				cert.setUserid(app.getUserid());// 用户名
				cert.setUsername(app.getUsername());
				cert.setBegindate(subCert.getNotBefore());
				cert.setEnddate(subCert.getNotAfter());
				if (null != app.getDeviceId() && !app.getDeviceId().equals("")) {
					cert.setCtype(4);
				}else if (null != app.getIp() && !app.getIp().equals("")) {
					cert.setCtype(5);
				}
				cert.setIssuredn(subCert.getIssuerDN().getName());
				cert.setSubjectdn(subCert.getSubjectDN().getName());
				if(publicKey!=null)
				{					
					cert.setPublickey(CertUtils.bytesToHexString(publicKey.getEncoded()));
				}
				cert.setSerialnumber(CertCommonUtils.getSerialNumber(subCert));
				cert.setSigalgname(subCert.getSigAlgName());
				cert.setVersion(subCert.getVersion());
				cert.setAlias(certname);//条目别名
				cert.setCertType(UnCert.UnCertStatus.CertType.child);
				cert.setSecret(app.getSecret()==null?"123456":app.getSecret());
				cert.setStatus(1);
				cert.setCreateOperator(user.getId());
				cert.setCreateOperators(user.getName());
				cert.setCreateTime(date);
				cert.setCreateCrl(0);
				cert.setEnabled(UnCertAward.UnCertAwardStatus.Lock.unlock);
				result = saveOrUpdateObj(cert);
			}				
		return result;
	}
	
	/**
	 * 导出证书 
	 * @param params
	 */
	public boolean exportCert(String ... params)
	{
		String id = params[0];
		String userId = params[1];
		
		String uql = SQL.User.findUserById(userId);
		
		List<User> users = (List<User>) this.userDao.findAll(uql);
		User user = null;
		if (null != users && users.size() > 0) {
			user = users.get(0);
		}
		
		String hql = "from UnCertAward where id = "+id;
		
		List<UnCertAward> unCertAwards = (List<UnCertAward>)this.certAdministrationDao.findAll(hql);
		
		if(unCertAwards!=null && !unCertAwards.isEmpty())
		{
			UnCertAward unCertAward = unCertAwards.get(0);
			
			hql = "from UnCert where batch='"+unCertAward.getBatch()+"'";
			List<UnCert> uNCerts = (List<UnCert>)this.certAdministrationDao.findAll(hql);
			
			UnCert uNCert = null;
			if(uNCerts != null && !uNCerts.isEmpty())
			{
				uNCert = uNCerts.get(0);
			}else
			{
				throw new RuntimeException("当前需要导出证书的密钥库不存在或非法!");
			}
			String path = unCertAward.getPath();//取得密钥库文件的完整路径
			String alias = unCertAward.getAlias();
			String fileType = unCertAward.getFileType();//密钥库类型 JKS
			String password = uNCert.getSecret();
			String fname = unCertAward.getFname();
			int certType = unCertAward.getCertType();
			
			String newFileName = fname.substring(0,fname.indexOf("."))+"."+CertCommonUtils.EXPORT_CERT_SUFFIX;
			String newPath = "";
			X509Certificate x509Certificate = null;
			if(certType == UnCertAward.UnCertAwardStatus.CertType.root)
			{
				newPath = CertCommonUtils.ROOTCA_ONLINECERTSPATH+newFileName;
				x509Certificate = HttpInvoker.getRootCert();//certUtil.getCertFromKeyStoreFile(path, alias, password, fileType);
				certUtil.saveCertToFile(newPath, x509Certificate);
			}
			else
			{
				if (fileType.equals("PFX")) {
					newPath = CertCommonUtils.SUBCA_ONLINECERTSPATH+newFileName;
					x509Certificate = certUtil.getCertFromKeyStoreFile(path, alias, password, fileType);
					//将证书保存到证书文件.CER
					certUtil.saveCertToFile(newPath, x509Certificate);
				}else {
					newPath = CertCommonUtils.SUBCA_ONLINECERTSPATH+newFileName;
//					x509Certificate = certUtil.getCertFromCertFile(path);
				}
			}
			
			
			//将证书保存到证书文件.CER
			
			unCertAward.setIsExportCert(UnCertAward.UnCertAwardStatus.ExportCert.yes);
			
			this.certAdministrationDao.saveOrUpdateObj(unCertAward);
			
			Date date = new Date();
			UnCertExport export = new UnCertExport();
			export.setBatch(unCertAward.getBatch());
			export.setCreateOperator(Integer.parseInt(userId));
			export.setCreateOperators(user.getName());
			export.setCreateTime(date);
			export.setDownLoadCount(0);
			export.setExportFlag(UnCertExport.UnCertExportStatus.ExportFlag.publicKey);
			export.setFileType(CertCommonUtils.EXPORT_CERT_SUFFIX);
			export.setFname(newFileName);
			export.setPath(newPath);
			export.setStatus(0);
			this.certAdministrationDao.saveOrUpdateObj(export);
			
			logger.debug("密钥库["+fname+"]生成Cer证书文件["+newFileName+"]");
		}else
		{
			throw new RuntimeException("当前需要导出证书的密钥库不存在或非法!");
		}
		
		return false;
	}

	@Override
	public Object findAllBySql(String sql) {
		return this.certAdministrationDao.findAllBySql(sql);
	}
	
}
