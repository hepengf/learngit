package com.congoal.cert.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.bouncycastle.asn1.x509.CRLReason;
import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.x509.extension.AuthorityKeyIdentifierStructure;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.congoal.cert.pojo.UnCert;
import com.congoal.cert.pojo.UnCert.UnCertStatus;
import com.congoal.cert.pojo.UnCertApp.UnCertAppStatus.NewKeyPair;
import com.congoal.cert.pojo.Sedevice;
import com.congoal.cert.pojo.UnCertApp;
import com.congoal.cert.pojo.UnCertAward;
import com.congoal.cert.pojo.UnCertBulk;
import com.congoal.cert.pojo.UnCertCRL;
import com.congoal.cert.pojo.UnCertRevokeApp;
import com.congoal.cert.pojo.User;
import com.congoal.cert.req.CertUtil;
import com.congoal.cert.service.CertAdministrationService;
import com.congoal.cert.service.SedeviceService;
import com.congoal.cert.service.UnCertAppService;
import com.congoal.cert.service.UnCertAwardService;
import com.congoal.cert.service.UnCertBulkService;
import com.congoal.cert.service.UnCertCRLService;
import com.congoal.cert.service.UnCertExportService;
import com.congoal.cert.service.UnCertRevokeAppService;
import com.congoal.cert.service.UnCertService;
import com.congoal.cert.test.CertUtils;
import com.congoal.cert.utils.BasicAction;
import com.congoal.cert.utils.CertCommonUtils;
import com.congoal.cert.utils.HttpInvoker;
import com.congoal.cert.utils.RSAEncrypt;

@SuppressWarnings("all")
@Scope("prototype")
@Controller("certAdministrationAction")
public class CertAdministrationAction extends BaseAction {
	
	public static String contextPath = ServletActionContext.getServletContext().getContextPath();
	private static Log logger = LogFactory
			.getLog(CertAdministrationAction.class);
	
	@Autowired
	private UnCertBulkService unCertBulkService;
	@Autowired
	private UnCertAwardService unCertAwardService;
	@Autowired
	private UnCertService unCertService;
	@Autowired
	private UnCertRevokeAppService unCertRevokeAppService;
	@Autowired
	private UnCertCRLService unCertCRLService;
	@Autowired
	private UnCertAppService unCertAppService;
	@Autowired
	private UnCertExportService unCertExportService;
	@Autowired
	private SedeviceService sedeviceService;
	
	
	@Autowired
	private CertUtil certUtil;
	@Autowired
	private CertAdministrationService certAdministrationService;

	private String message;
	private String redirectUrl;
	private File props;
	private String propsFileName;
	private String propsContentType;



	/**
	 * 
	 * 证书申请请求(页面跳转)
	 * 
	 * @return
	 */
	public String certAppRequest() {
		return SUCCESS;
	}
	
	
	
	public String subCertAppDetail() {
		String id = this.getRequest().getParameter("id");
		if (id == null || id.equals("")) {
			this.message = "传入的主键标识为空";
			return ERROR;
		}

		String hql = "from UnCertApp where id = " + id;
//		List<UnCertApp> apps = (List<UnCertApp>) certAdministrationService
//				.findAll(hql);
		List<UnCertApp> apps = unCertAppService.findAll(hql);

		if (apps != null && !apps.isEmpty()) {

			this.getRequest().setAttribute("app", apps.get(0));

			hql = "from UnCert where certType="
					+ UnCert.UnCertStatus.CertType.root + " and status="
					+ UnCert.UnCertStatus.Status.enable;
			List<UnCert> certs = (List<UnCert>) this.unCertService
					.findAll(hql);

			this.getRequest().setAttribute("certs", certs);

		}

		return SUCCESS;
	}

	/**
	 * 根证书申请
	 * 
	 * @return
	 */
	public String rootCertApp() {
		HttpServletRequest request = this.getRequest();
		String CN = request.getParameter("CN");//名称
		String C = request.getParameter("C");//国家
		String O = request.getParameter("O");//部门
		String OU = request.getParameter("OU");//公司
		String ST = request.getParameter("ST");//省份
		String L = request.getParameter("L");//城市
		String NET = request.getParameter("NET");//网址
		String EMAIL = request.getParameter("EMAIL");//邮件
		String effect = request.getParameter("effect");// 用途
		String DUETIME = request.getParameter("dueTime");// 证书期限
/*		String DUETIME = "10";*/
		String REMARK = request.getParameter("REMARK");// 备注

		String signAlgorithm = request.getParameter("signAlgorithm");// 签名算法
		String secret = request.getParameter("secret");// 密钥密码
		String resecret = request.getParameter("resecret");// 密钥确认密码
		String newKeyPairStr = request.getParameter("newKeyPair");// 是否需要创建新密钥
		String keySize = request.getParameter("keySize");// 密钥长度
		String importName = request.getParameter("importName");// 导入密钥文件名

		if (null == C || "".equals(C.trim())) {
			this.message = "证书名称为空";
			return ERROR;
		}

		
		UnCertApp ca = new UnCertApp();
		
		int newKeyPair = -1;
		if (null == newKeyPairStr || "".equals(newKeyPairStr.trim())) {
			this.message = "是否需要创建新密钥对为空";
			return ERROR;
		} else {
			try {
				newKeyPair = Integer.parseInt(newKeyPairStr);
			} catch (Exception e) {
				this.message = "是否需要创建新密钥对转换错误";
				return ERROR;
			}

			if (newKeyPair == UnCertApp.UnCertAppStatus.NewKeyPair.no) {
				ca.setSecret(null);
				ca.setKeySize(null);
			} else {
				
				if (null == secret || "".equals(secret.trim())) {
					this.message = "密钥密码为空";
					return ERROR;
				}

				if (null == resecret || "".equals(resecret.trim())) {
					this.message = "密钥确认密码为空";
					return ERROR;
				}

				if (!secret.equals(resecret)) {
					this.message = "两次输入的密钥密码不一致";
					return ERROR;
				}
				
				if (null == keySize || "".equals(keySize.trim())) {
					this.message = "密钥长度为空";
					return ERROR;
				}
				ca.setSecret(resecret);
				ca.setKeySize(Integer.parseInt(keySize));
			}
		}
		ca.setNewKeyPair(newKeyPair);

		Date now = new Date();

		ca.setC(C);
		ca.setCn(CN);
		ca.setL(L);
		ca.setSt(ST);
		ca.setO(O);
		ca.setOu(OU);
		ca.setEmail(EMAIL);
		ca.setNet(NET);
		ca.setCreatedate(now);
		ca.setIsSign(0);
		ca.setBatch(System.nanoTime() + "");
		ca.setCertType(UnCertApp.UnCertAppStatus.CertType.root);
		if (DUETIME != null && !"".equals(DUETIME)) {
			try {
				ca.setDueTime(Integer.parseInt(DUETIME));
			} catch (Exception e) {
				ca.setDueTime(1);
			}
		}

		ca.setRemark(REMARK);
		ca.setEffect(effect);
		ca.setSignAlgorithm(signAlgorithm);		
		ca.setPrivateKeyHex(null);
		ca.setPublickeyHex(null);
		ca.setStatus(UnCertApp.UnCertAppStatus.Status.uncheck);

		User user = (User) this.getRequest().getSession().getAttribute("user");
		ca.setUserid(user.getId());// 保存申请用户
		ca.setCreateTime(now);
		ca.setCreateOperator(user.getId());
		ca.setCreateOperators(user.getUsername());

		unCertAppService.saveOrUpdate(ca);

		return SUCCESS;
	}

