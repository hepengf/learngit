package com.congoal.cert.test;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;

/**
 * 验证CRL，检查证书是否被吊销
 * 
 * @author S.J
 * @version 1.0, 11/03/2009
 */
public class ValidateCRLTest {
	/**
	 * crl文件地址
	 */
	private final static String crlPath = "F:/tempWorkSpaces/CCA/WebRoot/certs/crl/crl_20150118.crl";
	/**
	 * 已签发子证书的目录
	 */
	private final static String certPath = "F:/tempWorkSpaces/CCA/WebRoot/certs/new/sub/";
	
	private final static String keyword = "CER";
	
	public static void main(String[] args) throws Exception {

		// 创建CRL对象
		X509CRL crl = loadX509CRL(crlPath);

		// 创建包含可用和被吊销的两个证书对象的数组
		File files = new File(certPath);
		
		if(files.isDirectory())
		{
			FileFilter filter = new FileFilter(){

				@Override
				public boolean accept(File pathname) {
					
					if(pathname.getName().toUpperCase().contains(keyword))
					{
						return true;
					}
					return false;
				}
				
			};
				
			File[] certs = files.listFiles(filter);
			
			int len = certs.length;
			if(len > 0)
			{
				X509Certificate[] certArray = new X509Certificate[len];
				FileInputStream fis = null;
				for(int i = 0; i < len;i++)
				{
					File cert = certs[i];
					try{
						fis = new FileInputStream(cert);
						certArray[i] = getCertificate(fis);
					
					}finally{
						if(fis != null)
						{
							fis.close();
						}
					}
				}
				
				// 验证证书是否被吊销
				for (int i = 0; i < len; i++) {
					System.out.println("证书序列号=" + getSerialNumber(certArray[i]));
					System.out.println("证书DN=" + certArray[i].getSubjectDN());
					
					
					if (crl.isRevoked(certArray[i])) {
						System.out.println("证书被吊销\n");
					} else {
						System.out.println("证书可用\n");
					}
				}
				
				
				
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
	public static X509Certificate getCertificate(InputStream inputStream)
			throws Exception {
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		X509Certificate cert = (X509Certificate) cf.generateCertificate(inputStream);
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
}
