package com.congoal.cert.utils;

import java.io.FileInputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLEntry;
import java.security.cert.X509Certificate;

/**
 * 验证CRL，检查证书是否被吊销
 * 
 * @author S.J
 * @version 1.0, 11/03/2009
 */
public class ValidateCRL {
	public static void main(String[] args) throws Exception {
		String packagePath = "com/example/certificate/crl/";

		// 创建CRL对象
		String crlPath = packagePath + "1.crl";
		X509CRL crl = loadX509CRL(crlPath);

		// 创建包含可用和被吊销的两个证书对象的数组
		String availCertPath = packagePath + "wanghongjun.cer";
		String unAvailCertPath = packagePath + "liuheng.cer";
		X509Certificate[] certArray = new X509Certificate[2];
		X509Certificate cert = null;
		certArray[0] = getCertificate(availCertPath);
		certArray[1] = getCertificate(unAvailCertPath);

		// 验证证书是否被吊销
		for (int i = 0; i < certArray.length; i++) {
			System.out.println("证书序列号=" + getSerialNumber(certArray[i]));
			System.out.println("证书DN=" + certArray[i].getSubjectDN());
			if (crl.isRevoked(certArray[i])) {
				System.out.println("证书被吊销\n");
			} else {
				System.out.println("证书可用\n");
			}
		}
	}

	/**
	 * 加载CRL证书吊销列表文件
	 * 
	 * @param crlFilePath
	 * @return
	 * @throws Exception
	 */
	public static X509CRL loadX509CRL(String crlFilePath) throws Exception {
		FileInputStream in = new FileInputStream(crlFilePath);
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		X509CRL crl = (X509CRL) cf.generateCRL(in);
		in.close();
		return crl;
	}

	/**
	 * 加载证书文件
	 * 
	 * @param certFilePath
	 * @return
	 * @throws Exception
	 */
	public static X509Certificate getCertificate(String certFilePath)
			throws Exception {
		FileInputStream in = new FileInputStream(certFilePath);
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		X509Certificate cert = (X509Certificate) cf.generateCertificate(in);
		return cert;
	}

	/**
	 * 读取证书序列号
	 * 
	 * @param cert
	 * @return
	 */
	public static String getSerialNumber(X509Certificate cert) {
		if (null == cert)
			return null;
		byte[] serial = cert.getSerialNumber().toByteArray();
		if (serial.length > 0) {
			String serialNumberString = new String();
			for (int i = 0; i < serial.length; i++) {
				String s = Integer.toHexString(Byte.valueOf(serial[i])
						.intValue());
				if (s.length() == 8)
					s = s.substring(6);
				else if (1 == s.length())
					s = "0" + s;
				serialNumberString += s + " ";
			}
			return serialNumberString;
		}
		return null;
	}

	/**
	 * 格式化证书序列号
	 * 
	 * @param entry
	 * @return
	 */
	public static String getSerialNumber(X509CRLEntry entry) {
		if (entry != null) {
			byte[] serial = entry.getSerialNumber().toByteArray();
			if (serial.length > 0) {
				String serialNumberString = new String();
				for (int i = 0; i < serial.length; i++) {
					String s = Integer.toHexString(Byte.valueOf(serial[i])
							.intValue());
					if (s.length() == 8) {
						s = s.substring(6);
					} else if (1 == s.length()) {
						s = "0" + s;
					}
					serialNumberString += s + " ";
				}
				return serialNumberString;
			}
		}
		return null;
	}
}