	/**
	 * 根证书审核界面跳转
	 * 
	 * @return
	 */
	public String redirectRootCertCheck() {
		String hql = "from UnCertApp where status="
				+ UnCertApp.UnCertAppStatus.Status.uncheck
				+ " and isSign=0 and certType="
				+ UnCertApp.UnCertAppStatus.CertType.root + " order by id desc";// 待审核证书信息
		List<UnCertApp> certapps = (List<UnCertApp>) unCertAppService
				.findAll(hql);

		this.getRequest().setAttribute("UnSignCertList", certapps);
		return SUCCESS;
	}

	/**
	 * 子证书审核界面跳转
	 * 
	 * @return
	 */
	public String redirectCertCheck() {
		String hql = "from UnCertApp where status="
				+ UnCertApp.UnCertAppStatus.Status.uncheck
				+ " and isSign=0 and certType="
				+ UnCertApp.UnCertAppStatus.CertType.child
				+ " order by id desc";// 待审核证书信息
		List<UnCertApp> certapps = (List<UnCertApp>) unCertAppService
				.findAll(hql);

		this.getRequest().setAttribute("UnSignCertList", certapps);
		return SUCCESS;
	}

	/**
	 * 证书审核
	 * @return
	 */
	public String certCheck() {
		HttpServletRequest request = this.getRequest();
		String certTypeStr = request.getParameter("certType");
		String result = request.getParameter("result");
		String id = request.getParameter("id");
		String checkResult = request.getParameter("checkResult");
		
		int certType = Integer.parseInt(certTypeStr);

		User user = getUser();
		int uid = user.getId();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = sdf.format(new Date());

		if (certType == UnCertApp.UnCertAppStatus.CertType.root) {
			// 根证书

			if ("approve".equals(result)) {
				// 审核通过

				String sql = "update un_cert_app set status="
						+ UnCertApp.UnCertAppStatus.Status.checkAppove
						+ ",checkOperator=" + uid + ",checkdate='" + now
						+ "',checkResult='"+checkResult+"' where id = " + id;
				this.unCertAppService.UpdateWithSql(sql);
				this.redirectUrl = "redirectRootCertCheckAction";

			} else {
				// 审核不通过
			}

		} else {
			// 子证书
			if ("approve".equals(result)) {
				// 审核通过

				String sql = "update un_cert_app set status="
						+ UnCertApp.UnCertAppStatus.Status.checkAppove
						+ ",checkOperator=" + uid + ",checkdate='" + now
						+ "',checkResult='"+checkResult+"' where id = " + id;
				this.unCertAppService.UpdateWithSql(sql);
				this.redirectUrl = "redirectCertCheckAction";

			} else {
				// 审核不通过
			}
		}
		return SUCCESS;
	}

