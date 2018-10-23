package com.congoal.cert.req;

import java.util.ArrayList;
import java.util.Date;

/**
 * 签章申请信息 EsealInfo.java
 * 
 * @CopyRight KOAL Co. Lmt 2009
 * @author Administrator
 * @Since
 * @version
 * @Date: 2009-7-22
 */
@SuppressWarnings("rawtypes")
public class EsealInfo {
	// 1 eseal basic info
	private int version = 1; // must 0 for v1.0; 1 for v1.0.1
	private String esealName; // must
	private Date esealNotBefore; // must
	private Date esealNotAfter; // must

	// 2 sign cert info
	private byte[] derSignCert; // must
	
	// 3 picture info
	private int pictureType = 1; // must
	private byte[] pictureData; // must

	// 4 eseal extended info
	
	private ArrayList userAttr = new ArrayList(); // optional
	private ArrayList esealAttr = new ArrayList();// optional

	/**
	 * @return the derSignCert
	 */
	public byte[] getDerSignCert() {
		return derSignCert;
	}

	/**
	 * @param derSignCert
	 *            the derSignCert to set
	 */
	public void setDerSignCert(byte[] derSignCert) {
		this.derSignCert = derSignCert;
	}

	/**
	 * @return the version
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(int version) {
		this.version = version;
	}

	/**
	 * @return the esealName
	 */
	public String getEsealName() {
		return esealName;
	}

	/**
	 * @param esealName
	 *            the esealName to set
	 */
	public void setEsealName(String esealName) {
		this.esealName = esealName;
	}

	/**
	 * @return the esealNotBefore
	 */
	public Date getEsealNotBefore() {
		return esealNotBefore;
	}

	/**
	 * @param esealNotBefore
	 *            the esealNotBefore to set
	 */
	public void setEsealNotBefore(Date esealNotBefore) {
		this.esealNotBefore = esealNotBefore;
	}

	/**
	 * @return the esealNotAfter
	 */
	public Date getEsealNotAfter() {
		return esealNotAfter;
	}

	/**
	 * @param esealNotAfter
	 *            the esealNotAfter to set
	 */
	public void setEsealNotAfter(Date esealNotAfter) {
		this.esealNotAfter = esealNotAfter;
	}

	/**
	 * @return the pictureType
	 */
	public int getPictureType() {
		return pictureType;
	}

	/**
	 * @param pictureType
	 *            the pictureType to set
	 */
	public void setPictureType(int pictureType) {
		this.pictureType = pictureType;
	}

	/**
	 * @return the pictureData
	 */
	public byte[] getPictureData() {
		return pictureData;
	}

	/**
	 * @param pictureData
	 *            the pictureData to set
	 */
	public void setPictureData(byte[] pictureData) {
		this.pictureData = pictureData;
	}

	/**
	 * @return the userAttr
	 */
	public ArrayList getUserAttr() {
		return userAttr;
	}

	/**
	 * @param userAttr
	 *            the userAttr to set
	 */
	public void setUserAttr(ArrayList userAttr) {
		this.userAttr = userAttr;
	}

	/**
	 * @return the esealAttr
	 */
	public ArrayList getEsealAttr() {
		return esealAttr;
	}

	/**
	 * @param esealAttr
	 *            the esealAttr to set
	 */
	public void setEsealAttr(ArrayList esealAttr) {
		this.esealAttr = esealAttr;
	}

	protected String attrListToString(ArrayList attrList) {
		if (null == attrList || 0 == attrList.size())
			return "";

		StringBuffer sbuf = new StringBuffer();

		for (int i = 0; i < attrList.size(); i++) {
			String s = (String) attrList.get(i);
			sbuf.append(s);
			if (i != attrList.size() - 1)
				sbuf.append(",");
		}
		return sbuf.toString();
	}

}
