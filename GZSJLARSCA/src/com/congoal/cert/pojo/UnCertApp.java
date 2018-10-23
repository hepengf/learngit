package com.congoal.cert.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * 证书申请对象
 * 
 * @author charles
 * 
 */
@SuppressWarnings("all")
@Entity
@Table(name = "UN_CERT_APP")
public class UnCertApp implements java.io.Serializable {

	public static class UnCertAppStatus {
		/**
		 * 是否需要创建新密钥对
		 * 
		 */
		public static class NewKeyPair {
			/**
			 * 是
			 */
			public static final int yes = 1;
			/**
			 * 否
			 */
			public static final int no = 0;
		}

		public static class CertType {
			/**
			 * 根证书
			 */
			public static final int root = 1;
			/**
			 * 子证书
			 */
			public static final int child = 2;
		}

		public static class Status {
			/**
			 * 待审核
			 */
			public static final int uncheck = 0;
			/**
			 * 审核通过
			 */
			public static final int checkAppove = 1;
			/**
			 * 审核失败
			 */
			public static final int checkDeny = 2;
		}
	}

	// Fields
	@Id
	@GeneratedValue(generator = "UnCertAppGenerator")
	@GenericGenerator(name = "UnCertAppGenerator", strategy = "native")
	private Integer id;
	
