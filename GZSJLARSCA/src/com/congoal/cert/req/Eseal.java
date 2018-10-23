package com.congoal.cert.req;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
@SuppressWarnings("rawtypes")
public class Eseal {
	private String issuerDN;
	private Date esealNotBefore;
	private Date esealNotAfter;
	private int version;
	private byte[] pictureData;

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public byte[] getPictureData() {
		return pictureData;
	}

	public void setPictureData(byte[] pictureData) {
		this.pictureData = pictureData;
	}

	private BigInteger signCertSn;

	public BigInteger getSignCertSn() {
		return signCertSn;
	}

	private int pictureType;

	public int getPictureType() {
		return pictureType;
	}

	public void setPictureType(int pictureType) {
		this.pictureType = pictureType;
	}

	public byte[] getHashedSignCert() {
		return hashedSignCert;
	}

	public void setHashedSignCert(byte[] hashedSignCert) {
		this.hashedSignCert = hashedSignCert;
	}

	private byte[] hashedSignCert;

	public void setSignCertSn(BigInteger signCertSn) {
		this.signCertSn = signCertSn;
	}

	private BigInteger esealSn;
	private String esealName;
	private String signCertSubject;
	private String signCertIssuer;

	private ArrayList userAttr;
	private ArrayList esealAttr;

	public ArrayList getUserAttr() {
		return userAttr;
	}

	public void setUserAttr(ArrayList userAttr) {
		this.userAttr = userAttr;
	}

	public ArrayList getEsealAttr() {
		return esealAttr;
	}

	public void setEsealAttr(ArrayList esealAttr) {
		this.esealAttr = esealAttr;
	}

	public String getSignCertIssuer() {
		return signCertIssuer;
	}

	public void setSignCertIssuer(String signCertIssuer) {
		this.signCertIssuer = signCertIssuer;
	}

	public String getSignCertSubject() {
		return signCertSubject;
	}

	public void setSignCertSubject(String signCertSubject) {
		this.signCertSubject = signCertSubject;
	}

	public String getEsealName() {
		return esealName;
	}

	public void setEsealName(String esealName) {
		this.esealName = esealName;
	}

	public BigInteger getEsealSn() {
		return esealSn;
	}

	public void setEsealSn(BigInteger esealSn) {
		this.esealSn = esealSn;
	}

	public Date getEsealNotBefore() {
		return esealNotBefore;
	}

	public void setEsealNotBefore(Date esealNotBefore) {
		this.esealNotBefore = esealNotBefore;
	}

	public Date getEsealNotAfter() {
		return esealNotAfter;
	}

	public void setEsealNotAfter(Date esealNotAfter) {
		this.esealNotAfter = esealNotAfter;
	}

	public String getIssuerDN() {
		return issuerDN;
	}

	public void setIssuerDN(String issuerDN) {
		this.issuerDN = issuerDN;
	}

}