	/**
	 * 子证书申请
	 * 
	 * @return
	 * @throws Exception 
	 */
	public String certApp() throws Exception{
		HttpServletRequest request = this.getRequest();
		String types=request.getParameter("ftype");
		if(types==null)
		{			
			message = "您好，文件错误，请重新选择！";
			return ERROR;
		}
		Integer type = Integer.parseInt(types);
		request.setAttribute("ftype", type);
		String signAlgorithm = "SHA1WithRSA";// 签名算法
		String DUETIME = "10";//根证书 默认是10年
		String c = "CN";//国家，默认为中国
		String s = "GZ";//省份，默认为贵州
		String l = "GY";//城市，默认为贵阳
		List<HashMap<String, Object>> pubKeys = upload(type);
		//文件中数据是否重复
		if (null==pubKeys || pubKeys.size() <= 0) {
			return ERROR;
		}
		
		String hql = "from UnCertApp where certType=2";
		List<UnCertApp> apps = unCertAppService.findAll(hql);
		HashMap<String, String> appItems = new HashMap<String, String>();
		if (null!=apps && apps.size() > 0) {
			for (int i = 0; i < apps.size(); i++) {
				UnCertApp app = apps.get(i);
				if (type==1) {
					appItems.put(app.getDeviceId(), app.getDeviceId());
				}else{
					appItems.put(app.getIp(), app.getIp());
				}
			}
		}
		//数据库中是否存在相同的数据
		for (int i = 0; i < pubKeys.size(); i++) {
			String CNCheck = (String) pubKeys.get(i).get(CertCommonUtils.MAP_SERVER_IP);
			if (appItems.containsKey(pubKeys.get(i).get(CertCommonUtils.MAP_SERVER_IP))) {
				if (CNCheck.indexOf(".") > 0) {//如果是ip值，则是服务器ip
					message = "您好，系统中已经存在服务器IP为："+
							pubKeys.get(i).get(CertCommonUtils.MAP_SERVER_IP)+"的数据！";
					return ERROR;
				} else {
					message = "您好，系统中已经存在设备表面号为："+
							pubKeys.get(i).get(CertCommonUtils.MAP_SERVER_IP)+"的数据！";
					return ERROR;
				}
			}
		}
		
		User user = null;
		String unique = request.getSession().getId()+""+System.nanoTime();
		logger.info("unique: "+unique);
		int count = pubKeys.size();
		
		for (int i = 0; i < pubKeys.size(); i++) {
			HashMap<String, Object> keyInfo = pubKeys.get(i);
			String CN = (String) keyInfo.get(CertCommonUtils.MAP_SERVER_IP);
			String company = (String) keyInfo.get(CertCommonUtils.MAP_COMPANY);
			String publicKeyStr = (String) keyInfo.get(CertCommonUtils.MAP_PUBLIC_KEY);
			logger.info("CN  "+CN);
			logger.info("publicKeyStr  "+publicKeyStr);
			UnCertApp app = new UnCertApp();
			app.setCompany(company);
			Date now = new Date();
			
			if (CN.indexOf(".") > 0) {//如果是ip值，则是服务器ip
				app.setIp(CN);
				app.setCn(CN);
				DUETIME = CertCommonUtils.SERVICE_DUETIME;//服务器证书 有效期（年）
			} else {
				app.setDeviceId(CN);
				app.setCn(CN);
				DUETIME = CertCommonUtils.DEVICE_DUETIME;//终端证书有效期（年）
			}
			app.setC(c);
			app.setSt(s);
			app.setL(l);
			app.setOu(company);
			app.setO(company);
			app.setCreatedate(now);
			app.setBatch(System.nanoTime() + "");
			app.setCertType(UnCertApp.UnCertAppStatus.CertType.child);
			
			if (DUETIME != null && !"".equals(DUETIME)) {
				try {
					app.setDueTime(Integer.parseInt(DUETIME));
				} catch (Exception e) {
					app.setDueTime(1);
				}
			}
			
			app.setSignAlgorithm(signAlgorithm);
			app.setStatus(UnCertApp.UnCertAppStatus.Status.checkAppove);
			app.setIsSign(0);
			try {
				//验证
				String validKeyStr = publicKeyStr.replaceAll(" ", "").toUpperCase();
				logger.info("publicKeyStr: "+validKeyStr);
				PublicKey publicKeys = RSAEncrypt.loadPublicKeyByStr(validKeyStr);
				logger.info("publicKeys: "+CertUtils.bytesToHexString(publicKeys.getEncoded()));
				app.setPublickeyHex(publicKeyStr);
			}catch(Exception e) {
				message = "您好，导入的公钥非法,请检查后再次导入!";
				return ERROR;
			}
			app.setNewKeyPair(NewKeyPair.no);
			user = (User) this.getRequest().getSession().getAttribute("user");
			app.setUserid(user.getId());// 保存申请用户
			app.setUsername(user.getName());
			app.setCreateOperator(user.getId());
			app.setCreateOperators(user.getName());
			app.setCreateTime(now);
			//保存证书实体
			try {
				unCertAppService.saveOrUpdate(app);//签发证书
				certAdministrationService.signCert(app, user, null, unique,false);	
				if(app.getCn().indexOf(".") < 0){
					Sedevice sedevice=new Sedevice();
					sedevice.setSenumber(app.getCn());
					sedevice.setPerson(app.getCompany());
					sedevice.setType(2);
					sedevice.setStatus(0);
					sedevice.setCreatetime(getNow());
					sedeviceService.save(sedevice);//保存到设备表
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("e "+e);
				message = "您好，导入失败！";
				return ERROR;
			}
				
			
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String fname = "TERMINATED_"+sdf.format(new Date())+".zip";
		String fpath =  CertCommonUtils.ZIPFILE_ONLINECERTSPATH;
		hql = "from UnCertAward where status=1 and uniqBatch='"+unique+"'";
		List<UnCertAward> uncerts = (List<UnCertAward>) this.unCertAwardService.findAll(hql);
		try {
			CertUtils.zipAllFiles(uncerts, CertCommonUtils.ZIPFILE_ONLINECERTSPATH, fname);
		} catch (Throwable e) {
			logger.info("e "+e);
			message = "压缩文件失败！";
			return ERROR;
		}
		
		UnCertBulk bulk = new UnCertBulk();
		bulk.setCount(count);
		bulk.setCreateOperator(user.getId());
		bulk.setCreateOperators(user.getName());
		bulk.setCreateTime(new Date());
		bulk.setDownFname(fname);
		bulk.setDownpath(fpath+fname);
		bulk.setFtype(type);
		bulk.setFname(getPropsFileName());
		this.unCertBulkService.saveOrUpdate(bulk);
		return SUCCESS;
	}
	/**
	 * 根证书导入
	 * @return
	 */
	public String rootExport() {
		return SUCCESS;
	}
	/**
	 * 根证书详细信息
	 * @return
	 */
	public String certInfo() {
		return "";
	}
	/**
	 * 根证书信息
	 * @return
	 */
	public String rootInfo() {
		CertUtil certUtil = new CertUtil();
		
		HttpServletRequest request = this.getRequest();
		// 获取CA的根证书
		X509Certificate root = certUtil.getCertFromCertFile(CertCommonUtils.ROOTCA_ONLINECERTSPATH+CertCommonUtils.ROOT_CERT_NAME);
		if (root == null) {
		    message = "根证书不存在";
            return ERROR;
		}
		/*X509Certificate root = certUtil.getCertFromKeyStoreFile(
				CertCommonUtils.ROOTCA_PATH + CertCommonUtils.CA_NAME,
				CertCommonUtils.CA_ALIAS, CertCommonUtils.PASSWORD,
				CertCommonUtils.STORE_TYPE_JKS);*/
		logger.info("contextPath: "+request.getContextPath());
		int version = root.getVersion();
		Principal issuerDN = root.getIssuerDN();
		Principal subjectDN = root.getSubjectDN();
		String type = root.getType();
		boolean[] issuerUniqueID = root.getIssuerUniqueID();
		BigInteger serialNumber = root.getSerialNumber();//序列号
		String sigAlgName = root.getSigAlgName();//签名算法
		String sigAlgOID = root.getSigAlgOID();
		Date notAfter = root.getNotAfter();
		Date notBefore = root.getNotBefore();
		int year = notAfter.getYear() - notBefore.getYear();
		byte[] signature = root.getSignature();

		
		request.setAttribute("version", version);
		request.setAttribute("issureDN", issuerDN.getName());
		request.setAttribute("type", type);
		request.setAttribute("serialNumber", CertCommonUtils.getSerialNumber(root));
		request.setAttribute("subjectDN", subjectDN.getName());
		request.setAttribute("sigAlgName", sigAlgName);
		request.setAttribute("sigAlgOID", sigAlgOID);
		request.setAttribute("notAfter", notAfter);
		request.setAttribute("notBefore", notBefore);
		request.setAttribute("duetime", year);
//		request.setAttribute("publicKey", CertUtils.bytesToHexString(publicKey.getEncoded()));

		return SUCCESS;
	}
	/**
	 * 查询待签名证书
	 * 
	 * @return
	 */
	public String findUnSignCert() {
		String hql = "from UnCertApp where status="
				+ UnCertApp.UnCertAppStatus.Status.checkAppove
				+ " and isSign=0 order by id desc";// 待签发证书信息
		List<UnCertApp> certapps = (List<UnCertApp>) unCertAppService
				.findAll(hql);
		this.getRequest().setAttribute("UnSignCertList", certapps);
		return SUCCESS;
	}
	/**
	 * 查询根证书信息
	 * 
	 * @return
	 */
	public String findRootCertInfo() {
		CertUtil certUtil = new CertUtil();
		X509Certificate ca = certUtil.getCertFromKeyStoreFile(
				CertCommonUtils.ROOTCA_PATH + CertCommonUtils.CA_NAME,
				CertCommonUtils.CA_ALIAS, CertCommonUtils.PASSWORD,
				CertCommonUtils.STORE_TYPE_JKS);
		
		return SUCCESS;
	}
	/**
	 * 查询已签名证书
	 * 
	 * @return
	 */
	public String findSignCert() {
		String hql = "from UnCertAward where status=1 and certType=1  order by awardDate desc";// 签发证书信息
		List<UnCertAward> certapps = (List<UnCertAward>) unCertAwardService
				.findAll(hql);

		this.getRequest().setAttribute("SignCertList", certapps);
		return SUCCESS;
	}
	/**
	 * 查询已签发的终端证书
	 * 
	 * @return
	 */
	public String findSignDevCert() {
		HttpServletRequest request = this.getRequest();
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String status = request.getParameter("status");
		
		String hql = "from UnCertAward ua where certType=2 and batch in "
				+ "(select batch from UnCertApp where deviceId is not null) "
				+ "";
		
		if (StringUtils.isNotBlank(startDate)) {
			hql += " and ua.awardDate >='"+startDate+"'";
		}
		
		if (StringUtils.isNotBlank(endDate)) {
			hql += " and ua.awardDate <= '"+endDate+"'";
		}
		
		if (StringUtils.isNotBlank(status)) {
			hql += " and status='"+status+"'";
		}		
		int totalSize = unCertAwardService.getTotalSize(hql);
		pager.setTotalSize(totalSize);		
		hql += " order by awardDate desc";
		List<UnCertAward> certapps = (List<UnCertAward>) unCertAwardService.findAllCerts(hql, pager);
		logger.info("---------------->size: "+certapps.size());
		this.getRequest().setAttribute("startDate", startDate);
		this.getRequest().setAttribute("endDate", endDate);
		this.getRequest().setAttribute("SignCertList", certapps);
		this.getRequest().setAttribute("status", status);
		return SUCCESS;
	}
	/**
	 * 查询已签发的服务器证书
	 * @return
	 */
	public String findSignSevCert() {
		HttpServletRequest request = this.getRequest();
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String status = request.getParameter("status");
		
		String hql = "from UnCertAward ua where certType=2 and batch in "
				+ "(select batch from UnCertApp where ip is not null) "
				+ "";
		if (StringUtils.isNotBlank(startDate)) {
			hql += " and ua.awardDate >='"+startDate+"'";
		}
		
		if (StringUtils.isNotBlank(endDate)) {
			hql += " and ua.awardDate <= '"+endDate+"'";
		}
		
		if (StringUtils.isNotBlank(status)) {
			hql += " and status='"+status+"'";
		}
		
		int totalSize = unCertAwardService.getTotalSize(hql);
		pager.setTotalSize(totalSize);
		
		hql += " order by awardDate desc";
		
		
		List<UnCertAward> certapps = (List<UnCertAward>) unCertAwardService.findAllCerts(hql, pager);
		this.getRequest().setAttribute("startDate", startDate);
		this.getRequest().setAttribute("endDate", endDate);
		this.getRequest().setAttribute("SignCertList", certapps);
		this.getRequest().setAttribute("status", status);
		return SUCCESS;
	}
	/**
	 * 证书签发
	 * 
	 * @return
	 * @throws Exception
	 */
	public String signCert() throws Exception {
		HttpServletRequest request = this.getRequest();
		boolean result = false;
		String id = request.getParameter("id");// 证书申请id
		String certType = request.getParameter("certType");// 证书类型
		String rootCert = request.getParameter("rootCert");// 签发的根证书
		logger.info("contextPath: "+contextPath);
		if (null == id || "".equals(id))
			return ERROR;

		String hql = "from UnCertApp where id=" + id;
		List<UnCertApp> unCertApps = (List<UnCertApp>) unCertAppService
				.findAll(hql);

		if (null != unCertApps || 0 < unCertApps.size()) {
			User user = (User) this.getSession().get("user");

			if (null == user) {
				// 取得当前用户实例
				user = (User) SecurityContextHolder.getContext()
						.getAuthentication().getPrincipal();
				// 把登陆后的用户放入session中
				this.getRequest().getSession().setAttribute("user", user);
			}

			certAdministrationService.signCert(unCertApps.get(0), user,
					rootCert, "",false);

		} else {
			request.setAttribute("ErrorMessage", "证书不存在");
			return ERROR;
		}

		return SUCCESS;
	}
	
	/**
	 * 生成证书吊销文件
	 * 
	 * @return
	 * @throws Exception
	 */
	public String genCRL() throws Exception {
		String certsPath = "C:\\Documents and Settings\\huang\\桌面\\证书\\openssl\\openssl-0.9.8h-1-bin\\bin\\testca\\C03RootCA\\";

		// 生成crl证书吊销列表文件
		org.bouncycastle.x509.X509V2CRLGenerator crlGen = new org.bouncycastle.x509.X509V2CRLGenerator();

		Date now = new Date();
		Date nextUpdate = DateUtils.addDays(now, 3);// 下一次更新时间为三天后
		X509Certificate caCrlCert = certUtil.getCertFromKeyStoreFile(certsPath
				+ "ssl\\C03CA1.keystore", "1", "123456", "JKS");
		PrivateKey caCrlPrivateKey = certUtil.getPrivateFromKeyStore(certsPath
				+ "ssl\\C03CA1.keystore", "1", "123456", "JKS");

		X509Certificate subCert = certUtil.getCertFromKeyStoreFile(certsPath
				+ "ssl\\Client2FromCA.pfx", "client2", "123456", "PKCS12");

		// System.err.println("SubCert SerialNumber: "+subCert.getSerialNumber().intValue());

		crlGen.setIssuerDN(caCrlCert.getIssuerX500Principal());
		crlGen.setThisUpdate(now);
		crlGen.setNextUpdate(nextUpdate);

		// System.err.println(sdf.format(nextUpdate));

		crlGen.setSignatureAlgorithm("SHA512withRSA");

		crlGen.addExtension(X509Extensions.AuthorityKeyIdentifier, false,
				new AuthorityKeyIdentifierStructure(caCrlCert));
		crlGen.addExtension(
				X509Extensions.CRLNumber,
				false,
				new org.bouncycastle.asn1.x509.CRLNumber(caCrlCert
						.getSerialNumber()));

		crlGen.addCRLEntry(subCert.getSerialNumber(), now,
				CRLReason.privilegeWithdrawn);

		X509CRL crl = crlGen.generateX509CRL(caCrlPrivateKey, "BC");

		SimpleDateFormat sdf = new SimpleDateFormat("hhmmss");

		FileOutputStream fos = new FileOutputStream(
				"D:\\workspace10\\CertWebTest\\WebRoot\\web\\crl\\test"
						+ sdf.format(new Date()) + ".crl");
		fos.write(crl.getEncoded());
		fos.close();

		this.getRequest().setAttribute("crl", crl);
		return SUCCESS;
	}

	/**
	 * 查询可作废证书列表
	 */
	public String disabledCertCheck() {
		User user = this.getUser();

		int userid = user.getId();

		String sql = "select ua.id,ua.userid,ua.signOperator,ua.awardDate,ua.fname,"// 0-4
				+ "ua.batch,ua.certType,u.tid,u.serialnumber,u.issuredn,u.begindate,"// 5-10
				+ "u.enddate,u.ctype,u.version,u.subjectdn,u.certType "// 11-15
				+ "from Un_Cert_Award ua,"
				+ "(select id as tid,serialnumber,issuredn,begindate,enddate,ctype,version,subjectdn,certType,batch,userid,status,fname from Un_Cert) u "
				+ "where ua.batch = u.batch and ua.fname = u.fname and ua.userid = u.userid and u.status="
				+ UnCertStatus.Status.enable + "";// 签发证书信息

		List<Object[]> certs = (List<Object[]>) unCertAwardService.findAllWithSql(sql);
		
		this.getRequest().setAttribute("CertLists", certs);
		return SUCCESS;
	}

	/**
	 * 作废证书申请
	 * @return
	 */
	public String disabledCertApp() {
		HttpServletRequest request = this.getRequest();
		String type = request.getParameter("type");
		String uid = request.getParameter("cid");// 证书实体表

		if (uid == null || "".equals(uid.trim())) {
			this.message = "传入的证书标识为空!";
			return ERROR;
		}
		
		this.certAdministrationService.disabledCert(uid, this.getUser());
		if (null==type || type.equals("")) {
			return "mobile";
		}
		return type;
	}

	/**
	 * 证书吊销申请审核列表
	 * 
	 * @return
	 */
	public String redirectDisabledCertAppCheck() {
		HttpServletRequest request = this.getRequest();
		String cid = request.getParameter("cid");
		String uaid = request.getParameter("uaid");
		logger.info("uaId=========: "+uaid);

		String sql = "select r.id,r.reason,r.createTime,r.createOperator,r.certid,c.subjectdn,c.enddate,c.serialnumber from UN_CERT_REVOKE_APP r,UN_CERT c where r.certid=c.id and c.status="
				+ UnCert.UnCertStatus.Status.beforeDisable;// 0-4

		List<Object[]> certs = (List<Object[]>) unCertRevokeAppService.findAllWithSql(sql);

		this.getRequest().setAttribute("CertLists", certs);
		this.getRequest().setAttribute("uaid", uaid);
		return SUCCESS;
	}

	/**
	 * 作废证书
	 * 
	 * @return
	 */
	/*
	 * public String disabledCert() { HttpServletRequest request =
	 * this.getRequest(); String uaid = request.getParameter("uaid");// 证书颁发表
	 * String cid = request.getParameter("cid");// 证书实体表 if
	 * (StringUtils.isEmpty(uaid) || StringUtils.isEmpty(cid)) {
	 * request.setAttribute("Message", "传入的主键标识为空"); return ERROR; }
	 * 
	 * User user = (User) this.getSession().get("user"); String userid = ""; if
	 * (null == user) { // 取得当前用户实例 user = (User)
	 * SecurityContextHolder.getContext() .getAuthentication().getPrincipal();
	 * // 把登陆后的用户放入session中 this.getRequest().getSession().setAttribute("user",
	 * user); userid = user.getName(); } else { userid = user.getName(); }
	 * 
	 * certAdministrationService.disabledCert(uaid, cid, userid);
	 * 
	 * return SUCCESS; }
	 */

	/**
	 * 证书吊销审核跳转
	 * 
	 * @return
	 */
	public String redirectDisabledCertCheck() {
		HttpServletRequest request = this.getRequest();
		String revokeId = request.getParameter("revokeId");// 证书吊销表
		String cid = request.getParameter("cid");// 证书实体表
		String uaid = request.getParameter("uaid");//实体颁发表

		logger.info("revokeId ====>"+revokeId);
		logger.info("cid =====>"+cid);
		logger.info("uaid ====>"+uaid);
		String sql = "select r.id,r.reason,r.createTime,r.createOperator, r.createOperators,"
				+ "r.certid,c.subjectdn,c.issuredn,c.begindate,"
				+ "c.enddate,c.serialnumber,c.createOperators,c.createOperator,"
				+ "ua.signOperators,ua.signOperator "
				+ " from UN_CERT_REVOKE_APP r,UN_CERT c, UN_CERT_AWARD ua "
				+ "where r.certid=c.id and c.batch=ua.batch and c.id="
				+ cid + " and ua.id="+uaid+" and r.id=" + revokeId;// 0-4
		List<Object[]> certs = (List<Object[]>) unCertService
				.findAllWithSql(sql);

		if (certs != null && !certs.isEmpty()) {
			this.getRequest().setAttribute("certDetail", certs.get(0));
			this.getRequest().setAttribute("uaid", uaid);
		}

		return SUCCESS;
	}

	/**
	 * 根证书申请
	 * 
	 * @return
	 */
	public String redirectRootCertApp() {
		return SUCCESS;
	}

	/**
	 * 证书申请明细查询
	 * 
	 * @return
	 */
	public String certAppDetail() {
		String id = this.getRequest().getParameter("id");
		String type = this.getRequest().getParameter("type");// check - 审核
																// detail -明细查询
		if (id == null || id.equals("")) {
			this.message = "传入的主键标识为空";
			return ERROR;
		}

		String hql = "from UnCertApp where id = " + id;
		List<UnCertApp> apps = (List<UnCertApp>) unCertAppService
				.findAll(hql);

		if (apps != null && !apps.isEmpty()) {
			this.getRequest().setAttribute("type", type);// type="check" 审核

			this.getRequest().setAttribute("app", apps.get(0));
		}
		return SUCCESS;
	}

	/**
	 * 导出证书
	 * 
	 * @return
	 */
	public String exportCert() {
		String id = this.getRequest().getParameter("id");
		String type = this.getRequest().getParameter("type");// root - 根 sub -子
		if (id == null || id.equals("")) {
			this.message = "传入的主键标识为空";
			return ERROR;
		}
		User user = this.getUser();

		this.certAdministrationService.exportCert(id, user.getId() + "");
		this.message = "系统提示：证书导出成功！";
		return SUCCESS;
	}

	/**
	 * 作废证书审核提交
	 * 
	 * @return
	 */
	public String disabledCert() {
		HttpServletRequest request = this.getRequest();
		String revokeId = request.getParameter("revokeId");
		String cid = request.getParameter("cid");// 证书实体表
		String uid = request.getParameter("uid");// 证书颁发表
		String result = request.getParameter("result");//审核结果
		String checkResult = request.getParameter("checkResult");//审核描述

		if (StringUtils.isEmpty(revokeId) || StringUtils.isEmpty(cid) || StringUtils.isEmpty(uid)) {
			this.message = "传入的主键标识为空";
			return ERROR;
		}
		User user = (User) this.getSession().get("user");
		// 审核通过
		if ("approve".equals(result)) {
			certAdministrationService.disabledCert(cid, revokeId, user.getId()+"",uid,
					user.getUsername(), checkResult, result);

		}
		
		return SUCCESS;
	}

	/**
	 * 可作废证书查询
	 * @return
	 */
	public String invokedCertList() {
		HttpServletRequest request = this.getRequest();
		String sql = "select ua.id,ua.userid,ua.signOperators,ua.awardDate,ua.fname,"// 0-4
				+ "ua.batch,ua.certType,u.tid,u.serialnumber,u.issuredn,u.begindate,"// 5-10
				+ "u.enddate,ua.ctype,u.version,u.subjectdn,u.certType,u.createOperators "// 11-15
				+ "from Un_Cert_Award ua,"
				+ "(select id as tid,serialnumber,issuredn,begindate,enddate,ctype,version,subjectdn,certType,batch,userid,status,fname,createOperators from Un_Cert) u "
				+ "where ua.batch = u.batch and ua.fname = u.fname and ua.certType=2 and u.status="
				+ UnCertStatus.Status.enable + "";
		List<Object[]> unCerts = (List<Object[]>) this.unCertService.findAllWithSql(sql);
		request.setAttribute("certs", unCerts);
		return SUCCESS;
	}
	/**
	 * 生成证书吊销列表跳转
	 * 
	 * @return
	 */
	public String redirectCreateCrl() {
		StringBuffer sb = new StringBuffer("from UnCert where status=");
		sb.append(UnCert.UnCertStatus.Status.disable);
		sb.append(" and createCrl=");
		sb.append(UnCert.UnCertStatus.CRL.no);

		List<UnCert> certs = (List<UnCert>) this.unCertService
				.findAll(sb.toString());

		this.getRequest().setAttribute("certs", certs);

		return SUCCESS;
	}

	/**
	 * 生成证书吊销列表
	 * 
	 * @return
	 */
	public String createCertCrl() {
		HttpServletRequest request = this.getRequest();
		
		String[] certs = request.getParameterValues("certs");
		
		if(certs == null || certs.length ==0)
		{
			this.message="没有可吊销的证书";
			return ERROR;
		}
		
		String hql = "from UnCert where certType="+UnCert.UnCertStatus.CertType.root+" and status="+UnCert.UnCertStatus.Status.enable;
		
		List<UnCert> rootCas = (List<UnCert>)this.unCertService.findAll(hql);
		
		if(rootCas != null && !rootCas.isEmpty())
		{
			UnCert rootCa = rootCas.get(0);
			String batch = rootCa.getBatch();
			
			hql = "from UnCertAward where batch='"+batch+"'";
			List<UnCertAward> unCertAwards = (List<UnCertAward>)this.unCertAwardService.findAll(hql);
			
			if(unCertAwards != null && !unCertAwards.isEmpty())
			{
				UnCertAward unCertAward = unCertAwards.get(0);
				
				this.certAdministrationService.createCertCrl(certs,this.getUser(),unCertAward,rootCa);
				return SUCCESS;
			}else
			{
				this.message = "没有可签发的根证书";
				return ERROR;
			}
			
		}
		else
		{
			this.message = "没有可签发的根证书";
			return ERROR;
		}
		
	}
	
	/**
	 * 吊销证书查询
	 * @return
	 */
	public String revokedCertList()
	{
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String id = this.getRequest().getParameter("id");
		
		String hql = "from UnCert uc where crl="+id;
		
		if (StringUtils.isNotBlank(startDate)) {
			hql += " and uc.createCrlTime >='"+startDate+"'";
		}
		
		if (StringUtils.isNotBlank(endDate)) {
			hql += " and uc.createCrlTime <= '"+endDate+"'";
		}
		logger.info("revokedCertList -- id======> "+id);
		int totalSize = this.unCertService.getTotalSize(hql);
		pager.setTotalSize(totalSize);
		hql+=" order by createCrlTime desc";
		List<UnCert> certs = (List<UnCert>)this.unCertService.findAllCerts(hql, pager);
		
		request.setAttribute("startDate", startDate);
		request.setAttribute("endDate", endDate);
		request.setAttribute("id", id);
		request.setAttribute("certs", certs);
		
		return SUCCESS;
	}
	
	/**
	 * 证书吊销列表查询
	 * @return
	 */
	public String certCrlCheck()
	{
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String hql = "from UnCertCRL uc where 1=1";
		if (!StringUtils.isEmpty(startDate) && !StringUtils.isBlank(startDate)) {
			hql += " and uc.awardDate >='"+startDate+"'";
		}
		
		if (!StringUtils.isEmpty(endDate) && !StringUtils.isBlank(endDate)) {
			hql += " and uc.awardDate <= '"+endDate+"'";
		}
		int totalSize = this.unCertCRLService.getTotalSize(hql);
		pager.setTotalSize(totalSize);
		hql+=" order by id desc";
		List<UnCertCRL> crls = (List<UnCertCRL>)this.unCertCRLService.findAllCRL(hql, pager);;
		
		this.getRequest().setAttribute("startDate", startDate);
		this.getRequest().setAttribute("endDate", endDate);
		this.getRequest().setAttribute("crls", crls);
		
		return SUCCESS;
	}

	public void setCertAdministrationService(
			CertAdministrationService certAdministrationService) {
		this.certAdministrationService = certAdministrationService;
	}

	public void setCertUtil(CertUtil certUtil) {
		this.certUtil = certUtil;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	

	public File getProps() {
		return props;
	}


	public void setProps(File props) {
		this.props = props;
	}


	public String getPropsFileName() {
		return propsFileName;
	}

	public void setPropsFileName(String propsFileName) {
		this.propsFileName = propsFileName;
	}

	public String getPropsContentType() {
		return propsContentType;
	}

	public void setPropsContentType(String propsContentType) {
		this.propsContentType = propsContentType;
	}
	/**
	 * 解析导入的excel
	 * @return
	 */
	private List<HashMap<String, Object>> upload(Integer ftype) {
		String filename = getPropsFileName();//文件名
		if(filename==null)
		{
			message = "您好，文件错误请重新选择！";
			return null;
		}
		if(filename.length()>60)
		{
			message = "您好，文件名过长！";
			return null;
		}
		logger.info("fileName: "+filename);
		String hql = "from UnCertBulk";
		List<UnCertBulk> bulks = this.unCertBulkService.findAll(hql);
		HashMap<String, String> temp = new HashMap<String, String>();
		for (int i = 0; i < bulks.size(); i++) {
			UnCertBulk bulk = bulks.get(i);
			//系统已经存在相同的文件名称
			if (bulk.getFname().trim().equals(filename.trim())) {
				message = "您好，同一个文件不能重复导入！";
				return null;
			}
		}
		try {
			File pro=getProps();
			InputStream inputStream = new FileInputStream(pro);
			Workbook workbook = null;
			//excel是2007还是2003
			if (is2007(getPropsFileName())) {
				workbook = new XSSFWorkbook(inputStream);
			} else {
				workbook = new HSSFWorkbook(inputStream);
			}
			
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> iter_rows = sheet.iterator();
			
			list = new ArrayList<HashMap<String,Object>>();
			int lastRowIndex = sheet.getPhysicalNumberOfRows();
			if(lastRowIndex<=0)
			{
				message = "您好，您导入的文件为空，请确认！";
				return null;
			}
			if(lastRowIndex<2)
			{
				message = "您好，您导入的文件无效，请确认！";
				return null;
			}
			int rowIndex = 0;
			while (iter_rows.hasNext()) {
				Row next = iter_rows.next();
				if (rowIndex==0) {
					//nothing do here
				}else{
					//
					String company = getCellNumberFormat2String(next.getCell(1));
					String uniqueFiled = getCellNumberFormat2String(next.getCell(2));
					String key = getCellNumberFormat2String(next.getCell(3));
					//如果excel文件中的某个数据为空，则为非法数据
					if (ftype==1 && uniqueFiled.contains(".")) {
						message = "您好，您导入的不是终端设备公钥文件，请确认！";
						return null;
					}else if (ftype==2 && !uniqueFiled.contains(".")) {
						message = "您好，您导入的不是服务器公钥文件，请确认！";
						return null;
					}
					if (uniqueFiled.equals("")) {
						message = "您好，文件中第"+rowIndex+"条数据存在空值！";
						return null;
					}
					else {
						if (uniqueFiled.indexOf(".") > 0) {//如果是ip值，则是服务器ip
							if(company==null||company.trim()=="")
							{
								message = "您好，文件中第"+rowIndex+"条数据接入商为空！";
								return null;
							}
							if(company.length()>50)
							{
								message = "您好，文件中第"+rowIndex+"条数据接入商名称称过长！";
								return null;
							}
							if(key==null||key.trim()=="")
							{
								message = "您好，文件中第"+rowIndex+"条数据服务器公钥为空！";
								return null;
							}
						}else
						{
							if(company==null||company.trim()=="")
							{
								message = "您好，文件中第"+rowIndex+"条数据接入公司为空！";
								return null;
							}
							if(company.length()>50)
							{
								message = "您好，文件中第"+rowIndex+"条数据公司名称过长！";
								return null;
							}
							if(key==null||key.trim()=="")
							{
								message = "您好，文件中第"+rowIndex+"条数据设备公钥为空！";
								return null;
							}
						}
					}
					try {
						//验证
						String validKeyStr = key.replaceAll(" ", "").toUpperCase();
						logger.info("publicKeyStr: "+validKeyStr);
						PublicKey publicKeys = RSAEncrypt.loadPublicKeyByStr(validKeyStr);
						logger.info("publicKeys: "+CertUtils.bytesToHexString(publicKeys.getEncoded()));
					}catch(Exception e) {
						message = "您好，文件中第"+rowIndex+"条数据公钥非法,请检查后再次导入!";
						return null;
					}
					HashMap<String, Object> item = new HashMap<String, Object>();
					item.put(CertCommonUtils.MAP_SERVER_IP, uniqueFiled);//标识符(设备表面号/服务器ip)
					item.put(CertCommonUtils.MAP_COMPANY, company);//厂家/接入服务商
					item.put(CertCommonUtils.MAP_PUBLIC_KEY, key);//公钥
				    String ip = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}"; 
				    String uniqueNo = "^\\w{1,16}$"; 
				    Pattern pattern = Pattern.compile(ip);   
				    Matcher matcher = pattern.matcher(uniqueFiled); 
				    Pattern patternun = Pattern.compile(uniqueNo);   
				    Matcher matcherun = patternun.matcher(uniqueFiled); 
				    boolean is=matcher.matches();
				    if(ftype==2)
				    {
						if(!matcher.matches()){
							message = "您好，文件中IP为："+uniqueFiled+" 的数据错误！";
							return null;
						}
						
						if (temp.containsKey(uniqueFiled)) {
							message = "您好，文件中存在多条IP为："+uniqueFiled+" 的数据！";
							return null;
						}
				    }
				    else if (ftype==1)
				    {
						if(!matcherun.matches()){
							message = "您好，文件中设备唯一号为："+uniqueFiled+" 的数据错误！";
							return null;
						}
						if(uniqueFiled.length()!=16){
							message = "您好，文件中设备唯一号为："+uniqueFiled+" 的数据长度不是16位！";
							return null;
						}
				    	if (temp.containsKey(uniqueFiled)) {
							message = "您好，文件中存在多条设备唯一号为："+uniqueFiled+" 的数据！";
							return null;
						} 
				    }
					temp.put(uniqueFiled, uniqueFiled);				
					logger.info("sedCell: "+uniqueFiled);
					logger.info("company: "+company);
					logger.info("pubCell: "+key);
					logger.info("rowIndex: "+rowIndex);
					list.add(item);
				}
				
				rowIndex++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return list;
	}
	
	/**
	 * excel文件是否是2007格式
	 * @param filename
	 * @return
	 */
	private boolean is2007(String filename) {
		if (filename.endsWith("xlsx")) {
			return true;
		}
		return false;
	}
	/**
	 * 
	 * @param cell
	 * @return
	 */
	private String getCellNumberFormat2String(Cell cell) {
		String value = "";
		if (null==cell) {
			return "";
		}
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_NUMERIC:
			Double numericCellValue = cell.getNumericCellValue();
			Integer iv = numericCellValue.intValue();
			if (iv.equals("0")) {
				value = "";
			} else {
				value = iv.toString();
			}
			
			break;
		case Cell.CELL_TYPE_STRING:
			value = cell.getStringCellValue()==null?"":cell.getStringCellValue();
			break;
		default:
			value = "";
			break;
		}
		return value;
	}
	/**
	 * 批量导入跳转界面
	 * @return
	 */
	public String bulkImport() {
		HttpServletRequest request = this.getRequest();
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String createOperators = request.getParameter("createOperators");
		String ftype = request.getParameter("ftype");
		
		String hql = "from UnCertBulk uc where 1=1";
		if (!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(startDate)) {
			hql += " and uc.createTime >= '"+startDate+"'";
		}
		if (!StringUtils.isEmpty(endDate) && !StringUtils.isEmpty(endDate)) {
			hql += " and uc.createTime <= '"+endDate+"'";
		}
		if (!StringUtils.isEmpty(createOperators) && !StringUtils.isEmpty(createOperators)) {
			hql += " and uc.createOperators like '%"+createOperators+"%'";
		}
		if (!StringUtils.isEmpty(ftype) && !StringUtils.isEmpty(ftype)) {
			hql += " and ftype = '"+ftype+"'";
		}
		int totalSize = this.unCertBulkService.getTotalSize(hql);
		pager.setTotalSize(totalSize);
		hql += " order by createTime desc";
		List<UnCertBulk> certs = (List<UnCertBulk>) this.unCertBulkService.findAllUnCertBulks(hql, pager);
		this.getRequest().setAttribute("startDate", startDate);
		this.getRequest().setAttribute("endDate", endDate);
		this.getRequest().setAttribute("certs", certs);
		this.getRequest().setAttribute("ftype", ftype);
		this.getRequest().setAttribute("createOperators", createOperators);
		return SUCCESS;
	}
	/**
	 * 证书导入界面跳转
	 * @return
	 */
	public String bulkImportFile() {
		String flag = this.getRequest().getParameter("ftype");
		this.getRequest().setAttribute("ftype", flag);
		return SUCCESS;
	}
	/**
	 * 冻结证书
	 * @return
	 */
	public String lockCert() {
		String cid = getRequest().getParameter("cid");
		String type = getRequest().getParameter("type");
		if (StringUtils.isEmpty(cid) || StringUtils.isEmpty(cid)) {
			message = "证书不明确！";
			return ERROR;
		}
		//冻结为 1为正常 3冻结, 2为注销
		String updateHql = "update un_cert_award set status=3 where id="+cid;
		this.unCertAwardService.UpdateWithSql(updateHql);
		
		updateHql = "select u.id from un_cert u,un_cert_award ua where ua.batch=u.batch and ua.id="+cid;
		List<Object> object = (List<Object>) this.unCertAwardService.findBySql(updateHql);
		
		if (null!=object && object.size()>0) {
			String id = object.get(0).toString();
			updateHql = "update un_cert set status=3 where id="+id;
			this.unCertService.UpdateWithSql(updateHql);
		}
		
		if (type==null || type.equals("")) {
			return "mobile";
		}
		
		return type;
	}
	
	/**
	 * 解锁证书
	 * @return
	 */
	public String unlockCert() {
		String cid = getRequest().getParameter("cid");
		String type = getRequest().getParameter("type");
		if (StringUtils.isEmpty(cid) || StringUtils.isEmpty(cid)) {
			message = "证书不明确！";
			return ERROR;
		}
		//冻结为 1为正常 3冻结, 2为注销
		//更新证书颁发表
		String updateHql = "update un_cert_award set status=1 where id="+cid;
		this.unCertService.UpdateWithSql(updateHql);
		//更新证书实体表
		updateHql = "select u.id from un_cert u,un_cert_award ua where ua.batch=u.batch and ua.id="+cid;
		List<Object> object = (List<Object>) this.unCertAwardService.findBySql(updateHql);
		
		if (null!=object && object.size()>0) {
			String id = object.get(0).toString();
			logger.info("id-------------------->"+id);
			updateHql = "update un_cert set status=1 where id="+id;
			this.unCertService.UpdateWithSql(updateHql);
		}
		
		if (type==null || type.equals("")) {
			return "mobile";
		}
		return type;
		
	}
	
	/**
	 * 服务器证书申请页面跳转
	 * @return
	 */
	public String serverCertApp()
	{
		return SUCCESS;
	}
	
	/*public String signServerCertApp()
	{
		HttpServletRequest request = this.getRequest();
		String CN = request.getParameter("IP");//名称
		String C = request.getParameter("C");//国家
		String O = request.getParameter("O");//部门
		String OU = request.getParameter("OU");//公司
		String ST = request.getParameter("ST");//省份
		String L = request.getParameter("L");//城市
		String signAlgorithm = "SHA1WithRSA";// 签名算法
		String DUETIME = request.getParameter("dueTime");// 证书期限


		String hql = "from UnCertApp where certType=2 and ip is not null";
		List<UnCertApp> apps = unCertAppService.findAll(hql);
		HashMap<String, String> appItems = new HashMap<String, String>();
		if (null!=apps && apps.size() > 0) {
			for (int i = 0; i < apps.size(); i++) {
				UnCertApp app = apps.get(i);
				if(CN.equals(app.getIp()))
				{
					message = "您好，系统中已经存在服务器IP为："+CN;
					return ERROR;
				}
			}
		}
		UnCertApp ca = new UnCertApp();		
		Date now = new Date();

		ca.setC(C);
		ca.setCn(CN);
		ca.setL(L);
		ca.setSt(ST);
		ca.setO(O);
		ca.setOu(OU);
		ca.setCreatedate(now);
		ca.setIsSign(0);
		ca.setSignAlgorithm(signAlgorithm);
		ca.setBatch(System.nanoTime() + "");
		ca.setCertType(UnCertApp.UnCertAppStatus.CertType.child);
		if (DUETIME != null && !"".equals(DUETIME)) {
			try {
				ca.setDueTime(Integer.parseInt(DUETIME));
			} catch (Exception e) {
				ca.setDueTime(1);
			}
		}

		ca.setStatus(UnCertApp.UnCertAppStatus.Status.checkAppove);

		User user = (User) this.getRequest().getSession().getAttribute("user");
		ca.setUserid(user.getId());// 保存申请用户
		ca.setCreateTime(now);
		ca.setCreateOperator(user.getId());
		ca.setCreateOperators(user.getUsername());
		try {
			certAdministrationService.signCert(ca, user, null, "",true);
			unCertAppService.saveOrUpdate(ca);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}*/


	public String signServerCertApp()
    {
        HttpServletRequest request = this.getRequest();
        String CN = request.getParameter("IP");//名称
        String C = request.getParameter("C");//国家
        String O = request.getParameter("O");//部门
        String OU = request.getParameter("OU");//公司
        String ST = request.getParameter("ST");//省份
        String L = request.getParameter("L");//城市
        String keyIndex = request.getParameter("keyIndex");
        String signAlgorithm = "SHA1WithRSA";// 签名算法
        String DUETIME = request.getParameter("dueTime");// 证书期限


        String hql = "from UnCertApp where certType=2 and ip is not null";
        List<UnCertApp> apps = unCertAppService.findAll(hql);
        HashMap<String, String> appItems = new HashMap<String, String>();
        if (null!=apps && apps.size() > 0) {
            for (int i = 0; i < apps.size(); i++) {
                UnCertApp app = apps.get(i);
                if(CN.equals(app.getIp()))
                {
                    message = "您好，系统中已经存在服务器IP为："+CN;
                    return ERROR;
                }
            }
        }
        UnCertApp ca = new UnCertApp();     
        Date now = new Date();

        ca.setIp(CN);
        ca.setC(C);
        ca.setCn(CN);
        ca.setL(L);
        ca.setSt(ST);
        ca.setO(O);
        ca.setOu(OU);
        ca.setCreatedate(now);
        ca.setIsSign(0);
        ca.setSignAlgorithm(signAlgorithm);
        ca.setBatch(System.nanoTime() + "");
        ca.setCertType(UnCertApp.UnCertAppStatus.CertType.child);
        if (DUETIME != null && !"".equals(DUETIME)) {
            try {
                ca.setDueTime(Integer.parseInt(DUETIME));
            } catch (Exception e) {
                ca.setDueTime(1);
            }
        }

        ca.setStatus(UnCertApp.UnCertAppStatus.Status.checkAppove);

        User user = (User) this.getRequest().getSession().getAttribute("user");
        ca.setUserid(user.getId());// 保存申请用户
        ca.setCreateTime(now);
        ca.setCreateOperator(user.getId());
        ca.setCreateOperators(user.getUsername());
        try {
            unCertAppService.saveOrUpdate(ca);
            certAdministrationService.signCert(ca, user, keyIndex, "",true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return SUCCESS;
    }

	public void setUnCertBulkService(UnCertBulkService unCertBulkService) {
		this.unCertBulkService = unCertBulkService;
	}



	public void setUnCertAwardService(UnCertAwardService unCertAwardService) {
		this.unCertAwardService = unCertAwardService;
	}



	public void setUnCertService(UnCertService unCertService) {
		this.unCertService = unCertService;
	}



	public void setUnCertRevokeAppService(
			UnCertRevokeAppService unCertRevokeAppService) {
		this.unCertRevokeAppService = unCertRevokeAppService;
	}



	public void setUnCertCRLService(UnCertCRLService unCertCRLService) {
		this.unCertCRLService = unCertCRLService;
	}



	public void setUnCertAppService(UnCertAppService unCertAppService) {
		this.unCertAppService = unCertAppService;
	}



	public void setUnCertExportService(UnCertExportService unCertExportService) {
		this.unCertExportService = unCertExportService;
	}
	
}