	private String ip;
	private String deviceId;//终端设备表面号，服务器没有
	private String c;//如果是服务器则为ip
	private String cn;
	private String l;
	private String st;
	private String o;
	private String ou;
	private String net;
	private String email;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdate;
	private Integer isSign;
	private Integer signOperator;
	private String signOperators;
	@Temporal(TemporalType.TIMESTAMP)
	private Date signdate;
	private Integer userid;
	private String username;
	private String batch;
	private Integer dueTime;
	private String effect;
	private String remark;
	private Integer keySize;
	private String publickeyHex;
	private Integer status;
	private Integer checkOperator;
	private String checkOperators;
	@Temporal(TemporalType.TIMESTAMP)
	private Date checkdate;
	private String checkResult;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	private Integer createOperator;
	private String createOperators;
	private String company;
	
	
	@Column(name="ip")
	public String getIp() {
		return ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	@Column(name="deviceId")
	public String getDeviceId() {
		return deviceId;
	}
	
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	@Column(name = "createTime")
	public Date getCreateTime() {
		return createTime;
	}
	
	public void setCompany(String company) {
		this.company = company;
	}
	
	@Column(name = "company")
	public String getCompany() {
		return company;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column(name = "createOperator")
	public Integer getCreateOperator() {
		return createOperator;
	}

	public void setCreateOperator(Integer createOperator) {
		this.createOperator = createOperator;
	}

	@Column(name = "checkOperator")
	public Integer getCheckOperator() {
		return checkOperator;
	}

	public void setCheckOperator(Integer checkOperator) {
		this.checkOperator = checkOperator;
	}

	@Column(name = "checkdate")
	public Date getCheckdate() {
		return checkdate;
	}

	public void setCheckdate(Date checkdate) {
		this.checkdate = checkdate;
	}

	@Column(name = "checkResult")
	public String getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}

	@Column(name = "publickeyHex")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 是否需要生成新密钥对 0-否 1-是
	 */
	private Integer newKeyPair;

	/**
	 * 私钥密码
	 */
	private String secret;

	/**
	 * 证书类型 1-根证书 2-子证书
	 */
	private Integer certType;

	/**
	 * 导入的私钥
	 */
	private String privateKeyHex;

	/**
	 * 导入的文件名
	 */
	private String importName;

	/**
	 * 签名算法
	 */
	private String signAlgorithm;

	@Column(name = "publickeyHex")
	public String getPublickeyHex() {
		return publickeyHex;
	}

	public void setPublickeyHex(String publickeyHex) {
		this.publickeyHex = publickeyHex;
	}

	@Column(name = "keySize")
	public Integer getKeySize() {
		return keySize;
	}

	public void setKeySize(Integer keySize) {
		this.keySize = keySize;
	}

	@Column(name = "signAlgorithm")
	public String getSignAlgorithm() {
		return signAlgorithm;
	}

	public void setSignAlgorithm(String signAlgorithm) {
		this.signAlgorithm = signAlgorithm;
	}

	@Column(name = "privateKeyHex")
	public String getPrivateKeyHex() {
		return privateKeyHex;
	}

	public void setPrivateKeyHex(String privateKeyHex) {
		this.privateKeyHex = privateKeyHex;
	}

	@Column(name = "importName")
	public String getImportName() {
		return importName;
	}

	public void setImportName(String importName) {
		this.importName = importName;
	}

	@Column(name = "dueTime")
	public Integer getDueTime() {
		return dueTime;
	}

	public void setDueTime(Integer dueTime) {
		this.dueTime = dueTime;
	}

	@Column(name = "effect")
	public String getEffect() {
		return effect;
	}

	public void setEffect(String effect) {
		this.effect = effect;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "batch")
	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	@Column(name = "userid")
	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	@Column(name = "signdate")
	public Date getSigndate() {
		return signdate;
	}

	public void setSigndate(Date signdate) {
		this.signdate = signdate;
	}

	@Column(name = "createdate")
	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	@Column(name = "issign")
	public Integer getIsSign() {
		return isSign;
	}

	public void setIsSign(Integer isSign) {
		this.isSign = isSign;
	}

	@Column(name = "signoperator")
	public Integer getSignOperator() {
		return signOperator;
	}

	public void setSignOperator(Integer signOperator) {
		this.signOperator = signOperator;
	}

	@Column(name = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "c")
	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	@Column(name = "cn")
	public String getCn() {
		return cn;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}

	@Column(name = "l")
	public String getL() {
		return l;
	}

	public void setL(String l) {
		this.l = l;
	}

	@Column(name = "st")
	public String getSt() {
		return st;
	}

	public void setSt(String st) {
		this.st = st;
	}

	@Column(name = "o")
	public String getO() {
		return o;
	}

	public void setO(String o) {
		this.o = o;
	}

	@Column(name = "ou")
	public String getOu() {
		return ou;
	}

	public void setOu(String ou) {
		this.ou = ou;
	}

	@Column(name = "net")
	public String getNet() {
		return net;
	}

	public void setNet(String net) {
		this.net = net;
	}

	@Column(name = "newKeyPair")
	public Integer getNewKeyPair() {
		return newKeyPair;
	}

	public void setNewKeyPair(Integer newKeyPair) {
		this.newKeyPair = newKeyPair;
	}

	@Column(name = "secret")
	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	@Column(name = "certType")
	public Integer getCertType() {
		return certType;
	}

	public void setCertType(Integer certType) {
		this.certType = certType;
	}

	@Column(name = "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "signOperators")
	public String getSignOperators() {
		return signOperators;
	}

	public void setSignOperators(String signOperators) {
		this.signOperators = signOperators;
	}
	
	@Column(name = "checkOperators")
	public String getCheckOperators() {
		return checkOperators;
	}

	public void setCheckOperators(String checkOperators) {
		this.checkOperators = checkOperators;
	}

	@Column(name = "createOperators")
	public String getCreateOperators() {
		return createOperators;
	}

	public void setCreateOperators(String createOperators) {
		this.createOperators = createOperators;
	}

	@Column(name = "username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public UnCertApp() {
	}

	public UnCertApp(Integer id, String c, String cn, String l, String st,
			String o, String ou, String net, String email, Date createdate,
			Integer isSign, Integer signOperator, Date signdate, Integer userid,
			String batch, Integer dueTime, String effect, String remark,
			Integer keySize, String publickeyHex, Integer status,
			Integer checkOperator, Date checkdate, String checkResult,
			Date createTime, Integer createOperator, Integer newKeyPair,
			String secret, Integer certType, String privateKeyHex,
			String importName, String signAlgorithm, String deviceId, String ip) {
		super();
		this.id = id;
		this.c = c;
		this.cn = cn;
		this.l = l;
		this.st = st;
		this.o = o;
		this.ou = ou;
		this.net = net;
		this.email = email;
		this.deviceId = deviceId;
		this.ip = ip;
		this.createdate = createdate;
		this.isSign = isSign;
		this.signOperator = signOperator;
		this.signdate = signdate;
		this.userid = userid;
		this.batch = batch;
		this.dueTime = dueTime;
		this.effect = effect;
		this.remark = remark;
		this.keySize = keySize;
		this.publickeyHex = publickeyHex;
		this.status = status;
		this.checkOperator = checkOperator;
		this.checkdate = checkdate;
		this.checkResult = checkResult;
		this.createTime = createTime;
		this.createOperator = createOperator;
		this.newKeyPair = newKeyPair;
		this.secret = secret;
		this.certType = certType;
		this.privateKeyHex = privateKeyHex;
		this.importName = importName;
		this.signAlgorithm = signAlgorithm;
	}


}