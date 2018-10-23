package com.congoal.cert.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Date;

import com.congoal.cert.req.CertUtil;
import com.congoal.cert.utils.CertCommonUtils;

public class RootInfo {
	public static void main(String[] args) {
		CertUtil certUtil = new CertUtil();
//		// 获取CA的根证书
		X509Certificate root = certUtil.getCertFromKeyStoreFile(
				CertCommonUtils.ROOTCA_PATH + CertCommonUtils.CA_NAME,
				CertCommonUtils.CA_ALIAS, CertCommonUtils.PASSWORD,
				CertCommonUtils.STORE_TYPE_JKS);
//		
		System.out.println("path: "+CertCommonUtils.ROOTCA_PATH + CertCommonUtils.CA_NAME);
		int version = root.getVersion();
		Principal issuerDN = root.getIssuerDN();
		String type = root.getType();
		boolean[] issuerUniqueID = root.getIssuerUniqueID();
		BigInteger serialNumber = root.getSerialNumber();
		Principal subjectDN = root.getSubjectDN();
		String sigAlgName = root.getSigAlgName();
		String sigAlgOID = root.getSigAlgOID();
		Date notAfter = root.getNotAfter();
		Date notBefore = root.getNotBefore();
		byte[] signature = root.getSignature();
		PublicKey publicKey = root.getPublicKey();
		System.out.println("version: "+version);
		System.out.println("issuerDN: "+issuerDN.getName());
		System.out.println("type: "+type);
//		System.out.println("issuerUniqueID: "+issuerUniqueID[0]);
		System.out.println("serialNumber: "+serialNumber);
		System.out.println("subjectDN: "+subjectDN);
		System.out.println("sigAlgName: "+sigAlgName);
		System.out.println("sigAlgOID: "+sigAlgOID);
		System.out.println("notAfter: "+notAfter);
		System.out.println("notBefore: "+notBefore);
		System.out.println("signature: "+CertUtils.bytesToHexString(signature));
		System.out.println("publicKey: "+CertUtils.bytesToHexString(publicKey.getEncoded()));
//		String filename = "/CCA/WebRoot/certs/root/ca.keystore";
		String isdn = subjectDN.getName();
		String cn = isdn.substring(isdn.indexOf("CN=")+3,isdn.indexOf(",", isdn.indexOf("CN=")));
		System.out.println("cn= "+cn);
//		try {
//			FileInputStream in = new FileInputStream(filename);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println(file.exists());
	}
}
